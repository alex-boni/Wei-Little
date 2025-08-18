package Negocio.ClienteJPA;

import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.EntityManager;
import javax.persistence.LockModeType;
import javax.persistence.TypedQuery;

import Integracion.EMFSingleton.EMFSingleton;
import Negocio.AlquilerJPA.Alquiler;
import Negocio.AlquilerJPA.LineaAlquiler;

public class SAClienteImp implements SACliente {

	synchronized public int alta_cliente(TCliente tCliente) {

		int res = -1;

		EntityManager entityManager = EMFSingleton.getInstancia().getEntityManagerFactory().createEntityManager();
		try {

			entityManager.getTransaction().begin();

			TypedQuery<Cliente> clienteByDNI = entityManager.createNamedQuery("Negocio.ClienteJPA.Cliente.findBydni",
					Cliente.class);
			clienteByDNI.setParameter("dni", tCliente.get_dni());

			Cliente cliente = null;

			if (clienteByDNI.getResultList().isEmpty()) {

				cliente = new Cliente(tCliente);
				entityManager.persist(cliente);
				entityManager.getTransaction().commit();
				res = cliente.get_id_cliente();
			} else {

				// Solo da de alta si el cliente dado de baja tiene el mismo DNI
				cliente = clienteByDNI.getSingleResult();
				if (cliente.get_activo() == 0) {
					cliente.set_activo(1);
					cliente.set_nombre(tCliente.get_nombre());
					entityManager.getTransaction().commit();
					res = cliente.get_id_cliente();
				} else
					entityManager.getTransaction().rollback();
			}
		} catch (Exception e) {
			e.printStackTrace();
			entityManager.getTransaction().rollback();
		}
		entityManager.close();

		return res;
	}

	public int baja_cliente(int id) {

		int res = -1;

		EntityManager entityManager = EMFSingleton.getInstancia().getEntityManagerFactory().createEntityManager();
		try {
			entityManager.getTransaction().begin();

			Cliente cliente = entityManager.find(Cliente.class, id);

			if (cliente != null && cliente.get_activo() == 1) {

				Set<Alquiler> lista_Alquileres_Cliente = cliente.get_lista_factura();

				// Compruebo que el cliente no tenga ninun alquiler activo, mirando la linea de
				// alquiler para ver que este todo devuelto
				boolean noDevueltos = false;
				for (Alquiler a : lista_Alquileres_Cliente) {
					Set<LineaAlquiler> lista_Lineas_Alquiler_Cliente = a.get_lineas_alquiler();
					for (LineaAlquiler la : lista_Lineas_Alquiler_Cliente) {
						if (la.get_devuelto() == 0) {
							noDevueltos = true;
							break;
						}
					}
					if (noDevueltos)
						break;
				}

				if (lista_Alquileres_Cliente.isEmpty() || !noDevueltos) {
					cliente.set_activo(0);
					entityManager.getTransaction().commit();
					res = cliente.get_id_cliente();
				} else
					entityManager.getTransaction().rollback();

			} else
				entityManager.getTransaction().rollback();

		} catch (Exception e) {
			e.printStackTrace();
			entityManager.getTransaction().rollback();
		}
		entityManager.close();

		return res;
	}

	public int modificar_cliente(TCliente tCliente) {

		int res = -1;

		EntityManager entityManager = EMFSingleton.getInstancia().getEntityManagerFactory().createEntityManager();
		try {

			entityManager.getTransaction().begin();

			Cliente clienteByID = entityManager.find(Cliente.class, tCliente.get_id_cliente());
			TypedQuery<Cliente> clienteByDNI = entityManager.createNamedQuery("Negocio.ClienteJPA.Cliente.findBydni",
					Cliente.class);
			clienteByDNI.setParameter("dni", tCliente.get_dni());

			List<Cliente> listaClientesByDNI = clienteByDNI.getResultList();

			if (clienteByID != null && clienteByID.get_activo() == 1) {

				// No puede modificar si el DNI es el mismo que el guardado en la
				// base de datos
				if (listaClientesByDNI.isEmpty() || (listaClientesByDNI.size() == 1
						&& listaClientesByDNI.get(0).get_id_cliente() == tCliente.get_id_cliente())) {
					clienteByID.set_dni(tCliente.get_dni());
					clienteByID.set_nombre(tCliente.get_nombre());

					entityManager.getTransaction().commit();

					res = clienteByID.get_id_cliente();
				} else
					entityManager.getTransaction().rollback();
			} else
				entityManager.getTransaction().rollback();

		} catch (Exception e) {
			e.printStackTrace();
			entityManager.getTransaction().rollback();
		}
		entityManager.close();

		return res;
	}

	public TCliente buscar_cliente(int id) {

		TCliente tCliente = null;

		EntityManager entityManager = EMFSingleton.getInstancia().getEntityManagerFactory().createEntityManager();
		try {
			entityManager.getTransaction().begin();
			// Bloqueas el Cliente ya que no quieres mostrar desactualizados por si otra
			// hebra ha intentado modificar el cliente
			Cliente clienteByID = entityManager.find(Cliente.class, id, LockModeType.OPTIMISTIC);

			if (clienteByID != null) {
				tCliente = clienteByID.toTransfer();
				entityManager.getTransaction().commit();
			} else
				entityManager.getTransaction().rollback();

		} catch (Exception e) {
			e.printStackTrace();
			entityManager.getTransaction().rollback();
		}
		entityManager.close();

		return tCliente;
	}

	public Set<TCliente> listar_clientes() {

		Set<TCliente> lista = null;

		EntityManager entityManager = EMFSingleton.getInstancia().getEntityManagerFactory().createEntityManager();
		try {

			entityManager.getTransaction().begin();

			lista = new LinkedHashSet<TCliente>();

			TypedQuery<Cliente> readAllCliente = entityManager.createQuery("SELECT c FROM Cliente c", Cliente.class);
			List<Cliente> resultado = readAllCliente.getResultList();
			Iterator<Cliente> it = resultado.iterator();

			while (it.hasNext()) {
				Cliente cliente = it.next();
				// Bloqueas optimista cliente ya que no quieres mostrar datos que hayan sido
				// modificados por otra hebra comprobancdo el numero de version en tiempo de
				// commit
				entityManager.lock(cliente, LockModeType.OPTIMISTIC);
				lista.add(cliente.toTransfer());
			}

			entityManager.getTransaction().commit();

		} catch (Exception e) {
			e.printStackTrace();
			entityManager.getTransaction().rollback();
		}
		entityManager.close();

		return lista;
	}
}
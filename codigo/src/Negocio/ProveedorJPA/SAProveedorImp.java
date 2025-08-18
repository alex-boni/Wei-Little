/**
 * 
 */
package Negocio.ProveedorJPA;

import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.EntityManager;
import javax.persistence.LockModeType;
import javax.persistence.TypedQuery;

import Integracion.EMFSingleton.EMFSingleton;
import Negocio.ModeloJPA.Modelo;
import Negocio.ModeloJPA.VinculacionModeloProveedor;

public class SAProveedorImp implements SAProveedor {

	public synchronized int alta_proveedor(TProveedor tProveedor) {

		int res = -1;
		EntityManager em = EMFSingleton.getInstancia().getEntityManagerFactory().createEntityManager();

		try {

			em.getTransaction().begin();

			List<Proveedor> proveedoresList = em
					.createNamedQuery("Negocio.ProveedorJPA.Proveedor.findByCIF", Proveedor.class)
					.setParameter("cif", tProveedor.get_CIF()).getResultList();

			Proveedor resultado = proveedoresList.isEmpty() ? null : proveedoresList.get(0);

			if (resultado == null) {
				// No existe proveedor con el CIF, lo persistimos como nuevo
				Proveedor nuevoProveedor = new Proveedor(tProveedor);
				em.persist(nuevoProveedor);
				em.getTransaction().commit();
				res = nuevoProveedor.get_id_proveedor();

			} else if (resultado.get_activo() == 0) {
				// Si estaba inactivo, lo activamos
				resultado.set_activo(1);
				resultado.set_nombre(tProveedor.get_nombre());
				em.getTransaction().commit();
				res = resultado.get_id_proveedor();

			} else {
				// Si ya estaba activo, no hacemos nada
				em.getTransaction().rollback();
			}
		} catch (Exception e) {
			e.printStackTrace();
			em.getTransaction().rollback();
		} finally {
			em.close();
		}

		return res;
	}

	public int baja_proveedor(int id_proveedor) {

		int res = -1;
		EntityManager em = EMFSingleton.getInstancia().getEntityManagerFactory().createEntityManager();

		try {

			em.getTransaction().begin();

			Proveedor proveedor = em.find(Proveedor.class, id_proveedor);

			if (proveedor != null && proveedor.get_activo() == 1) {
				// Obtenemos si proveedor tiene modelos vinculados
				// Todos los modelos están inactivos: dar de baja al proveedor

				Set<VinculacionModeloProveedor> vinculaciones = proveedor.get_vinculaciones();

				if (vinculaciones.isEmpty()) {
					proveedor.set_activo(0);
					em.getTransaction().commit();
					res = proveedor.get_id_proveedor();
				} else
					em.getTransaction().rollback();
			} else
				em.getTransaction().rollback();

		} catch (Exception e) {
			e.printStackTrace();
			em.getTransaction().rollback();
		} finally {
			em.close();
		}
		return res;
	}

	public int modificar_proveedor(TProveedor tProveedor) {

		int res = -1;
		EntityManager em = EMFSingleton.getInstancia().getEntityManagerFactory().createEntityManager();

		try {

			// Iniciar la transacción
			em.getTransaction().begin();

			// Buscar el proveedor existente por su id
			Proveedor proveedorExistente = em.find(Proveedor.class, tProveedor.get_id_proveedor());

			if (proveedorExistente != null && proveedorExistente.get_activo() == 1) {

				List<Proveedor> proveedorByCIF = em
						.createNamedQuery("Negocio.ProveedorJPA.Proveedor.findByCIF", Proveedor.class)
						.setParameter("cif", tProveedor.get_CIF()).getResultList();

				if (proveedorByCIF.isEmpty() || (proveedorByCIF.size() == 1
						&& proveedorByCIF.get(0).get_id_proveedor() == tProveedor.get_id_proveedor())) {

					// Modificar el proveedor
					proveedorExistente.set_nombre(tProveedor.get_nombre());
					proveedorExistente.set_CIF(tProveedor.get_CIF());

					em.getTransaction().commit();

					res = proveedorExistente.get_id_proveedor();

				} else {
					em.getTransaction().rollback();
				}
			} else
				em.getTransaction().rollback();
		} catch (Exception e) {
			e.printStackTrace();
			em.getTransaction().rollback();
		} finally {
			em.close();
		}

		return res;
	}

	public TProveedor buscar_proveedor(int id_proveedor) {
		TProveedor proveedor = null;
		EntityManager em = EMFSingleton.getInstancia().getEntityManagerFactory().createEntityManager();

		try {

			// Iniciar la transacción
			em.getTransaction().begin();

			// Bloqueas Proveedor porque no quieres mostrar informacion desactualizada por
			// si otra hebra ha modificado el proveedor
			Proveedor proveedorByID = em.find(Proveedor.class, id_proveedor, LockModeType.OPTIMISTIC);

			if (proveedorByID != null) {
				proveedor = proveedorByID.toTransfer();
				em.getTransaction().commit();
			} else
				em.getTransaction().rollback();

		} catch (Exception e) {
			e.printStackTrace();
			em.getTransaction().rollback();
		} finally {
			em.close();
		}

		return proveedor;
	}

	public Set<TProveedor> listar_proveedores() {

		EntityManager em = EMFSingleton.getInstancia().getEntityManagerFactory().createEntityManager();

		Set<TProveedor> listaProveedores = null;

		try {

			// Iniciar la transacción
			em.getTransaction().begin();

			TypedQuery<Proveedor> query = em.createNamedQuery("Negocio.ProveedorJPA.Proveedor.findAll",
					Proveedor.class);
			// desactualizada

			if (!query.getResultList().isEmpty()) {

				listaProveedores = new LinkedHashSet<TProveedor>();

				for (Proveedor p : query.getResultList()) {
					// Hacemos un bloqueo optimista por si otra hebra intenta modificar proveedor p
					// combrobandolo en tiempo de commit
					em.lock(p, LockModeType.OPTIMISTIC);
					listaProveedores.add(p.toTransfer());
				}

				em.getTransaction().commit();
			} else
				em.getTransaction().rollback();

		} catch (Exception e) {
			e.printStackTrace();
			em.getTransaction().rollback();
		} finally {
			em.close();
		}

		return listaProveedores;
	}

	public Set<TProveedor> listar_proveedores_por_modelo(int id_modelo) {

		EntityManager em = EMFSingleton.getInstancia().getEntityManagerFactory().createEntityManager();

		Set<TProveedor> listaProveedores = null;

		try {

			// Iniciar la transacción
			em.getTransaction().begin();

			// Hacemos un bloqueo optimista por si otra hebra vincula o desvincula un
			// proveedor del modelo
			Modelo modelo = em.find(Modelo.class, id_modelo, LockModeType.OPTIMISTIC);

			if (modelo != null) {

				listaProveedores = new LinkedHashSet<TProveedor>();
				// Filtrar los proveedores cuya vinculación con el modelo esté activa
				for (VinculacionModeloProveedor proveedor : modelo.get_vinculaciones()) {
					// OPTIMISTA porque otra hebra puede modificar los datos de este proveedor
					em.lock(proveedor, LockModeType.OPTIMISTIC);
					listaProveedores.add(proveedor.get_proveedor().toTransfer());
				}
				em.getTransaction().commit();
			} else
				em.getTransaction().rollback();

		} catch (Exception e) {
			e.printStackTrace();
			em.getTransaction().rollback();
		} finally {
			em.close();
		}

		return listaProveedores;

	}
}
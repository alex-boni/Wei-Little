package Negocio.EmpleadoJPA;

import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.EntityManager;
import javax.persistence.LockModeType;
import javax.persistence.TypedQuery;

import Integracion.EMFSingleton.EMFSingleton;

public class SAEmpleadoImp implements SAEmpleado {

	@Override
	public synchronized int altaEmpleado(TEmpleado tEmpleado) {
		int id = -1;
		EntityManager em = EMFSingleton.getInstancia().getEntityManagerFactory().createEntityManager();
		try {
			em.getTransaction().begin();
			TypedQuery<Empleado> query = em.createNamedQuery("Negocio.EmpleadoJPA.Empleado.findByDNI", Empleado.class);
			query.setParameter("DNI", tEmpleado.get_DNI());

			List<Empleado> empleadoExistente = query.getResultList();

			if (empleadoExistente.isEmpty()) {

				Empleado empleadoNuevo = null;

				if (tEmpleado instanceof TEmpleadoTecnico) {
					TEmpleadoTecnico tecnico = (TEmpleadoTecnico) tEmpleado;
					empleadoNuevo = new EmpleadoTecnico();
					((EmpleadoTecnico) empleadoNuevo).setEspecializacion(tecnico.getEspecializacion());
				} else if (tEmpleado instanceof TEmpleadoDependiente) {
					TEmpleadoDependiente dependiente = (TEmpleadoDependiente) tEmpleado;
					empleadoNuevo = new EmpleadoDependiente();
					((EmpleadoDependiente) empleadoNuevo).setIdioma(dependiente.getIdioma());
				}
				empleadoNuevo.set_DNI(tEmpleado.get_DNI());
				empleadoNuevo.set_nombre(tEmpleado.get_nombre());
				empleadoNuevo.set_salario(tEmpleado.get_salario());
				empleadoNuevo.set_activo(tEmpleado.get_activo());

				em.persist(empleadoNuevo);
				em.getTransaction().commit();

				id = empleadoNuevo.get_id_empleado();

			} else {

				Empleado existente = query.getSingleResult();

				if (existente.get_activo() == 0) {
					if (tEmpleado instanceof TEmpleadoTecnico && existente instanceof EmpleadoTecnico) {
						EmpleadoTecnico tecnico = (EmpleadoTecnico) existente;
						TEmpleadoTecnico t_tecnicoNuevo = (TEmpleadoTecnico) tEmpleado;
						tecnico.set_DNI(t_tecnicoNuevo.get_DNI());
						tecnico.set_nombre(t_tecnicoNuevo.get_nombre());
						tecnico.set_salario(t_tecnicoNuevo.get_salario());
						tecnico.setEspecializacion(t_tecnicoNuevo.getEspecializacion());
						tecnico.set_activo(1);
						em.getTransaction().commit();
						id = tecnico.get_id_empleado();
					} else if (tEmpleado instanceof TEmpleadoDependiente && existente instanceof EmpleadoDependiente) {
						EmpleadoDependiente dependiente = (EmpleadoDependiente) existente;
						TEmpleadoDependiente t_dependienteNuevo = (TEmpleadoDependiente) tEmpleado;
						dependiente.set_DNI(t_dependienteNuevo.get_DNI());
						dependiente.set_nombre(t_dependienteNuevo.get_nombre());
						dependiente.set_salario(t_dependienteNuevo.get_salario());
						dependiente.setIdioma(t_dependienteNuevo.getIdioma());
						dependiente.set_activo(1);
						em.getTransaction().commit();
						id = dependiente.get_id_empleado();
					} else
						em.getTransaction().rollback();
				}

			}
		} catch (Exception e) {
			e.printStackTrace();
			em.getTransaction().rollback();
		} finally {
			em.close();
		}
		return id;
	}

	public int bajaEmpleado(int id_empleado) {
		int id = -1;
		EntityManager em = EMFSingleton.getInstancia().getEntityManagerFactory().createEntityManager();
		try {
			em.getTransaction().begin();

			Empleado empleadoExistente = em.find(Empleado.class, id_empleado);

			if (empleadoExistente != null && empleadoExistente.get_activo() == 1) {

				empleadoExistente.set_activo(0);
				em.getTransaction().commit();
				id = id_empleado;
			} else {
				em.getTransaction().rollback();
			}
		} catch (Exception e) {
			e.printStackTrace();
			em.getTransaction().rollback();
		} finally {
			em.close();
		}
		return id;
	}

	public int modificarEmpleado(TEmpleado empleado) {
		EntityManager em = EMFSingleton.getInstancia().getEntityManagerFactory().createEntityManager();
		int id = -1;
		try {
			em.getTransaction().begin();

			Empleado empleadoExistente = em.find(Empleado.class, empleado.get_id_empleado());

			if (empleadoExistente != null && empleadoExistente.get_activo() == 1
					&& this.checkTipos(empleadoExistente, empleado)) {
				TypedQuery<Empleado> query = em.createNamedQuery("Negocio.EmpleadoJPA.Empleado.findByDNI",
						Empleado.class);
				query.setParameter("DNI", empleado.get_DNI());

				List<Empleado> empleadoDNI = query.getResultList();

				if (empleadoDNI.isEmpty() || query.getSingleResult().get_id_empleado() == empleado.get_id_empleado()) {

					empleadoExistente.set_nombre(empleado.get_nombre());
					empleadoExistente.set_DNI(empleado.get_DNI());
					empleadoExistente.set_salario(empleado.get_salario());

					if (empleadoExistente instanceof EmpleadoTecnico) {
						EmpleadoTecnico tecnicoExistente = (EmpleadoTecnico) empleadoExistente;
						TEmpleadoTecnico tecnico = (TEmpleadoTecnico) empleado;
						tecnicoExistente.setEspecializacion(tecnico.getEspecializacion());
					} else if (empleadoExistente instanceof EmpleadoDependiente) {
						EmpleadoDependiente dependienteExistente = (EmpleadoDependiente) empleadoExistente;
						TEmpleadoDependiente dependiente = (TEmpleadoDependiente) empleado;
						dependienteExistente.setIdioma(dependiente.getIdioma());
					}

					em.getTransaction().commit();
					id = empleadoExistente.get_id_empleado();
				} else {
					em.getTransaction().rollback();
				}
			} else {
				em.getTransaction().rollback();
			}
		} catch (Exception e) {
			e.printStackTrace();
			em.getTransaction().rollback();
		} finally {
			em.close();
		}

		return id;
	}

	public Set<TEmpleado> listarEmpleado() {

		EntityManager em = EMFSingleton.getInstancia().getEntityManagerFactory().createEntityManager();

		Set<TEmpleado> tEmpleados = new LinkedHashSet<TEmpleado>();

		try {
			em.getTransaction().begin();
			TypedQuery<Empleado> query = em.createQuery("SELECT e FROM Empleado e", Empleado.class);

			List<Empleado> empleados = query.getResultList();

			for (Empleado e : empleados) {
				// Bloqueo en optimsta por si hay otra hebra que intenta modificar
				em.lock(e, LockModeType.OPTIMISTIC);
				TEmpleado t = e.toTransfer();
				tEmpleados.add(t);
			}
			em.getTransaction().commit();
		} catch (Exception e) {
			e.printStackTrace();
			em.getTransaction().rollback();
		} finally {
			em.close();
		}
		return tEmpleados;
	}

	public TEmpleado mostrarEmpleado(int id_empleado) {
		EntityManager em = EMFSingleton.getInstancia().getEntityManagerFactory().createEntityManager();
		TEmpleado tEmpleado = null;
		try {
			em.getTransaction().begin();
			Empleado empleado = null;
			// Bloqueo en optimista por si hay otro proceso que intenta modificar el
			// empleado
			empleado = em.find(Empleado.class, id_empleado, LockModeType.OPTIMISTIC);
			if (empleado != null) {
				tEmpleado = empleado.toTransfer();
				em.getTransaction().commit();
			} else {
				em.getTransaction().rollback();
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			em.close();
		}
		return tEmpleado;
	}

	private boolean checkTipos(Empleado e1, TEmpleado e2) {
		return e1 instanceof EmpleadoTecnico && e2 instanceof TEmpleadoTecnico
				|| e1 instanceof EmpleadoDependiente && e2 instanceof TEmpleadoDependiente;
	}
}

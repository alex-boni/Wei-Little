/**
 * 
 */
package Negocio.MaquinaJPA;

import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.LockModeType;
import javax.persistence.TypedQuery;

import Integracion.EMFSingleton.EMFSingleton;
import Negocio.ModeloJPA.Modelo;

public class SAMaquinaImp implements SAMaquina {

	public synchronized int altaMaquina(TMaquina tMaquina) {
		Integer id = -1;

		EntityManager em = EMFSingleton.getInstancia().getEntityManagerFactory().createEntityManager();
		EntityTransaction tr = em.getTransaction();
		try {
			tr.begin();

			TypedQuery<Maquina> query = em.createNamedQuery("Negocio.MaquinaJPA.Maquina.findBynum_serie",
					Maquina.class);

			query.setParameter("num_serie", tMaquina.get_num_serie());
			Maquina ma = null;
			// INCREMENTO FORZADDO porque si listamos maquinas por modelo en otra hebra,
			// necesita enterarse que tiene una nueva maquina asignada a este modelo
			Modelo mod = em.find(Modelo.class, tMaquina.get_id_modelo(), LockModeType.OPTIMISTIC_FORCE_INCREMENT);

			try {
				ma = query.getSingleResult();
			} catch (Exception e) {
			}

			Maquina nuevamaquina = null;

			if (ma == null && mod != null && mod.get_activo() == 1) {

				if (tMaquina instanceof TArcade) {
					TArcade tarcade = (TArcade) tMaquina;
					Arcade arcade = new Arcade(tarcade);
					arcade.set_Modelo(mod);
					nuevamaquina = arcade;
					em.persist(nuevamaquina);
					tr.commit();
					id = nuevamaquina.get_id();

				}

				else if (tMaquina instanceof TRecreativa) {
					TRecreativa trecreativa = (TRecreativa) tMaquina;
					Recreativa recreativa = new Recreativa(trecreativa);
					recreativa.set_Modelo(mod);
					nuevamaquina = recreativa;
					em.persist(nuevamaquina);
					tr.commit();
					id = nuevamaquina.get_id();
				} else {
					tr.rollback();
				}

			} else if (ma != null && mod != null && ma.get_activo() == 0 && mod.get_activo() == 1) {
				if (tMaquina instanceof TArcade && ma instanceof Arcade) {
					TArcade tarcade = (TArcade) tMaquina;
					Arcade arcade = (Arcade) ma;
					arcade.set_nombre(tarcade.get_nombre());
					arcade.set_precio_hora_actual(tarcade.get_precio_hora_actual());
					arcade.set_coe_valor_mul(tarcade.get_coe_valor_mul());
					arcade.set_precio_pantalla(tarcade.get_precio_pantalla());
					arcade.set_activo(1);
					arcade.set_Modelo(mod);
					tr.commit();
					id = ma.get_id();

				}

				else if (tMaquina instanceof TRecreativa && ma instanceof Recreativa) {
					TRecreativa tRecreativa = (TRecreativa) tMaquina;
					Recreativa recreativa = (Recreativa) ma;
					recreativa.set_nombre(tRecreativa.get_nombre());
					recreativa.set_precio_hora_actual(tRecreativa.get_precio_hora_actual());
					recreativa.set_activo(1);
					recreativa.set_precio_mantenimiento(tRecreativa.get_precio_mantenimiento());
					recreativa.set_coe_valor_div(tRecreativa.get_coe_valor_div());
					recreativa.set_Modelo(mod);
					tr.commit();
					id = ma.get_id();
				} else {
					tr.rollback();
				}

			} else {
				tr.rollback();
			}

		} catch (Exception e) {
			tr.rollback();
		} finally {
			em.close();
		}

		return id;
	}

	@SuppressWarnings("unused")
	public int bajaMaquina(int id_maquina) {
		int exito = -1;

		EntityManager em = EMFSingleton.getInstancia().getEntityManagerFactory().createEntityManager();
		EntityTransaction tr = em.getTransaction();

		try {
			tr.begin();
			Maquina maquina = em.find(Maquina.class, id_maquina);

			if (maquina != null && maquina.get_activo() == 1 && maquina.get_alquilado() == 0) {
				// INCREMENTO FORZADO porque si listamos maquinas por modelo, modelo necesita
				// enterarse que tiene una maquina menos asignada este modelo
				Modelo mod = em.find(Modelo.class, maquina.get_Modelo().get_id(),
						LockModeType.OPTIMISTIC_FORCE_INCREMENT);

				maquina.set_activo(0);
				tr.commit();
				exito = maquina.get_id();
			} else {
				tr.rollback();
			}
		} catch (Exception e) {
			tr.rollback();
			e.printStackTrace();
		} finally {
			em.close();
		}

		return exito;
	}

	public int modificarMaquina(TMaquina tMaquina) {

		int res = -1;

		EntityManager entityManager = EMFSingleton.getInstancia().getEntityManagerFactory().createEntityManager();
		try {

			entityManager.getTransaction().begin();

			Maquina maquinaByID = entityManager.find(Maquina.class, tMaquina.get_id());
			TypedQuery<Maquina> maquinaByNumeroSerie = entityManager
					.createNamedQuery("Negocio.MaquinaJPA.Maquina.findBynum_serie", Maquina.class);
			maquinaByNumeroSerie.setParameter("num_serie", tMaquina.get_num_serie());

			List<Maquina> listaMaquinasByNumeroSerie = maquinaByNumeroSerie.getResultList();
			Modelo mod = entityManager.find(Modelo.class, tMaquina.get_id_modelo());
			Modelo mod2 = maquinaByID.get_Modelo();
			if (mod2 != mod) {// si modificamos el modelo de la maquina, y en otra hebra listamos maquinas por
								// modelo..

				// el modelo mod debe enterarse que tiene una maquina menos asignada
				entityManager.lock(mod, LockModeType.OPTIMISTIC_FORCE_INCREMENT);
				// el modelo mod2 debe enterarse que tiene una maquina mas asignada
				entityManager.lock(mod2, LockModeType.OPTIMISTIC_FORCE_INCREMENT);
			}
			if ((maquinaByNumeroSerie != null && maquinaByID.get_activo() == 1)
					&& (mod != null && mod.get_activo() == 1)) {
				if (listaMaquinasByNumeroSerie.isEmpty() || listaMaquinasByNumeroSerie.size() == 1
						&& listaMaquinasByNumeroSerie.get(0).get_id() == tMaquina.get_id()) {

					if (tMaquina instanceof TArcade && maquinaByID instanceof Arcade) {
						TArcade tarcade = (TArcade) tMaquina;
						Arcade arcade = (Arcade) maquinaByID;
						arcade.set_nombre(tarcade.get_nombre());
						arcade.set_precio_hora_actual(tarcade.get_precio_hora_actual());
						arcade.set_num_serie(tarcade.get_num_serie());
						arcade.set_precio_pantalla(tarcade.get_precio_pantalla());
						arcade.set_coe_valor_mul(tarcade.get_coe_valor_mul());
						arcade.set_Modelo(mod);
						entityManager.getTransaction().commit();
						res = arcade.get_id();
					}

					else if (tMaquina instanceof TRecreativa && maquinaByID instanceof Recreativa) {
						TRecreativa trecreativa = (TRecreativa) tMaquina;
						Recreativa recreativa = (Recreativa) maquinaByID;
						recreativa.set_nombre(trecreativa.get_nombre());
						recreativa.set_precio_hora_actual(trecreativa.get_precio_hora_actual());
						recreativa.set_num_serie(trecreativa.get_num_serie());
						recreativa.set_precio_mantenimiento(trecreativa.get_precio_mantenimiento());
						recreativa.set_coe_valor_div(trecreativa.get_coe_valor_div());
						recreativa.set_Modelo(mod);
						entityManager.getTransaction().commit();
						res = recreativa.get_id();
					} else {
						entityManager.getTransaction().rollback();
					}
				} else {
					entityManager.getTransaction().rollback();
				}
			} else {
				entityManager.getTransaction().rollback();
			}

		} catch (Exception e) {
			e.printStackTrace();
			if (entityManager.getTransaction().isActive())
				entityManager.getTransaction().rollback();
		}
		entityManager.close();

		return res;
	}

	public TMaquina buscarMaquina(int id_maquina) {

		TMaquina tmaquina = null;
		EntityManager em = EMFSingleton.getInstancia().getEntityManagerFactory().createEntityManager();
		try {
			em.getTransaction().begin();

			// OPTIMISTA porque en otra hebra la puede modificar (dar de baja) y en tiempo
			// de commit debe comprobar el numero de version
			Maquina maquina = em.find(Maquina.class, id_maquina, LockModeType.OPTIMISTIC);

			if (maquina != null) {
				Maquina aux = maquina;
				tmaquina = aux.entityToTransfer();
			}

		} catch (Exception e) {
			e.printStackTrace();
			em.getTransaction().rollback();
		}
		em.close();

		return tmaquina;
	}

	public Set<TMaquina> listarTodasMaquinas() {
		EntityManager em = EMFSingleton.getInstancia().getEntityManagerFactory().createEntityManager();

		EntityTransaction tr = em.getTransaction();
		Set<TMaquina> setmaquinas = new LinkedHashSet<TMaquina>();
		try {
			tr.begin();
			TypedQuery<Maquina> query = em.createQuery("SELECT n FROM Maquina n", Maquina.class);
			Set<Maquina> maquinas = new LinkedHashSet<>(query.getResultList());

			for (Maquina m : maquinas) {
				// OPTIMISTA porque puede la maquina m puede ser modificada (dar de baja) en
				// otra hebra y en tiempo de commit debe comprobar el numero de version
				em.lock(m, LockModeType.OPTIMISTIC);
				TMaquina tmaquina = m.entityToTransfer();
				setmaquinas.add(tmaquina);
			}
		} catch (Exception e) {
			e.printStackTrace();
			em.getTransaction().rollback();
		}
		em.close();
		return setmaquinas;

	}

	public Set<TMaquina> listarTodoMaquinaPorModelo(int id_modelo) {
		EntityManager em = EMFSingleton.getInstancia().getEntityManagerFactory().createEntityManager();

		EntityTransaction tr = em.getTransaction();
		Set<TMaquina> maquinas = new LinkedHashSet<>();

		try {
			tr.begin();

			// OPTIMISTA Bloqueamos el modelo para que compruebe el numero de version en
			// tiempo de commit porque otra hebra puede dar de baja o insertar una maquina
			// en este modelo
			Modelo mod = em.find(Modelo.class, id_modelo, LockModeType.OPTIMISTIC);
			if (mod != null) {
				Set<Maquina> maquinasmod = mod.get_lista_maquinas();
				for (Maquina m : maquinasmod) {
					// OPTIMISTA porque puede la maquina m puede ser modificada (dar de baja) en
					// otra hebra y en tiempo de commit debe comprobar el numero de version
					em.lock(m, LockModeType.OPTIMISTIC);
					TMaquina tmaquina = m.entityToTransfer();
					maquinas.add(tmaquina);
				}

			}

			if (maquinas.isEmpty()) {
				maquinas = null;
			}
		} catch (Exception e) {
			e.printStackTrace();
			em.getTransaction().rollback();
		}
		em.close();
		return maquinas;
	}
}
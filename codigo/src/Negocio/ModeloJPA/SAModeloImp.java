/**
 * 
 */
package Negocio.ModeloJPA;

import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.EntityManager;
import javax.persistence.LockModeType;
import javax.persistence.TypedQuery;

import Integracion.EMFSingleton.EMFSingleton;
import Negocio.MaquinaJPA.Maquina;
import Negocio.ProveedorJPA.Proveedor;

public class SAModeloImp implements SAModelo {

	public synchronized int insertar_modelo(TModelo model) {

		int res = -1;
		EntityManager em = EMFSingleton.getInstancia().getEntityManagerFactory().createEntityManager();

		try {
			em.getTransaction().begin();

			TypedQuery<Modelo> query = em.createNamedQuery("Negocio.ModeloJPA.Modelo.findBynombre", Modelo.class);
			query.setParameter("nombre", model.get_nombre());

			// Solo debería de haber uno con ese nombre
			List<Modelo> modelos = query.getResultList();

			// Si no existe, lo creamos
			if (modelos.isEmpty()) {

				Modelo modelo = new Modelo(model);
				em.persist(modelo);
				em.getTransaction().commit();
				res = modelo.get_id();

				// Si existe, lo activamos
			} else if (query.getSingleResult().get_activo() == 0) {

				Modelo modelo = query.getSingleResult();
				modelo.set_activo(1);
				res = modelo.get_id();
				em.getTransaction().commit();

				// Si existe y está activo, no hacemos nada
			} else {
				em.getTransaction().rollback();
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			em.close();
		}

		return res;
	}

	public int eliminar_modelo(int id) {

		int res = -1;
		EntityManager em = EMFSingleton.getInstancia().getEntityManagerFactory().createEntityManager();

		try {
			em.getTransaction().begin();

			Modelo modelo = em.find(Modelo.class, id);

			if (modelo != null) {
				boolean algunActivo = false;

				for (Maquina maquina : modelo.get_lista_maquinas()) {
					if (maquina.get_activo() == 1) {
						res = -2;
						algunActivo = true;
						break;
					}
				}

				if (!algunActivo && !modelo.get_vinculaciones().isEmpty()) {
					res = -3;
					algunActivo = true;
				}

				if (!algunActivo) {
					res = modelo.get_id();
					modelo.set_activo(0);
					em.getTransaction().commit();
				} else {
					em.getTransaction().rollback();
				}
			} else {
				em.getTransaction().rollback();
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			em.close();
		}

		return res;
	}

	public int modificar_modelo(TModelo model) {

		int res = -1;
		EntityManager em = EMFSingleton.getInstancia().getEntityManagerFactory().createEntityManager();

		try {
			em.getTransaction().begin();

			Modelo modelo = em.find(Modelo.class, model.get_id());

			if (modelo != null && modelo.get_activo() == 1) {
				modelo.set_nombre(model.get_nombre());

				res = 1;
				em.getTransaction().commit();
			} else {
				em.getTransaction().rollback();
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			em.close();
		}

		return res;
	}

	public TModelo buscar_modelo(int id) {

		TModelo res = null;
		EntityManager em = EMFSingleton.getInstancia().getEntityManagerFactory().createEntityManager();

		try {
			em.getTransaction().begin();

			// Bloqueamos optimista el modelo para evitar que lo modifiquen mientras lo
			// leemos
			Modelo modelo = em.find(Modelo.class, id, LockModeType.OPTIMISTIC);

			if (modelo != null) {
				res = modelo.toTransfer();
				em.getTransaction().commit();
			} else {
				em.getTransaction().rollback();
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			em.close();
		}

		return res;
	}

	public Set<TModelo> listar_todo_modelo() {

		Set<TModelo> res = null;
		EntityManager em = EMFSingleton.getInstancia().getEntityManagerFactory().createEntityManager();

		try {
			em.getTransaction().begin();

			// Solo los activos porque lo pone en la SRS
			// Bloqueamos optimista para evitar que se modifiquen los modelos mientras los
			// leemos
			TypedQuery<Modelo> query = em.createQuery("SELECT m FROM Modelo m", Modelo.class);
			query.setLockMode(LockModeType.OPTIMISTIC);

			List<Modelo> result = query.getResultList();

			if (result != null) {

				res = new LinkedHashSet<TModelo>();

				for (Modelo modelo : result) {
					em.lock(modelo, LockModeType.OPTIMISTIC);
					res.add(modelo.toTransfer());
				}

				em.getTransaction().commit();
			} else {
				em.getTransaction().rollback();
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			em.close();
		}

		return res;
	}

	public Set<TModelo> listar_modelo_por_proveedor(int id_proveedor) {

		Set<TModelo> res = null;
		EntityManager em = EMFSingleton.getInstancia().getEntityManagerFactory().createEntityManager();

		try {
			em.getTransaction().begin();

			Proveedor proveedor = em.find(Proveedor.class, id_proveedor, LockModeType.OPTIMISTIC);
			res = new LinkedHashSet<TModelo>();

			if (proveedor != null) {

				for (VinculacionModeloProveedor vinculacion : proveedor.get_vinculaciones()) {
					res.add(vinculacion.get_modelo().toTransfer());
				}

				em.getTransaction().commit();
			} else {
				em.getTransaction().rollback();
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			em.close();
		}

		return res;
	}

	public int vincular_modelo_proveedor(TVinculacionModeloProveedor tVinculacion) {

		int res = -1;
		EntityManager em = EMFSingleton.getInstancia().getEntityManagerFactory().createEntityManager();

		try {
			em.getTransaction().begin();

			// Bloqueamos optimista de incremento forzadoel proveedor y el modelo porque las
			// estamos modificando indirectamente al añadir la vinculación
			Proveedor proveedor = em.find(Proveedor.class, tVinculacion.get_id_provider(),
					LockModeType.OPTIMISTIC_FORCE_INCREMENT);
			Modelo modelo = em.find(Modelo.class, tVinculacion.get_id_model(), LockModeType.OPTIMISTIC_FORCE_INCREMENT);

			VinculacionModeloProveedorID id = new VinculacionModeloProveedorID(tVinculacion.get_id_model(),
					tVinculacion.get_id_provider());
			VinculacionModeloProveedor vinculacion = em.find(VinculacionModeloProveedor.class, id);

			if (proveedor != null && modelo != null && proveedor.get_activo() == 1 && modelo.get_activo() == 1
					&& vinculacion == null) {
				vinculacion = new VinculacionModeloProveedor(modelo, proveedor);
				em.persist(vinculacion);
				res = 1;
				em.getTransaction().commit();
			} else {
				em.getTransaction().rollback();
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			em.close();
		}

		return res;
	}

	public int desvincular_modelo_proveedor(TVinculacionModeloProveedor tVinculacion) {

		int res = -1;
		EntityManager em = EMFSingleton.getInstancia().getEntityManagerFactory().createEntityManager();

		try {
			em.getTransaction().begin();

			// Bloqueamos optimista de incremento forzadoel proveedor y el modelo porque las
			// estamos modificando indirectamente al añadir la vinculación
			Proveedor proveedor = em.find(Proveedor.class, tVinculacion.get_id_provider(),
					LockModeType.OPTIMISTIC_FORCE_INCREMENT);
			Modelo modelo = em.find(Modelo.class, tVinculacion.get_id_model(), LockModeType.OPTIMISTIC_FORCE_INCREMENT);

			if (proveedor != null && modelo != null && proveedor.get_activo() == 1 && modelo.get_activo() == 1) {

				VinculacionModeloProveedorID id = new VinculacionModeloProveedorID(modelo.get_id(),
						proveedor.get_id_proveedor());
				VinculacionModeloProveedor vinculacion = em.find(VinculacionModeloProveedor.class, id);

				if (vinculacion != null) {
					em.remove(vinculacion);
					res = 1;
					em.getTransaction().commit();
				} else {
					em.getTransaction().rollback();
				}
			} else {
				em.getTransaction().rollback();
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			em.close();
		}

		return res;
	}

	/**
	 * 
	 * Se consulta en la base de datos del modelo con el id_modelo de entrada. Se
	 * comprueba si existe y está activo el modelo. Después, se consulta en la base
	 * de datos de Máquina, todas las máquinas que tienen el id_modelo de entrada.
	 * Se comprueba que si esta activo y si lo está dependiendo del tipo de Máquina,
	 * por POLIMORFISMO se calcula el coste del seguro haciendo un sumatorio de
	 * todos los costes de las máquinas que pertenecen al modelo de entrada.
	 * 
	 * Finalmente, se mostrará el valor calculado por pantalla, si ocurre algún
	 * error se mostrará un mensaje de error y si no hay maquinas para ese modelo o
	 * no exista el modelo se mostrará un mensaje según corresponda.
	 * 
	 * -----------------------------Cálculos—--------------------------------
	 *
	 * Si la máquina es de tipo Arcade: Coste Total = precio_pantalla *
	 * coe_valor_mul
	 * 
	 * Si la máquina es de tipo Recreativo: Coste Total = precio_mantenimiento /
	 * coe_valor_div
	 * 
	 * Siendo el coste total acumulado: C.T.AC = Sumatorio(Coste Total Maquina i)
	 *
	 */
	public double calcular_coste_seguro_modelo(int id_modelo) {

		double res = -1;
		EntityManager em = EMFSingleton.getInstancia().getEntityManagerFactory().createEntityManager();

		try {
			em.getTransaction().begin();

			Modelo modelo = em.find(Modelo.class, id_modelo, LockModeType.OPTIMISTIC);

			if (modelo != null && modelo.get_activo() == 1) {

				Set<Maquina> maquinas = modelo.get_lista_maquinas();

				if (maquinas != null) {

					res = 0;

					for (Maquina maquina : maquinas) {
						em.lock(maquina, LockModeType.OPTIMISTIC);
						if (maquina.get_activo() == 1) {
							res += maquina.coste_seguro();
						}
					}

					em.getTransaction().commit();
				} else {
					em.getTransaction().rollback();
				}
			} else {
				em.getTransaction().rollback();
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			em.close();
		}

		return res;
	}
}
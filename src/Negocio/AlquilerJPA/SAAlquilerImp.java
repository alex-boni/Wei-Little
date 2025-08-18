
package Negocio.AlquilerJPA;

import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import javax.persistence.EntityManager;
import javax.persistence.LockModeType;
import javax.persistence.TypedQuery;

import Integracion.EMFSingleton.EMFSingleton;
import Negocio.ClienteJPA.Cliente;
import Negocio.EmpleadoJPA.Empleado;
import Negocio.MaquinaJPA.Maquina;

public class SAAlquilerImp implements SAAlquiler {

	public TCarritoAlquiler abrir_alquiler(TAlquiler tAlquiler) {
		TCarritoAlquiler carrito = new TCarritoAlquiler();
		carrito.set_tLineasAlquiler(new HashSet<TLineaAlquiler>());
		carrito.set_tAlquiler(tAlquiler);

		return carrito;
	}

	@Override
	public int pasar_carrito(TCarritoAlquiler carrito) {
		return carrito == null ? -1 : 1;
	}

	public int anyadir_maquina(TCarritoAlquiler tCarrito) {
		int res = -1;
		int id = tCarrito.get_id_maquina();
		int duracion = tCarrito.get_duracion();
		Set<TLineaAlquiler> listaLineasAlquiler = tCarrito.get_tLineasAlquiler();
		TLineaAlquiler lineaAlquiler = buscar_en_carrito(listaLineasAlquiler, id);

		if (lineaAlquiler == null) {
			lineaAlquiler = new TLineaAlquiler();
			lineaAlquiler.set_id_maquina(id);
			lineaAlquiler.set_duracion(duracion);
			listaLineasAlquiler.add(lineaAlquiler);
			res = 1;
		}

		return res;
	}

	private TLineaAlquiler buscar_en_carrito(Set<TLineaAlquiler> lineasAlquiler, int id) {

		Iterator<TLineaAlquiler> it = lineasAlquiler.iterator();
		TLineaAlquiler res = null;
		while (it.hasNext()) {
			TLineaAlquiler lineaAlquiler = it.next();
			if (lineaAlquiler.get_id_maquina() == id)
				res = lineaAlquiler;
		}

		return res;
	}

	public int eliminar_maquina(TCarritoAlquiler tCarrito) {
		int res = -1;
		int id = tCarrito.get_id_maquina();
		Set<TLineaAlquiler> listaLineasAlquiler = tCarrito.get_tLineasAlquiler();
		TLineaAlquiler lineaAlquiler = buscar_en_carrito(listaLineasAlquiler, id);
		if (lineaAlquiler != null) {
			res = 1;
			listaLineasAlquiler.remove(lineaAlquiler);
			tCarrito.set_tLineasAlquiler(listaLineasAlquiler);
		}
		return res;
	}

	public int cerrar_alquiler(TCarritoAlquiler tCarrito) {
		int res = -1;
		try {
			EntityManager em = EMFSingleton.getInstancia().getEntityManagerFactory().createEntityManager();
			em.getTransaction().begin();
			Cliente cliente = em.find(Cliente.class, tCarrito.get_tAlquiler().get_id_cliente());
			Empleado empleado = em.find(Empleado.class, tCarrito.get_tAlquiler().get_id_empleado());
			if (cliente == null || cliente.get_activo() == 0)
				res = -2;
			if (empleado == null || empleado.get_activo() == 0)
				res = -3;
			if (cliente != null && empleado != null && cliente.get_activo() == 1 && empleado.get_activo() == 1) {
				double total = 0;
				Alquiler alquiler = new Alquiler();
				alquiler.set_cliente(cliente);
				alquiler.set_empleado(empleado);
				alquiler.set_activo(1);
				em.persist(alquiler);
				em.flush();// PREGUNTAR A ANTONIO que si no lo pongo no funciona
				Set<LineaAlquiler> lineasAlquilerPersistentes = new HashSet<LineaAlquiler>();
				Set<TLineaAlquiler> tLineasAlquilerPersistentes = new HashSet<TLineaAlquiler>();
				for (TLineaAlquiler tLineaAlquiler : tCarrito.get_tLineasAlquiler()) {
					Maquina maquina = em.find(Maquina.class, tLineaAlquiler.get_id_maquina());
					if (maquina != null && maquina.get_activo() == 1 && maquina.get_alquilado() == 0) {
						maquina.set_alquilado(1);
						LineaAlquiler lineaAlquiler = new LineaAlquiler(alquiler, maquina);
						lineaAlquiler.set_duracion(tLineaAlquiler.get_duracion());
						lineaAlquiler.set_devuelto(0);
						lineaAlquiler.set_precio(maquina.get_precio_hora_actual() * tLineaAlquiler.get_duracion());
						tLineaAlquiler.set_precio(lineaAlquiler.get_precio());
						tLineasAlquilerPersistentes.add(tLineaAlquiler);
						em.persist(lineaAlquiler);
						total += lineaAlquiler.get_precio();
						lineasAlquilerPersistentes.add(lineaAlquiler);
					}
				}
				if (total != 0) {
					alquiler.set_total(total);
					alquiler.set_fecha(new Date());
					alquiler.set_lineas_alquiler(lineasAlquilerPersistentes);
					em.persist(alquiler);
					em.getTransaction().commit();
					res = alquiler.get_id_alquiler();
					tCarrito.set_tAlquiler(alquiler.toTransfer());
					tCarrito.set_tLineasAlquiler(tLineasAlquilerPersistentes);
				} else {
					res = -4;
					em.getTransaction().rollback();
				}
			} else {
				em.getTransaction().rollback();
			}
			em.close();

		} catch (Exception e) {
			e.printStackTrace();
		}
		return res;
	}

	public Set<TAlquiler> listar_alquileres() {
		EntityManager em = EMFSingleton.getInstancia().getEntityManagerFactory().createEntityManager();
		Set<TAlquiler> listaAlquileres = new HashSet<TAlquiler>();
		try {
			em.getTransaction().begin();
			TypedQuery<Alquiler> query = em.createQuery("SELECT a FROM Alquiler a", Alquiler.class);

			List<Alquiler> alquileres = query.getResultList();
			for (Alquiler alquiler : alquileres) {
				// OPTIMISTA porque otra hebra puede modificar el alquiler y debe comprobar en
				// tiempo de commit el numero de version
				em.lock(alquiler, LockModeType.OPTIMISTIC);
				if (alquiler.get_activo() == 1) {
					listaAlquileres.add(alquiler.toTransfer());
				}
			}
			em.getTransaction().commit();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			em.close();
		}
		return listaAlquileres;
	}

	public TAlquilerTOA buscar_alquiler(int id_alquiler) {

		TAlquilerTOA tAlquilerTOA = null;
		EntityManager em = EMFSingleton.getInstancia().getEntityManagerFactory().createEntityManager();

		try {
			em.getTransaction().begin();

			// OPTIMISTA porque otra hebra puede modificar el alquiler y debe comprobar en
			// tiempo de commit el numero de version

			Alquiler alquiler = em.find(Alquiler.class, id_alquiler, LockModeType.OPTIMISTIC);

			if (alquiler != null && alquiler.get_activo() == 1) {

				tAlquilerTOA = new TAlquilerTOA();

				Empleado empleado = alquiler.get_empleado();
				// OPTIMISTA porque otra hebra puede modificar el empleado de este alquiler y
				// debe comprobar en tiempo de commit el numero de version
				em.lock(empleado, LockModeType.OPTIMISTIC);

				Cliente cliente = alquiler.get_cliente();
				// OPTIMISTA porque otra hebra puede modificar el cliente de este alquiler y
				// debe comprobar en tiempo de commit el numero de version
				em.lock(empleado, LockModeType.OPTIMISTIC);

				Set<TLineaAlquiler> listaTLineasAlquiler = new HashSet<TLineaAlquiler>();

				for (LineaAlquiler linea_alquiler : alquiler.get_lineas_alquiler()) {
					// OPTIMISTA porque otra hebra puede cancelar esta linea de alquiler y en tiempo
					// de commit debe comprobar el numero de version
					em.lock(linea_alquiler, LockModeType.OPTIMISTIC);
					listaTLineasAlquiler.add(linea_alquiler.toTransfer());
				}

				tAlquilerTOA.set_tAlquiler(alquiler.toTransfer());
				tAlquilerTOA.set_tCliente(cliente.toTransfer());
				tAlquilerTOA.set_tEmpleado(empleado.toTransfer());
				tAlquilerTOA.set_tLineasAlquiler(listaTLineasAlquiler);
				em.getTransaction().commit();
			}

			else {
				em.getTransaction().rollback();
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			em.close();
		}

		return tAlquilerTOA;

	}

	/**
	 * Modifica un alquiler existente en la base de datos
	 * 
	 * @param id_empleado Identificador del nuevo empleado que se va a poner en el
	 *                    alquiler
	 * @param id_cliente  Identificador del nuevo cliente que se va a poner en el
	 *                    alquiler
	 * @param id_alquiler Identificador del alquiler que se va a modificar
	 * @return resultado Booleano que nos indica si se ha modificado o no el
	 *         alquiler
	 * 
	 */
	@SuppressWarnings("unused")
	public int modificar_alquiler(TAlquiler tAlquiler) {
		int resultado = 0;
		EntityManager em = EMFSingleton.getInstancia().getEntityManagerFactory().createEntityManager();

		try {
			em.getTransaction().begin();

			// Busco el alquiler de manera optimista
			Alquiler alquiler = em.find(Alquiler.class, tAlquiler.get_id_alquiler());
			if (alquiler == null) {
				resultado = -3;
			}

			if (alquiler != null && alquiler.get_activo() == 1) {

				Empleado empleadoActual = em.find(Empleado.class, alquiler.get_empleado().get_id_empleado());
				Cliente clienteActual = em.find(Cliente.class, alquiler.get_cliente().get_id_cliente());

				Cliente nuevoCliente = em.find(Cliente.class, tAlquiler.get_id_cliente());
				if (nuevoCliente == null || nuevoCliente.get_activo() == 0)
					resultado = -1;

				Empleado nuevoEmpleado = em.find(Empleado.class, tAlquiler.get_id_empleado());
				if (nuevoEmpleado == null || nuevoEmpleado.get_activo() == 0)
					resultado = -2;

				if (nuevoEmpleado != null && nuevoEmpleado.get_activo() == 1 && nuevoCliente != null
						&& nuevoCliente.get_activo() == 1) {
					if (empleadoActual != nuevoEmpleado) {
						// OPTIMISTA INCREMENTO FORZADO porque en otra hebra podemos listar alquileres
						// por empleado,
						// teniendo un alquiler menos el empleadoActual y un alquiler mas el
						// nuevoEmpleado
						em.lock(nuevoEmpleado, LockModeType.OPTIMISTIC_FORCE_INCREMENT);
						em.lock(empleadoActual, LockModeType.OPTIMISTIC_FORCE_INCREMENT);
						alquiler.set_empleado(nuevoEmpleado);
					}
					if (clienteActual != nuevoCliente) {
						// OPTIMISTA INCREMENTO FORZADO porque en otra hebra podemos listar alquileres
						// por cliente,
						// teniendo un alquiler menos el clienteActual y un alquiler mas el nuevoCliente
						em.lock(clienteActual, LockModeType.OPTIMISTIC_FORCE_INCREMENT);
						em.lock(nuevoCliente, LockModeType.OPTIMISTIC_FORCE_INCREMENT);
						alquiler.set_cliente(nuevoCliente);
					}

					em.persist(alquiler);
					em.getTransaction().commit();
					resultado = alquiler.get_id_alquiler();
				} else { // Si o el nuevoempleado, o el nuevocliente o ambos no estan activos o no
							// existen
					em.getTransaction().rollback();
				}
			} else { // Si no existe el alquiler
				em.getTransaction().rollback();
			}
		} catch (Exception ex) {
			ex.printStackTrace();
			if (em.getTransaction().isActive()) {
				em.getTransaction().rollback();
			}
		} finally {
			em.close();
		}

		return resultado;
	}

	public Set<TAlquiler> listar_alquileres_por_empleado(int id_empleado) {
		EntityManager em = EMFSingleton.getInstancia().getEntityManagerFactory().createEntityManager();
		Set<TAlquiler> listaAlquileres = new HashSet<>();
		try {
			em.getTransaction().begin();

			// Bloqueamos el empleado de manera optimista para garantizar que otro proceso
			// no lo modifique
			Empleado empleado = em.find(Empleado.class, id_empleado, LockModeType.OPTIMISTIC);
			if (empleado != null && empleado.get_activo() == 1) {
				for (Alquiler alquiler : empleado.get_facturas_empleado()) {
					if (alquiler.get_activo() == 1) {
						listaAlquileres.add(alquiler.toTransfer());
					}
				}

			}
			em.getTransaction().commit();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			em.close();
		}
		return listaAlquileres;
	}

	public Set<TAlquiler> listar_alquileres_por_cliente(int id_cliente) {
		EntityManager em = EMFSingleton.getInstancia().getEntityManagerFactory().createEntityManager();
		Set<TAlquiler> listaAlquileres = new HashSet<>();
		try {
			em.getTransaction().begin();

			// OPTIMISTA porque en otra hebra la puede modificar (dar de baja) y en tiempo
			// de commit debe comprobar el numero de version
			Cliente cliente = em.find(Cliente.class, id_cliente, LockModeType.OPTIMISTIC);
			if (cliente != null && cliente.get_activo() == 1) {
				for (Alquiler alquiler : cliente.get_lista_factura()) {
					if (alquiler.get_activo() == 1) {
						listaAlquileres.add(alquiler.toTransfer());
					}
				}
			}
			em.getTransaction().commit();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			em.close();
		}
		return listaAlquileres;
	}

	public int cancelar_alquiler(TLineaAlquiler tLineaAlquiler) {

		int res = -1;
		EntityManager em = EMFSingleton.getInstancia().getEntityManagerFactory().createEntityManager();

		try {
			em.getTransaction().begin();

			Alquiler alquiler = em.find(Alquiler.class, tLineaAlquiler.get_id_alquiler());

			if (alquiler != null) {

				LineaAlquilerID lineaAlquilerID = new LineaAlquilerID(tLineaAlquiler.get_id_alquiler(),
						tLineaAlquiler.get_id_maquina());

				// Otro Proceso en conflicto: Buscar alquiler con id =
				// tLineaAlquiler.get_id_alquiler()
				// Tipo de Bloqueo:NINGUNO porque al modificar el campo devuelto cambia el
				// numero de version
				// y la otra hebra se entera al tener un bloqueo optimista
				// Entidad afectada: LineaAlquiler
				LineaAlquiler lineaAlquiler = em.find(LineaAlquiler.class, lineaAlquilerID);

				if (alquiler.get_activo() == 1) {
					if (lineaAlquiler != null) {

						int maquina_devuelta = lineaAlquiler.get_devuelto();
						if (maquina_devuelta == 0) {
							lineaAlquiler.set_devuelto(1);

							Maquina maquina = em.find(Maquina.class, tLineaAlquiler.get_id_maquina());

							if (maquina != null) {
								maquina.set_alquilado(0);
								em.getTransaction().commit();
								res = 1;
							} else {
								em.getTransaction().rollback();

								// Si no se encuentra la maquina...
								res = -5;
							}

						} else {
							em.getTransaction().rollback();

							// Si la maquina ya ha sido devuelta...
							res = -4;
						}
					} else {
						em.getTransaction().rollback();
						// Si no se encuentra la linea de alquiler...
						res = -3;
					}
				} else {
					em.getTransaction().rollback();
					// Si no está activo alquiler...
					res = -2;
				}

			} else {
				em.getTransaction().rollback();
				// Si no se encuentra el alquiler, resultado ya será -1
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {

			// Siempre cerramos el EntityManager
			em.close();
		}

		return res;

	}

}
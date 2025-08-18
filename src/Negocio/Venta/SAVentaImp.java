package Negocio.Venta;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import java.util.LinkedHashSet;

import Integracion.FactoriaIntegracion.FactoriaIntegracion;
import Integracion.FactoriaQuery.FactoriaQuery;
import Integracion.FactoriaQuery.Query;
import Integracion.ProductoEnPlataforma.DAOProductoEnPlataforma;
import Integracion.Trabajador.DAOTrabajador;
import Integracion.Transaction.TManager;
import Integracion.Transaction.Transaction;
import Integracion.Venta.DAOVenta;
import Negocio.ProductoEnPlataforma.TProductoEnPlataforma;
import Negocio.Trabajador.TTrabajador;
import Integracion.Venta.DAOLineaVenta;

public class SAVentaImp implements SAVenta {

	/**
	 * Abre una nueva venta asociada a un trabajador específico.
	 *
	 * @param id_trabajador El identificador del trabajador que realiza la venta.
	 * @return Un objeto TCarrito que contiene la venta abierta y una lista vacía de
	 *         líneas de venta.
	 */
	public TCarrito abrir_venta(int id_trabajador) {

		TCarrito carrito = new TCarrito();
		carrito.set_lista_lineaVenta(new HashSet<TLineaVenta>());
		TVenta venta = new TVenta();
		venta.set_trabajador(id_trabajador);
		carrito.set_venta(venta);

		return carrito;
	}

	/**
	 * Este método procesa el carrito de compras.
	 *
	 * @param carrito El objeto TCarrito que representa el carrito de compras.
	 * @return Un entero que indica el resultado de la operación: -1 si el carrito
	 *         es nulo, 1 si la operación se realizó con éxito.
	 */
	public int pasar_carrito(TCarrito carrito) {
		return carrito == null ? -1 : 1;
	}

	/**
	 * Inserta un producto en el carrito de compras.
	 *
	 * @param carrito El carrito de compras que contiene la lista de productos y sus
	 *                cantidades.
	 * @return Un entero que indica el resultado de la operación (1 si se ha
	 *         insertado correctamente).
	 *
	 *         Este método busca en el carrito si el producto ya existe. Si no
	 *         existe, crea una nueva línea de venta con el producto y la cantidad
	 *         especificada y la añade al carrito. Si el producto ya existe en el
	 *         carrito, simplemente actualiza la cantidad del producto sumando la
	 *         cantidad especificada a la cantidad existente.
	 */
	public int insertar_producto_carrito(TCarrito carrito) {

		int id = carrito.get_id_producto_final();
		int cantidad = carrito.get_cantidad();
		Set<TLineaVenta> listaLineasVenta = carrito.get_lista_lineaVenta();
		TLineaVenta lineaVenta = buscar_en_carrito(listaLineasVenta, id);

		if (lineaVenta == null) {
			lineaVenta = new TLineaVenta();
			lineaVenta.set_producto(id);
			lineaVenta.set_cantidad(cantidad);
			listaLineasVenta.add(lineaVenta);
		} else
			lineaVenta.set_cantidad(cantidad + lineaVenta.get_cantidad());

		return 1;
	}

	/**
	 * Elimina un producto del carrito de compras.
	 *
	 * @param carrito El carrito de compras del cual se eliminará el producto.
	 * @return Un entero que indica el resultado de la operación: -1 si el producto
	 *         no se encuentra en el carrito, 1 si el producto se eliminó
	 *         exitosamente.
	 */
	public int eliminar_producto_carrito(TCarrito carrito) {

		int id = carrito.get_id_producto_final();
		int cantidad = carrito.get_cantidad();
		Set<TLineaVenta> listaLineasVenta = carrito.get_lista_lineaVenta();
		TLineaVenta lineaVenta = buscar_en_carrito(listaLineasVenta, id);

		if (lineaVenta == null)
			return -1;
		else {
			lineaVenta.set_cantidad(lineaVenta.get_cantidad() - cantidad);
			if (lineaVenta.get_cantidad() <= 0)
				listaLineasVenta.remove(lineaVenta);
		}

		return 1;
	}

	/**
	 * Busca una línea de venta en el carrito por el ID del producto.
	 *
	 * @param lineasVenta Conjunto de líneas de venta en el carrito.
	 * @param id          ID del producto a buscar.
	 * @return La línea de venta que contiene el producto con el ID especificado, o
	 *         null si no se encuentra ninguna línea de venta con ese ID.
	 */
	private TLineaVenta buscar_en_carrito(Set<TLineaVenta> lineasVenta, int id) {

		Iterator<TLineaVenta> it = lineasVenta.iterator();
		TLineaVenta res = null;
		while (it.hasNext()) {
			TLineaVenta lineaVenta = it.next();
			if (lineaVenta.get_producto() == id)
				res = lineaVenta;
		}

		return res;
	}

	/**
	 * Cierra una venta a partir de un carrito de compras.
	 *
	 * @param carrito El carrito de compras que contiene los productos a vender.
	 * @return El ID de la venta creada si la operación es exitosa, -1 en caso
	 *         contrario.
	 *
	 */
	public int cerrar_venta(TCarrito carrito) {

		TManager tm = TManager.getInstance();
		Transaction tr = tm.createTransaction();
		boolean error = false;
		int id_venta = -1;

		if (tr != null) {

			tr.start();
			TVenta tVenta = carrito.get_venta();

			DAOTrabajador daoTrabajador = FactoriaIntegracion.getInstancia().generaDAOTrabajador();
			TTrabajador tTrabajador = daoTrabajador.read(tVenta.get_trabajador());

			if (tTrabajador != null && tTrabajador.get_activo() != 0) {

				Iterator<TLineaVenta> it = carrito.iterator();
				Set<TLineaVenta> listaFinal = new LinkedHashSet<TLineaVenta>();
				DAOProductoEnPlataforma daoProductoPlataforma = FactoriaIntegracion.getInstancia()
						.generaDAOProductoEnPlataforma();
				// Recorre cada linea de venta del carrito y devuelve el total de la venta
				// double total = recorrerVenta(it, listaFinal);

				double total = 0;

				while (it.hasNext() && !error) {

					TLineaVenta tLineaVentaCarrito = it.next();
					TProductoEnPlataforma tProductoPlataforma = daoProductoPlataforma
							.read(tLineaVentaCarrito.get_producto());

					if (esProductoValido(tProductoPlataforma)) {

						TLineaVenta tLineaVenta_final = new TLineaVenta();

						if (tProductoPlataforma.get_cantidad() >= tLineaVentaCarrito.get_cantidad()) {

							total = actualizarLineaVenta(tLineaVenta_final, tLineaVentaCarrito, tProductoPlataforma,
									listaFinal, total);

							if (daoProductoPlataforma.update(tProductoPlataforma) == -1) {
								error = true;
								break;
							}

							TProductoEnPlataforma tProductoPlataforma_aux = daoProductoPlataforma
									.read(tProductoPlataforma.get_id());

							if (esProductoEliminado(tProductoPlataforma_aux, daoProductoPlataforma)) {
								error = true;
								break;
							}
						}
					}
				}

				if (listaFinal.size() > 0 && !error) {

					DAOVenta daoVenta = FactoriaIntegracion.getInstancia().generaDAOVenta();

					tVenta.set_total_factura(total);

					id_venta = daoVenta.create(tVenta);

					if (id_venta != -1) {

						carrito.set_venta(tVenta);
						tVenta.set_id(id_venta);
						tVenta.set_activo(1);

						DAOLineaVenta daoLineaVenta = FactoriaIntegracion.getInstancia().generaDAOLineaVenta();

						for (TLineaVenta tLineaVenta : listaFinal) {

							tLineaVenta.set_activo(1);
							tLineaVenta.set_factura(id_venta);

							if (daoLineaVenta.create(tLineaVenta) == -1) {
								error = true;
								break;
							}
						}

						carrito.set_lista_lineaVenta(listaFinal);

						if (error) {
							tr.rollback();
						}

						else {
							tr.commit();
						}

					} else {
						tr.rollback();
					}

				} else {
					tr.rollback();
				}

			} else {
				tr.rollback();
			}
		}

		return id_venta;
	}

	/**
	 * Consulta el total de dinero generado por el trabajador que más ha vendido de
	 * un cierto producto en plataforma.
	 *
	 * @param idProductoPlataforma El identificador del producto en la plataforma.
	 * @return El total de dinero generado, o -1 si ocurre un error o el producto no
	 *         ha sido vendido.
	 */
	public int calcularTotalTrabajadorQueMasVende(Integer idProductoPlataforma) {

		TManager tm = TManager.getInstance();
		Transaction tr = tm.createTransaction();
		int res = -1;

		if (tr != null) {

			tr.start();
			Query q = FactoriaQuery.getInstance().getNewQuery("CalcularTotalTrabajadorQueMasVende");
			res = (Integer) q.execute(idProductoPlataforma);

			if (res != -1) {
				tr.commit();
			}

			else {
				tr.rollback();
			}
		}

		return res;
	}

	/**
	 * Elimina una venta de la base de datos.
	 *
	 * @param factura El identificador de la factura de la venta a eliminar.
	 * @return Un entero que indica el resultado de la operación: - Si la operación
	 *         es exitosa, retorna el resultado de la eliminación de la venta. - Si
	 *         ocurre un error, retorna -1.
	 */
	public int eliminar_venta(int factura) {

		TManager tm = TManager.getInstance();
		Transaction tr = tm.createTransaction();
		int res = -1;
		boolean error = false;

		if (tr != null) {
			tr.start();

			DAOVenta daoVenta = FactoriaIntegracion.getInstancia().generaDAOVenta();
			TVenta venta = daoVenta.read(factura);

			if (venta != null && venta.get_activo() != 0) {

				DAOLineaVenta daoLineaVenta = FactoriaIntegracion.getInstancia().generaDAOLineaVenta();
				Set<TLineaVenta> setLineaVenta = daoLineaVenta.read_all(venta.get_id());

				for (TLineaVenta lineaVenta : setLineaVenta) {
					if (daoLineaVenta.delete(lineaVenta) == -1) {
						error = true;
						break;
					}
				}

				if (!error) {

					res = daoVenta.delete(factura);

					if (res != -1) {
						tr.commit();
					}

					else {
						tr.rollback();
					}

				} else {
					tr.rollback();
				}
			} else {
				tr.rollback();
			}
		}

		return res;
	}

	/**
	 * Busca una venta completa a partir del ID de la factura.
	 *
	 * @param id_factura El ID de la factura de la venta a buscar.
	 * @return Un objeto TVentaCompletaTOA que contiene los datos completos de la
	 *         venta, incluyendo la venta, las líneas de venta, los productos en
	 *         plataforma y el trabajador. Retorna null si la venta no existe o si
	 *         no hay productos en la plataforma asociados a la venta.
	 */
	public TVentaCompletaTOA buscar_venta(int id_factura) {

		TManager tm = TManager.getInstance();
		Transaction tr = tm.createTransaction();
		TVentaCompletaTOA tVentaCompleta = null;

		if (tr != null) {
			tr.start();

			DAOVenta daoVenta = FactoriaIntegracion.getInstancia().generaDAOVenta();

			TVenta venta = daoVenta.read(id_factura);

			if (venta != null) {

				DAOTrabajador daoTrabajador = FactoriaIntegracion.getInstancia().generaDAOTrabajador();
				TTrabajador trabajador = daoTrabajador.read(venta.get_trabajador());

				if (trabajador != null) {

					DAOLineaVenta daoLineaVenta = FactoriaIntegracion.getInstancia().generaDAOLineaVenta();
					Set<TLineaVenta> lista_lineaVenta = daoLineaVenta.read_all(id_factura);

					if (lista_lineaVenta != null) {

						Iterator<TLineaVenta> it = lista_lineaVenta.iterator();
						Set<TProductoEnPlataforma> lista_producto_plataforma = new LinkedHashSet<>();
						DAOProductoEnPlataforma daoProductoPlataforma = FactoriaIntegracion.getInstancia()
								.generaDAOProductoEnPlataforma();

						boolean error = false;

						while (it.hasNext()) {

							TLineaVenta lineaVenta = it.next();
							TProductoEnPlataforma productoPlataforma = daoProductoPlataforma
									.read(lineaVenta.get_producto());

							if (productoPlataforma == null) {
								error = true;
								break;
							}

							lista_producto_plataforma.add(productoPlataforma);
						}

						if (!lista_producto_plataforma.isEmpty() && !error) {

							tVentaCompleta = new TVentaCompletaTOA();
							tVentaCompleta.colocar_datos(venta, lista_lineaVenta, lista_producto_plataforma,
									trabajador);

							tr.commit();
						} else {
							tr.rollback();
						}
					} else {
						tr.rollback();
					}
				} else {
					tr.rollback();
				}
			} else {
				tr.rollback();
			}
		}
		return tVentaCompleta;
	}

	/**
	 * Modifica una venta existente en la base de datos.
	 *
	 * @param tVenta Objeto TVenta que contiene la información de la venta a
	 *               modificar.
	 * @return Un entero que indica el resultado de la operación: 1 si la
	 *         modificación fue exitosa, -1 si hubo un error o la venta no existe.
	 * 
	 */
	public int modificar_venta(TVenta tVenta) {

		TManager tm = TManager.getInstance();
		Transaction tr = tm.createTransaction();
		int res = -1;

		if (tr != null) {

			tr.start();
			DAOVenta daoVenta = FactoriaIntegracion.getInstancia().generaDAOVenta();
			TVenta tVentaAux = daoVenta.read(tVenta.get_id());

			if (tVentaAux != null) {
				TTrabajador tTrabajador = FactoriaIntegracion.getInstancia().generaDAOTrabajador()
						.read(tVenta.get_trabajador());

				if (tTrabajador != null && tTrabajador.get_activo() == 1) {

					tVentaAux.set_trabajador(tVenta.get_trabajador());
					res = daoVenta.update(tVentaAux);

					if (res != -1) {
						tr.commit();
					}

					else {
						tr.rollback();
					}

				} else {
					tr.rollback();
				}

			} else {
				tr.rollback();
			}
		}
		return res;
	}

	/**
	 * Método para devolver una venta.
	 *
	 * @param tLineaVenta Objeto TLineaVenta que contiene la información de la línea
	 *                    de venta a devolver.
	 * @return int -1 si la devolución no se pudo realizar, 1 si la devolución fue
	 *         exitosa, o el resultado de la eliminación de la venta si no quedan
	 *         líneas activas.
	 *
	 */
	public int devolver_venta(TLineaVenta tLineaVenta) {

		TManager tm = TManager.getInstance();
		Transaction tr = tm.createTransaction();
		int res = -1;
		if (tr != null) {
			tr.start();

			DAOVenta daoVenta = FactoriaIntegracion.getInstancia().generaDAOVenta();
			TVenta tVenta = daoVenta.read(tLineaVenta.get_factura());

			if (tVenta != null) {

				DAOLineaVenta daoLineaVenta = FactoriaIntegracion.getInstancia().generaDAOLineaVenta();
				TLineaVenta tLineaVentaAux = daoLineaVenta.read(tLineaVenta);

				if (tLineaVentaAux != null) {

					DAOProductoEnPlataforma daoProductoPlataforma = FactoriaIntegracion.getInstancia()
							.generaDAOProductoEnPlataforma();
					TProductoEnPlataforma tProductoPlataforma = daoProductoPlataforma.read(tLineaVenta.get_producto());

					if (tProductoPlataforma != null && tLineaVentaAux.get_cantidad() >= tLineaVenta.get_cantidad()) {

						int cantidad = tLineaVentaAux.get_cantidad() - tLineaVenta.get_cantidad();
						double precio = tLineaVentaAux.get_precio_cantidad() / tLineaVentaAux.get_cantidad();
						double totalFactura = tVenta.get_total_factura() - tLineaVenta.get_cantidad() * precio;
						double precioCantidad = cantidad * precio;

						tLineaVentaAux.set_precio_cantidad(precioCantidad);
						tLineaVentaAux.set_cantidad(cantidad);

						tVenta.set_total_factura(totalFactura);

						if (daoLineaVenta.update(tLineaVentaAux) == 1) {

							if (tProductoPlataforma.get_activo() == 0 && tProductoPlataforma.get_cantidad() == 0) {
								tProductoPlataforma.set_activo(1);
							}

							tProductoPlataforma
									.set_cantidad(tProductoPlataforma.get_cantidad() + tLineaVenta.get_cantidad());

							// Error al actualizar el producto en plataforma
							if (daoProductoPlataforma.update(tProductoPlataforma) != -1) {

								TLineaVenta tLineaVenta_aux = daoLineaVenta.read(tLineaVenta);

								// Linea de venta no válida
								if (tLineaVenta_aux != null) {

									// Error al actualizar la venta
									if (daoVenta.update(tVenta) != -1) {

										if (tLineaVenta_aux.get_cantidad() != 0
												|| daoLineaVenta.delete(tLineaVenta) != -1) {

											Set<TLineaVenta> setLineaVenta = daoLineaVenta
													.read_all(tLineaVenta.get_factura());

											if (setLineaVenta != null) {

												if (setLineaVenta.stream().filter(venta -> venta.get_activo() == 1)
														.count() != 0
														|| daoVenta.delete(tLineaVenta.get_factura()) != -1) {
													tr.commit();
													res = 1;
												} else {
													tr.rollback();
												}
											} else {
												tr.rollback();
											}
										} else {
											tr.rollback();
										}
									} else {
										tr.rollback();
									}
								} else {
									tr.rollback();
								}
							} else {
								tr.rollback();
							}
						} else {
							tr.rollback();
						}
					} else {
						tr.rollback();
					}
				} else {
					tr.rollback();
				}
			} else {
				tr.rollback();
			}
		}

		return res;
	}

	/**
	 * Método para listar todas las ventas.
	 * 
	 * @return Un conjunto de objetos TVenta que representa todas las ventas.
	 *         Devuelve null si ocurre algún error durante la transacción o si no se
	 *         encuentran ventas.
	 * 
	 * @throws Exception Si ocurre algún error durante la transacción.
	 */
	public Set<TVenta> listar_todo_venta() {

		TManager tm = TManager.getInstance();
		Transaction tr = tm.createTransaction();
		Set<TVenta> res = null;

		if (tr != null) {
			tr.start();

			DAOVenta daoVenta = FactoriaIntegracion.getInstancia().generaDAOVenta();
			res = daoVenta.read_all();

			if (res != null) {
				tr.commit();
			}

			else {
				tr.rollback();
			}
		}

		return res;
	}

	/**
	 * Lista las ventas realizadas por un trabajador específico.
	 *
	 * @param id_trabajador El identificador del trabajador cuyas ventas se desean
	 *                      listar.
	 * @return Un conjunto de objetos TVenta que representan las ventas realizadas
	 *         por el trabajador. Si no se encuentran ventas o ocurre un error, se
	 *         devuelve null.
	 */
	public Set<TVenta> listar_por_trabajador_venta(int id_trabajador) {

		TManager tm = TManager.getInstance();
		Transaction tr = tm.createTransaction();
		Set<TVenta> res = null;

		if (tr != null) {
			tr.start();

			DAOVenta daoVenta = FactoriaIntegracion.getInstancia().generaDAOVenta();
			res = daoVenta.readVentaPorTrabajador(id_trabajador);

			if (res != null) {
				tr.commit();
			}

			else {
				tr.rollback();
			}
		}

		return res;
	}


	/**
	 * Lista las líneas de venta asociadas a un producto en una plataforma
	 * específica.
	 *
	 * @param id_producto_final El identificador del producto final.
	 * @return Un conjunto de objetos TLineaVenta que representan las líneas de
	 *         venta asociadas al producto en la plataforma. Si no se encuentran
	 *         líneas de venta, se devuelve null.
	 * @throws Exception Si ocurre un error durante la transacción o la lectura de
	 *                   las líneas de venta.
	 */
	public Set<TLineaVenta> listar_por_producto_en_plataforma_venta(int id_producto_final) {

		TManager tm = TManager.getInstance();
		Transaction tr = tm.createTransaction();
		Set<TLineaVenta> res = null;

		if (tr != null) {
			tr.start();

			DAOLineaVenta daoLineaVenta = FactoriaIntegracion.getInstancia().generaDAOLineaVenta();
			res = daoLineaVenta.readVentaPorProductoPlataforma(id_producto_final);
			;

			if (res != null) {
				tr.commit();
			}

			else {
				tr.rollback();
			}
		}

		return res;
	}

	/**
	 * Crea una nueva línea de venta.
	 *
	 * @param t  La línea de venta a crear.
	 * @param d  El DAO utilizado para interactuar con la base de datos de líneas de
	 *           venta.
	 * @param id El identificador de la factura asociada a la línea de venta.
	 * @throws VentaException Si ocurre un error al crear la línea de venta.
	 */
	private void cerrarLineaVenta(TLineaVenta t, DAOLineaVenta d, int id) throws VentaException {
		t.set_activo(1);
		t.set_factura(id);
		if (d.create(t) == -1)
			throw new VentaException("Error al crear la linea de venta");
	}

	/**
	 * Actualiza una línea de venta con la información proporcionada y ajusta el
	 * inventario del producto.
	 *
	 * @param lineaVentaFinal   La línea de venta final que se actualizará.
	 * @param lineaVentaCarrito La línea de venta del carrito que contiene la
	 *                          información a actualizar.
	 * @param producto          El producto asociado a la línea de venta.
	 * @param listaFinal        El conjunto de líneas de venta finales.
	 * @param total             El total acumulado de la venta.
	 * @return El nuevo total acumulado de la venta después de actualizar la línea
	 *         de venta.
	 */
	private double actualizarLineaVenta(TLineaVenta lineaVentaFinal, TLineaVenta lineaVentaCarrito,
			TProductoEnPlataforma producto, Set<TLineaVenta> listaFinal, double total) {

		lineaVentaFinal.set_cantidad(lineaVentaCarrito.get_cantidad());
		lineaVentaFinal.set_producto(lineaVentaCarrito.get_producto());

		double precioTotal = producto.get_precio() * lineaVentaFinal.get_cantidad();

		lineaVentaFinal.set_precio_cantidad(precioTotal);
		listaFinal.add(lineaVentaFinal);

		int cantidadTotal = producto.get_cantidad() - lineaVentaFinal.get_cantidad();

		total += producto.get_precio() * lineaVentaCarrito.get_cantidad();
		producto.set_cantidad(cantidadTotal);

		return total;
	}

	/**
	 * Verifica si un producto en plataforma es válido.
	 *
	 * @param p El producto en plataforma a verificar.
	 * @return true si el producto es válido (no es nulo, está activo y tiene
	 *         cantidad mayor a 0), false en caso contrario.
	 */
	private boolean esProductoValido(TProductoEnPlataforma p) {
		return p != null && p.get_activo() == 1 && p.get_cantidad() > 0;
	}

	/**
	 * Verifica si un producto en plataforma ha sido eliminado.
	 *
	 * @param p   El producto en plataforma a verificar.
	 * @param dao El DAO utilizado para interactuar con la base de datos de
	 *            productos en plataforma.
	 * @return true si el producto es nulo o su cantidad es 0 y la eliminación en la
	 *         base de datos falla; false en caso contrario.
	 */
	private boolean esProductoEliminado(TProductoEnPlataforma p, DAOProductoEnPlataforma dao) {
		return p == null || p.get_cantidad() == 0 && dao.delete(p.get_id()) == -1;
	}

	/**
	 * Comprueba si la lista de líneas de venta es correcta y crea la venta.
	 *
	 * @param listaFinal El conjunto de líneas de venta final.
	 * @param carrito    El carrito de compras que contiene la venta.
	 * @param tVenta     La venta a crear.
	 * @param total      El total acumulado de la venta.
	 * @return El ID de la venta creada si la operación es exitosa.
	 */
	@SuppressWarnings("unused")
	private int comprobarListaCorrecta(Set<TLineaVenta> listaFinal, TCarrito carrito, TVenta tVenta, double total) {

		if (!listaFinal.isEmpty()) {

			DAOVenta daoVenta = FactoriaIntegracion.getInstancia().generaDAOVenta();

			tVenta.set_total_factura(total);

			int id_venta = daoVenta.create(tVenta);

			if (id_venta != -1) {

				carrito.set_venta(tVenta);
				tVenta.set_id(id_venta);
				tVenta.set_activo(1);

				DAOLineaVenta daoLineaVenta = FactoriaIntegracion.getInstancia().generaDAOLineaVenta();
				listaFinal.forEach(tLineaVenta -> cerrarLineaVenta(tLineaVenta, daoLineaVenta, id_venta));

				carrito.set_lista_lineaVenta(listaFinal);

			} else {
				throw new VentaException("Error al crear la venta");
			}

			return id_venta;

		} else {
			throw new VentaException("Error al crear la venta");
		}
	}

	/**
	 * Recorre las líneas de venta del carrito y actualiza la cantidad de productos
	 * en la plataforma.
	 *
	 * @param it         El iterador de las líneas de venta del carrito.
	 * @param listaFinal El conjunto de líneas de venta final.
	 * @return El total acumulado de la venta.
	 */
	@SuppressWarnings("unused")
	private double recorrerVenta(Iterator<TLineaVenta> it, Set<TLineaVenta> listaFinal) {

		DAOProductoEnPlataforma daoProductoPlataforma = FactoriaIntegracion.getInstancia()
				.generaDAOProductoEnPlataforma();
		double total = 0;

		while (it.hasNext()) {

			TLineaVenta tLineaVentaCarrito = it.next();
			TProductoEnPlataforma tProductoPlataforma = daoProductoPlataforma.read(tLineaVentaCarrito.get_producto());

			if (esProductoValido(tProductoPlataforma)) {

				TLineaVenta tLineaVenta_final = new TLineaVenta();

				if (tProductoPlataforma.get_cantidad() >= tLineaVentaCarrito.get_cantidad()) {

					total = actualizarLineaVenta(tLineaVenta_final, tLineaVentaCarrito, tProductoPlataforma, listaFinal,
							total);

					if (daoProductoPlataforma.update(tProductoPlataforma) == -1)
						throw new VentaException("Error al actualizar el producto");

					TProductoEnPlataforma tProductoPlataforma_aux = daoProductoPlataforma
							.read(tProductoPlataforma.get_id());

					if (esProductoEliminado(tProductoPlataforma_aux, daoProductoPlataforma))
						throw new VentaException("Error al eliminar el producto");
				}
			} else
				throw new VentaException("Producto no válido");
		}
		return total;
	}

	/**
	 * Excepción personalizada para manejar errores específicos relacionados con las
	 * ventas. Esta excepción extiende RuntimeException, lo que significa que es una
	 * excepción no verificada.
	 * 
	 * <p>
	 * Se puede utilizar para lanzar errores con un mensaje específico o sin
	 * mensaje.
	 * </p>
	 * 
	 * <p>
	 * Ejemplo de uso:
	 * </p>
	 * 
	 * <pre>
	 * {@code
	 * if (condicionDeError) {
	 * 	throw new VentaException("Mensaje de error específico");
	 * }
	 * }
	 * </pre>
	 * 
	 * @see RuntimeException
	 */
	@SuppressWarnings("serial")
	class VentaException extends RuntimeException {
		public VentaException(String message) {
			super(message);
		}

		public VentaException() {
			super();
		}
	}
}
package Negocio.ProductoEnPlataforma;

import Integracion.FactoriaIntegracion.FactoriaIntegracion;
import Integracion.FactoriaQuery.FactoriaQuery;
import Integracion.FactoriaQuery.Query;
import Integracion.Plataforma.DAOPlataforma;
import Integracion.Producto.DAOProducto;
import Integracion.ProductoEnPlataforma.DAOProductoEnPlataforma;
import Integracion.Transaction.TManager;
import Integracion.Transaction.Transaction;
import Negocio.Plataforma.TPlataforma;
import Negocio.Producto.TProducto;
import java.util.HashSet;
import java.util.Set;

public class SAProductoEnPlataformaImp implements SAProductoEnPlataforma {

	@Override
	public int altaProductoEnPlataforma(TProductoEnPlataforma tProductoPlataforma) {

		int exito = -1;
		boolean existe = false;
		TManager tm = TManager.getInstance();
		Transaction tr = tm.createTransaction();
		DAOProductoEnPlataforma daoprodenplat = FactoriaIntegracion.getInstancia().generaDAOProductoEnPlataforma();
		DAOProducto daoProd = FactoriaIntegracion.getInstancia().generaDAOProducto();
		DAOPlataforma daoPlat = FactoriaIntegracion.getInstancia().generaDAOPlataforma();

		if (tr != null) {
			tr.start();

			// Verificar si el producto y la plataforma existen y están activos
			TProducto tprod = daoProd.read(tProductoPlataforma.get_id_producto());
			TPlataforma tplat = daoPlat.read(tProductoPlataforma.get_id_plataforma());
			if (tplat == null || tprod == null || tplat.get_activo() == 0 || tprod.get_activo() == 0) {
				tr.rollback();
				return -1; // Error si producto o plataforma son nulos o inactivos
			}

			// Verificar si ya existe una relación de producto en plataforma con los mismos
			// IDs
			for (TProductoEnPlataforma tpp : daoprodenplat.read_all()) {
				if (tpp.get_id_plataforma() == tProductoPlataforma.get_id_plataforma()
						&& tpp.get_id_producto() == tProductoPlataforma.get_id_producto()) {

					existe = true;

					if (tpp.get_activo() == 1) {
						exito = -1; // Si ya existe y está activo, no se permite la alta
						break;
					} else {
						// Si existe pero está inactivo, lo reactivamos y actualizamos los datos
						tProductoPlataforma.set_id(tpp.get_id());
						tpp.set_activo(1);
						tpp.set_precio(tProductoPlataforma.get_precio());
						tpp.set_cantidad(tProductoPlataforma.get_cantidad());

						exito = daoprodenplat.update(tpp);
						break;
					}
				}
			}

			// Si no existe, lo creamos
			if (!existe) {
				exito = daoprodenplat.create(tProductoPlataforma);
				tProductoPlataforma.set_id(exito);
			}

			// Confirmamos la transacción en caso de éxito, o la revertimos en caso de fallo
			if (exito == -1) {
				tr.rollback();
			} else {
				tr.commit();
			}
		}

		return exito;
	}

	@Override
	public int bajaProductoEnPlataforma(int id_producto_final) {

		int exito = -1;
		TManager tm = TManager.getInstance();
		Transaction tr = tm.createTransaction();

		if (tr != null) {
			tr.start();

			DAOProductoEnPlataforma daoprodenplat = FactoriaIntegracion.getInstancia().generaDAOProductoEnPlataforma();

			TProductoEnPlataforma t2 = daoprodenplat.read(id_producto_final);

			if (t2 != null && t2.get_activo() == 1) {
				exito = daoprodenplat.delete(id_producto_final);
				tr.commit();
			} else {
				tr.rollback();
			}
		}

		return exito;
	}

	@Override
	public int modificarProductoEnPlataforma(TProductoEnPlataforma tProductoPlataforma) {

		int exito = -1;

		TManager tm = TManager.getInstance();
		Transaction tr = tm.createTransaction();

		if (tr != null) {
			tr.start();

			DAOProductoEnPlataforma daoprodenplat = FactoriaIntegracion.getInstancia().generaDAOProductoEnPlataforma();
			TProductoEnPlataforma t2 = daoprodenplat.read(tProductoPlataforma.get_id());

			// Verificación de existencia del producto en plataforma
			if (t2 != null) {

				DAOProducto daoProd = FactoriaIntegracion.getInstancia().generaDAOProducto();
				TProducto tprod = daoProd.read(t2.get_id_producto());

				// Verificación del producto
				if (tprod != null && tprod.get_activo() != 0) {

					DAOPlataforma daoPlat = FactoriaIntegracion.getInstancia().generaDAOPlataforma();
					TPlataforma tplat = daoPlat.read(t2.get_id_plataforma());

					// Verificación de la plataforma
					if (tplat != null && tplat.get_activo() != 0) {

						// Actualización del producto en plataforma
						exito = daoprodenplat.update(tProductoPlataforma);
						if (exito != -1) {
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

		return exito;
	}

	@Override
	public TProductoEnPlataforma buscarProductoEnPlataforma(int id_producto_final) {
		TProductoEnPlataforma t2 = null;
		TManager tm = TManager.getInstance();
		Transaction tr = tm.createTransaction();
		if (tr != null) {
			tr.start();
			DAOProductoEnPlataforma daoProductoEnPlataforma = FactoriaIntegracion.getInstancia()
					.generaDAOProductoEnPlataforma();
			t2 = daoProductoEnPlataforma.read(id_producto_final);
			if (t2 != null) {
				tr.commit();
			} else {
				tr.rollback();
			}
		}
		return t2;
	}

	/**
	 * (non-Javadoc)
	 * 
	 * @see SAProductoEnPlataforma#listarTodosProductoEnPlataforma()
	 * @generated "UML a JPA
	 *            (com.ibm.xtools.transform.uml2.ejb3.java.jpa.internal.UML2JPATransform)"
	 */
	public Set<TProductoEnPlataforma> listarTodosProductoEnPlataforma() {
		// begin-user-code
		// TODO Auto-generated method stub
		return null;
		// end-user-code
	}

	@Override
	public Set<TProductoEnPlataforma> listarTodosProductoEnPlataforma(int aceptar) {
		Set<TProductoEnPlataforma> ths = new HashSet<>();

		TManager tm = TManager.getInstance();
		Transaction tr = tm.createTransaction();
		if (tr != null) {
			tr.start();

			DAOProductoEnPlataforma daoProductoPlataforma = FactoriaIntegracion.getInstancia()
					.generaDAOProductoEnPlataforma();
			ths = daoProductoPlataforma.read_all();
			if (ths == null) {
				tr.rollback();
			} else {
				tr.commit();
			}
		}

		return ths;
	}

	@Override
	public Set<TProductoEnPlataforma> listarPorPlataforma(int id_plataforma) {
		Set<TProductoEnPlataforma> ths = new HashSet<>();

		TManager tm = TManager.getInstance();
		Transaction tr = tm.createTransaction();
		if (tr != null) {
			tr.start();

			DAOProductoEnPlataforma daoProductoPlataforma = FactoriaIntegracion.getInstancia()
					.generaDAOProductoEnPlataforma();
			ths = daoProductoPlataforma.read_by_platform(id_plataforma);
			if (ths == null) {
				tr.rollback();
			} else {
				tr.commit();
			}
		}

		return ths;
		// return
		// FactoriaIntegracion.getInstancia().generaDAOProductoEnPlataforma().read_by_platform(id_plataforma);
	}

	@Override
	public Set<TProductoEnPlataforma> listarPorProducto(int id_producto) {
		Set<TProductoEnPlataforma> ths = new HashSet<>();

		TManager tm = TManager.getInstance();
		Transaction tr = tm.createTransaction();
		if (tr != null) {
			tr.start();

			DAOProductoEnPlataforma daoProductoPlataforma = FactoriaIntegracion.getInstancia()
					.generaDAOProductoEnPlataforma();
			ths = daoProductoPlataforma.read_by_product(id_producto);
			if (ths == null) {
				tr.rollback();
			} else {
				tr.commit();
			}
		}

		return ths;
		// return
		// FactoriaIntegracion.getInstancia().generaDAOProductoEnPlataforma().read_by_product(id_producto);
	}

	/**
	 * (non-Javadoc)
	 * 
	 * @see SAProductoEnPlataforma#calcularCantidadProductoenPlataforma()
	 * @generated "UML a JPA
	 *            (com.ibm.xtools.transform.uml2.ejb3.java.jpa.internal.UML2JPATransform)"
	 */
	public void calcularCantidadProductoenPlataforma() {
		// begin-user-code
		// TODO Auto-generated method stub

		// end-user-code
	}

	@Override
	public int calcularCantidadProductoEnPlataforma(int edad) {

		int cantidad = -1; // Valor inicial por defecto en caso de error
		TManager tm = TManager.getInstance();
		Transaction tr = tm.createTransaction();
		if (tr != null) {
			tr.start();

			FactoriaQuery fq = FactoriaQuery.getInstance();
			Query q = fq.getNewQuery("CalcularCantidadProductoEnPlataforma");

			// Verificación de la query

			Object calcular = q.execute(edad);

			if (calcular == null) {
				tr.rollback();
				return cantidad; // Devuelve -1 si no se pudo obtener la query
			}

			if (calcular != null && calcular instanceof Integer) {
				cantidad = (int) calcular;
				tr.commit(); // Solo hacemos commit si la ejecución fue exitosa
			} else {
				tr.rollback(); // En caso de fallo en la ejecución o resultado inválido
			}
		}

		return cantidad; // Devuelve la cantidad calculada o -1 si hubo error
	}

}

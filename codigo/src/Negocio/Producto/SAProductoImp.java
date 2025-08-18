
package Negocio.Producto;

import java.util.List;
import java.util.Set;

import Integracion.FactoriaIntegracion.FactoriaIntegracion;
import Integracion.Producto.DAOProducto;
import Integracion.ProductoEnPlataforma.DAOProductoEnPlataforma;
import Integracion.Transaction.TManager;
import Integracion.Transaction.Transaction;
import Negocio.ProductoEnPlataforma.TProductoEnPlataforma;

public class SAProductoImp implements SAProducto {

	public int altaProducto(TProducto tProducto) {

		int exito = -1;

		TManager tManager = TManager.getInstance();
		tManager.createTransaction();
		Transaction transaction = tManager.getTransaction();

		if (transaction != null) {
			transaction.start();

			DAOProducto daoProducto = FactoriaIntegracion.getInstancia().generaDAOProducto();
			TProducto pr = daoProducto.read_by_name(tProducto.get_nombre());

			if (pr == null) {
				exito = daoProducto.create(tProducto);
				if (exito != -1) {
					transaction.commit();
				} else
					transaction.rollback();

			} else if (pr.get_activo() == 0) {
				tProducto.set_id(pr.get_id());
				tProducto.set_activo(1);
				exito = daoProducto.update(tProducto);
				if (exito != -1) {
					exito = pr.get_id();
					transaction.commit();
				} else
					transaction.rollback();
			} else
				transaction.rollback();

		}
		return exito;

	}

	public int bajaProducto(int id_producto) {

		int exito = -1;

		TManager tManager = TManager.getInstance();
		tManager.createTransaction();
		Transaction transaction = tManager.getTransaction();

		if (transaction != null) {
			transaction.start();

			DAOProducto daoProducto = FactoriaIntegracion.getInstancia().generaDAOProducto();
			TProducto tpr = daoProducto.read(id_producto);

			if (tpr == null)
				transaction.rollback();
			else {

				DAOProductoEnPlataforma daoProdEnPlat = FactoriaIntegracion.getInstancia()
						.generaDAOProductoEnPlataforma();
				List<TProductoEnPlataforma> setProdEnPlat = daoProdEnPlat.read_by_product(tpr.get_id()).stream()
						.filter((TProductoEnPlataforma tpp) -> {
							return tpp.get_activo() == 1;
						}).toList();

				if (setProdEnPlat.size() == 0) {
					if (tpr.get_activo() == 1) {
						exito = daoProducto.delete(id_producto);
						transaction.commit();
					} else
						transaction.rollback();
				} else
					transaction.rollback();
			}
		}

		return exito;
	}

	public int modificarProducto(TProducto tProducto) {

		int exito = -1;

		TManager tManager = TManager.getInstance();
		tManager.createTransaction();
		Transaction transaction = tManager.getTransaction();

		if (transaction != null) {
			transaction.start();

			DAOProducto daoProducto = FactoriaIntegracion.getInstancia().generaDAOProducto();
			TProducto tpByName = daoProducto.read_by_name(tProducto.get_nombre());
			TProducto tpById = daoProducto.read(tProducto.get_id());

			if (tpByName == null
					|| (tpByName.get_id() == tpById.get_id() && tpByName.get_nombre().equals(tpById.get_nombre()))) {
				exito = daoProducto.update(tProducto);
				if (exito != -1)
					transaction.commit();
				else
					transaction.rollback();
			} else
				transaction.rollback();
		}
		return exito;

	}

	public Set<TProducto> listarTodoProducto() {

		Set<TProducto> tpr = null;

		TManager tManager = TManager.getInstance();
		tManager.createTransaction();
		Transaction transaction = tManager.getTransaction();

		if (transaction != null) {
			transaction.start();

			DAOProducto daoProducto = FactoriaIntegracion.getInstancia().generaDAOProducto();
			tpr = daoProducto.read_all();

			if (tpr == null)
				transaction.rollback();
			else
				transaction.commit();
		}
		return tpr;
	}

	public TProducto buscarProducto(int id_producto) {

		TProducto tpr = null;

		TManager tManager = TManager.getInstance();
		tManager.createTransaction();
		Transaction transaction = tManager.getTransaction();

		if (transaction != null) {
			transaction.start();

			DAOProducto daoProducto = FactoriaIntegracion.getInstancia().generaDAOProducto();
			tpr = daoProducto.read(id_producto);

			if (tpr == null)
				transaction.rollback();
			else
				transaction.commit();
		}
		return tpr;
	}

	public Set<TProducto> listarPorTipoProducto(String tipo) {

		Set<TProducto> tprT = null;

		TManager tManager = TManager.getInstance();
		tManager.createTransaction();
		Transaction transaction = tManager.getTransaction();

		if (transaction != null) {
			transaction.start();

			DAOProducto daoProducto = FactoriaIntegracion.getInstancia().generaDAOProducto();
			tprT = daoProducto.read_by_type(tipo);

			if (tprT == null)
				transaction.rollback();
			else
				transaction.commit();

		}
		return tprT;
	}
}
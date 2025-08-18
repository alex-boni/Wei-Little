
package Negocio.Plataforma;

import Integracion.FactoriaIntegracion.FactoriaIntegracion;
import Integracion.Plataforma.DAOPlataforma;
import Integracion.ProductoEnPlataforma.DAOProductoEnPlataforma;
import Integracion.Transaction.TManager;
import Integracion.Transaction.Transaction;
import Negocio.ProductoEnPlataforma.TProductoEnPlataforma;

import java.util.Set;

public class SAPlataformaImp implements SAPlataforma {

	public int altaPlataforma(TPlataforma tPlataforma) {
		int res = -1; // Inicializamos evento como fallo
		TManager tm = TManager.getInstance();
		Transaction tr = tm.createTransaction();

		if (tr != null) {
			tr.start();

			DAOPlataforma daoPlataforma = FactoriaIntegracion.getInstancia().generaDAOPlataforma();
			TPlataforma tPlataformaByName = daoPlataforma.read_by_name(tPlataforma.get_nombre());

			// Si la plataforma no existe, creamos una nueva
			if (tPlataformaByName == null) {
				int id = daoPlataforma.create(tPlataforma);
				if (id != -1) {
					res = id; // Evento de creación exitosa
					tr.commit();
				} else {
					tr.rollback();
				}

				// Si la plataforma existe pero está inactiva, la reactivamos
			} else if (tPlataformaByName.get_activo() == 0) {
				tPlataforma.set_id(tPlataformaByName.get_id());
				int id = daoPlataforma.update(tPlataforma);
				if (id != -1) {
					res = tPlataformaByName.get_id(); // Evento de reactivación exitosa
					tr.commit();
				} else {
					tr.rollback();
				}
			} else {
				tr.rollback(); // La plataforma ya estaba activa, no hay cambios
			}
		}

		return res;

	}

	public int bajaPlataforma(int id_plataforma) {

		int res = -1;

		TManager tm = TManager.getInstance();
		Transaction tr = tm.createTransaction();
		if (tr != null) {
			tr.start();

			DAOPlataforma daoPlataforma = FactoriaIntegracion.getInstancia().generaDAOPlataforma();
			TPlataforma plataforma = daoPlataforma.read(id_plataforma);

			// Verificar si la plataforma existe y está activa

			// Verificar si la plataforma existe y está activa
			if (plataforma != null && plataforma.get_activo() == 1) {
				DAOProductoEnPlataforma daoProdEnPlat = FactoriaIntegracion.getInstancia()
						.generaDAOProductoEnPlataforma();
				Set<TProductoEnPlataforma> setProdEnPlat = daoProdEnPlat.read_by_platform(plataforma.get_id());

				// Si no hay productos asociados a la plataforma, eliminarla
				boolean existe = false;
				for (TProductoEnPlataforma tProdEnPlat : setProdEnPlat) {
					if (tProdEnPlat.get_activo() == 1) {
						existe = true;
						break;
					}
				}
				if (!existe) {
					res = daoPlataforma.delete(id_plataforma);
					if (res != -1)
						tr.commit();
					else
						tr.rollback();
				} else {
					tr.rollback(); // No se puede eliminar si hay productos asociados
				}
			} else {
				tr.rollback(); // La plataforma no existe o ya está desactivada
			}
		}

		return res;
	}

	public int modificarPlataforma(TPlataforma tPlataforma) {
		int res = -1; // Inicializamos con valor de error
		TManager tm = TManager.getInstance();
		Transaction tr = tm.createTransaction();
		if (tr != null) {
			tr.start();

			DAOPlataforma daoPlataforma = FactoriaIntegracion.getInstancia().generaDAOPlataforma();
			TPlataforma plataformaExistente = daoPlataforma.read(tPlataforma.get_id());

			// Verificamos si la plataforma a modificar existe
			if (plataformaExistente == null) {
				res = -3; // La plataforma no existe
				tr.rollback();
			} else {
				// Verificamos si el nuevo nombre ya está siendo usado por otra plataforma
				TPlataforma plataformaConMismoNombre = daoPlataforma.read_by_name(tPlataforma.get_nombre());
				if (plataformaConMismoNombre != null
						&& plataformaConMismoNombre.get_id() != plataformaExistente.get_id()) {
					res = -2;// El nombre ya está en uso por otra plataforma
					tr.rollback();

				} else {

					// Si todo está correcto, procedemos a la actualización
					res = daoPlataforma.update(tPlataforma);
					if (res != -1) {
						tr.commit(); // Si la actualización fue exitosa, hacemos commit
					} else {
						tr.rollback(); // Si hubo un error en la actualizacion, hacemos rollback
					}
				}
			}
		}

		return res;
	}

	public TPlataforma buscarPlataforma(int idPlataforma) {
		TManager tm = TManager.getInstance();
		Transaction tr = tm.createTransaction();
		TPlataforma plataforma = null;
		if (tr != null) {
			tr.start();

			DAOPlataforma daoPlataforma = FactoriaIntegracion.getInstancia().generaDAOPlataforma();
			plataforma = daoPlataforma.read(idPlataforma);

			if (plataforma != null) {
				tr.commit(); // Si se encuentra la plataforma, hacemos commit
			} else {
				tr.rollback(); // Si no existe, hacemos rollback
			}
		}

		return plataforma;

	}

	public Set<TPlataforma> listarTodasPlataforma() {
		TManager tm = TManager.getInstance();
		Transaction tr = tm.createTransaction();
		Set<TPlataforma> lista = null;

		if (tr != null) {
			tr.start();

			DAOPlataforma daoPlataforma = FactoriaIntegracion.getInstancia().generaDAOPlataforma();
			lista = daoPlataforma.read_all();

			if (lista != null) {
				tr.commit(); // Si la lectura es exitosa, hacemos commit
			} else {
				tr.rollback(); // Si hay algún error, rollback
			}
		}

		return lista;

	}
}
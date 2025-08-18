
package Negocio.Habilidad;

import Integracion.FactoriaIntegracion.FactoriaIntegracion;
import Integracion.Trabajador.DAOVinculacionTrabajadorHabilidad;
import Integracion.Transaction.TManager;
import Integracion.Transaction.Transaction;
import Integracion.Habilidad.DAOHabilidad;
import Negocio.Trabajador.TVinculacionTrabHab;

import java.util.HashSet;

import java.util.Set;

public class SAHabilidadImp implements SAHabilidad {

	public int altaHabilidad(THabilidad tHabilidad) {
		int exito = -1;

		TManager tm = TManager.getInstance();

		Transaction tr = tm.createTransaction();
		// Comienza la transacción
		if (tr != null) {

			tr.start();
			DAOHabilidad daoHabilidad = FactoriaIntegracion.getInstancia().generaDAOHabilidad();
			THabilidad tHabilidadByName = daoHabilidad.read_by_name(tHabilidad.get_nombre());

			if (tHabilidadByName == null) {
				exito = daoHabilidad.create(tHabilidad);
				if (exito != -1) {
					tr.commit();
				} else
					tr.rollback();
			} else if (tHabilidadByName.get_activo() == 0) {
				tHabilidad.set_id(tHabilidadByName.get_id());
				exito = daoHabilidad.update(tHabilidad);
				if (exito != -1) {
					exito = tHabilidad.get_id();
					tr.commit();
				} else {
					// Evento sería -1 y debemos de hacer rollback()
					tr.rollback();
				}
			} else
				tr.rollback();
		}

		return exito;

	}

	public int bajaHabilidad(int id_habilidad) {
		int exito = -1;

		TManager tm = TManager.getInstance();
		Transaction tr = tm.createTransaction();
		if (tr != null) {
			tr.start();
			DAOHabilidad daoHabilidad = FactoriaIntegracion.getInstancia().generaDAOHabilidad();
			THabilidad tHabilidad = daoHabilidad.read(id_habilidad);
			if (tHabilidad != null) {
				if (tHabilidad.get_activo() == 1) {
					DAOVinculacionTrabajadorHabilidad daoVinculacion = FactoriaIntegracion.getInstancia()
							.generaDAOVinculacionTrabajadorHabilidad();
					Set<TVinculacionTrabHab> ts = daoVinculacion.read_all_by_habilidad(id_habilidad);
					if (ts.size() == 0) {
						exito = daoHabilidad.delete(id_habilidad);
						tr.commit();
					} else
						tr.rollback();

				} else
					tr.rollback();

			} else
				tr.rollback();
		}

		return exito;

	}

	public int modificarHabilidad(THabilidad tHabilidad) {
		// NO ID = -3 , NONAME = -2 , kO = -1
		int exito = -1;

		TManager tm = TManager.getInstance();
		Transaction tr = tm.createTransaction();
		if (tr != null) {
			tr.start();

			DAOHabilidad daoHabilidad = FactoriaIntegracion.getInstancia().generaDAOHabilidad();
			THabilidad tHabilidadById = daoHabilidad.read(tHabilidad.get_id());

			if (tHabilidadById == null) {

				exito = -3;
				tr.rollback();
				// return Evento.RES_MODIFICAR_HABILIDAD_KONOID;
			} else {
				THabilidad tHabilidadByName = daoHabilidad.read_by_name(tHabilidad.get_nombre());
				if (tHabilidadByName != null && tHabilidadById.get_id() != tHabilidadByName.get_id()) {
					exito = -2;
					tr.rollback();
					// return Evento.RES_MODIFICAR_HABILIDAD_KOSAMENAME;
				} else {
					exito = daoHabilidad.update(tHabilidad);
					if (exito != -1) {
						tr.commit();
					} else {
						tr.rollback();
					}
				}

			}
		}

		return exito;

	}

	public THabilidad buscarHabilidad(int id_habilidad) {

		THabilidad tHabilidad = null;

		TManager tm = TManager.getInstance();
		Transaction tr = tm.createTransaction();
		if (tr != null) {
			tr.start();

			DAOHabilidad daoHabilidad = FactoriaIntegracion.getInstancia().generaDAOHabilidad();
			tHabilidad = daoHabilidad.read(id_habilidad);

			if (tHabilidad != null) {
				tr.commit();
			} else {
				tr.rollback();
			}
		}

		return tHabilidad;
	}

	public Set<THabilidad> listarTodasHabilidades(int aceptar) {

		Set<THabilidad> ths = new HashSet<>();

		TManager tm = TManager.getInstance();
		Transaction tr = tm.createTransaction();
		if (tr != null) {
			tr.start();

			DAOHabilidad daoHabilidad = FactoriaIntegracion.getInstancia().generaDAOHabilidad();
			ths = daoHabilidad.read_all();
			if (ths == null) {
				tr.rollback();
			} else
				tr.commit();
		}

		return ths;
	}

	public Set<TVinculacionTrabHab> listarTodoHabilidadPorTrabajador(int id_trabajador) {

		Set<TVinculacionTrabHab> tListaVinculos = null;

		TManager tm = TManager.getInstance();
		Transaction tr = tm.createTransaction();
		if (tr != null) {

			tr.start();

			DAOVinculacionTrabajadorHabilidad daoVinculacionHab = FactoriaIntegracion.getInstancia()
					.generaDAOVinculacionTrabajadorHabilidad();
			tListaVinculos = daoVinculacionHab.read_all_by_trabajador(id_trabajador);
			if (tListaVinculos == null) {
				tr.rollback();
			} else {
				tr.commit();
			}
		}

		return tListaVinculos;
	}


}
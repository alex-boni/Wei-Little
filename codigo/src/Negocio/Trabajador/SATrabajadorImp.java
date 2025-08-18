
package Negocio.Trabajador;

import Integracion.FactoriaIntegracion.FactoriaIntegracion;
import Integracion.Habilidad.DAOHabilidad;
import Integracion.Trabajador.DAOTrabajador;
import Integracion.Trabajador.DAOVinculacionTrabajadorHabilidad;
import Integracion.Transaction.TManager;
import Integracion.Transaction.Transaction;
import Negocio.Habilidad.THabilidad;

import java.util.Set;

public class SATrabajadorImp implements SATrabajador {

	public int alta_trabajador(TTrabajador tTrabajador) {

		int exito = -1;

		TManager m = TManager.getInstance();
		Transaction tr = m.createTransaction();
		if (tr != null) {

			tr.start();
			DAOTrabajador daoTrabajador = FactoriaIntegracion.getInstancia().generaDAOTrabajador();
			TTrabajador trabajador = daoTrabajador.read_by_dni(tTrabajador.get_dni());

			if (trabajador == null) {
				exito = daoTrabajador.create(tTrabajador);
				tTrabajador.set_id(exito);
				tr.commit();
			} else if (trabajador.get_activo() == 0) {
				tTrabajador.set_activo(1);
				tTrabajador.set_id(trabajador.get_id());
				exito = daoTrabajador.update(tTrabajador);
				tr.commit();
			} else {
				tr.rollback();
			}
		}

		return exito;
	}

	public int baja_trabajador(int id_trabajador) {
		int exito = -1;

		TManager m = TManager.getInstance();
		Transaction tr = m.createTransaction();

		if (tr != null) {
			tr.start();
			DAOTrabajador daoTrabajador = FactoriaIntegracion.getInstancia().generaDAOTrabajador();
			TTrabajador trabajador = daoTrabajador.read(id_trabajador);

			if (trabajador != null) {
				if (trabajador.get_activo() == 1) {
					exito = daoTrabajador.delete(id_trabajador);
					if (exito != -1)
						tr.commit();
					else
						tr.rollback();
				} else
					tr.rollback();
			} else
				tr.rollback();
		}

		return exito;

	}

	public TTrabajador listar_por_id(int id_trabajador) {

		TManager m = TManager.getInstance();
		Transaction tr = m.createTransaction();
		TTrabajador trabajador = null;
		if (tr != null) {
			tr.start();
			DAOTrabajador daoTrabajador = FactoriaIntegracion.getInstancia().generaDAOTrabajador();
			trabajador = daoTrabajador.read(id_trabajador);
			tr.commit();
		}

		return trabajador;

	}

	public int modificar_trabajador(TTrabajador tTrabajador) {
		int exito = -1;

		TManager m = TManager.getInstance();
		Transaction tr = m.createTransaction();
		if (tr != null) {

			tr.start();

			DAOTrabajador daoTrabajador = FactoriaIntegracion.getInstancia().generaDAOTrabajador();
			TTrabajador trabajadorById = daoTrabajador.read(tTrabajador.get_id());
			TTrabajador trabajadorByDni = daoTrabajador.read_by_dni(tTrabajador.get_dni());
			if (trabajadorById != null
					&& (trabajadorByDni == null || trabajadorByDni.get_id() == trabajadorById.get_id())) {
				exito = daoTrabajador.delete(trabajadorById.get_id());
				if (exito == -1)
					tr.rollback();
				else {
					exito = daoTrabajador.update(tTrabajador);
					if (exito != -1)
						tr.commit();
					else
						tr.rollback();
				}
			} else
				tr.rollback();

		}

		return exito;
	}

	public Set<TTrabajador> listar_trabajadores() {

		TManager m = TManager.getInstance();
		Transaction tr = m.createTransaction();
		Set<TTrabajador> trabajadores = null;

		if (tr != null) {
			tr.start();
			DAOTrabajador daoTrabajador = FactoriaIntegracion.getInstancia().generaDAOTrabajador();
			trabajadores = daoTrabajador.read_all();
			tr.commit();
		}

		return trabajadores;
	}

	public int vincular_habilidad(TVinculacionTrabHab vinculacion) {
		int exito = -1;

		TManager m = TManager.getInstance();
		Transaction tr = m.createTransaction();
		if (tr != null) {
			tr.start();
			DAOTrabajador daoTrabajador = FactoriaIntegracion.getInstancia().generaDAOTrabajador();
			DAOHabilidad daoHabilidad = FactoriaIntegracion.getInstancia().generaDAOHabilidad();
			TTrabajador trabajador = daoTrabajador.read(vinculacion.get_id_trabajador());
			THabilidad habilidad = daoHabilidad.read(vinculacion.get_id_habilidad());
			if (habilidad != null && trabajador != null && habilidad.get_activo() == 1
					&& trabajador.get_activo() == 1) {
				DAOVinculacionTrabajadorHabilidad daoVinculacion = FactoriaIntegracion.getInstancia()
						.generaDAOVinculacionTrabajadorHabilidad();

				TVinculacionTrabHab vinculacion2 = new TVinculacionTrabHab();
				vinculacion2 = daoVinculacion.read(vinculacion.get_id_trabajador(), vinculacion.get_id_habilidad());
				if (vinculacion2 == null) {
					vinculacion2 = daoVinculacion.create(vinculacion);
					if (vinculacion2 != null) {
						tr.commit();
						exito = 1;
					} else
						tr.rollback();
				} else if (vinculacion2.get_activo() == 0) {
					vinculacion2.set_activo(1);
					exito = daoVinculacion.update(vinculacion2);
					if (exito != -1)
						tr.commit();
					else
						tr.rollback();

				} else
					tr.rollback();

			} else
				tr.rollback();
		}

		return exito;
	}

	public int desvincular_habilidad(TVinculacionTrabHab vinculacion) {
		int exito = -1;

		TManager m = TManager.getInstance();
		Transaction tr = m.createTransaction();
		if (tr != null) {
			tr.start();

			DAOVinculacionTrabajadorHabilidad daoVinculacion = FactoriaIntegracion.getInstancia()
					.generaDAOVinculacionTrabajadorHabilidad();
			TVinculacionTrabHab vinculacion2 = daoVinculacion.read(vinculacion.get_id_trabajador(),
					vinculacion.get_id_habilidad());
			if (vinculacion2 != null) {
				if (vinculacion.get_activo() == 1) {
					exito = daoVinculacion.delete(vinculacion.get_id_trabajador(), vinculacion.get_id_habilidad());
					if (exito != -1)
						tr.commit();
					else
						tr.rollback();
				} else
					tr.rollback();
			} else
				tr.rollback();
		}

		return exito;
	}

	public Set<TVinculacionTrabHab> listar_por_habilidad(int id_habilidad) {
		TManager m = TManager.getInstance();
		Transaction tr = m.createTransaction();
		Set<TVinculacionTrabHab> vinculaciones = null;
		if (tr != null) {
			tr.start();
			vinculaciones = FactoriaIntegracion.getInstancia().generaDAOVinculacionTrabajadorHabilidad()
					.read_all_by_habilidad(id_habilidad);
			if (vinculaciones != null)
				tr.commit();
			else
				tr.rollback();
		}

		return vinculaciones;
	}

	public Set<TVinculacionTrabHab> listar_por_trabajador(int id_trabajador) {

		TManager m = TManager.getInstance();
		Transaction tr = m.createTransaction();
		tr.start();

		Set<TVinculacionTrabHab> lista = FactoriaIntegracion.getInstancia().generaDAOVinculacionTrabajadorHabilidad()
				.read_all_by_trabajador(id_trabajador);
		tr.commit();

		return lista;
	}

	public Set<TTrabajador> listar_por_tipo(String tipo) {

		TManager m = TManager.getInstance();
		Transaction tr = m.createTransaction();
		Set<TTrabajador> trabajadores = null;

		if (tr != null) {
			tr.start();
			trabajadores = FactoriaIntegracion.getInstancia().generaDAOTrabajador().read_by_tipo(tipo);
			if (trabajadores != null)
				tr.commit();
			else
				tr.rollback();
		}

		return trabajadores;
	}
}
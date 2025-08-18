package Integracion.Trabajador;

import java.util.Set;

import org.junit.After;
import org.junit.Assert;
import org.junit.Test;

import Integracion.ConfiguracionBD.LimpiarTabla;
import Integracion.FactoriaIntegracion.FactoriaIntegracion;
import Integracion.Habilidad.DAOHabilidad;
import Integracion.Transaction.TManager;
import Integracion.Transaction.Transaction;
import Negocio.Habilidad.THabilidad;
import Negocio.Trabajador.TTrabajador;
import Negocio.Trabajador.TVendedor;
import Negocio.Trabajador.TVinculacionTrabHab;

public class TrabajadorHabilidadInTest {

	private boolean exito(TVinculacionTrabHab vinculo) {
		return vinculo != null;
	}

	private boolean compMos(TVinculacionTrabHab tpIn, TVinculacionTrabHab tpMos) {
		if (tpIn.get_id_trabajador() != tpMos.get_id_trabajador()
				&& tpIn.get_id_habilidad() != tpMos.get_id_habilidad()) {
			return false;
		}
		if (tpIn.get_activo() != tpMos.get_activo()) {
			return false;
		}

		return true;
	}

	private Transaction crearTransaccion() {
		TManager tManager = TManager.getInstance();
		tManager.createTransaction();
		Transaction transaction = tManager.getTransaction();
		transaction.start();
		return transaction;
	}

	@Test
	public void testAltaTrabajadorHabilidadNuevoOK() {

		Transaction transaction = crearTransaccion();

		DAOTrabajador daot = FactoriaIntegracion.getInstancia().generaDAOTrabajador();
		DAOHabilidad daoh = FactoriaIntegracion.getInstancia().generaDAOHabilidad();
		TTrabajador tpIn = new TVendedor();
		tpIn.colocar_datos(1, "testaltratrabajador", "99999919J");
		((TVendedor) tpIn).set_idioma("chino");
		int id = daot.create(tpIn);
		TTrabajador trabajador = daot.read(id);
		THabilidad tpHab = new THabilidad();
		tpHab.colocar_datos("testhabilidad", 3, 1);
		int id2 = daoh.create(tpHab);
		THabilidad habilidad = daoh.read(id2);
		DAOVinculacionTrabajadorHabilidad daoth = FactoriaIntegracion.getInstancia()
				.generaDAOVinculacionTrabajadorHabilidad();
		TVinculacionTrabHab tpVin = new TVinculacionTrabHab();
		tpVin.set_id_trabajador(trabajador.get_id());
		tpVin.set_id_habilidad(habilidad.get_id());
		TVinculacionTrabHab ex = daoth.create(tpVin);

		transaction.commit();
		Assert.assertTrue(exito(ex));
	}

	@Test
	public void testBajaTrabajadorHabilidadOK() {

		Transaction transaction = crearTransaccion();

		DAOTrabajador daot = FactoriaIntegracion.getInstancia().generaDAOTrabajador();
		DAOHabilidad daoh = FactoriaIntegracion.getInstancia().generaDAOHabilidad();
		TTrabajador tpIn = new TVendedor();
		tpIn.colocar_datos(1, "testbajatrabajador", "99959919J");
		((TVendedor) tpIn).set_idioma("chino");
		int id = daot.create(tpIn);
		TTrabajador trabajador = daot.read(id);
		THabilidad tpHab = new THabilidad();
		tpHab.colocar_datos("testhabilidadbaja", 3, 1);
		int id2 = daoh.create(tpHab);
		THabilidad habilidad = daoh.read(id2);
		DAOVinculacionTrabajadorHabilidad daoth = FactoriaIntegracion.getInstancia()
				.generaDAOVinculacionTrabajadorHabilidad();
		TVinculacionTrabHab tpVin = new TVinculacionTrabHab();
		tpVin.set_id_trabajador(trabajador.get_id());
		tpVin.set_id_habilidad(habilidad.get_id());
		daoth.create(tpVin);

		int ex = daoth.delete(tpVin.get_id_trabajador(), tpVin.get_id_habilidad());

		transaction.commit();
		Assert.assertTrue(ex != -1);
	}

	@Test
	public void testMostrarTrabajadorHabilidadOK() {

		Transaction transaction = crearTransaccion();

		DAOTrabajador daot = FactoriaIntegracion.getInstancia().generaDAOTrabajador();
		DAOHabilidad daoh = FactoriaIntegracion.getInstancia().generaDAOHabilidad();
		TTrabajador tpIn = new TVendedor();
		tpIn.colocar_datos(1, "testmoditrabajador", "99459919J");
		((TVendedor) tpIn).set_idioma("chino");
		int id = daot.create(tpIn);
		TTrabajador trabajador = daot.read(id);
		THabilidad tpHab = new THabilidad();
		tpHab.colocar_datos("testhabilidadmodi", 3, 1);
		int id2 = daoh.create(tpHab);
		THabilidad habilidad = daoh.read(id2);
		DAOVinculacionTrabajadorHabilidad daoth = FactoriaIntegracion.getInstancia()
				.generaDAOVinculacionTrabajadorHabilidad();
		TVinculacionTrabHab tpVin = new TVinculacionTrabHab();
		tpVin.set_id_trabajador(trabajador.get_id());
		tpVin.set_id_habilidad(habilidad.get_id());
		daoth.create(tpVin);

		TVinculacionTrabHab tpVin2 = daoth.read(tpVin.get_id_trabajador(), tpVin.get_id_habilidad());

		transaction.commit();
		Assert.assertTrue(compMos(tpVin, tpVin2));
	}

	@Test
	public void testModificarTrabajadorHabilidadOK() {

		Transaction transaction = crearTransaccion();

		DAOTrabajador daot = FactoriaIntegracion.getInstancia().generaDAOTrabajador();
		DAOHabilidad daoh = FactoriaIntegracion.getInstancia().generaDAOHabilidad();
		TTrabajador tpIn = new TVendedor();
		tpIn.colocar_datos(1, "testmoditrabajador", "93459919J");
		((TVendedor) tpIn).set_idioma("chino");
		int id = daot.create(tpIn);
		TTrabajador trabajador = daot.read(id);
		THabilidad tpHab = new THabilidad();
		tpHab.colocar_datos("testhabilidadmodii", 3, 1);
		int id2 = daoh.create(tpHab);
		THabilidad habilidad = daoh.read(id2);
		DAOVinculacionTrabajadorHabilidad daoth = FactoriaIntegracion.getInstancia()
				.generaDAOVinculacionTrabajadorHabilidad();
		TVinculacionTrabHab tpVin = new TVinculacionTrabHab();
		tpVin.set_id_trabajador(trabajador.get_id());
		tpVin.set_id_habilidad(habilidad.get_id());
		daoth.create(tpVin);

		int ex = daoth.update(tpVin);

		transaction.commit();
		Assert.assertTrue(ex != -1);

	}

	@Test
	public void testMostrar_all_TrabajadorHabilidadOK() {

		Transaction transaction = crearTransaccion();

		DAOTrabajador daot = FactoriaIntegracion.getInstancia().generaDAOTrabajador();
		DAOHabilidad daoh = FactoriaIntegracion.getInstancia().generaDAOHabilidad();
		TTrabajador tpIn = new TVendedor();
		tpIn.colocar_datos(1, "testmoditrabajador", "93459919J");
		((TVendedor) tpIn).set_idioma("chino");
		int id = daot.create(tpIn);
		TTrabajador trabajador = daot.read(id);
		THabilidad tpHab = new THabilidad();
		tpHab.colocar_datos("testhabilidadmodii", 3, 1);
		int id2 = daoh.create(tpHab);
		THabilidad habilidad = daoh.read(id2);
		DAOVinculacionTrabajadorHabilidad daoth = FactoriaIntegracion.getInstancia()
				.generaDAOVinculacionTrabajadorHabilidad();
		TVinculacionTrabHab tpVin = new TVinculacionTrabHab();
		tpVin.set_id_trabajador(trabajador.get_id());
		tpVin.set_id_habilidad(habilidad.get_id());
		daoth.create(tpVin);
		Set<TVinculacionTrabHab> vinculos = daoth.read_all();
		vinculos.add(tpVin);

		transaction.commit();
		Assert.assertNotNull(vinculos);

	}

	@Test
	public void testMostrar_all_TrabajadorHabilidad_por_habilidadOK() {

		Transaction transaction = crearTransaccion();

		DAOTrabajador daot = FactoriaIntegracion.getInstancia().generaDAOTrabajador();
		DAOHabilidad daoh = FactoriaIntegracion.getInstancia().generaDAOHabilidad();
		TTrabajador tpIn = new TVendedor();
		tpIn.colocar_datos(1, "testlistatrabajador", "91259919P");
		((TVendedor) tpIn).set_idioma("chino");
		int id = daot.create(tpIn);
		TTrabajador trabajador = daot.read(id);
		THabilidad tpHab = new THabilidad();
		tpHab.colocar_datos("testhabilidadmlist", 3, 1);
		int id2 = daoh.create(tpHab);
		THabilidad habilidad = daoh.read(id2);
		DAOVinculacionTrabajadorHabilidad daoth = FactoriaIntegracion.getInstancia()
				.generaDAOVinculacionTrabajadorHabilidad();
		TVinculacionTrabHab tpVin = new TVinculacionTrabHab();
		tpVin.set_id_trabajador(trabajador.get_id());
		tpVin.set_id_habilidad(habilidad.get_id());
		daoth.create(tpVin);
		Set<TVinculacionTrabHab> vinculos = daoth.read_all_by_habilidad(id2);
		vinculos.add(tpVin);

		transaction.commit();
		Assert.assertNotNull(vinculos);
	}

	@After
	public void limpiarDatos() {
		LimpiarTabla.limpiarTabla("TRABAJADOR");
		LimpiarTabla.limpiarTabla("VENDEDOR");
		LimpiarTabla.limpiarTabla("SUPERVISOR");
		LimpiarTabla.limpiarTabla("TRABAJADORHABILIDAD");
		LimpiarTabla.limpiarTabla("HABILIDAD");
	}

}

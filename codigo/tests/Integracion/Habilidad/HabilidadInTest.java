package Integracion.Habilidad;

import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.Set;

import org.junit.After;
import org.junit.Assert;
import org.junit.Test;

import Integracion.ConfiguracionBD.LimpiarTabla;
import Integracion.FactoriaIntegracion.FactoriaIntegracion;
import Integracion.Trabajador.DAOTrabajador;
import Integracion.Trabajador.DAOVinculacionTrabajadorHabilidad;
import Integracion.Transaction.TManager;
import Integracion.Transaction.Transaction;
import Negocio.Habilidad.THabilidad;
import Negocio.Trabajador.TTrabajador;
import Negocio.Trabajador.TVendedor;
import Negocio.Trabajador.TVinculacionTrabHab;

public class HabilidadInTest {

	private boolean exito(int id) {
		return id != -1;
	}

	private boolean compMod(THabilidad thIn, THabilidad thMod) {

		return thIn.get_id() == thMod.get_id() && thIn.get_activo() != thMod.get_activo()
				&& thIn.get_nombre() != thMod.get_nombre();
	}

	private boolean compMos(THabilidad thIn, THabilidad thMos) {
		return thIn.get_id() == thMos.get_id() && thIn.get_activo() == thMos.get_activo()
				&& thIn.get_nombre().equals(thMos.get_nombre());
	}

	private Transaction crearTransaccion() {
		TManager tManager = TManager.getInstance();
		tManager.createTransaction();
		Transaction transaction = tManager.getTransaction();
		transaction.start();
		return transaction;
	}

	@Test
	public void testAltaHabilidadNuevoOK() {

		Transaction transaction = crearTransaccion();

		DAOHabilidad daoh = FactoriaIntegracion.getInstancia().generaDAOHabilidad();
		THabilidad thIn = new THabilidad();
		thIn.colocar_datos("test", 1, 1);
		int id = daoh.create(thIn);

		transaction.commit();
		Assert.assertTrue(exito(id));
	}

	@Test
	public void testBajaHabilidadOK() {

		Transaction transaction = crearTransaccion();

		DAOHabilidad daoh = FactoriaIntegracion.getInstancia().generaDAOHabilidad();
		THabilidad thIn = new THabilidad();
		thIn.colocar_datos("testBaja", 1, 1);
		int id = daoh.create(thIn);
		int ex = daoh.delete(id);

		transaction.commit();
		Assert.assertTrue(exito(ex));
	}

	@Test
	public void testMostrarHabilidadOK() {

		Transaction transaction = crearTransaccion();

		DAOHabilidad daoh = FactoriaIntegracion.getInstancia().generaDAOHabilidad();
		THabilidad thIn = new THabilidad();
		thIn.colocar_datos("testMos", 1, 1);
		int id = daoh.create(thIn);
		thIn.set_id(id);
		THabilidad thMos = daoh.read(id);

		transaction.commit();
		Assert.assertTrue(compMos(thIn, thMos));
	}

	@Test
	public void testModificarHabilidadOK() {

		Transaction transaction = crearTransaccion();

		DAOHabilidad daoh = FactoriaIntegracion.getInstancia().generaDAOHabilidad();
		THabilidad thIn = new THabilidad();
		thIn.colocar_datos("testIn", 1, 1);
		int id = daoh.create(thIn);
		thIn.set_id(id);

		THabilidad thMod = new THabilidad();
		thMod.colocar_datos("testMod", 0, 0);
		thMod.set_id(id);
		int ex = daoh.update(thMod);
		thMod = daoh.read(id);
		Assert.assertTrue(exito(ex));

		transaction.commit();
		Assert.assertTrue(compMod(thIn, thMod));

	}

	@Test
	public void testMostrar_all_HabilidadOK() {

		Transaction transaction = crearTransaccion();

		DAOHabilidad daoh = FactoriaIntegracion.getInstancia().generaDAOHabilidad();
		THabilidad thIn = new THabilidad();
		Set<THabilidad> tpsIn = new LinkedHashSet<>();
		int id = 1;
		while (thIn != null) {
			thIn = daoh.read(id);
			if (thIn != null)
				tpsIn.add(thIn);
			id++;
		}
		Set<THabilidad> tpsModaohll = daoh.read_all();

		Iterator<THabilidad> i = tpsIn.iterator();
		Iterator<THabilidad> j = tpsModaohll.iterator();
		boolean ok = tpsIn.size() == tpsModaohll.size();
		while (i.hasNext() && j.hasNext()) {
			ok = compMos((THabilidad) i.next(), (THabilidad) j.next());
		}

		transaction.commit();
		Assert.assertTrue(ok);

	}

	@Test
	public void testMostrar_por_trabajadorOK() {

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
		LimpiarTabla.limpiarTabla("HABILIDAD");
		LimpiarTabla.limpiarTabla("TRABAJADOR");
		LimpiarTabla.limpiarTabla("VENDEDOR");
		LimpiarTabla.limpiarTabla("SUPERVISOR");
		LimpiarTabla.limpiarTabla("TRABAJADORHABILIDAD");
	}

}

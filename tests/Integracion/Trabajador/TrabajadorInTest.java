package Integracion.Trabajador;

import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.Set;

import org.junit.After;
import org.junit.Assert;
import org.junit.Test;

import Integracion.ConfiguracionBD.LimpiarTabla;
import Integracion.FactoriaIntegracion.FactoriaIntegracion;
import Integracion.Transaction.TManager;
import Integracion.Transaction.Transaction;
import Negocio.Trabajador.TSupervisor;
import Negocio.Trabajador.TTrabajador;
import Negocio.Trabajador.TVendedor;

public class TrabajadorInTest {

	private boolean exito(int id) {
		return id != -1;
	}

	private boolean compMod(TTrabajador tpIn, TTrabajador tpMod) {

		if (tpIn.get_id() != tpMod.get_id()) {
			return false;
		}

		if (tpIn.get_activo() == tpMod.get_activo()) {
			return false;
		}

		if (tpIn.get_nombre().equalsIgnoreCase(tpMod.get_nombre())) {
			return false;
		}
		if (tpIn.get_dni().equalsIgnoreCase(tpMod.get_dni())) {
			return false;
		}

		if (tpIn instanceof TSupervisor && tpMod instanceof TSupervisor) {
			return !((TSupervisor) tpIn).get_experiencia().equalsIgnoreCase(((TSupervisor) tpMod).get_experiencia());
		}

		if (tpIn instanceof TVendedor && tpMod instanceof TVendedor) {
			return !((TVendedor) tpIn).get_idioma().equalsIgnoreCase(((TVendedor) tpMod).get_idioma());
		}

		return false;
	}

	private boolean compMos(TTrabajador tpIn, TTrabajador tpMos) {
		if (tpIn.get_id() != tpMos.get_id()) {
			return false;
		}
		if (tpIn.get_activo() != tpMos.get_activo()) {
			return false;
		}

		if (!tpIn.get_nombre().toUpperCase().equals(tpMos.get_nombre())) {
			return false;
		}
		if (!tpIn.get_dni().toUpperCase().equals(tpMos.get_dni())) {
			return false;
		}

		if (tpIn instanceof TSupervisor && tpMos instanceof TSupervisor) {
			return ((TSupervisor) tpIn).get_experiencia().toUpperCase().equals(((TSupervisor) tpMos).get_experiencia());
		}

		if (tpIn instanceof TVendedor && tpMos instanceof TVendedor) {
			return ((TVendedor) tpIn).get_idioma().toUpperCase().equals(((TVendedor) tpMos).get_idioma());
		}

		// Si los tipos no coinciden, se consideran diferentes
		return false;
	}

	private Transaction crearTransaccion() {
		TManager tManager = TManager.getInstance();
		tManager.createTransaction();
		Transaction transaction = tManager.getTransaction();
		transaction.start();
		return transaction;
	}

	@Test
	public void testAltaTrabajadorNuevoOK() {

		Transaction transaction = crearTransaccion();

		DAOTrabajador daot = FactoriaIntegracion.getInstancia().generaDAOTrabajador();
		TTrabajador tpIn = new TTrabajador();
		tpIn.colocar_datos(1, "testtrabajador", "7777777Y");
		if (tpIn instanceof TSupervisor) {
			((TSupervisor) tpIn).set_experiencia("bastante");
		} else if (tpIn instanceof TVendedor) {
			((TVendedor) tpIn).set_idioma("ingles");
		}
		int id = daot.create(tpIn);

		transaction.commit();
		Assert.assertTrue(exito(id));
	}

	@Test
	public void testBajaTrabajadorOK() {

		Transaction transaction = crearTransaccion();

		DAOTrabajador daot = FactoriaIntegracion.getInstancia().generaDAOTrabajador();
		TTrabajador tpIn = new TTrabajador();
		tpIn.colocar_datos(1, "testBajatrabajador", "99999999J");
		if (tpIn instanceof TSupervisor) {
			((TSupervisor) tpIn).set_experiencia("poca");
		} else if (tpIn instanceof TVendedor) {
			((TVendedor) tpIn).set_idioma("chino");
		}

		int id = daot.create(tpIn);
		tpIn.set_id(id);

		int ex = daot.delete(tpIn.get_id());

		transaction.commit();
		Assert.assertTrue(exito(ex));
	}

	@Test
	public void testMostrarTrabajadorOK() {

		Transaction transaction = crearTransaccion();

		DAOTrabajador daot = FactoriaIntegracion.getInstancia().generaDAOTrabajador();
		TTrabajador tpIn = new TSupervisor();
		tpIn.colocar_datos(1, "testMostrabajador", "00000000S");
		((TSupervisor) tpIn).set_experiencia("poca");
		int id = daot.create(tpIn);
		tpIn.set_id(id);
		TTrabajador tpMos = daot.read(id);

		transaction.commit();
		Assert.assertTrue(compMos(tpIn, tpMos));
	}

	@Test
	public void testModificarTrabajadorOK() {

		Transaction transaction = crearTransaccion();

		DAOTrabajador daot = FactoriaIntegracion.getInstancia().generaDAOTrabajador();
		TTrabajador tpIn = new TVendedor();
		tpIn.colocar_datos(1, "testIntrabajador", "88888888U");
		((TVendedor) tpIn).set_idioma("aleman");
		tpIn.set_activo(1);
		int id = daot.create(tpIn);
		tpIn.set_id(id);

		TTrabajador tpMod = new TVendedor();
		tpMod.colocar_datos(0, "testModtrabajador", "72834839G");
		((TVendedor) tpMod).set_idioma("ruso");
		tpMod.set_activo(0);
		tpMod.set_id(id);
		int ex = daot.update(tpMod);
		tpMod = daot.read(id);
		Assert.assertTrue(exito(ex));

		transaction.commit();
		Assert.assertTrue(compMod(tpIn, tpMod));

	}

	@Test
	public void testMostrar_all_TrabajadorOK() {

		Transaction transaction = crearTransaccion();

		DAOTrabajador daot = FactoriaIntegracion.getInstancia().generaDAOTrabajador();
		TTrabajador tpIn = new TTrabajador();
		Set<TTrabajador> tpsIn = new LinkedHashSet<>();
		int id = 1;
		while (tpIn != null) {
			tpIn = daot.read(id);
			if (tpIn != null)
				tpsIn.add(tpIn);
			id++;
		}
		Set<TTrabajador> tpsModaopll = daot.read_all();

		Iterator<TTrabajador> i = tpsIn.iterator();
		Iterator<TTrabajador> j = tpsModaopll.iterator();
		boolean ok = tpsIn.size() == tpsModaopll.size();
		while (i.hasNext() && j.hasNext()) {
			ok = compMos((TTrabajador) i.next(), (TTrabajador) j.next());
		}

		transaction.commit();
		Assert.assertTrue(ok);

	}

	@Test
	public void testMostrar_all_TrabajadorTipoOK() {

		Transaction transaction = crearTransaccion();

		DAOTrabajador daoTrabajador = FactoriaIntegracion.getInstancia().generaDAOTrabajador();

		TTrabajador tpIn = new TSupervisor();
		tpIn.colocar_datos(1, "supervisorprueba", "33333333L");
		((TSupervisor) tpIn).set_experiencia("mucha");
		String tipo = "supervisor";
		Set<TTrabajador> trabajadores = daoTrabajador.read_by_tipo(tipo);

		Assert.assertNotNull(trabajadores);

		daoTrabajador = FactoriaIntegracion.getInstancia().generaDAOTrabajador();

		TTrabajador tpIn2 = new TVendedor();
		tpIn2.colocar_datos(1, "vendedorprueba", "44444444P");
		((TVendedor) tpIn2).set_idioma("ingles");
		tipo = "vendedor";
		trabajadores = daoTrabajador.read_by_tipo(tipo);

		transaction.commit();
		Assert.assertNotNull(trabajadores);
	}

	@After
	public void limpiarDatos() {
		LimpiarTabla.limpiarTabla("TRABAJADOR");
		LimpiarTabla.limpiarTabla("VENDEDOR");
		LimpiarTabla.limpiarTabla("SUPERVISOR");
	}

}

package Integracion.Plataforma;

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
import Negocio.Plataforma.TPlataforma;

public class PlataformaInTest {

	private boolean exito(int id) {
		return id != -1;
	}

	private boolean compMod(TPlataforma tpIn, TPlataforma tpMod) {

		return tpIn.get_id() == tpMod.get_id() && tpIn.get_activo() != tpMod.get_activo()
				&& tpIn.get_nombre() != tpMod.get_nombre();
	}

	private boolean compMos(TPlataforma tpIn, TPlataforma tpMos) {
		return tpIn.get_id() == tpMos.get_id() && tpIn.get_activo() == tpMos.get_activo()
				&& tpIn.get_nombre().equals(tpMos.get_nombre());
	}

	private Transaction crearTransaccion() {
		TManager tManager = TManager.getInstance();
		tManager.createTransaction();
		Transaction transaction = tManager.getTransaction();
		transaction.start();
		return transaction;
	}

	@Test
	public void testAltaPlataformaNuevoOK() {

		Transaction transaction = crearTransaccion();

		DAOPlataforma daop = FactoriaIntegracion.getInstancia().generaDAOPlataforma();
		TPlataforma tpIn = new TPlataforma();
		tpIn.colocar_datos("test", 1);
		int id = daop.create(tpIn);

		transaction.commit();
		Assert.assertTrue(exito(id));
	}

	@Test
	public void testBajaPlataformaOK() {

		Transaction transaction = crearTransaccion();

		DAOPlataforma daop = FactoriaIntegracion.getInstancia().generaDAOPlataforma();
		TPlataforma tpIn = new TPlataforma();
		tpIn.colocar_datos("testBaja", 1);
		int id = daop.create(tpIn);
		int ex = daop.delete(id);

		transaction.commit();
		Assert.assertTrue(exito(ex));
	}

	@Test
	public void testMostrarPlataformaOK() {

		Transaction transaction = crearTransaccion();

		DAOPlataforma daop = FactoriaIntegracion.getInstancia().generaDAOPlataforma();
		TPlataforma tpIn = new TPlataforma();
		tpIn.colocar_datos("testMos", 1);
		int id = daop.create(tpIn);
		tpIn.set_id(id);
		TPlataforma tpMos = daop.read(id);

		transaction.commit();
		Assert.assertTrue(compMos(tpIn, tpMos));
	}

	@Test
	public void testModificarPlataformaOK() {

		Transaction transaction = crearTransaccion();

		DAOPlataforma daop = FactoriaIntegracion.getInstancia().generaDAOPlataforma();
		TPlataforma tpIn = new TPlataforma();
		tpIn.colocar_datos("testIn", 1);
		int id = daop.create(tpIn);
		tpIn.set_id(id);

		TPlataforma tpMod = new TPlataforma();
		tpMod.colocar_datos("testMod", 0);
		tpMod.set_id(id);
		int ex = daop.update(tpMod);
		tpMod = daop.read(id);
		Assert.assertTrue(exito(ex));

		transaction.commit();
		Assert.assertTrue(compMod(tpIn, tpMod));

	}

	@Test
	public void testMostrar_all_PlataformaOK() {

		Transaction transaction = crearTransaccion();

		DAOPlataforma daop = FactoriaIntegracion.getInstancia().generaDAOPlataforma();
		TPlataforma tpIn = new TPlataforma();
		Set<TPlataforma> tpsIn = new LinkedHashSet<>();
		int id = 1;
		while (tpIn != null) {
			tpIn = daop.read(id);
			if (tpIn != null)
				tpsIn.add(tpIn);
			id++;
		}
		Set<TPlataforma> tpsModaopll = daop.read_all();

		Iterator<TPlataforma> i = tpsIn.iterator();
		Iterator<TPlataforma> j = tpsModaopll.iterator();
		boolean ok = tpsIn.size() == tpsModaopll.size();
		while (i.hasNext() && j.hasNext()) {
			ok = compMos((TPlataforma) i.next(), (TPlataforma) j.next());
		}

		transaction.commit();
		Assert.assertTrue(ok);

	}

	@After
	public void limpiarDatos() {
		LimpiarTabla.limpiarTabla("PLATAFORMA");
	}

}

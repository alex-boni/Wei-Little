package Integracion.Producto;

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
import Negocio.Producto.TComplemento;
import Negocio.Producto.TProducto;
import Negocio.Producto.TVideojuego;

public class ProductoInTest {
	private boolean exito(int id) {
		return id != -1;
	}

	private boolean compMod(TProducto tpIn, TProducto tpMod) {

		if (tpIn.get_id() != tpMod.get_id()) {

			return false;
		}

		if (tpIn.get_activo() == tpMod.get_activo()) {

			return false;
		}

		if (tpIn.get_nombre().equalsIgnoreCase(tpMod.get_nombre())) {

			return false;

		}
		if (tpIn.get_marca().equalsIgnoreCase(tpMod.get_marca())) {

			return false;
		}

		if (tpIn instanceof TComplemento && tpMod instanceof TComplemento) {

			double pesoIn = ((TComplemento) tpIn).get_peso();
			double pesoMod = ((TComplemento) tpMod).get_peso();

			return pesoIn != pesoMod;
		}

		if (tpIn instanceof TVideojuego && tpMod instanceof TVideojuego) {
			int edadIn = ((TVideojuego) tpIn).get_restriccionEdad();
			int edadMod = ((TVideojuego) tpMod).get_restriccionEdad();
			System.out.println(edadIn + " " + edadMod);
			return edadIn != edadMod;
		}
		return false;

	}

	private boolean compMos(TProducto tpIn, TProducto tpMos) {

		if (tpIn.get_id() != tpMos.get_id()) {

			return false;
		}
		if (tpIn.get_activo() != tpMos.get_activo()) {

			return false;
		}

		if (!tpIn.get_nombre().equalsIgnoreCase(tpMos.get_nombre())) {

			return false;
		}
		if (!tpIn.get_marca().equalsIgnoreCase(tpMos.get_marca())) {

			return false;
		}

		if (tpIn instanceof TComplemento && tpMos instanceof TComplemento) {

			double pesoIn = ((TComplemento) tpIn).get_peso();
			double pesoMos = ((TComplemento) tpMos).get_peso();

			return pesoIn == pesoMos;
		}

		if (tpIn instanceof TVideojuego && tpMos instanceof TVideojuego) {
			int edadIn = ((TVideojuego) tpIn).get_restriccionEdad();
			int edadMos = ((TVideojuego) tpMos).get_restriccionEdad();
			return edadIn == edadMos;
		}

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
	public void testProductoNuevoOK() {

		Transaction transaction = crearTransaccion();

		DAOProducto daop = FactoriaIntegracion.getInstancia().generaDAOProducto();
		TProducto tpIn = new TProducto("test", "testBrand", 1);
		if (tpIn instanceof TComplemento)
			((TComplemento) tpIn).set_peso(5.5);
		if (tpIn instanceof TVideojuego)
			((TVideojuego) tpIn).set_restriccionEdad(18);
		int id = daop.create(tpIn);

		transaction.commit();
		Assert.assertTrue(exito(id));
	}

	@Test
	public void testBajaProductoOK() {

		Transaction transaction = crearTransaccion();

		DAOProducto daop = FactoriaIntegracion.getInstancia().generaDAOProducto();
		TProducto tpIn = new TProducto("testBaja", "testBrand", 1);
		if (tpIn instanceof TComplemento)
			((TComplemento) tpIn).set_peso(10.0);
		if (tpIn instanceof TVideojuego)
			((TVideojuego) tpIn).set_restriccionEdad(7);
		int id = daop.create(tpIn);
		int ex = daop.delete(id);

		transaction.commit();
		Assert.assertTrue(exito(ex));
	}

	@Test
	public void testMostrarProductoOK() {

		Transaction transaction = crearTransaccion();

		DAOProducto daop = FactoriaIntegracion.getInstancia().generaDAOProducto();
		TProducto tpIn = new TComplemento("testMos", "testBrand", 1, 0.9);
		int id = daop.create(tpIn);
		tpIn.set_id(id);
		TProducto tpMos = daop.read(id);

		transaction.commit();
		Assert.assertTrue(compMos(tpIn, tpMos));
	}

	@Test
	public void testModificarProductoOK() {

		Transaction transaction = crearTransaccion();

		DAOProducto daot = FactoriaIntegracion.getInstancia().generaDAOProducto();
		TProducto tpIn = new TComplemento("testModifica", "apple", 1, 0.8);
		int id = daot.create(tpIn);
		tpIn.set_id(id);

		TProducto tpMod = new TComplemento("testMod", "ea", 0, 3.3);

		tpMod.set_activo(0);
		tpMod.set_id(id);

		int ex = daot.update(tpMod);
		tpMod = daot.read(id);

		Assert.assertTrue(exito(ex));

		transaction.commit();
		Assert.assertTrue(compMod(tpIn, tpMod));

	}

	@Test
	public void testMostrarAllProductosOK() {

		Transaction transaction = crearTransaccion();

		DAOProducto daop = FactoriaIntegracion.getInstancia().generaDAOProducto();
		TProducto tpIn = new TProducto("nombre", "marca", 0);
		Set<TProducto> tpsIn = new LinkedHashSet<>();
		int id = 1;
		while (tpIn != null) {
			tpIn = daop.read(id);
			if (tpIn != null)
				tpsIn.add(tpIn);
			id++;
		}
		Set<TProducto> tpsModaopll = daop.read_all();

		Iterator<TProducto> i = tpsIn.iterator();
		Iterator<TProducto> j = tpsModaopll.iterator();
		boolean ok = tpsIn.size() == tpsModaopll.size();
		while (i.hasNext() && j.hasNext()) {
			ok = compMos((TProducto) i.next(), (TProducto) j.next());
		}

		transaction.commit();
		Assert.assertTrue(ok);

	}

	@Test
	public void testMostrar_type_OK() {

		Transaction transaction = crearTransaccion();

		DAOProducto daoProducto = FactoriaIntegracion.getInstancia().generaDAOProducto();

		new TComplemento("testModifica", "apple", 1, 8.3);

		String tipo = "Complemento";

		Set<TProducto> productos = daoProducto.read_by_type(tipo);

		Assert.assertNotNull(productos);

		daoProducto = FactoriaIntegracion.getInstancia().generaDAOProducto();

		new TVideojuego("testMod", "ea", 1, 3);

		tipo = "Videojuego";
		productos = daoProducto.read_by_type(tipo);

		transaction.commit();
		Assert.assertNotNull(productos);

	}

	@After
	public void limpiarDatos() {
		LimpiarTabla.limpiarTabla("PRODUCTO");
	}

}

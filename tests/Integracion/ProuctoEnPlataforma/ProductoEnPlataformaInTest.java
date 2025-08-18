package Integracion.ProuctoEnPlataforma;

import java.util.Iterator;
import java.util.Set;

import org.junit.After;
import org.junit.Assert;
import org.junit.Test;

import Integracion.ConfiguracionBD.LimpiarTabla;
import Integracion.FactoriaIntegracion.FactoriaIntegracion;
import Integracion.FactoriaQuery.FactoriaQuery;
import Integracion.Plataforma.DAOPlataforma;
import Integracion.Producto.DAOProducto;
import Integracion.ProductoEnPlataforma.DAOProductoEnPlataforma;
import Integracion.Transaction.TManager;
import Integracion.Transaction.Transaction;
import Negocio.Plataforma.TPlataforma;
import Negocio.Producto.TComplemento;
import Negocio.Producto.TProducto;
import Negocio.Producto.TVideojuego;
import Negocio.ProductoEnPlataforma.TProductoEnPlataforma;

public class ProductoEnPlataformaInTest {

	private boolean exito(int id) {
		return id != -1;
	}

	private boolean compMod(TProductoEnPlataforma tpIn, TProductoEnPlataforma tpMod) {

		if (tpIn.get_id() != tpMod.get_id()) {
			return false;
		}

		if (tpIn.get_id_producto() != tpMod.get_id_producto()) {
			return false;
		}

		if (tpIn.get_id_plataforma() != tpMod.get_id_plataforma()) {
			return false;
		}

		if (tpIn.get_activo() != tpMod.get_activo() || tpIn.get_cantidad() != tpMod.get_cantidad()
				|| (tpIn.get_precio() != tpMod.get_precio())) {
			return true;
		}

		return false;
	}

	private boolean compMos(TProductoEnPlataforma tpIn, TProductoEnPlataforma tpMos) {

		if (tpIn.get_id() != tpMos.get_id()) {
			return false;
		}

		if (tpIn.get_activo() != tpMos.get_activo()) {
			return false;
		}

		if (tpIn.get_cantidad() != tpMos.get_cantidad()) {
			return false;
		}

		if (tpIn.get_id_producto() != tpMos.get_id_producto()) {
			return false;
		}

		if (tpIn.get_id_plataforma() != tpMos.get_id_plataforma()) {
			return false;
		}

		if (tpIn.get_precio() != tpMos.get_precio()) {
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

	private TProductoEnPlataforma crearProductoEnPlataforma() {

		DAOProductoEnPlataforma daot = FactoriaIntegracion.getInstancia().generaDAOProductoEnPlataforma();
		DAOProducto daop = FactoriaIntegracion.getInstancia().generaDAOProducto();
		DAOPlataforma daopl = FactoriaIntegracion.getInstancia().generaDAOPlataforma();

		TProducto producto = new TProducto("test1", "testBrand1", 1);
		if (producto instanceof TComplemento)
			((TComplemento) producto).set_peso(5.5);
		if (producto instanceof TVideojuego)
			((TVideojuego) producto).set_restriccionEdad(18);
		int idpr = daop.create(producto);
		producto.set_id(idpr);

		TPlataforma plataforma = new TPlataforma();
		plataforma.colocar_datos("test", 1);
		int idpl = daopl.create(plataforma);
		plataforma.set_id(idpl);

		TProductoEnPlataforma tpIn = new TProductoEnPlataforma();

		tpIn.colocar_datos(1, 39, 3, idpr, idpl);

		int id = daot.create(tpIn);

		tpIn.set_id(id);

		return tpIn;
	}

	@Test
	public void testAltaProductoEnPlataformaNuevaOK() {

		Transaction transaction = crearTransaccion();

		TProductoEnPlataforma prodplat = crearProductoEnPlataforma();

		transaction.commit();
		Assert.assertTrue(exito(prodplat.get_id()));
	}

	@Test
	public void testBajaProductoEnPlataformaOK() {

		Transaction transaction = crearTransaccion();

		DAOProductoEnPlataforma daot = FactoriaIntegracion.getInstancia().generaDAOProductoEnPlataforma();

		TProductoEnPlataforma prodPlat = this.crearProductoEnPlataforma();

		int ex = daot.delete(prodPlat.get_id());

		transaction.commit();
		Assert.assertTrue(exito(ex));
	}

	@Test
	public void testMostrarProductoEnPlataformaOK() {

		Transaction transaction = crearTransaccion();

		TProductoEnPlataforma prodPlat = this.crearProductoEnPlataforma();

		DAOProductoEnPlataforma daot = FactoriaIntegracion.getInstancia().generaDAOProductoEnPlataforma();

		TProductoEnPlataforma tpIn = new TProductoEnPlataforma();

		tpIn.colocar_datos(1, 39, 3, prodPlat.get_id_producto(), prodPlat.get_id_plataforma());

		int id = daot.create(tpIn);

		tpIn.set_id(id);

		TProductoEnPlataforma tpMos = daot.read(id);

		transaction.commit();
		Assert.assertTrue(compMos(tpIn, tpMos));
	}

	@Test
	public void testModificarProductoEnPlataformaOK() {

		Transaction transaction = crearTransaccion();

		TProductoEnPlataforma prodPlat = this.crearProductoEnPlataforma();

		DAOProductoEnPlataforma daot = FactoriaIntegracion.getInstancia().generaDAOProductoEnPlataforma();
		TProductoEnPlataforma tpMod = new TProductoEnPlataforma();

		tpMod.colocar_datos(0, 33, 23, prodPlat.get_id_producto(), prodPlat.get_id_plataforma());

		tpMod.set_id(prodPlat.get_id());

		int ex = daot.update(tpMod);

		tpMod = daot.read(prodPlat.get_id());

		Assert.assertTrue(exito(ex));

		transaction.commit();
		Assert.assertTrue(compMod(prodPlat, tpMod));

	}

	@Test
	public void testMostrarAllProductoEnPlataforma() {

		Transaction transaction = crearTransaccion();

		this.crearProductoEnPlataforma();

		DAOProductoEnPlataforma daot = FactoriaIntegracion.getInstancia().generaDAOProductoEnPlataforma();

		Set<TProductoEnPlataforma> tpsIn = daot.read_all();

		Assert.assertNotNull(tpsIn);

		Set<TProductoEnPlataforma> tpsModaopll = daot.read_all();

		Iterator<TProductoEnPlataforma> i = tpsIn.iterator();
		Iterator<TProductoEnPlataforma> j = tpsModaopll.iterator();

		boolean ok = tpsIn.size() == tpsModaopll.size();

		while (i.hasNext() && j.hasNext()) {
			ok = ok && compMos((TProductoEnPlataforma) i.next(), (TProductoEnPlataforma) j.next());
		}

		transaction.commit();
		Assert.assertTrue(ok);

	}

	@Test
	public void testMostrarProductoEnPlataformaPorPlataforma() {

		Transaction transaction = crearTransaccion();

		TProductoEnPlataforma prodPlat = this.crearProductoEnPlataforma();

		DAOProductoEnPlataforma daot = FactoriaIntegracion.getInstancia().generaDAOProductoEnPlataforma();

		Set<TProductoEnPlataforma> setIn = daot.read_by_platform(prodPlat.get_id_plataforma());

		transaction.commit();
		Assert.assertNotNull(setIn);
	}

	@Test
	public void testMostrarProductoEnPlataformaPorProducto() {

		Transaction transaction = crearTransaccion();

		TProductoEnPlataforma prodPlat = this.crearProductoEnPlataforma();

		DAOProductoEnPlataforma daot = FactoriaIntegracion.getInstancia().generaDAOProductoEnPlataforma();

		Set<TProductoEnPlataforma> setIn = daot.read_by_product(prodPlat.get_id_producto());

		transaction.commit();
		Assert.assertNotNull(setIn);
	}

	@Test
	public void testCalcularCantidadProductoEnPlataforma() {

		Transaction transaction = crearTransaccion();

		this.crearProductoEnPlataforma();

		int cantidad = (int) FactoriaQuery.getInstance().getNewQuery("CalcularCantidadProductoEnPlataforma")
				.execute(18);

		transaction.commit();
		Assert.assertTrue(cantidad == 0);
	}

	@After
	public void limpiarDatos() {
		LimpiarTabla.limpiarTabla("PLATAFORMA");
		LimpiarTabla.limpiarTabla("PRODUCTO");
		LimpiarTabla.limpiarTabla("PRODUCTOENPLATAFORMA");
	}

}

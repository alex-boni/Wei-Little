package Integracion.Venta;

import static org.junit.Assert.*;

import java.util.LinkedHashSet;
import java.util.Iterator;
import java.util.Set;

import org.junit.After;
import org.junit.Test;

import Integracion.ConfiguracionBD.LimpiarTabla;
import Integracion.FactoriaIntegracion.FactoriaIntegracion;
import Integracion.Plataforma.DAOPlataforma;
import Integracion.Producto.DAOProducto;
import Integracion.ProductoEnPlataforma.DAOProductoEnPlataforma;
import Integracion.Trabajador.DAOTrabajador;
import Integracion.Transaction.TManager;
import Integracion.Transaction.Transaction;
import Negocio.Plataforma.TPlataforma;
import Negocio.Producto.TProducto;
import Negocio.Producto.TVideojuego;
import Negocio.ProductoEnPlataforma.TProductoEnPlataforma;
import Negocio.Trabajador.TSupervisor;
import Negocio.Trabajador.TTrabajador;
import Negocio.Venta.TLineaVenta;
import Negocio.Venta.TVenta;

public class VentaInTest {

	private boolean exito(int id) {
		return id != -1;
	}

	private boolean compReadVenta(TVenta tvIn, TVenta tvRead) {
		return tvIn.get_activo() == tvRead.get_activo() && tvIn.get_id() == tvRead.get_id()
				&& tvIn.get_total_factura() == tvRead.get_total_factura()
				&& tvIn.get_trabajador() == tvRead.get_trabajador();
	}
	
	private boolean compReadLineaVenta(TLineaVenta tLVIn, TLineaVenta tLVRead) {
		return tLVIn.get_activo() == tLVRead.get_activo() && tLVIn.get_cantidad() == tLVRead.get_cantidad()
				&& tLVIn.get_factura() == tLVRead.get_factura()
				&& tLVIn.get_precio_cantidad() == tLVRead.get_precio_cantidad()
				&& tLVIn.get_producto() == tLVRead.get_producto();
	}

	private TTrabajador crearTrabajador(String nombre, String dni) {

		DAOTrabajador daoTrab = FactoriaIntegracion.getInstancia().generaDAOTrabajador();
		TTrabajador tTrab = new TSupervisor();
		tTrab.colocar_datos(1, nombre, dni);
		((TSupervisor) tTrab).set_experiencia("Mucha");
		int id = daoTrab.create(tTrab);
		tTrab.set_id(id);

		return tTrab;
	}

	private TPlataforma crearPlataforma(String nombre) {

		DAOPlataforma daoPlat = FactoriaIntegracion.getInstancia().generaDAOPlataforma();
		TPlataforma tPlat = new TPlataforma();
		tPlat.set_nombre(nombre);
		int id = daoPlat.create(tPlat);
		tPlat.set_id(id);

		return tPlat;
	}

	private TProducto crearProducto(String nombre) {

		DAOProducto daoProd = FactoriaIntegracion.getInstancia().generaDAOProducto();
		TProducto tProd = new TVideojuego(nombre, "AAA", 1, 16);
		int id = daoProd.create(tProd);
		tProd.set_id(id);

		return tProd;
	}

	private TProductoEnPlataforma crearProductoEnPlataforma(int idProd, int idPlat) {

		DAOProductoEnPlataforma daoPPl = FactoriaIntegracion.getInstancia().generaDAOProductoEnPlataforma();
		TProductoEnPlataforma tPPl = new TProductoEnPlataforma();
		tPPl.set_id_plataforma(idPlat);
		tPPl.set_id_producto(idProd);
		tPPl.set_precio(34);
		tPPl.set_cantidad(10);
		tPPl.set_activo(1);
		int id = daoPPl.create(tPPl);
		tPPl.set_id(id);

		return tPPl;
	}

	private TVenta crearVenta(int idTrab, double total, int activo) {

		DAOVenta daoV = FactoriaIntegracion.getInstancia().generaDAOVenta();
		TVenta tV = new TVenta();
		tV.set_activo(activo);
		tV.set_total_factura(total);
		tV.set_trabajador(idTrab);
		int id = daoV.create(tV);
		tV.set_id(id);

		return tV;
	}

	private TLineaVenta crearLineaVenta(int idVenta, int idProd, int cantidad, double precio, int activo) {

		DAOProductoEnPlataforma daoPPl = FactoriaIntegracion.getInstancia().generaDAOProductoEnPlataforma();
		DAOLineaVenta daoLV = FactoriaIntegracion.getInstancia().generaDAOLineaVenta();
		TLineaVenta tLV = new TLineaVenta();
		tLV.set_activo(activo);
		tLV.set_cantidad(cantidad);
		tLV.set_factura(idVenta);
		tLV.set_precio_cantidad(precio);
		tLV.set_producto(idProd);
		
		TProductoEnPlataforma tPPl = daoPPl.read(idProd);
		tPPl.set_cantidad(tPPl.get_cantidad() - cantidad);
		daoPPl.update(tPPl);
		daoLV.create(tLV);

		return tLV;
	}

	@Test
	public void testCrearVenta() {

		Transaction t = TManager.getInstance().createTransaction();

		if (t != null) {

			t.start();

			DAOVenta daoV = FactoriaIntegracion.getInstancia().generaDAOVenta();
			DAOLineaVenta daoLV = FactoriaIntegracion.getInstancia().generaDAOLineaVenta();

			TTrabajador tTrab = crearTrabajador("Pepe", "12345678A");
			TPlataforma tPlat = crearPlataforma("Consola");
			TProducto tProd = crearProducto("Mario");
			TProductoEnPlataforma tPPl = crearProductoEnPlataforma(tProd.get_id(), tPlat.get_id());

			TVenta tv = new TVenta();
			tv.set_activo(1);
			tv.set_total_factura(tPPl.get_precio() * tPPl.get_cantidad());
			tv.set_trabajador(tTrab.get_id());
			int id = daoV.create(tv);
			tv.set_id(id);

			assertTrue("Deberia haberse creado la venta", exito(id));

			TLineaVenta tLV = new TLineaVenta();
			tLV.set_activo(1);
			tLV.set_cantidad(tPPl.get_cantidad());
			tLV.set_factura(tv.get_id());
			tLV.set_precio_cantidad(tPPl.get_precio() * tPPl.get_cantidad());
			tLV.set_producto(tProd.get_id());

			int exito = daoLV.create(tLV);

			assertTrue("Deberia haberse creado la linea de venta", exito(exito));
			t.commit();
		} else 
			fail("No se ha podido crear la transaccion");
		
	}

	@Test
	public void testReadVenta() {

		Transaction t = TManager.getInstance().createTransaction();

		if (t != null) {

			t.start();

			DAOVenta daoV = FactoriaIntegracion.getInstancia().generaDAOVenta();
			
			TTrabajador tTrab = crearTrabajador("Pepe", "12345678A");
			TProducto tProd = crearProducto("Mario");
			TPlataforma tPlat = crearPlataforma("Consola");
			TProductoEnPlataforma tPPl = crearProductoEnPlataforma(tProd.get_id(), tPlat.get_id());
			
			TVenta tvIn = crearVenta(tTrab.get_id(), tPPl.get_cantidad() * tPPl.get_precio(), 1);
			TVenta tvRead = daoV.read(tvIn.get_id());

			assertTrue("Deberia ser la misma venta", compReadVenta(tvIn, tvRead));
			
			DAOLineaVenta daoLV = FactoriaIntegracion.getInstancia().generaDAOLineaVenta();
			
			TLineaVenta tLVIn = crearLineaVenta(tvIn.get_id(), tProd.get_id(), tPPl.get_cantidad(), tPPl.get_precio(), 1);
			TLineaVenta tLVRead = daoLV.read(tLVIn);
			
			assertTrue("Deberia ser la misma linea de venta", compReadLineaVenta(tLVIn, tLVRead));
			t.commit();
		} else 
			fail("No se ha podido crear la transaccion");
		
	}

	@Test
	public void testReadAllVenta() {
		
		Transaction t = TManager.getInstance().createTransaction();

		if (t != null) {

			t.start();
			
			DAOVenta daoV = FactoriaIntegracion.getInstancia().generaDAOVenta();
			
			TTrabajador tTrab = crearTrabajador("Pepe", "12345678A");
			TProducto tProd = crearProducto("Mario");
			TPlataforma tPlat = crearPlataforma("Consola");
			TProductoEnPlataforma tPPl = crearProductoEnPlataforma(tProd.get_id(), tPlat.get_id());
			
			TVenta tvIn1 = crearVenta(tTrab.get_id(), (tPPl.get_cantidad()/2) * tPPl.get_precio(), 1);
			TVenta tvIn2 = crearVenta(tTrab.get_id(), (tPPl.get_cantidad()/2) * tPPl.get_precio(), 1);
			
			Set<TVenta> setIn = new LinkedHashSet<TVenta>();
			setIn.add(tvIn1);
			setIn.add(tvIn2);
			
			Set<TVenta> setRead = daoV.read_all();
			
			assertEquals("Debería haber dos ventas", setIn.size(), setRead.size());
			
			Iterator<TVenta> i = setIn.iterator();
			Iterator<TVenta> j = setRead.iterator();
			
			while (i.hasNext() && j.hasNext()) {
				assertTrue("Deberian ser iguales", compReadVenta(i.next(), j.next()));
			}
			
			TLineaVenta tLVIn1 = crearLineaVenta(tvIn1.get_id(), tProd.get_id(), tPPl.get_cantidad()/2, tPPl.get_precio(), 1);
			TLineaVenta tLVIn2 = crearLineaVenta(tvIn2.get_id(), tProd.get_id(), tPPl.get_cantidad()/2, tPPl.get_precio(), 1);
			
			Set<TLineaVenta> setLVIn = new LinkedHashSet<TLineaVenta>();
			setLVIn.add(tLVIn1);
			setLVIn.add(tLVIn2);
			
			DAOLineaVenta daoLV = FactoriaIntegracion.getInstancia().generaDAOLineaVenta();
			
			Set<TLineaVenta> setLVRead = new LinkedHashSet<TLineaVenta>();
			Set<TLineaVenta> setLVAux = daoLV.read_all(tvIn1.get_id());
			
			Iterator<TLineaVenta> k = setLVAux.iterator();
			while (k.hasNext()) {
				setLVRead.add(k.next());
			}
			
			setLVAux = daoLV.read_all(tvIn2.get_id());
			k = setLVAux.iterator();
			while (k.hasNext()) {
				setLVRead.add(k.next());
			}
			
			assertEquals("Debería haber dos lineas de venta", setLVIn.size(), setLVRead.size());
			
			Iterator<TLineaVenta> u = setLVIn.iterator();
			Iterator<TLineaVenta> v = setLVRead.iterator();
			
			while (u.hasNext() && v.hasNext()) {
				assertTrue("Deberian ser iguales", compReadLineaVenta(u.next(), v.next()));
			}
			t.commit();
		}
		else fail("No se ha podido crear la transaccion");
	}

	@Test
	public void testModificarVenta() {
		
		Transaction t = TManager.getInstance().createTransaction();

		if (t != null) {

			t.start();
			
			DAOVenta daoV = FactoriaIntegracion.getInstancia().generaDAOVenta();
			
			TTrabajador tTrab = crearTrabajador("Pepe", "12345678A");
			TProducto tProd = crearProducto("Mario");
			TPlataforma tPlat = crearPlataforma("PC");
			TProductoEnPlataforma tPPl = crearProductoEnPlataforma(tProd.get_id(), tPlat.get_id());
			
			TVenta tv = crearVenta(tTrab.get_id(), (tPPl.get_cantidad()/2) * tPPl.get_precio(), 1);			
			TTrabajador tTrab2 = crearTrabajador("Juan", "12345678B");
			tv.set_trabajador(tTrab2.get_id());
			int exito = daoV.update(tv);
			
			assertTrue("Deberia haberse modificado la venta", exito(exito));
			//assertTrue("Deberia haberse modificado el trabajador", compMod(tv, daoV.read(tv.get_id())));
			t.commit();
		}
		else fail("No se ha podido crear la transaccion");
	}

	@Test
	public void testDeleteVenta() {
		
		Transaction t = TManager.getInstance().createTransaction();

		if (t != null) {

			t.start();
		
			DAOVenta daoV = FactoriaIntegracion.getInstancia().generaDAOVenta();
			DAOLineaVenta daoLV = FactoriaIntegracion.getInstancia().generaDAOLineaVenta();

			TTrabajador tTrab = crearTrabajador("Pepe", "12345678A");
			TProducto tProd = crearProducto("Mario");
			TPlataforma tPlat = crearPlataforma("PC");
			TProductoEnPlataforma tPPl = crearProductoEnPlataforma(tProd.get_id(), tPlat.get_id());
			
			TVenta tv = crearVenta(tTrab.get_id(), tPPl.get_cantidad() * tPPl.get_precio(), 1);
			TLineaVenta tLV = crearLineaVenta(tv.get_id(), tProd.get_id(), tPPl.get_cantidad(), tPPl.get_precio(), 1);
			
			int exito = daoV.delete(tv.get_id());
			
			assertTrue("Deberia haberse eliminado la venta", exito(exito));
			
			exito = daoLV.delete(tLV);
			
			assertTrue("Deberia haberse eliminado la linea de venta", exito(exito));
			t.commit();
		}
		else  fail("No se ha podido crear la transaccion");
	}

	@Test
	public void testReadPorTrabajador() {
		
		Transaction t = TManager.getInstance().createTransaction();

		if (t != null) {

			t.start();
			
			DAOVenta daoV = FactoriaIntegracion.getInstancia().generaDAOVenta();
			
			TTrabajador tTrab1 = crearTrabajador("Pepe", "12345678A");
			TTrabajador tTrab2 = crearTrabajador("Juan", "12345678B");
			TProducto tProd = crearProducto("Mario");
			TPlataforma tPlat = crearPlataforma("PC");
			TProductoEnPlataforma tPPl = crearProductoEnPlataforma(tProd.get_id(), tPlat.get_id());
			
			TVenta tvIn1 = crearVenta(tTrab1.get_id(), (tPPl.get_cantidad()/2) * tPPl.get_precio(), 1);
			TVenta tvIn2 = crearVenta(tTrab1.get_id(), (tPPl.get_cantidad()/2) * tPPl.get_precio(), 1);
			
			Set<TVenta> setIn = new LinkedHashSet<TVenta>();
			setIn.add(tvIn1);
			setIn.add(tvIn2);
			
			Set<TVenta> setRead = daoV.readVentaPorTrabajador(tTrab1.get_id());
			
			assertEquals("Debería haber dos ventas", setIn.size(), setRead.size());
			
			Iterator<TVenta> i = setIn.iterator();
			Iterator<TVenta> j = setRead.iterator();
			
			while (i.hasNext() && j.hasNext()) {
				assertTrue("Deberian ser iguales", compReadVenta(i.next(), j.next()));
			}
			
			setRead = daoV.readVentaPorTrabajador(tTrab2.get_id());
			
			assertTrue("No deberia haber ventas", setRead.isEmpty());
			t.commit();
		
		}
		else fail("No se ha podido crear la transaccion");
	}

	@Test
	public void testReadPorProductoPlataforma() {
		Transaction t = TManager.getInstance().createTransaction();

		if (t != null) {

			t.start();
			
			TTrabajador tTrab = crearTrabajador("Pepe", "12345678A");
			TProducto tProd1 = crearProducto("Mario");
			TProducto tProd2 = crearProducto("Pokemon");
			TPlataforma tPlat = crearPlataforma("PC");
			TProductoEnPlataforma tPPl1 = crearProductoEnPlataforma(tProd1.get_id(), tPlat.get_id());
			TProductoEnPlataforma tPPl2 = crearProductoEnPlataforma(tProd2.get_id(), tPlat.get_id());
			
			TVenta tvIn1 = crearVenta(tTrab.get_id(), (tPPl1.get_cantidad()/2) * tPPl1.get_precio(), 1);
			TVenta tvIn2 = crearVenta(tTrab.get_id(), (tPPl1.get_cantidad()/2) * tPPl1.get_precio(), 1);
			
			TLineaVenta tLVIn1 = crearLineaVenta(tvIn1.get_id(), tProd1.get_id(), tPPl1.get_cantidad()/2, tPPl1.get_precio(), 1);
			TLineaVenta tLVIn2 = crearLineaVenta(tvIn2.get_id(), tProd1.get_id(), tPPl1.get_cantidad()/2, tPPl1.get_precio(), 1);
			
			Set<TLineaVenta> setLVIn = new LinkedHashSet<TLineaVenta>();
			setLVIn.add(tLVIn1);
			setLVIn.add(tLVIn2);
			
			DAOLineaVenta daoLV = FactoriaIntegracion.getInstancia().generaDAOLineaVenta();
			
			Set<TLineaVenta> setLVRead = daoLV.readVentaPorProductoPlataforma(tPPl1.get_id());
			
			assertEquals("Debería haber dos lineas de venta", setLVIn.size(), setLVRead.size());
			
			Iterator<TLineaVenta> i = setLVIn.iterator();
			Iterator<TLineaVenta> j = setLVRead.iterator();
			
			while (i.hasNext() && j.hasNext()) {
				assertTrue("Deberian ser iguales", compReadLineaVenta(i.next(), j.next()));
			}
			
			setLVRead = daoLV.readVentaPorProductoPlataforma(tPPl2.get_id());
			
			assertTrue("No deberia haber lineas de venta", setLVRead.isEmpty());
			
			t.commit();
			
		}
		else fail("No se ha podido crear la transaccion");
	}
	
	@Test
	public void testDevolverVenta() {
		
		Transaction t = TManager.getInstance().createTransaction();

		if (t != null) {

			t.start();
			
			DAOLineaVenta daoLV = FactoriaIntegracion.getInstancia().generaDAOLineaVenta();
			
			TTrabajador tTrab = crearTrabajador("Pepe", "12345678A");
			TProducto tProd = crearProducto("Pokemon");
			TPlataforma tPlat = crearPlataforma("Consola");
			TProductoEnPlataforma tPPl = crearProductoEnPlataforma(tProd.get_id(), tPlat.get_id());
			
			TVenta tv = crearVenta(tTrab.get_id(), tPPl.get_cantidad() * tPPl.get_precio(), 1);
			TLineaVenta tLV = crearLineaVenta(tv.get_id(), tProd.get_id(), tPPl.get_cantidad(), tPPl.get_precio(), 1);
			tLV.set_cantidad(0);
			
			int exito = daoLV.update(tLV);
			
			assertTrue("Deberia haberse devuelto el producto", exito(exito));
			
			t.commit();
		}
		else fail("No se ha podido crear la transaccion");
	}

	private void limpiarDatos() {
		LimpiarTabla.limpiarTabla("VENTAS");
		LimpiarTabla.limpiarTabla("LINEAVENTA");
		LimpiarTabla.limpiarTabla("TRABAJADOR");
		LimpiarTabla.limpiarTabla("PLATAFORMA");
		LimpiarTabla.limpiarTabla("PRODUCTO");
		LimpiarTabla.limpiarTabla("PRODUCTOENPLATAFORMA");
	}

	@After
	public void borrarBaseDeDatos() {
		limpiarDatos();
	}
}

package Negocio.Venta;

import static org.junit.Assert.*;

import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.Set;

import org.junit.After;
import org.junit.Test;

import Integracion.ConfiguracionBD.LimpiarTabla;
import Negocio.FactoriaNegocio.FactoriaServicioAplicacion;
import Negocio.Plataforma.SAPlataforma;
import Negocio.Plataforma.TPlataforma;
import Negocio.Producto.SAProducto;
import Negocio.Producto.TProducto;
import Negocio.Producto.TVideojuego;
import Negocio.ProductoEnPlataforma.SAProductoEnPlataforma;
import Negocio.ProductoEnPlataforma.TProductoEnPlataforma;
import Negocio.Trabajador.SATrabajador;
import Negocio.Trabajador.TSupervisor;
import Negocio.Trabajador.TTrabajador;

public class VentaNegTest {

	private boolean exito(int id) {
		return id != -1;
	}

	private boolean compReadVenta(TVenta tvRead, TVenta tvIn) {
		return tvRead.get_id() == tvIn.get_id() && tvRead.get_activo() == tvIn.get_activo()
				&& tvRead.get_trabajador() == tvIn.get_trabajador();
	}

	private boolean compReadLineasVenta(TLineaVenta tLVRead, TLineaVenta tLVIn) {
		return tLVRead.get_activo() == tLVIn.get_activo() && tLVRead.get_cantidad() == tLVIn.get_cantidad()
				&& tLVRead.get_factura() == tLVIn.get_factura() && tLVRead.get_producto() == tLVIn.get_producto();
	}

	private boolean compRead(TVentaCompletaTOA tVCRead, TVentaCompletaTOA tVCIn) {

		boolean igual = true;

		TVenta tvRead = tVCRead.get_venta();
		TVenta tvIn = tVCIn.get_venta();

		if (!compReadVenta(tvRead, tvIn)) {
			igual = false;
		} else {

			Set<TLineaVenta> setLVRead = tVCRead.get_lista_lineasVenta();
			Set<TLineaVenta> setLVIn = tVCIn.get_lista_lineasVenta();

			if (setLVRead.size() != setLVIn.size()) {
				igual = false;
			} else {
				Iterator<TLineaVenta> itRead = setLVRead.iterator();
				Iterator<TLineaVenta> itIn = setLVIn.iterator();

				while (itRead.hasNext() && itIn.hasNext()) {

					TLineaVenta tLVRead = itRead.next();
					TLineaVenta tLVIn = itIn.next();

					if (!compReadLineasVenta(tLVRead, tLVIn)) {
						igual = false;
					}
				}
			}
		}

		return igual;
	}

	private TTrabajador crearTrabajador(String nombre, String DNI) {

		SATrabajador saTrab = FactoriaServicioAplicacion.getInstancia().generaSATrabajador();
		TTrabajador tTrab = new TSupervisor();
		tTrab.colocar_datos(1, nombre, DNI);
		((TSupervisor) tTrab).set_experiencia("Mucha");
		int id = saTrab.alta_trabajador(tTrab);
		tTrab.set_id(id);

		return tTrab;
	}

	private TProducto crearProducto(String nombre) {

		SAProducto saProd = FactoriaServicioAplicacion.getInstancia().generaSAProducto();
		TProducto tProd = new TVideojuego(nombre, "Nintendo", 1, 18);
		int id = saProd.altaProducto(tProd);
		tProd.set_id(id);

		return tProd;
	}

	private TPlataforma crearPlataforma(String nombre) {

		SAPlataforma saPlat = FactoriaServicioAplicacion.getInstancia().generarSAPlataforma();
		TPlataforma tPlat = new TPlataforma();
		tPlat.colocar_datos(nombre, 1);
		int id = saPlat.altaPlataforma(tPlat);
		tPlat.set_id(id);

		return tPlat;
	}

	private TProductoEnPlataforma crearProductoEnPlataforma(int idPlataforma, int idProducto, double precio) {

		SAProductoEnPlataforma saPPl = FactoriaServicioAplicacion.getInstancia().generaSAProductoEnPlataforma();
		TProductoEnPlataforma tPPl = new TProductoEnPlataforma();
		tPPl.colocar_datos(1, precio, 10, idProducto, idPlataforma);
		int id = saPPl.altaProductoEnPlataforma(tPPl);
		tPPl.set_id(id);

		return tPPl;
	}

	private TVentaCompletaTOA crearVenta(TCarrito carrito) {

		SAVenta saVenta = FactoriaServicioAplicacion.getInstancia().generaSAVenta();
		TVentaCompletaTOA tVC = new TVentaCompletaTOA();
		int idVenta = saVenta.cerrar_venta(carrito);

		carrito.get_venta().set_id(idVenta);
		tVC.set_venta(carrito.get_venta());
		tVC.set_lista_lineasVenta(carrito.get_lista_lineaVenta());

		SATrabajador saTrab = FactoriaServicioAplicacion.getInstancia().generaSATrabajador();
		TTrabajador tTrab = saTrab.listar_por_id(carrito.get_venta().get_trabajador());
		tVC.set_trabajador(tTrab);

		SAProductoEnPlataforma saPPl = FactoriaServicioAplicacion.getInstancia().generaSAProductoEnPlataforma();
		Set<TProductoEnPlataforma> setPPl = new LinkedHashSet<>();
		for (TLineaVenta lineaVenta : carrito.get_lista_lineaVenta()) {
			setPPl.add(saPPl.buscarProductoEnPlataforma(lineaVenta.get_producto()));
		}
		tVC.set_lista_producto_plataforma(setPPl);

		return tVC;
	}

	@Test
	public void testAbrirVenta() {

		SAVenta saVenta = FactoriaServicioAplicacion.getInstancia().generaSAVenta();

		TCarrito carrito = saVenta.abrir_venta(1);

		assertNotNull("Deberia crearse carrito", carrito);
	}

	@Test
	public void testPasarCarrito() {

		SAVenta saVenta = FactoriaServicioAplicacion.getInstancia().generaSAVenta();
		TCarrito carrito = saVenta.abrir_venta(1);

		int result = saVenta.pasar_carrito(carrito);

		assertTrue("Deberia pasarse el carrito", exito(result));
	}

	@Test
	public void testInsertarProductoCarrito() {

		SAVenta saVenta = FactoriaServicioAplicacion.getInstancia().generaSAVenta();
		TCarrito carrito = saVenta.abrir_venta(1);

		carrito.set_id_producto_final(1);
		carrito.set_cantidad(1);

		int result = saVenta.insertar_producto_carrito(carrito);

		assertTrue("Deberia insertarse el producto", exito(result));
	}

	@Test
	public void testEliminarProductoCarrito() {

		SAVenta saVenta = FactoriaServicioAplicacion.getInstancia().generaSAVenta();
		TCarrito carrito = saVenta.abrir_venta(1);

		carrito.set_id_producto_final(1);
		carrito.set_cantidad(1);
		saVenta.insertar_producto_carrito(carrito);

		assertTrue("Deberia eliminarse el producto", exito(saVenta.eliminar_producto_carrito(carrito)));

		carrito.set_id_producto_final(1);
		carrito.set_cantidad(1);
		saVenta.insertar_producto_carrito(carrito);

		carrito.set_id_producto_final(2);

		assertFalse("No deberia eliminarse el producto", exito(saVenta.eliminar_producto_carrito(carrito)));
	}

	@Test
	public void testCerrarVenta() {

		SAVenta saVenta = FactoriaServicioAplicacion.getInstancia().generaSAVenta();
		TTrabajador tTrab = crearTrabajador("Pepe", "12345678K");
		TProducto tProd = crearProducto("FFVII");
		TPlataforma tPlat = crearPlataforma("PC");
		TProductoEnPlataforma tPPl = crearProductoEnPlataforma(tPlat.get_id(), tProd.get_id(), 34);

		TCarrito carrito = saVenta.abrir_venta(tTrab.get_id());

		carrito.set_id_producto_final(tPPl.get_id());
		carrito.set_cantidad(1);
		saVenta.insertar_producto_carrito(carrito);

		int idVenta = saVenta.cerrar_venta(carrito);

		assertTrue("Deberia haber cerrado venta", exito(idVenta));

		carrito = saVenta.abrir_venta(2);

		carrito.set_id_producto_final(tPPl.get_id());
		carrito.set_cantidad(1);
		saVenta.insertar_producto_carrito(carrito);

		idVenta = saVenta.cerrar_venta(carrito);

		assertFalse("No deberia haber cerrado venta, no existe trabajador", exito(idVenta));

		carrito = saVenta.abrir_venta(tTrab.get_id());

		carrito.set_id_producto_final(tPPl.get_id());
		carrito.set_cantidad(9);
		saVenta.insertar_producto_carrito(carrito);

		saVenta.cerrar_venta(carrito);

		SAProductoEnPlataforma saPPl = FactoriaServicioAplicacion.getInstancia().generaSAProductoEnPlataforma();
		TProductoEnPlataforma auxpp = saPPl.buscarProductoEnPlataforma(tPPl.get_id());

		assertEquals("Deberia haber desactivado productoEnPlataforma", 0, auxpp.get_activo());

		carrito = saVenta.abrir_venta(tTrab.get_id());

		carrito.set_id_producto_final(tPPl.get_id());
		carrito.set_cantidad(1);
		saVenta.insertar_producto_carrito(carrito);

		idVenta = saVenta.cerrar_venta(carrito);

		assertFalse("No deberia haber cerrado venta, no hay productos para vender", exito(idVenta));

		carrito = saVenta.abrir_venta(tTrab.get_id());

		carrito.set_id_producto_final(2);
		carrito.set_cantidad(1);
		saVenta.insertar_producto_carrito(carrito);

		idVenta = saVenta.cerrar_venta(carrito);

		assertFalse("No deberia haber cerrado venta, no existe producto", exito(idVenta));
	}

	@Test
	public void testEliminarVenta() {

		SAVenta saVenta = FactoriaServicioAplicacion.getInstancia().generaSAVenta();

		TTrabajador tTrab = crearTrabajador("Pepe", "12345678K");
		TProducto tProd = crearProducto("FFVII");
		TPlataforma tPlat = crearPlataforma("PC");
		TProductoEnPlataforma pPl = crearProductoEnPlataforma(tPlat.get_id(), tProd.get_id(), 34);

		TCarrito carrito = saVenta.abrir_venta(tTrab.get_id());
		carrito.set_id_producto_final(pPl.get_id());
		carrito.set_cantidad(1);
		saVenta.insertar_producto_carrito(carrito);

		TVentaCompletaTOA tVC = crearVenta(carrito);
		int idVenta = tVC.get_venta().get_id();
		saVenta.eliminar_venta(idVenta);

		TVentaCompletaTOA tVCDel = saVenta.buscar_venta(idVenta);

		assertEquals("Deberia haber desactivado venta", 0, tVCDel.get_venta().get_activo());

		Set<TLineaVenta> setLineasVenta = tVCDel.get_lista_lineasVenta();

		for (TLineaVenta lineaVenta : setLineasVenta) {
			assertEquals("Deberia haber desactivado lineaVenta", 0, lineaVenta.get_activo());
		}
	}

	@Test
	public void testBuscarVenta() {

		SAVenta saVenta = FactoriaServicioAplicacion.getInstancia().generaSAVenta();

		TTrabajador tTrab = crearTrabajador("Pepe", "12345678K");
		TProducto tProd = crearProducto("FFVII");
		TPlataforma tPlat = crearPlataforma("PC");
		TProductoEnPlataforma pPl = crearProductoEnPlataforma(tPlat.get_id(), tProd.get_id(), 34);

		TCarrito carrito = saVenta.abrir_venta(tTrab.get_id());
		carrito.set_id_producto_final(pPl.get_id());
		carrito.set_cantidad(1);
		saVenta.insertar_producto_carrito(carrito);

		TVentaCompletaTOA tVCIn = crearVenta(carrito);
		int idVenta = tVCIn.get_venta().get_id();

		TVentaCompletaTOA tvRead = saVenta.buscar_venta(idVenta);

		assertNotNull("Deberia haber encontrado venta", tvRead);

		assertTrue("Deberian ser iguales", compRead(tvRead, tVCIn));
	}

	@Test
	public void testModificarVenta() {

		SAVenta saVenta = FactoriaServicioAplicacion.getInstancia().generaSAVenta();

		TTrabajador tTrab = crearTrabajador("Pepe", "12345678K");
		TProducto tProd = crearProducto("FFVII");
		TPlataforma tPlat = crearPlataforma("PC");
		TProductoEnPlataforma pPl = crearProductoEnPlataforma(tPlat.get_id(), tProd.get_id(), 34);

		TCarrito carrito = saVenta.abrir_venta(tTrab.get_id());
		carrito.set_id_producto_final(pPl.get_id());
		carrito.set_cantidad(1);
		saVenta.insertar_producto_carrito(carrito);

		TVentaCompletaTOA tVC = crearVenta(carrito);
		int idVenta = tVC.get_venta().get_id();
		saVenta.eliminar_venta(idVenta);

		TTrabajador tTrab2 = crearTrabajador("Juan", "12345678L");

		TVenta tv = tVC.get_venta();
		tv.set_trabajador(tTrab2.get_id());

		saVenta.modificar_venta(tv);

		assertEquals("Deberia haber cambiado el trabajador", tTrab2.get_id(),
				saVenta.buscar_venta(idVenta).get_venta().get_trabajador());
	}

	@Test
	public void testDevolverVenta() {
		
		SAVenta saVenta = FactoriaServicioAplicacion.getInstancia().generaSAVenta();

		TTrabajador tTrab = crearTrabajador("Pepe", "12345678K");
		TProducto tProd = crearProducto("FFVII");
		TPlataforma tPlat = crearPlataforma("PC");
		TProductoEnPlataforma pPl = crearProductoEnPlataforma(tPlat.get_id(), tProd.get_id(), 34);

		TCarrito carrito = saVenta.abrir_venta(tTrab.get_id());
		carrito.set_id_producto_final(pPl.get_id());
		carrito.set_cantidad(10);
		saVenta.insertar_producto_carrito(carrito);

		TVentaCompletaTOA tVC = crearVenta(carrito);
		
		TLineaVenta tLV = tVC.get_lista_lineasVenta().iterator().next();
		
		tLV.set_cantidad(5);
		
		saVenta.devolver_venta(tLV);
		
		SAProductoEnPlataforma saPPl = FactoriaServicioAplicacion.getInstancia().generaSAProductoEnPlataforma();
		
		TProductoEnPlataforma auxPP = saPPl.buscarProductoEnPlataforma(pPl.get_id());
		
		assertEquals("Deberia haberse reactivado", 1, auxPP.get_activo());
		
		
	}

	@Test
	public void testListarTodoVenta() {

		SAVenta saVenta = FactoriaServicioAplicacion.getInstancia().generaSAVenta();

		TTrabajador tTrab = crearTrabajador("Pepe", "12345678K");
		TProducto tProd = crearProducto("FFVII");
		TPlataforma tPlat = crearPlataforma("PC");
		TProductoEnPlataforma pPl = crearProductoEnPlataforma(tPlat.get_id(), tProd.get_id(), 34);

		TCarrito carrito = saVenta.abrir_venta(tTrab.get_id());
		carrito.set_id_producto_final(pPl.get_id());
		carrito.set_cantidad(1);
		saVenta.insertar_producto_carrito(carrito);

		Set<TVenta> setIn = new LinkedHashSet<>();
		TVentaCompletaTOA tVC = crearVenta(carrito);

		setIn.add(tVC.get_venta());

		TCarrito carrito2 = saVenta.abrir_venta(tTrab.get_id());
		carrito2.set_id_producto_final(pPl.get_id());
		carrito2.set_cantidad(1);
		saVenta.insertar_producto_carrito(carrito2);

		TVentaCompletaTOA tVC2 = crearVenta(carrito2);

		setIn.add(tVC2.get_venta());

		Set<TVenta> ventas = saVenta.listar_todo_venta();

		assertNotNull("Deberia haber ventas", ventas);

		Iterator<TVenta> itIn = setIn.iterator();
		Iterator<TVenta> itRead = ventas.iterator();

		while (itIn.hasNext() && itRead.hasNext()) {
			TVenta tvIn = itIn.next();
			TVenta tvRead = itRead.next();
			assertTrue("Deberian ser iguales", compReadVenta(tvRead, tvIn));
		}

	}

	@Test
	public void testListarPorTrabajadorVenta() {

		SAVenta saVenta = FactoriaServicioAplicacion.getInstancia().generaSAVenta();

		TTrabajador tTrab = crearTrabajador("Pepe", "12345678K");
		TTrabajador tTrab2 = crearTrabajador("Juan", "12345678L");
		TProducto tProd = crearProducto("FFVII");
		TPlataforma tPlat = crearPlataforma("PC");
		TProductoEnPlataforma pPl = crearProductoEnPlataforma(tPlat.get_id(), tProd.get_id(), 34);

		TCarrito carrito = saVenta.abrir_venta(tTrab.get_id());
		carrito.set_id_producto_final(pPl.get_id());
		carrito.set_cantidad(1);
		saVenta.insertar_producto_carrito(carrito);

		crearVenta(carrito);

		Set<TVenta> ventas = saVenta.listar_por_trabajador_venta(tTrab.get_id());

		assertNotNull("Deberia haber ventas", ventas);
		assertTrue("Deberia ser del trabajador", ventas.iterator().next().get_trabajador() == tTrab.get_id());

		Set<TVenta> ventas2 = saVenta.listar_por_trabajador_venta(tTrab2.get_id());

		assertTrue("No deberia haber ventas", ventas2.isEmpty());
	}

	@Test
	public void testListarPorProductoEnPlataformaVenta() {

		SAVenta saVenta = FactoriaServicioAplicacion.getInstancia().generaSAVenta();

		TTrabajador tTrab = crearTrabajador("Pepe", "12345678K");
		TProducto tProd = crearProducto("FFVII");
		TProducto tProd2 = crearProducto("AAA");
		TPlataforma tPlat = crearPlataforma("PC");
		TProductoEnPlataforma pPl = crearProductoEnPlataforma(tPlat.get_id(), tProd.get_id(), 34);
		TProductoEnPlataforma pPl2 = crearProductoEnPlataforma(tPlat.get_id(), tProd2.get_id(), 34);

		TCarrito carrito = saVenta.abrir_venta(tTrab.get_id());
		carrito.set_id_producto_final(pPl.get_id());
		carrito.set_cantidad(1);
		saVenta.insertar_producto_carrito(carrito);

		crearVenta(carrito);

		Set<TLineaVenta> lineasVenta = saVenta.listar_por_producto_en_plataforma_venta(pPl.get_id());

		assertNotNull("Deberia haber lineasVenta", lineasVenta);
		assertTrue("Deberia ser del trabajador", lineasVenta.iterator().next().get_producto() == pPl.get_id());

		Set<TLineaVenta> lineasVenta2 = saVenta.listar_por_producto_en_plataforma_venta(pPl2.get_id());

		assertTrue("No deberia haber ventas", lineasVenta2.isEmpty());
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
	public void limpiarDatosDespues() {
		limpiarDatos();
	}
}

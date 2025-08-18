package Negocio.ProductoEnPlataforma;

//import static org.junit.Assert.*;

import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.Set;

import org.junit.After;
import org.junit.Assert;
import org.junit.Test;

import Integracion.ConfiguracionBD.LimpiarTabla;
import Negocio.FactoriaNegocio.FactoriaServicioAplicacion;
import Negocio.Plataforma.SAPlataforma;
import Negocio.Plataforma.TPlataforma;
import Negocio.Producto.SAProducto;
import Negocio.Producto.TComplemento;
import Negocio.Producto.TProducto;
import Negocio.Producto.TVideojuego;

public class ProductoEnPlataformaNegTest {

	private boolean exito(int id) {
		return id != -1;
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

	private TProductoEnPlataforma crear_producto_en_plataforma(String nombreProducto, String marcaProducto,
			int cantidad, double peso, int restriccionEdad) {
		SAProducto saProd = FactoriaServicioAplicacion.getInstancia().generaSAProducto();
		SAPlataforma saPlat = FactoriaServicioAplicacion.getInstancia().generarSAPlataforma();
		SAProductoEnPlataforma saProdEnPlat = FactoriaServicioAplicacion.getInstancia().generaSAProductoEnPlataforma();

		// Creamos un producto
		TProducto producto = new TProducto(nombreProducto, marcaProducto, 1);
		if (producto instanceof TComplemento)
			((TComplemento) producto).set_peso(peso);
		if (producto instanceof TVideojuego)
			((TVideojuego) producto).set_restriccionEdad(restriccionEdad);
		int idpr = saProd.altaProducto(producto);
		producto.set_id(idpr);

		// Creamos una plataforma
		TPlataforma plataforma = new TPlataforma();
		plataforma.colocar_datos("test", 1);
		int idpl = saPlat.altaPlataforma(plataforma);
		plataforma.set_id(idpl);

		// Creamos un producto en una plataforma
		TProductoEnPlataforma prodplat = new TProductoEnPlataforma();
		prodplat.colocar_datos(1, cantidad, 3, producto.get_id(), plataforma.get_id());
		int id = saProdEnPlat.altaProductoEnPlataforma(prodplat);
		prodplat.set_id(id);

		return prodplat;
	}

	@Test
	public void testAltaProductoEnPlataformaNuevaOK() {

		TProductoEnPlataforma prodplat = crear_producto_en_plataforma("test1", "testBrand1", 3, 5.5, 18);
		Assert.assertTrue(exito(prodplat.get_id()));
	}

	@Test
	public void testBajaProductoEnPlataformaOK() {
		TProductoEnPlataforma prodplat = crear_producto_en_plataforma("test1", "testBrand1", 3, 5.5, 18);
		SAProductoEnPlataforma saProdEnPlat = FactoriaServicioAplicacion.getInstancia().generaSAProductoEnPlataforma();

		// Borramos el producto en la plataforma
		int ex = saProdEnPlat.bajaProductoEnPlataforma(prodplat.get_id());
		Assert.assertTrue(exito(ex));

		// Probamos a borrar un producto en una plataforma que no existe
		int ex2 = saProdEnPlat.bajaProductoEnPlataforma(2);
		Assert.assertFalse(exito(ex2));
	}

	@Test
	public void testMostrarProductoEnPlataformaOK() {

		TProductoEnPlataforma prodplat = crear_producto_en_plataforma("test1", "testBrand1", 3, 5.5, 18);
		SAProductoEnPlataforma saProdEnPlat = FactoriaServicioAplicacion.getInstancia().generaSAProductoEnPlataforma();

		// Buscamos el producto en la plataforma
		TProductoEnPlataforma tpIn = saProdEnPlat.buscarProductoEnPlataforma(prodplat.get_id());

		// Comprobamos que se ha encontrado correctamente
		Assert.assertTrue(compMos(tpIn, prodplat));
	}

	@Test
	public void testModificarProductoEnPlataformaOK() {

		TProductoEnPlataforma prodplat = crear_producto_en_plataforma("test1", "testBrand1", 3, 5.5, 18);
		SAProductoEnPlataforma saProdEnPlat = FactoriaServicioAplicacion.getInstancia().generaSAProductoEnPlataforma();

		TProductoEnPlataforma tpMod = new TProductoEnPlataforma();
		tpMod.colocar_datos(1, 8, 23, 1, 1);
		tpMod.set_id(prodplat.get_id());

		int ex = saProdEnPlat.modificarProductoEnPlataforma(tpMod);

		// Comprobamos que se ha modificado correctamente
		Assert.assertTrue(exito(ex));
	}

	@Test
	public void testMostrarAllProductoEnPlataforma() {

		SAProductoEnPlataforma saProdEnPlat = FactoriaServicioAplicacion.getInstancia().generaSAProductoEnPlataforma();

		TProductoEnPlataforma tpIn = new TProductoEnPlataforma();

		Set<TProductoEnPlataforma> tpsIn = new LinkedHashSet<>();

		int id = 1;

		while (tpIn != null) {

			tpIn = saProdEnPlat.buscarProductoEnPlataforma(id);

			if (tpIn != null)
				tpsIn.add(tpIn);

			id++;
		}

		Assert.assertNotNull(tpsIn);

		Set<TProductoEnPlataforma> tpsModaopll = saProdEnPlat.listarTodosProductoEnPlataforma(1);

		Iterator<TProductoEnPlataforma> i = tpsIn.iterator();
		Iterator<TProductoEnPlataforma> j = tpsModaopll.iterator();

		boolean ok = tpsIn.size() == tpsModaopll.size();

		while (i.hasNext() && j.hasNext()) {
			ok = ok && compMos((TProductoEnPlataforma) i.next(), (TProductoEnPlataforma) j.next());
		}

		Assert.assertTrue(ok);

	}

	@Test
	public void testMostrarProductoEnPlataformaPorPlataforma() {

		crear_producto_en_plataforma("test1", "testBrand1", 3, 5.5, 18);
		SAProductoEnPlataforma saProdEnPlat = FactoriaServicioAplicacion.getInstancia().generaSAProductoEnPlataforma();

		Set<TProductoEnPlataforma> setIn = saProdEnPlat.listarPorPlataforma(1);

		Assert.assertNotNull(setIn);
	}

	@Test
	public void testMostrarProductoEnPlataformaPorProducto() {

		crear_producto_en_plataforma("test1", "testBrand1", 3, 5.5, 18);
		SAProductoEnPlataforma saProdEnPlat = FactoriaServicioAplicacion.getInstancia().generaSAProductoEnPlataforma();

		Set<TProductoEnPlataforma> setIn = saProdEnPlat.listarPorProducto(1);

		Assert.assertNotNull(setIn);
	}

	@Test
	public void testCalcularCantidadProductoEnPlataforma() {
		crear_producto_en_plataforma("test1", "testBrand1", 3, 5.5, 18);
		SAProductoEnPlataforma saProdEnPlat = FactoriaServicioAplicacion.getInstancia().generaSAProductoEnPlataforma();

		int cantidad = saProdEnPlat.calcularCantidadProductoEnPlataforma(18);

		Assert.assertEquals(0, cantidad);
	}

	@After
	public void limpiarDatos() {
		LimpiarTabla.limpiarTabla("PLATAFORMA");
		LimpiarTabla.limpiarTabla("PRODUCTO");
		LimpiarTabla.limpiarTabla("PRODUCTOENPLATAFORMA");
	}

}

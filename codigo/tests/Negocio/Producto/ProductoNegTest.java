package Negocio.Producto;

//import static org.junit.Assert.*;

import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.Set;

import org.junit.After;
import org.junit.Assert;
import org.junit.Test;

import Integracion.ConfiguracionBD.LimpiarTabla;
import Negocio.FactoriaNegocio.FactoriaServicioAplicacion;

public class ProductoNegTest {

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

			return ((TComplemento) tpIn).get_peso() != (((TComplemento) tpMod).get_peso());
		}

		if (tpIn instanceof TVideojuego && tpMod instanceof TVideojuego) {

			return ((TVideojuego) tpIn).get_restriccionEdad() != (((TVideojuego) tpMod).get_restriccionEdad());
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

		if (!tpIn.get_nombre().equalsIgnoreCase((tpMos.get_nombre()))) {

			return false;
		}
		if (!tpIn.get_marca().equalsIgnoreCase((tpMos.get_marca()))) {

			return false;
		}

		if (tpIn instanceof TComplemento && tpMos instanceof TComplemento) {
			double pesoIn = ((TComplemento) tpIn).get_peso();
			double pesoMos = ((TComplemento) tpMos).get_peso();

			return pesoIn == pesoMos;
		}

		if (tpIn instanceof TVideojuego && tpMos instanceof TVideojuego) {

			return ((TVideojuego) tpIn).get_restriccionEdad() == (((TVideojuego) tpMos).get_restriccionEdad());
		}

		return false;
	}

	@Test
	public void testAltaProductoNuevoOK() {
		SAProducto sap = FactoriaServicioAplicacion.getInstancia().generaSAProducto();
		
		// Creamos producto 1
		TProducto producto1 = new TProducto("test1", "testBrand1", 1);
		if (producto1 instanceof TComplemento)
			((TComplemento) producto1).set_peso(5.5);
		if (producto1 instanceof TVideojuego)
			((TVideojuego) producto1).set_restriccionEdad(18);
		int id = sap.altaProducto(producto1);
		Assert.assertTrue(exito(id));
		
		// Creamos producto 2
		TProducto producto2 = new TProducto("test2", "testBrand2", 1);
		if (producto2 instanceof TComplemento)
			((TComplemento) producto2).set_peso(5.5);
		if (producto2 instanceof TVideojuego)
			((TVideojuego) producto2).set_restriccionEdad(18);
		int id2 = sap.altaProducto(producto2);
		Assert.assertTrue(exito(id2));
	}

	@Test
	public void testBajaProductoOK() {
		SAProducto sap = FactoriaServicioAplicacion.getInstancia().generaSAProducto();
		
		// Creamos producto 1
		TProducto producto1 = new TProducto("test", "testBrand", 1);
		if (producto1 instanceof TComplemento)
			((TComplemento) producto1).set_peso(5.5);
		if (producto1 instanceof TVideojuego)
			((TVideojuego) producto1).set_restriccionEdad(18);
		int id = sap.altaProducto(producto1);
		producto1.set_id(id);
		int ex = sap.bajaProducto(id);

		//Compromprobamos que se ha dado de baja correctamente
		Assert.assertTrue(exito(ex));
		
		// Creamos producto 2
		TProducto producto2 = new TProducto("test2", "testBrand2", 1);
		if (producto2 instanceof TComplemento)
			((TComplemento) producto2).set_peso(5.5);
		if (producto2 instanceof TVideojuego)
			((TVideojuego) producto2).set_restriccionEdad(18);
		int id2 = sap.altaProducto(producto2);
		producto2.set_id(id2);
		int ex2 = sap.bajaProducto(id2);
		
		//Compromprobamos que se ha dado de baja correctamente
		Assert.assertTrue(exito(ex2));
		
	}

	@Test
	public void testMostrarProductoOK() {
		SAProducto sap = FactoriaServicioAplicacion.getInstancia().generaSAProducto();
		
		// Creamos producto 1
		TProducto tpIn = new TComplemento("testMosPro", "apple", 1, 0.8);
		tpIn.set_activo(1);
		int id = sap.altaProducto(tpIn);
		tpIn.set_id(id);
		
		//Comprobamos que se ha creado correctamente
		Assert.assertTrue(exito(id));
		
		// Buscamos el producto creado
		TProducto producto_buscado = sap.buscarProducto(id);

		//Comprobamos que se ha encontrado correctamente
		Assert.assertTrue(compMos(tpIn, producto_buscado));
	}

	@Test
	public void testMostrar_all_ProductoOK() { 
		SAProducto sap = FactoriaServicioAplicacion.getInstancia().generaSAProducto();
		TProducto tpIn = new TProducto("test", "testBrand", 1);
		Set<TProducto> tpsIn = new LinkedHashSet<>();
		int id = 1;
		while (tpIn != null) {
			tpIn = sap.buscarProducto(id);
			if (tpIn != null)
				tpsIn.add(tpIn);
			id++;
		}
		Set<TProducto> tpsMosAll = sap.listarTodoProducto();

		Iterator<TProducto> i = tpsIn.iterator();
		Iterator<TProducto> j = tpsMosAll.iterator();
		boolean ok = tpsIn.size() == tpsMosAll.size();
		while (i.hasNext() && j.hasNext()) {
			ok = compMos((TProducto) i.next(), (TProducto) j.next());

		}
		Assert.assertTrue(ok);

	}

	@Test
	public void testModificarProductoOK() {
		SAProducto sa = FactoriaServicioAplicacion.getInstancia().generaSAProducto();

		TProducto tpIn = new TComplemento("testModifica", "apple", 1, 0.8);
		tpIn.set_activo(1);
		int id = sa.altaProducto(tpIn);
		tpIn.set_id(id);

		TProducto tpMod = new TComplemento("testMod", "ea", 0, 3);

		tpMod.set_activo(0);
		tpMod.set_id(id);

		int ex = sa.modificarProducto(tpMod);

		Assert.assertTrue(exito(ex));

		Assert.assertTrue(compMod(tpIn, tpMod));
	}

	@After
	public void limpiarDatos() {
		LimpiarTabla.limpiarTabla("PRODUCTO");
	}

}

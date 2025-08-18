package Negocio.Habilidad;

import java.util.Iterator;
import java.util.Set;

import org.junit.After;
import org.junit.Assert;
import org.junit.Test;

import Integracion.ConfiguracionBD.LimpiarTabla;
import Negocio.FactoriaNegocio.FactoriaServicioAplicacion;

public class HabilidadNegTest {

	private boolean exito(int id) {
		return id != -1;
	}

	private boolean compMos(THabilidad tpIn, THabilidad thMos) {
		return tpIn.get_id() == thMos.get_id() && tpIn.get_activo() == thMos.get_activo()
				&& tpIn.get_nombre().equals(thMos.get_nombre()) && tpIn.get_nivel() == thMos.get_nivel();
	}

	@Test
	public void testAltaHabilidadNuevoOK() {
		SAHabilidad sa = FactoriaServicioAplicacion.getInstancia().generaSAHabilidad();

		// Datos de entrada para crear una habilidad
		THabilidad habilidad1 = new THabilidad();
		habilidad1.colocar_datos("test", 1, 1);
		int id = sa.altaHabilidad(habilidad1);
		Assert.assertTrue(exito(id));

		// Lo mismo pero para una habilidad2
		THabilidad habilidad2 = new THabilidad();
		habilidad2.colocar_datos("test2", 2, 1);
		int id2 = sa.altaHabilidad(habilidad2);
		Assert.assertTrue(exito(id2));
	}

	@Test
	public void testBajaHabilidadOK() {
		SAHabilidad sa = FactoriaServicioAplicacion.getInstancia().generaSAHabilidad();

		// Datos de entrada para crear una habilidad
		THabilidad tpIn = new THabilidad();
		tpIn.colocar_datos("testBaja", 1, 1);
		int id = sa.altaHabilidad(tpIn);
		int ex = sa.bajaHabilidad(id);
		Assert.assertTrue(exito(ex));

		// Hacer lo mismo con una habilidad que no existe
		int ex2 = sa.bajaHabilidad(2);
		Assert.assertFalse(exito(ex2));
	}

	@Test
	public void testMostrarHabilidadOK() {
		SAHabilidad sa = FactoriaServicioAplicacion.getInstancia().generaSAHabilidad();
		THabilidad tpIn = new THabilidad();

		// Datos de entrada para crear una habilidad
		tpIn.colocar_datos("testMos", 1, 1);

		// Alta habilidad
		int id = sa.altaHabilidad(tpIn);
		tpIn.set_id(id);

		// Nos aseguramos de que la habilidad se ha creado correctamente
		Assert.assertTrue(exito(id));

		// Buscamos la habilidad creada
		THabilidad thMos = sa.buscarHabilidad(id);

		// Comparamos la habilidad creada con la habilidad encontrada. Aquí ya se
		// validan que todos los datos sean correctos
		Assert.assertTrue(compMos(tpIn, thMos));
	}

	@Test
	public void testModificarHabilidadOK() {
		SAHabilidad sa = FactoriaServicioAplicacion.getInstancia().generaSAHabilidad();
		THabilidad tpIn = new THabilidad();

		// Datos para crear una habilidad
		tpIn.colocar_datos("testIn", 1, 1);

		// Alta habilidad
		int id = sa.altaHabilidad(tpIn);

		// Preparamos los datos para modificar la habilidad
		THabilidad thMod = new THabilidad();
		thMod.colocar_datos("testMod", 1, 1);
		thMod.set_id(id);

		// Modificamos la habilidad
		int ex = sa.modificarHabilidad(thMod);

		// Comprobamos que se ha modificado correctamente
		Assert.assertTrue(exito(ex));

		// Buscamos la habilidad modificada
		THabilidad thMod2 = sa.buscarHabilidad(id);

		// Validamos que los atributos se han modificado correctamente
		Assert.assertEquals("El ID debería coincidir", id, thMod2.get_id());
		Assert.assertEquals("El nombre debería coincidir", "testMod", thMod2.get_nombre());
		Assert.assertEquals("El nivel debería coincidir", 1, thMod2.get_nivel());
		Assert.assertEquals("El estado activo debería coincidir", 1, thMod2.get_activo());
	}

	@Test
	public void testMostrar_all_HabilidadOK() {
		SAHabilidad sa = FactoriaServicioAplicacion.getInstancia().generaSAHabilidad();

		// Crear y añadir habilidades de prueba

		// Añadimos habilidad 1
		THabilidad habilidad1 = new THabilidad();
		habilidad1.colocar_datos("test1", 1, 1);
		int id1 = sa.altaHabilidad(habilidad1);
		habilidad1.set_id(id1);
		Assert.assertTrue("La habilidad1 debería ser creada correctamente", exito(id1));

		// Añadimos habilidad 2
		THabilidad habilidad2 = new THabilidad();
		habilidad2.colocar_datos("test2", 2, 1);
		int id2 = sa.altaHabilidad(habilidad2);
		habilidad2.set_id(id2);
		Assert.assertTrue("La habilidad2 debería ser creada correctamente", exito(id2));

		// Habilidades añadidas
		Set<THabilidad> habilidades = sa.listarTodasHabilidades(1);

		// Iteramos sobre las habilidades y comprobamos que son correctas respecto a las
		// creadas
		Iterator<THabilidad> i = habilidades.iterator();
		while (i.hasNext()) {
			THabilidad th = i.next();
			if (th.get_id() == id1) {
				Assert.assertTrue(compMos(habilidad1, th));
			} else if (th.get_id() == id2) {
				Assert.assertTrue(compMos(habilidad2, th));
			}
		}
	}

	@After
	public void limpiarDatos() {
		LimpiarTabla.limpiarTabla("HABILIDAD");
	}

}

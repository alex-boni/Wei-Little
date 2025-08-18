package Negocio.Plataforma;

import java.util.Iterator;
import java.util.Set;

import org.junit.After;
import org.junit.Assert;
import org.junit.Test;

import Integracion.ConfiguracionBD.LimpiarTabla;
import Negocio.FactoriaNegocio.FactoriaServicioAplicacion;

public class PlataformaNegTest {

	private boolean exito(int id) {
		return id != -1;
	}

	private boolean compMos(TPlataforma tpIn, TPlataforma tpMos) {
		return tpIn.get_id() == tpMos.get_id() && tpIn.get_activo() == tpMos.get_activo()
				&& tpIn.get_nombre().equals(tpMos.get_nombre());
	}

	@Test
	public void testAltaPlataformaNuevoOK() {
		SAPlataforma sa = FactoriaServicioAplicacion.getInstancia().generarSAPlataforma();
		
		// Creamos la plataforma 1
		TPlataforma plataforma1 = new TPlataforma();
		plataforma1.colocar_datos("test", 1);
		int id = sa.altaPlataforma(plataforma1);
		Assert.assertTrue(exito(id));
		
		// Creamos la plataforma 2
		TPlataforma plataforma2 = new TPlataforma();
		plataforma2.colocar_datos("test2", 1);
		int id2 = sa.altaPlataforma(plataforma2);
		Assert.assertTrue(exito(id2));
		
		// Creamos la plataforma 3
		TPlataforma plataforma3 = new TPlataforma();
		plataforma3.colocar_datos("test3", 1);
		int id3 = sa.altaPlataforma(plataforma3);
		Assert.assertTrue(exito(id3));
		
	}

	@Test
	public void testBajaPlataformaOK() {
		SAPlataforma sa = FactoriaServicioAplicacion.getInstancia().generarSAPlataforma();

		// Creamos la plataforma
		TPlataforma tpIn = new TPlataforma();
		tpIn.colocar_datos("testBaja", 1);
		int id = sa.altaPlataforma(tpIn);
		tpIn.set_id(id);

		// Comprobamos que se ha creado correctamente
		Assert.assertTrue(exito(id));

		// Borramos la plataforma
		int ex = sa.bajaPlataforma(id);

		// Comprobamos que se ha borrado correctamente
		Assert.assertTrue(exito(ex));

		// Buscamos una plataforma inexistente
		int ex2 = sa.bajaPlataforma(4);

		// Comprobamos que no se ha borrado
		Assert.assertFalse(exito(ex2));
	}

	@Test
	public void testMostrarPlataformaOK() {
		SAPlataforma sa = FactoriaServicioAplicacion.getInstancia().generarSAPlataforma();

		// Creamos la plataforma
		TPlataforma tpIn = new TPlataforma();
		tpIn.colocar_datos("testMos", 1);
		int id = sa.altaPlataforma(tpIn);

		// Comprobamos que se ha creado correctamente
		Assert.assertTrue(exito(id));
		tpIn.set_id(id);

		// Buscamos la plataforma
		TPlataforma tpMos = sa.buscarPlataforma(id);

		// Comprobamos que se ha encontrado
		Assert.assertTrue(compMos(tpIn, tpMos));

		// Buscamos una plataforma que no existe
		TPlataforma tpMos2 = sa.buscarPlataforma(2);
		Assert.assertNull(tpMos2);
	}

	@Test
	public void testModificarPlataformaOK() {
		SAPlataforma sa = FactoriaServicioAplicacion.getInstancia().generarSAPlataforma();

		// Creamos la plataforma a modificar
		TPlataforma plataforma1 = new TPlataforma();
		plataforma1.colocar_datos("testIn", 1);
		int id = sa.altaPlataforma(plataforma1);

		// Modificamos la plataforma
		TPlataforma tpMod = new TPlataforma();
		tpMod.colocar_datos("testMod", 1);
		tpMod.set_id(id);

		// Modificamos la plataforma
		int ex = sa.modificarPlataforma(tpMod);

		// Comprobamos que se ha modificado correctamente
		Assert.assertTrue(exito(ex));

		// Buscamos la plataforma modificada
		TPlataforma plataforma_buscada = sa.buscarPlataforma(id);

		// Vamos a comprobar que tenga los mismos datos
		Assert.assertEquals("El nombre debería de ser igual", plataforma_buscada.get_nombre(), tpMod.get_nombre());
		Assert.assertEquals("El id debería de ser igual", plataforma_buscada.get_id(), tpMod.get_id());
		Assert.assertEquals("El activo debería de ser igual", plataforma_buscada.get_activo(), tpMod.get_activo());
	}

	@Test
	public void testMostrar_all_PlataformaOK() {
		SAPlataforma sa = FactoriaServicioAplicacion.getInstancia().generarSAPlataforma();

		// Creamos las plataformas

		// Creamos la primera plataforma
		TPlataforma plataforma1 = new TPlataforma();
		plataforma1.colocar_datos("test1", 1);
		int id1 = sa.altaPlataforma(plataforma1);
		plataforma1.set_id(id1);
		Assert.assertTrue("La plataforma1 debería ser creada correctamente", exito(id1));

		// Creamos la segunda plataforma
		TPlataforma plataforma2 = new TPlataforma();
		plataforma2.colocar_datos("test2", 1);
		int id2 = sa.altaPlataforma(plataforma2);
		plataforma2.set_id(id2);		
		Assert.assertTrue("La plataforma2 debería ser creada correctamente", exito(id2));

		// Plataformas añadidas
		Set<TPlataforma> plataformas = sa.listarTodasPlataforma();

		// Iteramos sobre las plataformas y comprobamos que son correctas respecto a las
		// creadas
		Iterator<TPlataforma> i = plataformas.iterator();
		while (i.hasNext()) {
			TPlataforma tpMos = i.next();
			if (tpMos.get_id() == id1) {
				Assert.assertTrue(compMos(plataforma1, tpMos));
			} else if (tpMos.get_id() == id2) {
				Assert.assertTrue(compMos(plataforma2, tpMos));
			}
		}

	}

	@After
	public void limpiarDatos() {
		LimpiarTabla.limpiarTabla("PLATAFORMA");
	}

}

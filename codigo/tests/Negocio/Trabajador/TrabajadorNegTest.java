package Negocio.Trabajador;

import static org.junit.Assert.*;

import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.Set;

import org.junit.After;
import org.junit.Assert;
import org.junit.Test;

import Integracion.ConfiguracionBD.LimpiarTabla;

import Negocio.FactoriaNegocio.FactoriaServicioAplicacion;
import Negocio.Habilidad.SAHabilidad;
import Negocio.Habilidad.THabilidad;

public class TrabajadorNegTest {

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

		return false;
	}

	@Test
	public void testAltaTrabajadorNuevoOK() {
		SATrabajador sat = FactoriaServicioAplicacion.getInstancia().generaSATrabajador();

		// Creamos trabajador vendedor
		TTrabajador vendedor = new TVendedor();
		vendedor.colocar_datos(1, "testBajatrabajadorVendedor", "88888888F");
		((TVendedor) vendedor).set_idioma("chino");
		int id1 = sat.alta_trabajador(vendedor);

		// Comprobamos que se ha creado correctamente
		Assert.assertTrue(exito(id1));

		// Creamos trabajador supervisor
		TTrabajador supervisor = new TSupervisor();
		supervisor.colocar_datos(1, "testBajaTrabajadorSupervisor", "77777777F");
		((TSupervisor) supervisor).set_experiencia("poca");
		int id2 = sat.alta_trabajador(supervisor);

		// Comprobamos que se ha creado correctamente
		Assert.assertTrue(exito(id2));
	}

	@Test
	public void testBajaTrabajadorOK() {
		SATrabajador sap = FactoriaServicioAplicacion.getInstancia().generaSATrabajador();

		// Creamos trabajador vendedor
		TTrabajador vendedor = new TVendedor();
		vendedor.colocar_datos(1, "testBajatrabajadorVendedor", "88888888F");
		((TVendedor) vendedor).set_idioma("chino");
		int id1 = sap.alta_trabajador(vendedor);
		vendedor.set_id(id1);
		int ex1 = sap.baja_trabajador(vendedor.get_id());

		// Comprobamos que se ha dado de baja
		Assert.assertTrue(exito(ex1));

		// Creamos trabajador supervisor
		TTrabajador supervisor = new TSupervisor();
		supervisor.colocar_datos(1, "testBajaTrabajadorSupervisor", "77777777F");
		((TSupervisor) supervisor).set_experiencia("poca");
		int id2 = sap.alta_trabajador(supervisor);
		supervisor.set_id(id2);
		int ex2 = sap.baja_trabajador(supervisor.get_id());

		// Comprobamos que se ha dado de baja
		Assert.assertTrue(exito(ex2));
	}

	@Test
	public void testMostrarTrabajadorOK() {
		SATrabajador sap = FactoriaServicioAplicacion.getInstancia().generaSATrabajador();

		// Creamos trabajador supervisor
		TTrabajador supervisor = new TSupervisor();
		supervisor.colocar_datos(1, "testMostrabajador", "88888888D");
		((TSupervisor) supervisor).set_experiencia("poca");
		supervisor.set_activo(1);
		supervisor.set_nombre("testMostrabajador");
		int id = sap.alta_trabajador(supervisor);
		supervisor.set_id(id);

		// Comprobamos que se ha creado correctamente
		TTrabajador tpMos = sap.listar_por_id(id);
		Assert.assertTrue(compMos(supervisor, tpMos));
	}

	@Test
	public void testModificarTrabajadorOK() {
		SATrabajador sa = FactoriaServicioAplicacion.getInstancia().generaSATrabajador();

		// Creamos trabajador vendedor
		TTrabajador vendedor = new TVendedor();
		vendedor.colocar_datos(1, "testIntrabajador", "99999999K");
		((TVendedor) vendedor).set_idioma("aleman");
		vendedor.set_activo(1);
		int id = sa.alta_trabajador(vendedor);
		vendedor.set_id(id);

		// Comprobamos que se ha creado correctamente
		Assert.assertTrue(exito(id));

		// Modificamos datos del trabajador
		TTrabajador tpMod = new TVendedor();
		tpMod.colocar_datos(1, "testModtrabajador", "89239489F");
		((TVendedor) tpMod).set_idioma("ruso");
		tpMod.set_activo(0);
		tpMod.set_id(id);

		// Llamamos a la funcion de modificar
		int ex = sa.modificar_trabajador(tpMod);

		// Comprobamos que se ha modificado correctamente
		Assert.assertTrue(exito(ex));

		// Comprobamos que los datos coinciden

		Assert.assertTrue(compMod(vendedor, tpMod));
	}

	@Test
	public void testMostrar_all_TrabajadorOK() {
		SATrabajador sap = FactoriaServicioAplicacion.getInstancia().generaSATrabajador();
		TTrabajador tpIn = new TTrabajador();
		Set<TTrabajador> tpsIn = new LinkedHashSet<>();
		int id = 1;
		while (tpIn != null) {
			tpIn = sap.listar_por_id(id);
			if (tpIn != null)
				tpsIn.add(tpIn);
			id++;
		}
		Set<TTrabajador> tpsMosAll = sap.listar_trabajadores();

		Iterator<TTrabajador> i = tpsIn.iterator();
		Iterator<TTrabajador> j = tpsMosAll.iterator();
		boolean ok = tpsIn.size() == tpsMosAll.size();
		while (i.hasNext() && j.hasNext()) {
			ok = compMos((TTrabajador) i.next(), (TTrabajador) j.next());
		}
		Assert.assertTrue(ok);

	}

	@Test
	public void testVincularHabilidadNuevoOK() {

		SATrabajador sat = FactoriaServicioAplicacion.getInstancia().generaSATrabajador();
		SAHabilidad sah = FactoriaServicioAplicacion.getInstancia().generaSAHabilidad();

		// Creamos trabajador vendedor
		TTrabajador vendedor = new TVendedor();
		vendedor.colocar_datos(1, "testBajatrabajadorVendedor", "88888888F");
		((TVendedor) vendedor).set_idioma("chino");
		int id = sat.alta_trabajador(vendedor);
		vendedor.set_id(id);

		// Comprobamos que se ha creado correctamente
		Assert.assertTrue(exito(id));

		// Creamos trabajador supervisor
		TTrabajador supervisor = new TSupervisor();
		supervisor.colocar_datos(1, "testBajaTrabajadorSupervisor", "77777777F");
		((TSupervisor) supervisor).set_experiencia("poca");
		int id2 = sat.alta_trabajador(supervisor);
		supervisor.set_id(id2);

		// Comprobamos que se ha creado correctamente
		Assert.assertTrue(exito(id2));

		// Creamos habilidad
		THabilidad habilidad = new THabilidad();
		habilidad.colocar_datos("testhabilidad", 3, 1);
		int id3 = sah.altaHabilidad(habilidad);
		habilidad.set_id(id3);

		// Vinculamos la habilidad al trabajador
		TVinculacionTrabHab vinculaciontest = new TVinculacionTrabHab();

		vinculaciontest.set_id_trabajador(id);
		vinculaciontest.set_id_habilidad(id3);

		// Realizamos la vinculacion de la habilidad al trabajador
		int ex = sat.vincular_habilidad(vinculaciontest);

		Assert.assertTrue(exito(ex));

		TVinculacionTrabHab vinculaciontest2 = new TVinculacionTrabHab();

		vinculaciontest2.set_id_trabajador(id2);
		vinculaciontest2.set_id_habilidad(id3);

		int ex2 = sat.vincular_habilidad(vinculaciontest2);

		Assert.assertTrue(exito(ex2));
	}

	@Test
	public void testDesvincularHabilidadNuevoOK() {
		SATrabajador sat = FactoriaServicioAplicacion.getInstancia().generaSATrabajador();
		SAHabilidad sah = FactoriaServicioAplicacion.getInstancia().generaSAHabilidad();

		// Creamos trabajador vendedor
		TTrabajador vendedor = new TVendedor();
		vendedor.colocar_datos(1, "testBajatrabajadorVendedor", "88888888F");
		((TVendedor) vendedor).set_idioma("chino");
		int id = sat.alta_trabajador(vendedor);

		// Creamos habilidad para vincular
		THabilidad tpHab = new THabilidad();
		tpHab.colocar_datos("testhabilidad", 3, 1);
		int id2 = sah.altaHabilidad(tpHab);

		//Transfer de la vinculacion
		TVinculacionTrabHab vinculaciontest = new TVinculacionTrabHab();

		vinculaciontest.set_id_trabajador(id);
		vinculaciontest.set_id_habilidad(id2);
		vinculaciontest.set_activo(1);

		//Vinculamos la habilidad al trabajador
		sat.vincular_habilidad(vinculaciontest);

		//Desvinculamos la habilidad al trabajador
		int ex = sat.desvincular_habilidad(vinculaciontest);

		//Comprobamos que se ha desvinculado correctamente
		Assert.assertTrue(exito(ex));
	}

	@Test
	public void testListarTrabajadoresPorHabilidad() {
		SATrabajador sat = FactoriaServicioAplicacion.getInstancia().generaSATrabajador();
		SAHabilidad sah = FactoriaServicioAplicacion.getInstancia().generaSAHabilidad();

		TTrabajador vendedor = new TVendedor();
		vendedor.colocar_datos(1, "testBajatrabajadorVendedor", "88888888F");
		((TVendedor) vendedor).set_idioma("chino");
		int id = sat.alta_trabajador(vendedor);

		THabilidad tpHab = new THabilidad();
		tpHab.colocar_datos("testhabilidad", 3, 1);
		int id2 = sah.altaHabilidad(tpHab);

		TVinculacionTrabHab vinculaciontest = new TVinculacionTrabHab();

		vinculaciontest.set_id_trabajador(id);
		vinculaciontest.set_id_habilidad(id2);

		sat.vincular_habilidad(vinculaciontest);

		Set<TVinculacionTrabHab> vinculaciones = sat.listar_por_habilidad(id2);

		// Comprobamos que se listan correctamente
		assertNotNull(vinculaciones);

	}

	@Test
	public void testMostrar_all_TrabajadorTipoOK() {
		SATrabajador sat = FactoriaServicioAplicacion.getInstancia().generaSATrabajador();

		TTrabajador tpIn = new TSupervisor();
		tpIn.colocar_datos(1, "supervisorprueba", "33333333L");
		((TSupervisor) tpIn).set_experiencia("mucha");
		String tipo = "supervisor";
		Set<TTrabajador> trabajadores = sat.listar_por_tipo(tipo);

		Assert.assertNotNull(trabajadores);

		TTrabajador tpIn2 = new TVendedor();
		tpIn2.colocar_datos(1, "vendedorprueba", "44444444P");
		((TVendedor) tpIn2).set_idioma("ingles");
		tipo = "vendedor";
		trabajadores = sat.listar_por_tipo(tipo);

		Assert.assertNotNull(trabajadores);

	}

	@After
	public void limpiarDatos() {
		LimpiarTabla.limpiarTabla("TRABAJADOR");
		LimpiarTabla.limpiarTabla("VENDEDOR");
		LimpiarTabla.limpiarTabla("SUPERVISOR");
		LimpiarTabla.limpiarTabla("HABILIDAD");
		LimpiarTabla.limpiarTabla("TRABAJADORHABILIDAD");
	}

}

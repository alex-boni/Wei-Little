package Negocio.ClienteJPA;

import static org.junit.Assert.*;

import java.util.Set;
import java.util.Iterator;
import java.util.LinkedHashSet;

import org.junit.After;
import org.junit.Test;

import Integracion.ConfiguracionBD.LimpiarTabla;
import Negocio.AlquilerJPA.SAAlquiler;
import Negocio.AlquilerJPA.TAlquiler;
import Negocio.AlquilerJPA.TAlquilerTOA;
import Negocio.AlquilerJPA.TCarritoAlquiler;
import Negocio.EmpleadoJPA.SAEmpleado;
import Negocio.EmpleadoJPA.TEmpleado;
import Negocio.EmpleadoJPA.TEmpleadoDependiente;
import Negocio.FactoriaNegocio.FactoriaServicioAplicacion;
import Negocio.MaquinaJPA.SAMaquina;
import Negocio.MaquinaJPA.TArcade;
import Negocio.MaquinaJPA.TMaquina;
import Negocio.ModeloJPA.SAModelo;
import Negocio.ModeloJPA.TModelo;

public class ClienteNegJPATest {

	private boolean exito(int res) {

		return res != -1;
	}

	private boolean compRead(TCliente tCliente, TCliente tCliente2) {

		return tCliente.get_id_cliente() == tCliente2.get_id_cliente() && tCliente.get_dni().equals(tCliente2.get_dni())
				&& tCliente.get_nombre().equals(tCliente2.get_nombre());
	}

	private TAlquilerTOA crearAlquiler(TCliente tCliente) {

		SAEmpleado saEmpleado = FactoriaServicioAplicacion.getInstancia().generaSAEmpleado();
		SAModelo saModelo = FactoriaServicioAplicacion.getInstancia().generaSAModelo();
		SAMaquina saMaquina = FactoriaServicioAplicacion.getInstancia().generaSAMaquina();
		SAAlquiler saAlquiler = FactoriaServicioAplicacion.getInstancia().generaSAAlquiler();

		TEmpleado tEmpleado = new TEmpleadoDependiente();
		tEmpleado.set_DNI("12345678B");
		tEmpleado.set_nombre("Juan");
		((TEmpleadoDependiente) tEmpleado).setIdioma("es");
		tEmpleado.set_activo(1);
		int id_empleado = saEmpleado.altaEmpleado(tEmpleado);

		TModelo tModelo = new TModelo();
		tModelo.set_nombre("AAA");
		int id_modelo = saModelo.insertar_modelo(tModelo);

		TMaquina tMaquina = new TArcade(-1, "Poker", id_modelo, "123456A", 0, 12.3, 1, 12.3, 0.54);
		int id_maquina = saMaquina.altaMaquina(tMaquina);

		TAlquiler tAlquiler = new TAlquiler();
		tAlquiler.set_id_cliente(tCliente.get_id_cliente());
		tAlquiler.set_id_empleado(id_empleado);

		TCarritoAlquiler tCarrito = saAlquiler.abrir_alquiler(tAlquiler);
		tCarrito.set_id_maquina(id_maquina);
		tCarrito.set_duracion(2);
		saAlquiler.anyadir_maquina(tCarrito);

		int id_alquiler = saAlquiler.cerrar_alquiler(tCarrito);

		TAlquilerTOA alquiler = saAlquiler.buscar_alquiler(id_alquiler);

		return alquiler;
	}

	@Test
	public void testAltaCliente() {

		SACliente saCliente = FactoriaServicioAplicacion.getInstancia().generaSACliente();

		TCliente tCliente = new TCliente();
		tCliente.set_dni("12345678A");
		tCliente.set_nombre("Pepe");
		tCliente.set_activo(1);

		int id = saCliente.alta_cliente(tCliente);

		assertTrue("Debería haber dado de alta nuevo Cliente", exito(id));

		TCliente tCliente2 = new TCliente();
		tCliente2.set_dni("12345678A");
		tCliente2.set_nombre("Juan");

		int id2 = saCliente.alta_cliente(tCliente2);

		assertFalse("No deberia haber dado de alta a Cliente", exito(id2));
	}

	@Test
	public void testBajaCliente() {

		SACliente saCliente = FactoriaServicioAplicacion.getInstancia().generaSACliente();
		TCliente tCliente = new TCliente();
		tCliente.set_dni("12345678A");
		tCliente.set_nombre("Pepe");
		tCliente.set_activo(1);

		int id_cliente = saCliente.alta_cliente(tCliente);
		tCliente.set_id_cliente(id_cliente);

		TAlquilerTOA alquiler = crearAlquiler(tCliente);

		int res = saCliente.baja_cliente(id_cliente);

		assertFalse("No deberia haber dado de baja al cliente", exito(res));

		SAAlquiler saAlquiler = FactoriaServicioAplicacion.getInstancia().generaSAAlquiler();
		res = saAlquiler.cancelar_alquiler(alquiler.get_tLineasAlquiler().iterator().next());

		res = saCliente.baja_cliente(id_cliente);
		assertTrue("Deberia haber dado de baja al cliente", exito(res));
	}

	@Test
	public void testModificarCliente() {

		SACliente saCliente = FactoriaServicioAplicacion.getInstancia().generaSACliente();

		TCliente tCliente = new TCliente();
		tCliente.set_dni("12345678A");
		tCliente.set_nombre("Pepe");
		tCliente.set_activo(1);

		int id = saCliente.alta_cliente(tCliente);
		tCliente.set_id_cliente(id);

		tCliente.set_dni("12345678B");
		int exito = saCliente.modificar_cliente(tCliente);

		assertTrue("Deberia haber modificado el cliente", exito(exito));

		TCliente tCliente2 = new TCliente();
		tCliente2.set_dni("12345678C");
		tCliente2.set_nombre("Juan");
		tCliente2.set_activo(1);

		id = saCliente.alta_cliente(tCliente2);
		tCliente2.set_id_cliente(id);

		tCliente2.set_dni("12345678B");
		exito = saCliente.modificar_cliente(tCliente2);

		assertFalse("No deberia haber modificado el cliente por repetir dni", exito(exito));

		saCliente.baja_cliente(tCliente2.get_id_cliente());
		tCliente2.set_dni("123456678C");
		tCliente2.set_nombre("Pepe");

		exito = saCliente.modificar_cliente(tCliente2);
		assertFalse("No deberia haber modificado el cliente por estar de baja", exito(exito));
	}

	@Test
	public void BuscarCliente() {

		SACliente saCliente = FactoriaServicioAplicacion.getInstancia().generaSACliente();

		TCliente tCliente = new TCliente();
		tCliente.set_dni("12345678A");
		tCliente.set_nombre("Pepe");
		tCliente.set_activo(1);

		int id = saCliente.alta_cliente(tCliente);

		TCliente tCliente2 = saCliente.buscar_cliente(id);

		assertEquals("El id deberia ser igual", id, tCliente2.get_id_cliente());
		assertEquals("El DNI deberia ser el mismo", "12345678A", tCliente2.get_dni());
		assertEquals("El nombre deberia ser el mismo", "Pepe", tCliente2.get_nombre());

		TCliente tCliente3 = saCliente.buscar_cliente(2);

		assertNull("No deberia encontrar el cliente", tCliente3);
	}

	@Test
	public void listarTodoCliente() {

		SACliente saCliente = FactoriaServicioAplicacion.getInstancia().generaSACliente();

		Set<TCliente> listaIn = new LinkedHashSet<TCliente>();
		TCliente tCliente = new TCliente();
		tCliente.set_dni("12345678A");
		tCliente.set_nombre("Pepe");
		tCliente.set_activo(1);

		int id = saCliente.alta_cliente(tCliente);
		tCliente.set_id_cliente(id);
		listaIn.add(tCliente);

		TCliente tCliente2 = new TCliente();
		tCliente2.set_dni("12345678B");
		tCliente2.set_nombre("Juan");
		tCliente2.set_activo(1);

		id = saCliente.alta_cliente(tCliente2);
		tCliente2.set_id_cliente(id);
		listaIn.add(tCliente2);

		Set<TCliente> listaRead = saCliente.listar_clientes();

		assertEquals("Deberian ser iguales", listaIn.size(), listaRead.size());

		Iterator<TCliente> it = listaIn.iterator();
		Iterator<TCliente> it2 = listaRead.iterator();

		while (it.hasNext() && it2.hasNext()) {
			assertTrue("Deberian ser iguales", compRead(it.next(), it2.next()));
		}
	}

	@After
	public void limpiarDatos() {
		LimpiarTabla.limpiarTabla("LINEAALQUILER");
		LimpiarTabla.limpiarTabla("ALQUILER");
		LimpiarTabla.limpiarTabla("ARCADE");
		LimpiarTabla.limpiarTabla("RECREATIVA");
		LimpiarTabla.limpiarTabla("MAQUINA");
		LimpiarTabla.limpiarTabla("VINCULACIONMODELOPROVEEDOR");
		LimpiarTabla.limpiarTabla("PROVEEDOR");
		LimpiarTabla.limpiarTabla("MODELO");
		LimpiarTabla.limpiarTabla("EMPLEADODEPENDIENTE");
		LimpiarTabla.limpiarTabla("EMPLEADOTECNICO");
		LimpiarTabla.limpiarTabla("EMPLEADO");
		LimpiarTabla.limpiarTabla("CLIENTE");
	}
}

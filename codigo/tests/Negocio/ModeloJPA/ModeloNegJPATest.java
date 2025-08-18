package Negocio.ModeloJPA;

import static org.junit.Assert.*;

import javax.persistence.EntityManager;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import Integracion.EMFSingleton.EMFSingleton;
import Negocio.ProveedorJPA.SAProveedor;
import Negocio.ProveedorJPA.SAProveedorImp;
import Negocio.ProveedorJPA.TProveedor;

public class ModeloNegJPATest {

	private SAModelo saModelo;
	private SAProveedor saProveedor;
	private EntityManager em = EMFSingleton.getInstancia().getEntityManagerFactory().createEntityManager();

	@Before
	public void setUp() {
		saModelo = new SAModeloImp();
		saProveedor = new SAProveedorImp();
		limpiar();
	}

	@After
	public void tearDown() {
		em.getTransaction().begin();
		limpiar();
	}

	void limpiar() {
		if (em.getTransaction().isActive()) {
			em.getTransaction().rollback();
		}
		
		em.getTransaction().begin();
		em.createQuery("DELETE FROM LineaAlquiler").executeUpdate();
		em.createQuery("DELETE FROM Alquiler").executeUpdate();
		em.createQuery("DELETE FROM Arcade").executeUpdate();
		em.createQuery("DELETE FROM Recreativa").executeUpdate();
		em.createQuery("DELETE FROM Maquina").executeUpdate();
		em.createQuery("DELETE FROM VinculacionModeloProveedor").executeUpdate();
		em.createQuery("DELETE FROM Proveedor").executeUpdate();
		em.createQuery("DELETE FROM Modelo").executeUpdate();
		em.createQuery("DELETE FROM EmpleadoDependiente").executeUpdate();
		em.createQuery("DELETE FROM EmpleadoTecnico").executeUpdate();
		em.createQuery("DELETE FROM Empleado").executeUpdate();
		em.createQuery("DELETE FROM Cliente").executeUpdate();
		em.getTransaction().commit();
	}

	@Test
	public void testVincularModeloProveedor() {

		TProveedor proveedor = new TProveedor();
		proveedor.set_activo(1);
		proveedor.set_CIF("12345678A");
		proveedor.set_nombre("Proveedor Activo");

		int idProveedor = saProveedor.alta_proveedor(proveedor);
		proveedor.set_id_proveedor(idProveedor);

		TModelo modelo = new TModelo();
		modelo.set_activo(1);
		modelo.set_nombre("Modelo Activo");

		int idModelo = saModelo.insertar_modelo(modelo);
		modelo.set_id(idModelo);

		TVinculacionModeloProveedor tVinculacion = new TVinculacionModeloProveedor(modelo.get_id(),
				proveedor.get_id_proveedor());

		int resultado = saModelo.vincular_modelo_proveedor(tVinculacion);

		assertEquals("Vinculacion ok", 1, resultado);

		VinculacionModeloProveedor vinculacion = em.find(VinculacionModeloProveedor.class,
				new VinculacionModeloProveedorID(modelo.get_id(), proveedor.get_id_proveedor()));

		assertNotNull("Vinculacion existe", vinculacion);

		int resultadoNuevaVinculacion = saModelo.vincular_modelo_proveedor(tVinculacion);

		assertEquals("Vinculacion fallada por movelo proveedor ya vinculados", -1, resultadoNuevaVinculacion);
	}

	@Test
	public void testVincularProveedorInactivo() {

		TProveedor proveedor = new TProveedor();
		proveedor.set_activo(1);
		proveedor.set_CIF("12345678A");
		proveedor.set_nombre("Proveedor Activo");

		int idProveedor = saProveedor.alta_proveedor(proveedor);
		proveedor.set_id_proveedor(idProveedor);

		TModelo modelo = new TModelo();
		modelo.set_activo(1);
		modelo.set_nombre("Modelo Activo");

		int idModelo = saModelo.insertar_modelo(modelo);
		modelo.set_id(idModelo);

		saProveedor.baja_proveedor(proveedor.get_id_proveedor());

		TVinculacionModeloProveedor tVinculacion = new TVinculacionModeloProveedor(modelo.get_id(),
				proveedor.get_id_proveedor());

		int resultado = saModelo.vincular_modelo_proveedor(tVinculacion);

		assertEquals("Fallo por proveedor inactivo", -1, resultado);
	}

	@Test
	public void testVincularModeloInactivo() {

		TProveedor proveedor = new TProveedor();
		proveedor.set_activo(1);
		proveedor.set_CIF("12345678A");
		proveedor.set_nombre("Proveedor Activo");

		int idProveedor = saProveedor.alta_proveedor(proveedor);
		proveedor.set_id_proveedor(idProveedor);

		TModelo modelo = new TModelo();
		modelo.set_activo(1);
		modelo.set_nombre("Modelo Activo");

		int idModelo = saModelo.insertar_modelo(modelo);
		modelo.set_id(idModelo);

		saModelo.eliminar_modelo(modelo.get_id());

		TVinculacionModeloProveedor tVinculacion = new TVinculacionModeloProveedor(modelo.get_id(),
				proveedor.get_id_proveedor());

		int resultado = saModelo.vincular_modelo_proveedor(tVinculacion);

		assertEquals("Fallo por proveedor inactivo", -1, resultado);
	}

	@Test
	public void testDesvincularModeloProveedor() {

		TProveedor proveedor = new TProveedor();
		proveedor.set_activo(1);
		proveedor.set_CIF("12345678A");
		proveedor.set_nombre("Proveedor Activo");

		int idProveedor = saProveedor.alta_proveedor(proveedor);
		proveedor.set_id_proveedor(idProveedor);

		TModelo modelo = new TModelo();
		modelo.set_activo(1);
		modelo.set_nombre("Modelo Activo");

		int idModelo = saModelo.insertar_modelo(modelo);
		modelo.set_id(idModelo);

		int resultadoVinculacion = saModelo.vincular_modelo_proveedor(
				new TVinculacionModeloProveedor(modelo.get_id(), proveedor.get_id_proveedor()));

		assertEquals("Vinculacion ok", 1, resultadoVinculacion);

		TVinculacionModeloProveedor tVinculacion = new TVinculacionModeloProveedor(modelo.get_id(),
				proveedor.get_id_proveedor());

		int resultado = saModelo.desvincular_modelo_proveedor(tVinculacion);

		assertEquals("Desvinculacion ok", 1, resultado);

		VinculacionModeloProveedor vinculacionEliminada = em.find(VinculacionModeloProveedor.class,
				new VinculacionModeloProveedorID(modelo.get_id(), proveedor.get_id_proveedor()));

		assertNull("Vinculacion deberia de haberse eliminado, bajas físicas", vinculacionEliminada);

		int resultadoNuevaDesvinculacion = saModelo.desvincular_modelo_proveedor(tVinculacion);

		assertEquals("Desvinculacion fallada por movelo proveedor ya desvinculados", -1, resultadoNuevaDesvinculacion);
	}

	@Test
	public void testBajaConVinculacionesActivas() {

		TProveedor proveedor = new TProveedor();
		proveedor.set_activo(1);
		proveedor.set_CIF("12345678A");
		proveedor.set_nombre("Proveedor Activo");

		int idProveedor = saProveedor.alta_proveedor(proveedor);
		proveedor.set_id_proveedor(idProveedor);

		TModelo modelo = new TModelo();
		modelo.set_activo(1);
		modelo.set_nombre("Modelo Activo");

		int idModelo = saModelo.insertar_modelo(modelo);
		modelo.set_id(idModelo);

		int resultadoVinculacion = saModelo.vincular_modelo_proveedor(
				new TVinculacionModeloProveedor(modelo.get_id(), proveedor.get_id_proveedor()));

		assertEquals("Vinculacion ok", 1, resultadoVinculacion);

		int resultadoModelo = saModelo.eliminar_modelo(modelo.get_id());
		int resultadoProveedor = saProveedor.baja_proveedor(proveedor.get_id_proveedor());

		assertEquals("Fallo baja por vinculacion modelo", resultadoModelo, -3);
		assertEquals("Fallo baja por vinculacion proveedor", resultadoProveedor, -1);

	}

	@Test
	public void testVinculacionConEntidadesInexistentesODadasDeBaja() {

		TProveedor proveedor = new TProveedor();
		proveedor.set_activo(1);
		proveedor.set_CIF("12345678A");
		proveedor.set_nombre("Proveedor Activo");

		int idProveedor = saProveedor.alta_proveedor(proveedor);
		proveedor.set_id_proveedor(idProveedor);

		TModelo modelo = new TModelo();
		modelo.set_activo(1);
		modelo.set_nombre("Modelo Activo");

		int idModelo = saModelo.insertar_modelo(modelo);
		modelo.set_id(idModelo);

		saModelo.eliminar_modelo(modelo.get_id());

		int resultadoVinculacion = saModelo.vincular_modelo_proveedor(
				new TVinculacionModeloProveedor(modelo.get_id(), proveedor.get_id_proveedor()));

		assertEquals("Vinculacion no ok, modelo inactivo", -1, resultadoVinculacion);

		int altaModelo = saModelo.insertar_modelo(modelo);

		assertEquals("Alta modelo", true, altaModelo > 0);

		saProveedor.baja_proveedor(proveedor.get_id_proveedor());

		int resultadoVinculacionNueva = saModelo.vincular_modelo_proveedor(
				new TVinculacionModeloProveedor(modelo.get_id(), proveedor.get_id_proveedor()));

		assertEquals("Vinculacion no ok, proveedor inactivo", -1, resultadoVinculacionNueva);

		int resultadoVinculacionProveedorInexistente = saModelo
				.vincular_modelo_proveedor(new TVinculacionModeloProveedor(modelo.get_id(), 100));

		assertEquals("Vinculacion no ok, proveedor inexistente", -1, resultadoVinculacionProveedorInexistente);

		int resultadoVinculacionModeloInexistente = saModelo
				.vincular_modelo_proveedor(new TVinculacionModeloProveedor(100, proveedor.get_id_proveedor()));

		assertEquals("Vinculacion no ok, modelo inexistente", -1, resultadoVinculacionModeloInexistente);
	}

	@Test
	public void testDesvinculacionConEntidadesInexistentesODadasDeBaja() {

		TProveedor proveedor = new TProveedor();
		proveedor.set_activo(1);
		proveedor.set_CIF("12345678A");
		proveedor.set_nombre("Proveedor Activo");

		int idProveedor = saProveedor.alta_proveedor(proveedor);
		proveedor.set_id_proveedor(idProveedor);

		TModelo modelo = new TModelo();
		modelo.set_activo(1);
		modelo.set_nombre("Modelo Activo");

		int idModelo = saModelo.insertar_modelo(modelo);
		modelo.set_id(idModelo);

		saModelo.eliminar_modelo(modelo.get_id());

		int resultadoVinculacion = saModelo.desvincular_modelo_proveedor(
				new TVinculacionModeloProveedor(modelo.get_id(), proveedor.get_id_proveedor()));

		assertEquals("Desvinculacion no ok, modelo inactivo", -1, resultadoVinculacion);

		int altaModelo = saModelo.insertar_modelo(modelo);

		assertEquals("Alta modelo", true, altaModelo > 0);

		saProveedor.baja_proveedor(proveedor.get_id_proveedor());

		int resultadoVinculacionNueva = saModelo.desvincular_modelo_proveedor(
				new TVinculacionModeloProveedor(modelo.get_id(), proveedor.get_id_proveedor()));

		assertEquals("Desvinculacion no ok, proveedor inactivo", -1, resultadoVinculacionNueva);

		int resultadoVinculacionProveedorInexistente = saModelo
				.desvincular_modelo_proveedor(new TVinculacionModeloProveedor(modelo.get_id(), 100));

		assertEquals("Desvinculacion no ok, proveedor inexistente", -1, resultadoVinculacionProveedorInexistente);

		int resultadoVinculacionModeloInexistente = saModelo
				.desvincular_modelo_proveedor(new TVinculacionModeloProveedor(100, proveedor.get_id_proveedor()));

		assertEquals("Desvinculacion no ok, modelo inexistente", -1, resultadoVinculacionModeloInexistente);
	}
}
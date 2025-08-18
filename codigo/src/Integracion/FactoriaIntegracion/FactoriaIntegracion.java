
package Integracion.FactoriaIntegracion;

import Integracion.ProductoEnPlataforma.DAOProductoEnPlataforma;
import Integracion.Plataforma.DAOPlataforma;
import Integracion.Producto.DAOProducto;
import Integracion.Habilidad.DAOHabilidad;
import Integracion.Trabajador.DAOTrabajador;
import Integracion.Venta.DAOLineaVenta;
import Integracion.Venta.DAOVenta;
import Integracion.Trabajador.DAOVinculacionTrabajadorHabilidad;

public abstract class FactoriaIntegracion {

	private static FactoriaIntegracion instancia;

	public static synchronized FactoriaIntegracion getInstancia() {
		if (instancia == null)
			instancia = new FactoriaIntegracionImp();
		return instancia;
	}

	public abstract DAOProductoEnPlataforma generaDAOProductoEnPlataforma();

	public abstract DAOPlataforma generaDAOPlataforma();

	public abstract DAOProducto generaDAOProducto();

	public abstract DAOHabilidad generaDAOHabilidad();

	public abstract DAOTrabajador generaDAOTrabajador();

	public abstract DAOVenta generaDAOVenta();

	public abstract DAOLineaVenta generaDAOLineaVenta();

	public abstract DAOVinculacionTrabajadorHabilidad generaDAOVinculacionTrabajadorHabilidad();
}
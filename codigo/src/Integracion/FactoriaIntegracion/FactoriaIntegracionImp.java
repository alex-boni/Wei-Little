package Integracion.FactoriaIntegracion;

import Integracion.ProductoEnPlataforma.DAOProductoEnPlataforma;
import Integracion.ProductoEnPlataforma.DAOProductoEnPlataformaImp;
import Integracion.Plataforma.DAOPlataforma;
import Integracion.Plataforma.DAOPlataformaImp;
import Integracion.Producto.DAOProducto;
import Integracion.Producto.DAOProductoImp;

import Integracion.Habilidad.DAOHabilidad;
import Integracion.Habilidad.DAOHabilidadImp;
import Integracion.Trabajador.DAOTrabajador;
import Integracion.Venta.DAOLineaVenta;
import Integracion.Venta.DAOLineaVentaImp;
import Integracion.Venta.DAOVenta;
import Integracion.Venta.DAOVentaImp;
import Integracion.Trabajador.DAOVinculacionTrabajadorHabilidad;
import Integracion.Trabajador.DAOTrabajadorImp;
import Integracion.Trabajador.DAOVinculacionTrabajadorHabilidadImp;

public class FactoriaIntegracionImp extends FactoriaIntegracion {

	public DAOProductoEnPlataforma generaDAOProductoEnPlataforma() {

		return new DAOProductoEnPlataformaImp();
	}

	public DAOPlataforma generaDAOPlataforma() {
		return new DAOPlataformaImp();
	}

	public DAOProducto generaDAOProducto() {

		return new DAOProductoImp();

	}

	public DAOHabilidad generaDAOHabilidad() {

		return new DAOHabilidadImp();

	}

	public DAOTrabajador generaDAOTrabajador() {

		return new DAOTrabajadorImp();

	}

	public DAOVenta generaDAOVenta() {

		return new DAOVentaImp();
	}

	public DAOLineaVenta generaDAOLineaVenta() {

		return new DAOLineaVentaImp();
	}

	public DAOVinculacionTrabajadorHabilidad generaDAOVinculacionTrabajadorHabilidad() {

		return new DAOVinculacionTrabajadorHabilidadImp();

	}
}
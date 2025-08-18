
package Negocio.FactoriaNegocio;

import Negocio.AlquilerJPA.SAAlquiler;
import Negocio.AlquilerJPA.SAAlquilerImp;
import Negocio.ClienteJPA.SACliente;
import Negocio.ClienteJPA.SAClienteImp;
import Negocio.EmpleadoJPA.SAEmpleado;
import Negocio.EmpleadoJPA.SAEmpleadoImp;
import Negocio.Habilidad.SAHabilidad;
import Negocio.Habilidad.SAHabilidadImp;
import Negocio.MaquinaJPA.SAMaquina;
import Negocio.MaquinaJPA.SAMaquinaImp;
import Negocio.ModeloJPA.SAModelo;
import Negocio.ModeloJPA.SAModeloImp;
import Negocio.Plataforma.SAPlataforma;
import Negocio.Plataforma.SAPlataformaImp;
import Negocio.Producto.SAProducto;
import Negocio.Producto.SAProductoImp;
import Negocio.ProductoEnPlataforma.SAProductoEnPlataforma;
import Negocio.ProductoEnPlataforma.SAProductoEnPlataformaImp;
import Negocio.ProveedorJPA.SAProveedor;
import Negocio.ProveedorJPA.SAProveedorImp;
import Negocio.Trabajador.SATrabajador;
import Negocio.Trabajador.SATrabajadorImp;
import Negocio.Venta.SAVenta;
import Negocio.Venta.SAVentaImp;

class FactoriaSAImp extends FactoriaServicioAplicacion {

	@Override
	public SAPlataforma generarSAPlataforma() {
		return new SAPlataformaImp();
	}

	@Override
	public SAProducto generaSAProducto() {
		return new SAProductoImp();
	}

	@Override
	public SAProductoEnPlataforma generaSAProductoEnPlataforma() {
		return new SAProductoEnPlataformaImp();
	}

	@Override
	public SATrabajador generaSATrabajador() {
		return new SATrabajadorImp();
	}

	@Override
	public SAVenta generaSAVenta() {
		return new SAVentaImp();
	}

	@Override
	public SAHabilidad generaSAHabilidad() {
		return new SAHabilidadImp();
	}

	@Override
	public SAAlquiler generaSAAlquiler() {
		return new SAAlquilerImp();
	}

	@Override
	public SAMaquina generaSAMaquina() {
		return new SAMaquinaImp();
	}

	@Override
	public SACliente generaSACliente() {
		return new SAClienteImp();
	}

	public SAProveedor generaSAProveedor() {
		return new SAProveedorImp();
	}

	public SAModelo generaSAModelo() {
		return new SAModeloImp();

	}

	@Override
	public SAEmpleado generaSAEmpleado() {
		return new SAEmpleadoImp();
	}

}
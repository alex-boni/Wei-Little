package Negocio.FactoriaNegocio;

import Negocio.AlquilerJPA.SAAlquiler;
import Negocio.ClienteJPA.SACliente;
import Negocio.EmpleadoJPA.SAEmpleado;
import Negocio.Habilidad.SAHabilidad;
import Negocio.MaquinaJPA.SAMaquina;
import Negocio.ModeloJPA.SAModelo;
import Negocio.Plataforma.SAPlataforma;
import Negocio.Producto.SAProducto;
import Negocio.ProductoEnPlataforma.SAProductoEnPlataforma;
import Negocio.ProveedorJPA.SAProveedor;
import Negocio.Trabajador.SATrabajador;
import Negocio.Venta.SAVenta;

public abstract class FactoriaServicioAplicacion {

	private static FactoriaServicioAplicacion instancia;

	public static synchronized FactoriaServicioAplicacion getInstancia() {

		if (instancia == null) {
			instancia = new FactoriaSAImp();
		}
		return instancia;
	}

	public abstract SAPlataforma generarSAPlataforma();

	public abstract SAProducto generaSAProducto();

	public abstract SAProductoEnPlataforma generaSAProductoEnPlataforma();

	public abstract SATrabajador generaSATrabajador();

	public abstract SAVenta generaSAVenta();

	public abstract SAHabilidad generaSAHabilidad();

	public abstract SAMaquina generaSAMaquina();

	public abstract SACliente generaSACliente();

	public abstract SAProveedor generaSAProveedor();

	public abstract SAModelo generaSAModelo();

	public abstract SAAlquiler generaSAAlquiler();

	public abstract SAEmpleado generaSAEmpleado();

}

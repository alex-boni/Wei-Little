/**
 * 
 */
package Negocio.AlquilerJPA;

import Negocio.ClienteJPA.TCliente;
import Negocio.EmpleadoJPA.TEmpleado;

import java.util.Set;

public class TAlquilerTOA {

	private TCliente tCliente;

	private TEmpleado tEmpleado;

	private TAlquiler tAlquiler;

	private Set<TLineaAlquiler> tLineasAlquiler;

	public TAlquilerTOA() {
	}

	public TCliente get_tCliente() {

		return this.tCliente;
	}

	public TEmpleado get_tEmpleado() {

		return this.tEmpleado;
	}

	public TAlquiler get_tAlquiler() {

		return this.tAlquiler;
	}

	public Set<TLineaAlquiler> get_tLineasAlquiler() {

		return this.tLineasAlquiler;
	}

	public void set_tCliente(TCliente tCliente) {

		this.tCliente = tCliente;
	}

	public void set_tEmpleado(TEmpleado t_empleado) {

		this.tEmpleado = t_empleado;
	}

	public void set_tAlquiler(TAlquiler t_alquiler) {

		this.tAlquiler = t_alquiler;
	}

	public void set_tLineasAlquiler(Set<TLineaAlquiler> t_lineas_alquiler) {

		this.tLineasAlquiler = t_lineas_alquiler;
	}
}
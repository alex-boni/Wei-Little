
package Negocio.AlquilerJPA;

import java.util.Date;

public class TAlquiler {

	private int id_alquiler;
	private Date fecha;
	private double total;
	private int id_empleado;
	private int id_cliente;
	private int activo;

	public int get_activo() {
		return this.activo;
	}

	public int get_id_alquiler() {
		return id_alquiler;

	}

	public int get_id_cliente() {
		return id_cliente;

	}

	public Date get_fecha() {
		return fecha;

	}

	public double get_total() {
		return total;

	}

	public int get_id_empleado() {
		return id_empleado;

	}

	public void set_id_alquiler(int id_alquiler) {
		this.id_alquiler = id_alquiler;
	}

	public void set_id_cliente(int id_cliente) {
		this.id_cliente = id_cliente;
	}

	public void set_fecha(Date fecha) {
		this.fecha = fecha;
	}

	public void set_total(double total) {
		this.total = total;
	}

	public void set_id_empleado(int id_empleado) {
		this.id_empleado = id_empleado;
	}

	public void set_activo(int activo) {
		this.activo = activo;

	}
}
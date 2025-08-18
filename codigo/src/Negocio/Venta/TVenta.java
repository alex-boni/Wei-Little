package Negocio.Venta;

public class TVenta {

	private int id_factura;

	private double total_factura;

	private int id_trabajador;

	private int activo;

	public int get_id() {
		return id_factura;
	}

	public double get_total_factura() {
		return total_factura;
	}

	public void set_id(int id) {
		this.id_factura = id;
	}

	public void set_total_factura(double total) {
		this.total_factura = total;
	}

	public int get_trabajador() {
		return id_trabajador;
	}

	public void set_trabajador(int id) {
		this.id_trabajador = id;
	}

	public int get_activo() {
		return activo;
	}

	public void set_activo(int activo) {
		this.activo = activo;
	}
}
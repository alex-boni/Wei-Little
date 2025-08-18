package Negocio.Venta;

public class TLineaVenta {

	private int id_producto;

	private int id_factura;

	private int cantidad;

	private double precio_cantidad;

	private int activo;

	public void set_factura(int id_factura) {

		this.id_factura = id_factura;
	}

	public void set_producto(int id_producto) {

		this.id_producto = id_producto;
	}

	public void set_cantidad(int cantidad) {

		this.cantidad = cantidad;
	}

	public void set_precio_cantidad(double precio_cantidad) {
		this.precio_cantidad = precio_cantidad;
	}

	public void set_activo(int activo) {
		this.activo = activo;
	}

	public int get_producto() {

		return id_producto;
	}

	public int get_cantidad() {

		return this.cantidad;
	}

	public int get_factura() {

		return this.id_factura;
	}

	public double get_precio_cantidad() {
		return this.precio_cantidad;
	}

	public int get_activo() {
		return this.activo;
	}
}
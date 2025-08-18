package Negocio.ProductoEnPlataforma;

public class TProductoEnPlataforma {

	private int id_producto_plataforma;
	private double precio_venta_actual;
	private int cantidad;
	private int activo;
	private int id_plataforma;
	private int id_producto;

	public void set_activo(int estado) {
		this.activo = estado;
	}

	public void set_precio(double precio) {
		this.precio_venta_actual = precio;
	}

	public void set_cantidad(int cantidad) {
		this.cantidad = cantidad;
	}

	public void set_id(int id) {
		this.id_producto_plataforma = id;
	}

	public void set_id_producto(int id) {
		this.id_producto = id;
	}

	public void set_id_plataforma(int id) {
		this.id_plataforma = id;
	}

	public int get_id() {
		return this.id_producto_plataforma;
	}

	public double get_precio() {
		return this.precio_venta_actual;
	}

	public int get_cantidad() {
		return this.cantidad;
	}

	public int get_activo() {
		return this.activo;
	}

	public int get_id_producto() {
		return this.id_producto;
	}

	public int get_id_plataforma() {
		return this.id_plataforma;
	}

	public void colocar_datos(int activo, double precio, int cantidad, int id_producto, int id_plataforma) {
		this.activo = activo;
		this.cantidad = cantidad;
		this.precio_venta_actual = precio;
		this.id_plataforma = id_plataforma;
		this.id_producto = id_producto;
	}

}

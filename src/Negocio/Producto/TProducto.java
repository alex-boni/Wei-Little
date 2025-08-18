
package Negocio.Producto;

public class TProducto {

	private int id_producto;

	private String nombre;

	private String marca;

	private int activo;

	public TProducto(String nombre, String marca, int activo) {
		this.nombre = nombre;
		this.marca = marca;
		this.activo = activo;

	}

	public int get_id() {

		return this.id_producto;
	}

	public String get_nombre() {

		return this.nombre;

	}

	public String get_marca() {

		return this.marca;
	}

	public void set_id(int id) {
		this.id_producto = id;
	}

	public void set_marca(String marca) {
		this.marca = marca;
	}

	public void set_nombre(String nombre) {
		this.nombre = nombre;
	}

	public void set_activo(int activo) {
		this.activo = activo;

	}

	public int get_activo() {

		return this.activo;

	}

	public void colocar_datos(int id_producto, String nombre, String marca, int activo) {
		this.id_producto = id_producto;
		this.nombre = nombre;
		this.marca = marca;
		this.activo = activo;

	}

}
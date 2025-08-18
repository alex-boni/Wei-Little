
package Negocio.Producto;

public class TVideojuego extends TProducto {

	private int restriccion_edad;

	public TVideojuego(String nombre, String marca, int activo, int edad) {
		super(nombre, marca, activo);
		this.restriccion_edad = edad;
	}

	public int get_restriccionEdad() {
		return this.restriccion_edad;
	}

	public void set_restriccionEdad(int restriccion) {
		this.restriccion_edad = restriccion;
	}

}
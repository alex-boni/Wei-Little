/**
 * 
 */
package Negocio.Producto;

public class TComplemento extends TProducto {

	private double peso;

	public TComplemento(String nombre, String marca, int activo, double peso) {
		super(nombre, marca, activo);
		this.peso = peso;

	}

	public double get_peso() {
		return this.peso;
	}

	public void set_peso(double peso) {
		this.peso = peso;
	}
}
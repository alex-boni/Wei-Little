/**
 * 
 */
package Negocio.ModeloJPA;

public class TModelo {

	private int id;
	private int activo;

	private String nombre;

	public int get_id() {
		return this.id;
	}

	public int get_activo() {
		return this.activo;
	}

	public String get_nombre() {
		return this.nombre;
	}

	public void set_id(int id) {
		this.id = id;
	}

	public void set_activo(int activo) {
		this.activo = activo;
	}

	public void set_nombre(String nombre) {
		this.nombre = nombre;
	}
}
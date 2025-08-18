
package Negocio.Trabajador;

public class TTrabajador {

	private int id_trabajador;
	private String dni;
	private String nombre;
	protected int activo;

	public int get_id() {
		return id_trabajador;
	}

	public String get_nombre() {
		return nombre;
	}

	public void set_nombre(String nombre) {
		this.nombre = nombre;
	}

	public int get_activo() {
		return activo;
	}

	public void set_activo(int activo) {
		this.activo = activo;
	}

	public void set_id(int id_trabajador) {
		this.id_trabajador = id_trabajador;
	}

	public String get_dni() {
		return dni;
	}

	public void set_dni(String dni) {
		this.dni = dni;
	}

	public void colocar_datos(int activo, String nombre, String dni) {
		this.activo = activo;
		this.nombre = nombre;
		this.dni = dni;
	}
}
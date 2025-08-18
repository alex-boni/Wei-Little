package Negocio.Habilidad;

public class THabilidad {

	private int id_habilidad;
	private String nombre;
	private int nivel;
	private int activo;

	public THabilidad() {

	}

	public THabilidad(String nombre, int nivel, int activo) {
		this.nombre = nombre;
		this.nivel = nivel;
		this.activo = activo;
	}

	public int get_id() {
		return this.id_habilidad;
	}

	public String get_nombre() {
		return this.nombre;
	}

	public void set_nombre(String name) {
		this.nombre = name;
	}

	public int get_nivel() {
		return this.nivel;
	}

	public void set_nivel(int nivel) {
		this.nivel = nivel;
	}

	public void set_id(int id) {
		this.id_habilidad = id;
	}

	public int get_activo() {
		return this.activo;
	}

	public void set_activo(int activo) {
		this.activo = activo;
	}

	public void colocar_datos(String nombre, int nivel, int activo) {
		this.nombre = nombre;
		this.nivel = nivel;
		this.activo = activo;
	}
}
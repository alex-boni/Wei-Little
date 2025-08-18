package Negocio.Plataforma;

public class TPlataforma {
	private int id_plataforma;
	private String nombre;
	private int activo;

	public int get_id() {
		return id_plataforma;
	}

	public String get_nombre() {
		return nombre;
	}

	public void set_nombre(String nombre_nuevo) {
		nombre = nombre_nuevo;
	}

	public int get_activo() {
		return activo;
	}

	public void set_activo(int Parameter1) {
		activo = Parameter1;
	}

	public void set_id(int id_plataforma) {
		this.id_plataforma = id_plataforma;
	}

	public void colocar_datos(String nombre, int activo) {
		this.nombre = nombre;
		this.activo = activo;
	}
}

package Negocio.Trabajador;

public class TVendedor extends TTrabajador {

	private String idioma;
	private int activo;
	private int id_trabajador;

	public int get_id() {
		return id_trabajador;
	}

	public void set_id(int id_trabajador) {
		this.id_trabajador = id_trabajador;
	}

	public String get_idioma() {
		return idioma;
	}

	public int get_activo() {
		return activo;
	}

	public void set_activo(int activo) {
		this.activo = activo;
	}

	public void set_idioma(String idioma) {
		this.idioma = idioma;
	}
}
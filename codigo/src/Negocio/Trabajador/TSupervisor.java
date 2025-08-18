
package Negocio.Trabajador;

public class TSupervisor extends TTrabajador {

	private String experiencia;
	private int activo;
	private int id_trabajador;

	public int get_id() {
		return id_trabajador;
	}

	public void set_id(int id_trabajador) {
		this.id_trabajador = id_trabajador;
	}

	public String get_experiencia() {
		return experiencia;
	}

	public int get_activo() {
		return activo;
	}

	public void set_activo(int activo) {
		this.activo = activo;
	}

	public void set_experiencia(String experiencia) {
		this.experiencia = experiencia;
	}
}
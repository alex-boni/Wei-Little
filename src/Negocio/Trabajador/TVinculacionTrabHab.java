
package Negocio.Trabajador;

public class TVinculacionTrabHab {

	private int id_trabajador;

	private int id_habilidad;

	private int activo;

	public int get_id_habilidad() {
		return this.id_habilidad;
	}

	public int get_activo() {
		return this.activo;
	}

	public int get_id_trabajador() {
		return this.id_trabajador;
	}

	public void set_id_trabajador(int id_trabajador) {
		this.id_trabajador = id_trabajador;
	}

	public void set_activo(int activo) {
		this.activo = activo;
	}

	public void set_id_habilidad(int id_habilidad) {
		this.id_habilidad = id_habilidad;
	}

	public void colocar_datos(int activo, int id_trabajador, int id_habilidad) {
		this.activo = activo;
		this.id_trabajador = id_trabajador;
		this.id_habilidad = id_habilidad;
	}
}
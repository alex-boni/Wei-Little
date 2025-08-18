
package Negocio.AlquilerJPA;

public class TLineaAlquiler {

	private int id_maquina;
	private int duracion;
	private int devuelto;
	private int id_alquiler;
	private double precio;

	public int get_id_maquina() {
		return this.id_maquina;
	}

	public int get_id_alquiler() {
		return id_alquiler;

	}

	public int get_duracion() {
		return duracion;

	}

	public double get_precio() {
		return precio;

	}

	public int get_devuelto() {
		return devuelto;

	}

	public void set_id_maquina(int maquina_id) {
		this.id_maquina = maquina_id;
	}

	public void set_id_alquiler(int alquiler_id) {
		this.id_alquiler = alquiler_id;
	}

	public void set_duracion(int dur) {
		this.duracion = dur;
	}

	public void set_precio(double prec) {
		this.precio = prec;
	}

	public void set_devuelto(int dev) {
		this.devuelto = dev;
	}
}
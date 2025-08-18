/**
 * 
 */
package Negocio.MaquinaJPA;

public class TMaquina {

	private int id_maquina;

	private int activo;

	private int id_modelo;

	private String num_serie;

	private String nombre;

	private Double precio_hora_actual;

	private int alquilado;

	public TMaquina(Integer id, String nombre, int id_modelo, String num_serie, int alquilado,
			Double precio_hora_actual, int activo) {
		this.id_maquina = id;
		this.nombre = nombre;
		this.id_modelo = id_modelo;
		this.num_serie = num_serie;
		this.alquilado = alquilado;
		this.precio_hora_actual = precio_hora_actual;
		this.activo = activo;
	}

	public TMaquina(Maquina maquina) {
		this.id_maquina = maquina.get_id();
		this.nombre = maquina.get_nombre();
		this.id_modelo = maquina.get_Modelo().get_id();
		this.num_serie = maquina.get_num_serie();
		this.alquilado = maquina.get_alquilado();
		this.precio_hora_actual = maquina.get_precio_hora_actual();
		this.activo = maquina.get_activo();
	}

	public TMaquina() {

	}

	public int get_id() {

		return this.id_maquina;
	}

	public int get_activo() {

		return this.activo;
	}

	public int get_id_modelo() {
		return this.id_modelo;
	}

	public String get_num_serie() {

		return this.num_serie;
	}

	public String get_nombre() {

		return this.nombre;
	}

	public Double get_precio_hora_actual() {

		return precio_hora_actual;
	}

	public int get_alquilado() {

		return this.alquilado;
	}

	public void set_id(int id_maquina) {

		this.id_maquina = id_maquina;
	}

	public void set_activo(int activo) {

		this.activo = activo;
	}

	public void set_id_modelo(int id_modelo) {
		this.id_modelo = id_modelo;
	}

	public void set_num_serie(String num_serie) {

		this.num_serie = num_serie;
	}

	public void set_nombre(String nombre) {

		this.nombre = nombre;
	}

	public void set_precio_hora_actual(Double precio_hora_actual) {

		this.precio_hora_actual = precio_hora_actual;
	}

	public void set_alquilado(int alquilado) {

		this.alquilado = alquilado;
	}

}
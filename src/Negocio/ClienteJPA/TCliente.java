package Negocio.ClienteJPA;

public class TCliente {

	private int id_cliente;
	private String dni;
	private String nombre;
	private int activo;

	public int get_id_cliente() {

		return this.id_cliente;
	}

	public String get_dni() {

		return this.dni;
	}

	public String get_nombre() {

		return this.nombre;
	}

	public int get_activo() {

		return this.activo;
	}

	public void set_id_cliente(int id) {

		this.id_cliente = id;
	}

	public void set_dni(String dni) {

		this.dni = dni;
	}

	public void set_nombre(String nombre) {

		this.nombre = nombre;
	}

	public void set_activo(int activo) {

		this.activo = activo;
	}
}
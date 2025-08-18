package Negocio.EmpleadoJPA;

public class TEmpleado {

	private int id_empleado;
	private double salario;
	private String dni;
	private int activo;
	private String nombre;

	public TEmpleado() {
	}

	public TEmpleado(int id_empleado, String nombre, String dni, double salario, int activo) {
		this.id_empleado = id_empleado;
		this.nombre = nombre;
		this.dni = dni;
		this.salario = salario;
		this.activo = activo;
	}

	public int get_id_empleado() {
		return id_empleado;
	}

	public String get_nombre() {
		return nombre;
	}

	public String get_DNI() {
		return dni;
	}

	public double get_salario() {
		return salario;
	}

	public int get_activo() {
		return activo;
	}

	public void set_id_empleado(int id_empleado) {
		this.id_empleado = id_empleado;
	}

	public void set_nombre(String nombre) {
		this.nombre = nombre;
	}

	public void set_DNI(String dni) {
		this.dni = dni;
	}

	public void set_salario(double salario) {
		this.salario = salario;
	}

	public void set_activo(int activo) {
		this.activo = activo;
	}
}

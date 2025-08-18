package Negocio.EmpleadoJPA;

public class TEmpleadoDependiente extends TEmpleado {

	private String idioma;

	public TEmpleadoDependiente() {
	}

	public TEmpleadoDependiente(int id_empleado, String nombre, String dni, double salario, int activo, String idioma) {
		super(id_empleado, nombre, dni, salario, activo);
		this.idioma = idioma;
	}

	public String getIdioma() {
		return idioma;
	}

	public void setIdioma(String idioma) {
		this.idioma = idioma;
	}
}

package Negocio.EmpleadoJPA;

public class TEmpleadoTecnico extends TEmpleado {

	private String especializacion;

	public TEmpleadoTecnico() {
	}

	public TEmpleadoTecnico(int id_empleado, String nombre, String dni, double salario, int activo,
			String especializacion) {
		super(id_empleado, nombre, dni, salario, activo);
		this.especializacion = especializacion;
	}

	public String getEspecializacion() {
		return especializacion;
	}

	public void setEspecializacion(String especializacion) {
		this.especializacion = especializacion;
	}
}

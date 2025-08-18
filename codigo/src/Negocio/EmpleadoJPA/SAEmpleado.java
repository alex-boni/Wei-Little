package Negocio.EmpleadoJPA;

import java.util.Set;

public interface SAEmpleado {
	public int altaEmpleado(TEmpleado empleado);

	public int bajaEmpleado(int id_empleado);

	public int modificarEmpleado(TEmpleado empleado);

	public Set<TEmpleado> listarEmpleado();

	public TEmpleado mostrarEmpleado(int id_empleado);
}

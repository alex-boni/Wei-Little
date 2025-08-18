package Presentacion.Controlador.Command.CommandEmpleadoJPA;

import Negocio.EmpleadoJPA.TEmpleado;
import Negocio.FactoriaNegocio.FactoriaServicioAplicacion;
import Presentacion.Controlador.Command.Command;
import Presentacion.Controlador.Context;
import Presentacion.FactoriaPresentacion.Evento;
import java.util.Set;

public class ListarEmpleadosCommand implements Command {

	@Override
	public Context execute(Object transfer) {
		Set<TEmpleado> empleados = FactoriaServicioAplicacion.getInstancia().generaSAEmpleado().listarEmpleado();
		if (!empleados.isEmpty()) {
			return new Context(Evento.RES_LISTAR_EMPLEADO_ALL_OK, empleados);
		} else
			return new Context(Evento.RES_LISTAR_EMPLEADO_ALL_OK_VACIO, empleados);
	}
}

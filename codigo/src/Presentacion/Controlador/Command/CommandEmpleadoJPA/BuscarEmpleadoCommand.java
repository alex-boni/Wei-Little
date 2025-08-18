package Presentacion.Controlador.Command.CommandEmpleadoJPA;

import Negocio.EmpleadoJPA.TEmpleado;
import Negocio.FactoriaNegocio.FactoriaServicioAplicacion;
import Presentacion.Controlador.Command.Command;
import Presentacion.Controlador.Context;
import Presentacion.FactoriaPresentacion.Evento;

public class BuscarEmpleadoCommand implements Command {

	@Override
	public Context execute(Object transfer) {
		TEmpleado empleado = FactoriaServicioAplicacion.getInstancia().generaSAEmpleado()
				.mostrarEmpleado((int) transfer);

		if (empleado == null) {
			return new Context(Evento.RES_BUSCAR_EMPLEADO_KO, null);
		} else {
			return new Context(Evento.RES_BUSCAR_EMPLEADO_OK, empleado);
		}
	}
}

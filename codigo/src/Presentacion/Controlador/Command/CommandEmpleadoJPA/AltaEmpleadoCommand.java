package Presentacion.Controlador.Command.CommandEmpleadoJPA;

import Negocio.EmpleadoJPA.TEmpleado;
import Negocio.FactoriaNegocio.FactoriaServicioAplicacion;
import Presentacion.Controlador.Command.Command;
import Presentacion.Controlador.Context;
import Presentacion.FactoriaPresentacion.Evento;

public class AltaEmpleadoCommand implements Command {

	@Override
	public Context execute(Object transfer) {
		int empleado = FactoriaServicioAplicacion.getInstancia().generaSAEmpleado().altaEmpleado((TEmpleado) transfer);

		if (empleado == -1) {
			return new Context(Evento.RES_ALTA_EMPLEADO_KO, null);
		} else {
			return new Context(Evento.RES_ALTA_EMPLEADO_OK, empleado);
		}
	}
}

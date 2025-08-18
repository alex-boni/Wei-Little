package Presentacion.Controlador.Command.CommandEmpleadoJPA;

import Negocio.FactoriaNegocio.FactoriaServicioAplicacion;
import Presentacion.Controlador.Command.Command;
import Presentacion.Controlador.Context;
import Presentacion.FactoriaPresentacion.Evento;

public class BajaEmpleadoCommand implements Command {

	@Override
	public Context execute(Object transfer) {
		int empleado = FactoriaServicioAplicacion.getInstancia().generaSAEmpleado().bajaEmpleado((int) transfer);

		if (empleado == -1) {
			return new Context(Evento.RES_BAJA_EMPLEADO_KO, null);
		} else {
			return new Context(Evento.RES_BAJA_EMPLEADO_OK, empleado);
		}
	}
}

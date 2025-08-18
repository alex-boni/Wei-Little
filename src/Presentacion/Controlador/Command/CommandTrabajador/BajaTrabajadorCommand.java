package Presentacion.Controlador.Command.CommandTrabajador;

import Negocio.FactoriaNegocio.FactoriaServicioAplicacion;
import Presentacion.Controlador.Context;
import Presentacion.Controlador.Command.Command;
import Presentacion.FactoriaPresentacion.Evento;

public class BajaTrabajadorCommand implements Command {

	@Override
	public Context execute(Object transfer) {

		int id = (int) transfer;
		int res = FactoriaServicioAplicacion.getInstancia().generaSATrabajador().baja_trabajador(id);

		Evento evResponse;

		if (res == -1)
			evResponse = Evento.RES_BAJA_TRABAJADOR_KO;
		else
			evResponse = Evento.RES_BAJA_TRABAJADOR_OK;

		Context response = new Context(evResponse, id);

		return response;
	}

}

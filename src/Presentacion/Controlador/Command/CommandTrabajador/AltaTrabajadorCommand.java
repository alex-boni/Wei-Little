package Presentacion.Controlador.Command.CommandTrabajador;

import Negocio.FactoriaNegocio.FactoriaServicioAplicacion;
import Negocio.Trabajador.TTrabajador;
import Presentacion.Controlador.Context;
import Presentacion.Controlador.Command.Command;
import Presentacion.FactoriaPresentacion.Evento;

public class AltaTrabajadorCommand implements Command {

	@Override
	public Context execute(Object transfer) {

		TTrabajador tTrabajador = (TTrabajador) transfer;
		int res = FactoriaServicioAplicacion.getInstancia().generaSATrabajador().alta_trabajador(tTrabajador);

		Evento evResponse;

		if (res == -1)
			evResponse = Evento.RES_ALTA_TRABAJADOR_KO;
		else
			evResponse = Evento.RES_ALTA_TRABAJADOR_OK;

		Context response = new Context(evResponse, res);

		return response;
	}

}

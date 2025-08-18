package Presentacion.Controlador.Command.CommandTrabajador;

import Negocio.FactoriaNegocio.FactoriaServicioAplicacion;
import Negocio.Trabajador.TTrabajador;
import Presentacion.Controlador.Context;
import Presentacion.Controlador.Command.Command;
import Presentacion.FactoriaPresentacion.Evento;

public class ModificarTrabajadorCommand implements Command {

	@Override
	public Context execute(Object transfer) {
		TTrabajador tTrabajador = (TTrabajador) transfer;

		int res = FactoriaServicioAplicacion.getInstancia().generaSATrabajador().modificar_trabajador(tTrabajador);

		Evento evResponse;

		if (res == -1)
			evResponse = Evento.RES_MODIFICAR_TRABAJADOR_KO;
		else
			evResponse = Evento.RES_MODIFICAR_TRABAJADOR_OK;

		Context response = new Context(evResponse, tTrabajador);

		return response;
	}

}

package Presentacion.Controlador.Command.CommandTrabajador;

import Negocio.FactoriaNegocio.FactoriaServicioAplicacion;
import Negocio.Trabajador.TTrabajador;
import Presentacion.Controlador.Context;
import Presentacion.Controlador.Command.Command;
import Presentacion.FactoriaPresentacion.Evento;

public class BuscarTrabajadorCommand implements Command {
	@Override
	public Context execute(Object transfer) {
		int id = (int) transfer;
		TTrabajador trabBuscado = FactoriaServicioAplicacion.getInstancia().generaSATrabajador().listar_por_id(id);

		Evento evento;
		if (trabBuscado == null) {
			evento = Evento.RES_BUSCAR_TRABAJADOR_KO;
		} else {
			evento = Evento.RES_BUSCAR_TRABAJADOR_OK;
		}

		Context response = new Context(evento, trabBuscado);

		return response;

	}
}

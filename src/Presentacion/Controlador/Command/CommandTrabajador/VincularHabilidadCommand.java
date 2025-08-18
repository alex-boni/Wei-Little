package Presentacion.Controlador.Command.CommandTrabajador;

import Negocio.FactoriaNegocio.FactoriaServicioAplicacion;
import Negocio.Trabajador.TVinculacionTrabHab;
import Presentacion.Controlador.Context;
import Presentacion.Controlador.Command.Command;
import Presentacion.FactoriaPresentacion.Evento;

public class VincularHabilidadCommand implements Command {

	@Override
	public Context execute(Object transfer) {
		TVinculacionTrabHab tVinculacion = (TVinculacionTrabHab) transfer;
		int res = FactoriaServicioAplicacion.getInstancia().generaSATrabajador().vincular_habilidad(tVinculacion);

		Evento evResponse;

		if (res == -1)
			evResponse = Evento.RES_VINCULAR_HABILIDAD_TRABAJADOR_KO;
		else
			evResponse = Evento.RES_VINCULAR_HABILIDAD_TRABAJADOR_OK;

		Context response = new Context(evResponse, tVinculacion);

		return response;
	}

}

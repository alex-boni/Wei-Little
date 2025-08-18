package Presentacion.Controlador.Command.CommandTrabajador;

import java.util.Set;

import Negocio.FactoriaNegocio.FactoriaServicioAplicacion;
import Negocio.Trabajador.TVinculacionTrabHab;
import Presentacion.Controlador.Context;
import Presentacion.Controlador.Command.Command;
import Presentacion.FactoriaPresentacion.Evento;

public class ListarTrabajadorHabilidadCommand implements Command {

	@Override
	public Context execute(Object transfer) {
		int id = (int) transfer;
		Set<TVinculacionTrabHab> res = FactoriaServicioAplicacion.getInstancia().generaSATrabajador()
				.listar_por_habilidad(id);

		Evento evResponse;
		if (res == null)
			evResponse = Evento.RES_LISTAR_TRABAJADOR_X_HABILIDAD_KO;
		else
			evResponse = Evento.RES_LISTAR_TRABAJADOR_X_HABILIDAD_OK;

		Context response = new Context(evResponse, res);

		return response;
	}

}

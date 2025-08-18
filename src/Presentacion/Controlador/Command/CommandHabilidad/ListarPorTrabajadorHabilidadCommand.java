package Presentacion.Controlador.Command.CommandHabilidad;

import java.util.Set;

import Negocio.FactoriaNegocio.FactoriaServicioAplicacion;
import Negocio.Trabajador.TVinculacionTrabHab;
import Presentacion.Controlador.Context;
import Presentacion.Controlador.Command.Command;
import Presentacion.FactoriaPresentacion.Evento;

public class ListarPorTrabajadorHabilidadCommand implements Command {

	@Override
	public Context execute(Object transfer) {
		Set<TVinculacionTrabHab> res = FactoriaServicioAplicacion.getInstancia().generaSAHabilidad()
				.listarTodoHabilidadPorTrabajador((int) transfer);
		if (res.isEmpty())
			return new Context(Evento.RES_LISTAR_HABILIDAD_DEL_TRABAJADOR_KO, res);
		else
			return new Context(Evento.RES_LISTAR_HABILIDAD_DEL_TRABAJADOR_OK, res);
	}

}

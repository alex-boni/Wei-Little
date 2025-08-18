package Presentacion.Controlador.Command.CommandHabilidad;

import java.util.Set;

import Negocio.FactoriaNegocio.FactoriaServicioAplicacion;
import Negocio.Habilidad.THabilidad;
import Presentacion.Controlador.Context;
import Presentacion.Controlador.Command.Command;
import Presentacion.FactoriaPresentacion.Evento;

public class ListarTodoHabilidadCommand implements Command {

	@Override
	public Context execute(Object transfer) {
		Set<THabilidad> res = FactoriaServicioAplicacion.getInstancia().generaSAHabilidad()
				.listarTodasHabilidades((int) transfer);
		if (res == null)
			return new Context(Evento.RES_LISTAR_HABILIDAD_ALL_KO, res);
		else
			return new Context(Evento.RES_LISTAR_HABILIDAD_ALL_OK, res);
	}

}

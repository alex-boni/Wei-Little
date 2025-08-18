package Presentacion.Controlador.Command.CommandHabilidad;

import Negocio.FactoriaNegocio.FactoriaServicioAplicacion;
import Negocio.Habilidad.THabilidad;
import Presentacion.Controlador.Context;
import Presentacion.Controlador.Command.Command;
import Presentacion.FactoriaPresentacion.Evento;

public class AltaHabilidadCommand implements Command {

	@Override
	public Context execute(Object transfer) {
		int res = FactoriaServicioAplicacion.getInstancia().generaSAHabilidad().altaHabilidad((THabilidad) transfer);
		if (res == -1)
			return new Context(Evento.RES_ALTA_HABILIDAD_KO, res);
		else
			return new Context(Evento.RES_ALTA_HABILIDAD_OK, res);
	}

}

package Presentacion.Controlador.Command.CommandHabilidad;

import Negocio.FactoriaNegocio.FactoriaServicioAplicacion;
import Negocio.Habilidad.THabilidad;
import Presentacion.Controlador.Context;
import Presentacion.Controlador.Command.Command;
import Presentacion.FactoriaPresentacion.Evento;

public class BuscarHabilidadCommand implements Command {

	@Override
	public Context execute(Object transfer) {
		THabilidad res = FactoriaServicioAplicacion.getInstancia().generaSAHabilidad().buscarHabilidad((int) transfer);
		if (res == null)
			return new Context(Evento.RES_BUSCAR_HABILIDAD_KO, res);
		else
			return new Context(Evento.RES_BUSCAR_HABILIDAD_OK, res);
	}

}

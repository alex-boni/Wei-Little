package Presentacion.Controlador.Command.CommandHabilidad;

import Negocio.FactoriaNegocio.FactoriaServicioAplicacion;
import Presentacion.Controlador.Context;
import Presentacion.Controlador.Command.Command;
import Presentacion.FactoriaPresentacion.Evento;

public class BajaHabilidadCommand implements Command {

	@Override
	public Context execute(Object transfer) {
		int res = FactoriaServicioAplicacion.getInstancia().generaSAHabilidad().bajaHabilidad((int) transfer);
		if (res == -1)
			return new Context(Evento.RES_BAJA_HABILIDAD_KO, res);
		else
			return new Context(Evento.RES_BAJA_HABILIDAD_OK, transfer);
	}

}

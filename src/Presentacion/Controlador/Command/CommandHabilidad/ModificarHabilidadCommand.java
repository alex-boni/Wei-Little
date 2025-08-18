package Presentacion.Controlador.Command.CommandHabilidad;

import Negocio.FactoriaNegocio.FactoriaServicioAplicacion;
import Negocio.Habilidad.THabilidad;
import Presentacion.Controlador.Context;
import Presentacion.Controlador.Command.Command;
import Presentacion.FactoriaPresentacion.Evento;

public class ModificarHabilidadCommand implements Command {

	@Override
	public Context execute(Object transfer) {
		int res = FactoriaServicioAplicacion.getInstancia().generaSAHabilidad()
				.modificarHabilidad((THabilidad) transfer);
		if (res >= 0)
			return new Context(Evento.RES_MODIFICAR_HABILIDAD_OK, res);
		else if (res == -1)
			return new Context(Evento.RES_MODIFICAR_HABILIDAD_KO, res);
		else if (res == -2)
			return new Context(Evento.RES_MODIFICAR_HABILIDAD_KOSAMENAME, res);
		else if (res == -3)
			return new Context(Evento.RES_MODIFICAR_HABILIDAD_KONOID, res);
		return null;

	}

}

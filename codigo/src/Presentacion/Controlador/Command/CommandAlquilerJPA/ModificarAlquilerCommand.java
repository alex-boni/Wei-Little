
package Presentacion.Controlador.Command.CommandAlquilerJPA;

import Presentacion.Controlador.Command.Command;
import Presentacion.FactoriaPresentacion.Evento;
import Negocio.FactoriaNegocio.FactoriaServicioAplicacion;
import Presentacion.Controlador.Context;
import Negocio.AlquilerJPA.TAlquiler;

public class ModificarAlquilerCommand implements Command {

	public Context execute(Object transfer) {
		int res = FactoriaServicioAplicacion.getInstancia().generaSAAlquiler().modificar_alquiler((TAlquiler) transfer);
		if (res > 0)
			return new Context(Evento.RES_MODIFICAR_ALQUILER_OK, res);
		else if (res == -1)
			return new Context(Evento.RES_MODIFICAR_ALQUILER_KO_NOCLIENTE, res);
		else if (res == -2)
			return new Context(Evento.RES_MODIFICAR_ALQUILER_KO_NOEMPLEADO, res);
		else if (res == -3)
			return new Context(Evento.RES_MODIFICAR_ALQUILER_KO_NOALQUILER, res);
		else
			return new Context(Evento.RES_MODIFICAR_ALQUILER_KO, res);

	}
}
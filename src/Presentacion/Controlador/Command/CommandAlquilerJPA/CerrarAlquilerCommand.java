
package Presentacion.Controlador.Command.CommandAlquilerJPA;

import Presentacion.Controlador.Command.Command;
import Presentacion.FactoriaPresentacion.Evento;
import Negocio.AlquilerJPA.TCarritoAlquiler;
import Negocio.FactoriaNegocio.FactoriaServicioAplicacion;
import Presentacion.Controlador.Context;

public class CerrarAlquilerCommand implements Command {

	public Context execute(Object transfer) {
		int res = FactoriaServicioAplicacion.getInstancia().generaSAAlquiler()
				.cerrar_alquiler((TCarritoAlquiler) transfer);
		if (res > 0)
			return new Context(Evento.RES_CERRAR_ALQUILER_OK, transfer);
		else if (res == -2)
			return new Context(Evento.RES_CERRAR_ALQUILER_KO_NOCLIENTE, transfer);
		else if (res == -3)
			return new Context(Evento.RES_CERRAR_ALQUILER_KO_NOEMPLEADO, transfer);
		else if (res == -4)
			return new Context(Evento.RES_CERRAR_ALQUILER_KO_CARRITO_VACIO, transfer);
		else
			return new Context(Evento.RES_CERRAR_ALQUILER_KO, transfer);

	}
}
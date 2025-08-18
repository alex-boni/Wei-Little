/**
 * 
 */
package Presentacion.Controlador.Command.CommandAlquilerJPA;

import Presentacion.Controlador.Command.Command;
import Presentacion.FactoriaPresentacion.Evento;
import Negocio.AlquilerJPA.TLineaAlquiler;
import Negocio.FactoriaNegocio.FactoriaServicioAplicacion;
import Presentacion.Controlador.Context;

public class CancelarAlquilerCommand implements Command {

	public Context execute(Object data) {

		int res = FactoriaServicioAplicacion.getInstancia().generaSAAlquiler().cancelar_alquiler((TLineaAlquiler) data);
		if (res == 1)
			// Cancelar bien
			return new Context(Evento.RES_CANCELAR_ALQUILER_OK, null);

		else if (res == -1)

			// Alquiler no encontrado
			return new Context(Evento.RES_CANCELAR_ALQUILER_KO_NO_ALQUILER, null);

		else if (res == -2)
			// El alquiler no está activo
			return new Context(Evento.RES_CANCELAR_AQUILER_KO_ALQUILER_NO_ACTIVO, null);
		else if (res == -3)
			// Linea alquiler no encontrada
			return new Context(Evento.RES_CANCELAR_ALQUILER_KO_NO_LINEA_ALQUILER, null);

		else if (res == -4)
			// Maquina ya ha sido devuelta
			return new Context(Evento.RES_CANCELAR_ALQUILER_KO_MAQUINA_YA_DEVUELTA, null);
		else
			// Maquina no encontrada, cuando resultado es -5
			return new Context(Evento.RES_CANCELAR_ALQUILER_KO_MAQUINA_NO_ENCONTRADA, null);
	}
}
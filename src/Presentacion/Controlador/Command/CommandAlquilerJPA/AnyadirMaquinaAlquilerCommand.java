
package Presentacion.Controlador.Command.CommandAlquilerJPA;

import Presentacion.Controlador.Command.Command;
import Presentacion.FactoriaPresentacion.Evento;
import Negocio.AlquilerJPA.TCarritoAlquiler;
import Negocio.FactoriaNegocio.FactoriaServicioAplicacion;
import Presentacion.Controlador.Context;

public class AnyadirMaquinaAlquilerCommand implements Command {

	public Context execute(Object transfer) {
		int result = FactoriaServicioAplicacion.getInstancia().generaSAAlquiler()
				.anyadir_maquina((TCarritoAlquiler) transfer);

		if (result == 1)
			return new Context(Evento.RES_INSERTAR_MAQUINA_EN_ALQUILER_OK, transfer);
		else
			return new Context(Evento.RES_INSERTAR_MAQUINA_EN_ALQUILER_KO, transfer);
	}
}
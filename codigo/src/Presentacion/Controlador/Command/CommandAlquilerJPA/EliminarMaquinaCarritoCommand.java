package Presentacion.Controlador.Command.CommandAlquilerJPA;

import Presentacion.Controlador.Command.Command;
import Presentacion.FactoriaPresentacion.Evento;
import Negocio.AlquilerJPA.TCarritoAlquiler;
import Negocio.FactoriaNegocio.FactoriaServicioAplicacion;
import Presentacion.Controlador.Context;

public class EliminarMaquinaCarritoCommand implements Command {

	public Context execute(Object data) {
		int res = FactoriaServicioAplicacion.getInstancia().generaSAAlquiler()
				.eliminar_maquina((TCarritoAlquiler) data);
		if (res == 1)
			return new Context(Evento.RES_ELIMINAR_MAQUINA_EN_ALQUILER_OK, data);
		else
			return new Context(Evento.RES_ELIMINAR_MAQUINA_EN_ALQUILER_KO, data);
	}
}
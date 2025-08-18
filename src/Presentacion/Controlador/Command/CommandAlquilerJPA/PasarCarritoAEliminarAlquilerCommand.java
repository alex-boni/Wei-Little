package Presentacion.Controlador.Command.CommandAlquilerJPA;

import Negocio.AlquilerJPA.TCarritoAlquiler;
import Negocio.FactoriaNegocio.FactoriaServicioAplicacion;
import Presentacion.Controlador.Context;
import Presentacion.Controlador.Command.Command;
import Presentacion.FactoriaPresentacion.Evento;

public class PasarCarritoAEliminarAlquilerCommand implements Command {

	@Override
	public Context execute(Object transfer) {
		int result = FactoriaServicioAplicacion.getInstancia().generaSAAlquiler()
				.pasar_carrito((TCarritoAlquiler) transfer);
		if (result == 1)
			return new Context(Evento.RES_PASAR_CARRITO_ALQUILER_A_ELIMINAR_OK, transfer);
		else
			return new Context(Evento.RES_PASAR_CARRITO_ALQUILER_A_ELIMINAR_KO, transfer);
	}
}
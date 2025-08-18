/**
 * 
 */
package Presentacion.Controlador.Command.CommandAlquilerJPA;

import Presentacion.Controlador.Command.Command;
import Presentacion.FactoriaPresentacion.Evento;
import Negocio.AlquilerJPA.TAlquiler;
import Negocio.AlquilerJPA.TCarritoAlquiler;
import Negocio.FactoriaNegocio.FactoriaServicioAplicacion;
import Presentacion.Controlador.Context;

public class AbrirAlquilerCommand implements Command {

	public Context execute(Object transfer) {
		TCarritoAlquiler carrito = FactoriaServicioAplicacion.getInstancia().generaSAAlquiler()
				.abrir_alquiler((TAlquiler) transfer);

		if (carrito == null)
			return new Context(Evento.RES_ABRIR_ALQUILER_KO, null);
		else
			return new Context(Evento.RES_ABRIR_ALQUILER_OK, carrito);

	}
}
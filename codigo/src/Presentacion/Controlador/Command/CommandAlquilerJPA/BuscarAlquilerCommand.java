/**
 * 
 */
package Presentacion.Controlador.Command.CommandAlquilerJPA;

import Presentacion.Controlador.Command.Command;
import Presentacion.FactoriaPresentacion.Evento;
import Negocio.AlquilerJPA.TAlquilerTOA;
import Negocio.FactoriaNegocio.FactoriaServicioAplicacion;
import Presentacion.Controlador.Context;

public class BuscarAlquilerCommand implements Command {
	public Context execute(Object data) {

		TAlquilerTOA tAlquilerTOA = FactoriaServicioAplicacion.getInstancia().generaSAAlquiler()
				.buscar_alquiler((int) data);

		if (tAlquilerTOA != null)
			return new Context(Evento.RES_BUSCAR_ALQUILER_OK, tAlquilerTOA);
		else
			return new Context(Evento.RES_BUSCAR_ALQUILER_KO, null);

	}
}
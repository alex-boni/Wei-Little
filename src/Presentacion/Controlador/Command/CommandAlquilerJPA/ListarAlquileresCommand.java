/**
 * 
 */
package Presentacion.Controlador.Command.CommandAlquilerJPA;

import Presentacion.Controlador.Command.Command;
import Presentacion.FactoriaPresentacion.Evento;

import java.util.Set;

import Negocio.AlquilerJPA.TAlquiler;
import Negocio.FactoriaNegocio.FactoriaServicioAplicacion;
import Presentacion.Controlador.Context;

public class ListarAlquileresCommand implements Command {

	public Context execute(Object data) {
		Set<TAlquiler> alquileres = FactoriaServicioAplicacion.getInstancia().generaSAAlquiler().listar_alquileres();

		if (alquileres != null && !alquileres.isEmpty()) {
			return new Context(Evento.RES_LISTAR_ALQUILERES_ALL_OK, alquileres);
		} else {
			return new Context(Evento.RES_LISTAR_ALQUILERES_ALL_KO, null);
		}
	}
}
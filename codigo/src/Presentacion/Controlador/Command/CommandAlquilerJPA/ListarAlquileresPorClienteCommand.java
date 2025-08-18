package Presentacion.Controlador.Command.CommandAlquilerJPA;

import Presentacion.Controlador.Command.Command;
import Presentacion.Controlador.Context;
import Presentacion.FactoriaPresentacion.Evento;
import Negocio.AlquilerJPA.TAlquiler;
import Negocio.FactoriaNegocio.FactoriaServicioAplicacion;
import java.util.Set;

public class ListarAlquileresPorClienteCommand implements Command {

	@Override
	public Context execute(Object data) {

		Set<TAlquiler> alquileres = FactoriaServicioAplicacion.getInstancia().generaSAAlquiler()
				.listar_alquileres_por_cliente((int) data);

		if (alquileres != null && !alquileres.isEmpty()) {
			return new Context(Evento.RES_LISTAR_ALQUILERES_X_CLIENTE_OK, alquileres);
		} else {
			return new Context(Evento.RES_LISTAR_ALQUILERES_X_CLIENTE_KO, null);
		}
	}
}

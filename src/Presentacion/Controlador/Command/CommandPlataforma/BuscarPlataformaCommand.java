package Presentacion.Controlador.Command.CommandPlataforma;

import Negocio.FactoriaNegocio.FactoriaServicioAplicacion;
import Negocio.Plataforma.TPlataforma;
import Presentacion.Controlador.Context;
import Presentacion.Controlador.Command.Command;
import Presentacion.FactoriaPresentacion.Evento;

public class BuscarPlataformaCommand implements Command {

	@Override
	public Context execute(Object transfer) {
		TPlataforma res = FactoriaServicioAplicacion.getInstancia().generarSAPlataforma()
				.buscarPlataforma((int) transfer);
		if (res == null)
			return new Context(Evento.RES_MOSTRAR_PLATAFORMA_KO, res);
		else
			return new Context(Evento.RES_MOSTRAR_PLATAFORMA_OK, res);
	}
}

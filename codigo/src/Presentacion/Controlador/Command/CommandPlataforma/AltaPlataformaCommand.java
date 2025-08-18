package Presentacion.Controlador.Command.CommandPlataforma;

import Negocio.FactoriaNegocio.FactoriaServicioAplicacion;
import Negocio.Plataforma.TPlataforma;
import Presentacion.Controlador.Context;
import Presentacion.Controlador.Command.Command;
import Presentacion.FactoriaPresentacion.Evento;

public class AltaPlataformaCommand implements Command {

	@Override
	public Context execute(Object transfer) {
		int res = FactoriaServicioAplicacion.getInstancia().generarSAPlataforma()
				.altaPlataforma((TPlataforma) transfer);
		if (res == -1)
			return new Context(Evento.RES_ALTA_PLATAFORMA_KO, res);
		else
			return new Context(Evento.RES_ALTA_PLATAFORMA_OK, res);
	}
}

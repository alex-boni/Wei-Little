package Presentacion.Controlador.Command.CommandPlataforma;

import Negocio.FactoriaNegocio.FactoriaServicioAplicacion;
import Negocio.Plataforma.TPlataforma;
import Presentacion.Controlador.Context;
import Presentacion.Controlador.Command.Command;
import Presentacion.FactoriaPresentacion.Evento;

public class ModificarPlataformaCommand implements Command {

	@Override
	public Context execute(Object transfer) {
		int res = FactoriaServicioAplicacion.getInstancia().generarSAPlataforma()
				.modificarPlataforma((TPlataforma) transfer);
		if (res == -1)
			return new Context(Evento.RES_MODIFICAR_PLATAFORMA_KO, res);
		else if (res == -2)
			return new Context(Evento.RES_MODIFICAR_PLATAFORMA_KO_SAMENAME, res);
		else if (res == -3)
			return new Context(Evento.RES_MODIFICAR_PLATAFORMA_KO_NOID, res);
		else
			return new Context(Evento.RES_MODIFICAR_PLATAFORMA_OK, res);
	}
}

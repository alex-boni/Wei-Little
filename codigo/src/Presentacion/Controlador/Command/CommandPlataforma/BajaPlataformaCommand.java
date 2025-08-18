package Presentacion.Controlador.Command.CommandPlataforma;

import Negocio.FactoriaNegocio.FactoriaServicioAplicacion;
import Presentacion.Controlador.Context;
import Presentacion.Controlador.Command.Command;
import Presentacion.FactoriaPresentacion.Evento;

public class BajaPlataformaCommand implements Command {

	@Override
	public Context execute(Object transfer) {
		int res = FactoriaServicioAplicacion.getInstancia().generarSAPlataforma().bajaPlataforma((int) transfer);
		if (res == -1)
			return new Context(Evento.RES_BAJA_PLATAFORMA_KO, res);
		else
			return new Context(Evento.RES_BAJA_PLATAFORMA_OK, res);
	}
}

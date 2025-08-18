package Presentacion.Controlador.Command.CommandPlataforma;

import java.util.Set;

import Negocio.FactoriaNegocio.FactoriaServicioAplicacion;
import Negocio.Plataforma.TPlataforma;
import Presentacion.Controlador.Context;
import Presentacion.Controlador.Command.Command;
import Presentacion.FactoriaPresentacion.Evento;

public class ListarTodasPlataformaCommand implements Command {

	@Override
	public Context execute(Object transfer) {
		Set<TPlataforma> res = FactoriaServicioAplicacion.getInstancia().generarSAPlataforma().listarTodasPlataforma();
		if (res == null)
			return new Context(Evento.RES_MOSTRAR_ALL_PLATAFORMAS_KO, res);
		else
			return new Context(Evento.RES_MOSTRAR_ALL_PLATAFORMAS_OK, res);
	}
}

package Presentacion.Controlador.Command.CommandProductoPlataforma;

import Negocio.FactoriaNegocio.FactoriaServicioAplicacion;
import Negocio.ProductoEnPlataforma.TProductoEnPlataforma;
import Presentacion.Controlador.Command.Command;
import Presentacion.Controlador.Context;
import Presentacion.FactoriaPresentacion.Evento;
import java.util.Set;

public class ListarPorPlataformaCommand implements Command {

	@Override
	public Context execute(Object transfer) {
		Set<TProductoEnPlataforma> res = FactoriaServicioAplicacion.getInstancia().generaSAProductoEnPlataforma()
				.listarPorPlataforma((int) transfer);
		if (res == null) {
			return new Context(Evento.RES_LISTAR_PP_X_PLATAFORMA_KO, res);
		} else {
			return new Context(Evento.RES_LISTAR_PP_X_PLATAFORMA_OK, res);
		}
	}
}

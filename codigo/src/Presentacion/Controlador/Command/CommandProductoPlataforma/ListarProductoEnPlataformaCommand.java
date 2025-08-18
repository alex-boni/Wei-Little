package Presentacion.Controlador.Command.CommandProductoPlataforma;

import Negocio.FactoriaNegocio.FactoriaServicioAplicacion;
import Negocio.ProductoEnPlataforma.TProductoEnPlataforma;
import Presentacion.Controlador.Command.Command;
import Presentacion.Controlador.Context;
import Presentacion.FactoriaPresentacion.Evento;
import java.util.Set;

public class ListarProductoEnPlataformaCommand implements Command {

	@Override
	public Context execute(Object transfer) {
		Set<TProductoEnPlataforma> res = FactoriaServicioAplicacion.getInstancia().generaSAProductoEnPlataforma()
				.listarTodosProductoEnPlataforma((int) transfer);
		if (res == null) {
			return new Context(Evento.RES_LISTAR_PRODUCTO_PLATAFORMA_ALL_KO, res);
		} else {
			return new Context(Evento.RES_LISTAR_PRODUCTO_PLATAFORMA_ALL_OK, res);
		}
	}

}

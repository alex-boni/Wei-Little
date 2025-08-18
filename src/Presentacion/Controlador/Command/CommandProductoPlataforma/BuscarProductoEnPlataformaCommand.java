package Presentacion.Controlador.Command.CommandProductoPlataforma;

import Negocio.FactoriaNegocio.FactoriaServicioAplicacion;
import Negocio.ProductoEnPlataforma.TProductoEnPlataforma;
import Presentacion.Controlador.Command.Command;
import Presentacion.Controlador.Context;
import Presentacion.FactoriaPresentacion.Evento;

public class BuscarProductoEnPlataformaCommand implements Command {

	@Override
	public Context execute(Object transfer) {
		TProductoEnPlataforma res = FactoriaServicioAplicacion.getInstancia().generaSAProductoEnPlataforma()
				.buscarProductoEnPlataforma((int) transfer);
		if (res == null) {
			return new Context(Evento.RES_BUSCAR_PRODUCTO_PLATAFORMA_KO, res);
		} else {
			return new Context(Evento.RES_BUSCAR_PRODUCTO_PLATAFORMA_OK, res);
		}
	}

}

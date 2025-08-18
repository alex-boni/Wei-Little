package Presentacion.Controlador.Command.CommandProductoPlataforma;

import Negocio.FactoriaNegocio.FactoriaServicioAplicacion;
import Negocio.ProductoEnPlataforma.TProductoEnPlataforma;
import Presentacion.Controlador.Command.Command;
import Presentacion.Controlador.Context;
import Presentacion.FactoriaPresentacion.Evento;

public class AltaProductoEnPlataformaCommand implements Command {

	@Override
	public Context execute(Object transfer) {
		int res = FactoriaServicioAplicacion.getInstancia().generaSAProductoEnPlataforma()
				.altaProductoEnPlataforma((TProductoEnPlataforma) transfer);
		if (res == -1) {
			return new Context(Evento.RES_ALTA_PRODUCTO_PLATAFORMA_KO, res);
		} else {
			return new Context(Evento.RES_ALTA_PRODUCTO_PLATAFORMA_OK, res);
		}
	}

}

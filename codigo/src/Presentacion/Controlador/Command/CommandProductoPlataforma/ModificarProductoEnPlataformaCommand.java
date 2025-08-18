package Presentacion.Controlador.Command.CommandProductoPlataforma;

import Negocio.FactoriaNegocio.FactoriaServicioAplicacion;
import Negocio.ProductoEnPlataforma.TProductoEnPlataforma;
import Presentacion.Controlador.Command.Command;
import Presentacion.Controlador.Context;
import Presentacion.FactoriaPresentacion.Evento;

public class ModificarProductoEnPlataformaCommand implements Command {

	@Override
	public Context execute(Object transfer) {
		int res = FactoriaServicioAplicacion.getInstancia().generaSAProductoEnPlataforma()
				.modificarProductoEnPlataforma((TProductoEnPlataforma) transfer);
		if (res == -1) {
			return new Context(Evento.RES_MODIFICAR_PRODUCTO_PLATAFORMA_KO, res);
		} else {
			return new Context(Evento.RES_MODIFICAR_PRODUCTO_PLATAFORMA_OK, res);
		}
	}

}

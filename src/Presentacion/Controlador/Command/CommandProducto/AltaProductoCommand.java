package Presentacion.Controlador.Command.CommandProducto;

import Negocio.FactoriaNegocio.FactoriaServicioAplicacion;
import Negocio.Producto.TProducto;

import Presentacion.Controlador.Context;
import Presentacion.Controlador.Command.Command;
import Presentacion.FactoriaPresentacion.Evento;

public class AltaProductoCommand implements Command {

	@Override
	public Context execute(Object transfer) {

		TProducto tProducto = (TProducto) transfer;
		int exito = FactoriaServicioAplicacion.getInstancia().generaSAProducto().altaProducto(tProducto);

		Evento evento;

		if (exito < 0) {
			evento = Evento.RES_ALTA_PRODUCTO_KO;
		} else {
			evento = Evento.RES_ALTA_PRODUCTO_OK;
		}
		Context response = new Context(evento, exito);
		return response;
	}

}

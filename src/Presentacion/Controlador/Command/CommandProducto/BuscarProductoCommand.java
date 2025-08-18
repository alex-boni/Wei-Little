package Presentacion.Controlador.Command.CommandProducto;

import Negocio.FactoriaNegocio.FactoriaServicioAplicacion;
import Negocio.Producto.TProducto;

import Presentacion.Controlador.Context;
import Presentacion.Controlador.Command.Command;
import Presentacion.FactoriaPresentacion.Evento;

public class BuscarProductoCommand implements Command {

	@Override
	public Context execute(Object transfer) {

		TProducto prodBuscado = FactoriaServicioAplicacion.getInstancia().generaSAProducto()
				.buscarProducto((int) transfer);

		Evento evento;
		if (prodBuscado == null) {
			evento = Evento.RES_BUSCAR_PRODUCTO_KO;
		} else {
			evento = Evento.RES_BUSCAR_PRODUCTO_OK;
		}

		Context response = new Context(evento, prodBuscado);

		return response;
	}
}

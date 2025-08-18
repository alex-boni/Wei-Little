package Presentacion.Controlador.Command.CommandProducto;

import java.util.Set;

import Negocio.FactoriaNegocio.FactoriaServicioAplicacion;
import Negocio.Producto.TProducto;

import Presentacion.Controlador.Context;
import Presentacion.Controlador.Command.Command;
import Presentacion.FactoriaPresentacion.Evento;

public class listarTodoProductoCommand implements Command {

	@Override
	public Context execute(Object transfer) {

		Set<TProducto> lista = FactoriaServicioAplicacion.getInstancia().generaSAProducto().listarTodoProducto();

		Evento event;
		if (lista == null) {
			event = Evento.RES_LISTAR_PRODUCTO_ALL_KO;
		} else {
			event = Evento.RES_LISTAR_PRODUCTO_ALL_OK;
		}

		Context response = new Context(event, lista);

		return response;
	}

}

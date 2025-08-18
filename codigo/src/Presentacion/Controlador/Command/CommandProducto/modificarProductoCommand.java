package Presentacion.Controlador.Command.CommandProducto;

import Negocio.FactoriaNegocio.FactoriaServicioAplicacion;
import Negocio.Producto.TProducto;
import Presentacion.Controlador.Context;
import Presentacion.Controlador.Command.Command;
import Presentacion.FactoriaPresentacion.Evento;

public class modificarProductoCommand implements Command {

	@Override
	public Context execute(Object transfer) {

		TProducto tProducto = (TProducto) transfer;

		int res = FactoriaServicioAplicacion.getInstancia().generaSAProducto().modificarProducto(tProducto);

		Evento evResponse;
		if (res == -1)
			evResponse = Evento.RES_MODIFICAR_PRODUCTO_KO;
		else
			evResponse = Evento.RES_MODIFICAR_PRODUCTO_OK;

		Context reponse = new Context(evResponse, tProducto);

		return reponse;
	}

}

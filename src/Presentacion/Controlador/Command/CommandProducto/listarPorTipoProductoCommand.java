package Presentacion.Controlador.Command.CommandProducto;

import java.util.Set;

import Negocio.FactoriaNegocio.FactoriaServicioAplicacion;
import Negocio.Producto.TProducto;
import Presentacion.Controlador.Context;
import Presentacion.Controlador.Command.Command;
import Presentacion.FactoriaPresentacion.Evento;

public class listarPorTipoProductoCommand implements Command {

	@Override
	public Context execute(Object transfer) {

		String tProducto = (String) transfer;

		Set<TProducto> res = FactoriaServicioAplicacion.getInstancia().generaSAProducto()
				.listarPorTipoProducto(tProducto);

		Evento evResponse;
		if (res == null)
			evResponse = Evento.RES_LISTAR_PRODUCTOS_X_TIPO_KO;
		else
			evResponse = Evento.RES_LISTAR_PRODUCTOS_X_TIPO_OK;

		Context reponse = new Context(evResponse, res);

		return reponse;
	}

}

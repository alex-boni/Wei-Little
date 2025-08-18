package Presentacion.Controlador.Command.CommandProducto;

import Negocio.FactoriaNegocio.FactoriaServicioAplicacion;

import Presentacion.Controlador.Context;
import Presentacion.Controlador.Command.Command;
import Presentacion.FactoriaPresentacion.Evento;

public class BajaProductoCommand implements Command {

	@Override
	public Context execute(Object transfer) {

		int res = FactoriaServicioAplicacion.getInstancia().generaSAProducto().bajaProducto((int) transfer);

		Evento evResponse;
		if (res == -1)
			evResponse = Evento.RES_BAJA_PRODUCTO_KO;
		else
			evResponse = Evento.RES_BAJA_PRODUCTO_OK;

		Context reponse = new Context(evResponse, (int) transfer);

		return reponse;
	}

}

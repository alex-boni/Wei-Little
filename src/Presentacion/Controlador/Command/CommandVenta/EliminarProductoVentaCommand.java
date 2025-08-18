package Presentacion.Controlador.Command.CommandVenta;

import Negocio.FactoriaNegocio.FactoriaServicioAplicacion;
import Negocio.Venta.TCarrito;
import Presentacion.Controlador.Context;
import Presentacion.Controlador.Command.Command;
import Presentacion.FactoriaPresentacion.Evento;

public class EliminarProductoVentaCommand implements Command {

	@Override
	public Context execute(Object transfer) {
		int result = FactoriaServicioAplicacion.getInstancia().generaSAVenta()
				.eliminar_producto_carrito((TCarrito) transfer);

		if (result == 1)
			return new Context(Evento.RES_ELIMINAR_PP_EN_VENTA_OK, transfer);
		else
			return new Context(Evento.RES_ELIMINAR_PP_EN_VENTA_KO, transfer);
	}

}

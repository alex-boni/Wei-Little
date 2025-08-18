package Presentacion.Controlador.Command.CommandVenta;

import Negocio.FactoriaNegocio.FactoriaServicioAplicacion;
import Negocio.Venta.TCarrito;
import Presentacion.Controlador.Context;
import Presentacion.Controlador.Command.Command;
import Presentacion.FactoriaPresentacion.Evento;

public class AbrirVentaCommand implements Command {

	@Override
	public Context execute(Object transfer) {

		TCarrito carrito = FactoriaServicioAplicacion.getInstancia().generaSAVenta().abrir_venta((int) transfer);

		if (carrito == null)
			return new Context(Evento.RES_ABRIR_VENTA_KO, null);
		else
			return new Context(Evento.RES_ABRIR_VENTA_OK, carrito);
	}
}

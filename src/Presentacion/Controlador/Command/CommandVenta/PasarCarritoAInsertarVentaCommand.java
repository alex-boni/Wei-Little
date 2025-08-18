package Presentacion.Controlador.Command.CommandVenta;

import Negocio.FactoriaNegocio.FactoriaServicioAplicacion;
import Negocio.Venta.TCarrito;
import Presentacion.Controlador.Context;
import Presentacion.Controlador.Command.Command;
import Presentacion.FactoriaPresentacion.Evento;

public class PasarCarritoAInsertarVentaCommand implements Command {

	@Override
	public Context execute(Object transfer) {
		int result = FactoriaServicioAplicacion.getInstancia().generaSAVenta().pasar_carrito((TCarrito) transfer);
		if (result == 1)
			return new Context(Evento.RES_PASAR_CARRITO_A_INSERTAR_OK, transfer);
		else
			return new Context(Evento.RES_PASAR_CARRITO_A_INSERTAR_KO, transfer);
	}
}

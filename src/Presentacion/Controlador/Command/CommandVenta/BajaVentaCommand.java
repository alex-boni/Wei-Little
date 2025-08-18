package Presentacion.Controlador.Command.CommandVenta;

import Negocio.FactoriaNegocio.FactoriaServicioAplicacion;
import Presentacion.Controlador.Context;
import Presentacion.Controlador.Command.Command;
import Presentacion.FactoriaPresentacion.Evento;

public class BajaVentaCommand implements Command {

	@Override
	public Context execute(Object transfer) {
		int result = FactoriaServicioAplicacion.getInstancia().generaSAVenta().eliminar_venta((Integer) transfer);
		if (result == 1)
			return new Context(Evento.RES_BAJA_VENTA_OK, transfer);
		else
			return new Context(Evento.RES_BAJA_VENTA_KO, transfer);
	}
}

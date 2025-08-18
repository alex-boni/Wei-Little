package Presentacion.Controlador.Command.CommandVenta;

import Negocio.FactoriaNegocio.FactoriaServicioAplicacion;
import Negocio.Venta.TVenta;
import Presentacion.Controlador.Context;
import Presentacion.Controlador.Command.Command;
import Presentacion.FactoriaPresentacion.Evento;

public class ModificarVentaCommand implements Command {

	@Override
	public Context execute(Object transfer) {
		int result = FactoriaServicioAplicacion.getInstancia().generaSAVenta().modificar_venta((TVenta) transfer);
		if (result == 1)
			return new Context(Evento.RES_MODIFICAR_VENTA_OK, null);
		else
			return new Context(Evento.RES_MODIFICAR_VENTA_KO, null);
	}
}
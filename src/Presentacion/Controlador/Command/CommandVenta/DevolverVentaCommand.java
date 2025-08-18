
package Presentacion.Controlador.Command.CommandVenta;

import Negocio.FactoriaNegocio.FactoriaServicioAplicacion;
import Negocio.Venta.TLineaVenta;
import Presentacion.Controlador.Context;
import Presentacion.Controlador.Command.Command;
import Presentacion.FactoriaPresentacion.Evento;

public class DevolverVentaCommand implements Command {

	@Override
	public Context execute(Object transfer) {
		int result = FactoriaServicioAplicacion.getInstancia().generaSAVenta().devolver_venta((TLineaVenta) transfer);

		if (result == -1)
			return new Context(Evento.RES_DEVOLVER_VENTA_KO, transfer);
		else
			return new Context(Evento.RES_DEVOLVER_VENTA_OK, transfer);
	}
}

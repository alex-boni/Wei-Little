package Presentacion.Controlador.Command.CommandVenta;

import Negocio.FactoriaNegocio.FactoriaServicioAplicacion;
import Negocio.Venta.TVentaCompletaTOA;
import Presentacion.Controlador.Context;
import Presentacion.Controlador.Command.Command;
import Presentacion.FactoriaPresentacion.Evento;

public class BuscarVentaCommand implements Command {

	@Override
	public Context execute(Object transfer) {
		TVentaCompletaTOA ventaCompletaBus = FactoriaServicioAplicacion.getInstancia().generaSAVenta()
				.buscar_venta((int) transfer);
		if (ventaCompletaBus == null)
			return new Context(Evento.RES_BUSCAR_VENTA_KO, ventaCompletaBus);
		else
			return new Context(Evento.RES_BUSCAR_VENTA_OK, ventaCompletaBus);
	}

}

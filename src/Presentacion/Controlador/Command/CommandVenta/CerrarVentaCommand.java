package Presentacion.Controlador.Command.CommandVenta;

import Negocio.FactoriaNegocio.FactoriaServicioAplicacion;
import Negocio.Venta.TCarrito;
import Presentacion.Controlador.Context;
import Presentacion.Controlador.Command.Command;
import Presentacion.FactoriaPresentacion.Evento;

public class CerrarVentaCommand implements Command {

	@Override
	public Context execute(Object transfer) {
		int id_venta = FactoriaServicioAplicacion.getInstancia().generaSAVenta().cerrar_venta((TCarrito) transfer);
		if (id_venta != -1)
			return new Context(Evento.RES_CERRAR_VENTA_OK, transfer);
		else
			return new Context(Evento.RES_CERRAR_VENTA_KO, transfer);
	}

}

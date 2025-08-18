package Presentacion.Controlador.Command.CommandVenta;

import java.util.Set;

import Negocio.FactoriaNegocio.FactoriaServicioAplicacion;
import Negocio.Venta.TVenta;
import Presentacion.Controlador.Context;
import Presentacion.Controlador.Command.Command;
import Presentacion.FactoriaPresentacion.Evento;

public class ListarPorTrabajadorVentaCommand implements Command {

	@Override
	public Context execute(Object transfer) {
		Set<TVenta> setVentaTrabajador = FactoriaServicioAplicacion.getInstancia().generaSAVenta()
				.listar_por_trabajador_venta((int) transfer);
		if (setVentaTrabajador != null)
			return new Context(Evento.RES_LISTAR_VENTAS_X_TRABAJADOR_OK, setVentaTrabajador);
		else
			return new Context(Evento.RES_LISTAR_VENTAS_X_TRABAJADOR_KO, setVentaTrabajador);
	}
}

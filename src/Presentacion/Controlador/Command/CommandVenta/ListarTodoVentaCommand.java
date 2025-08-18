package Presentacion.Controlador.Command.CommandVenta;

import java.util.Set;

import Negocio.FactoriaNegocio.FactoriaServicioAplicacion;
import Negocio.Venta.TVenta;
import Presentacion.Controlador.Context;
import Presentacion.Controlador.Command.Command;
import Presentacion.FactoriaPresentacion.Evento;

public class ListarTodoVentaCommand implements Command {

	@Override
	public Context execute(Object transfer) {
		Set<TVenta> setVenta = FactoriaServicioAplicacion.getInstancia().generaSAVenta().listar_todo_venta();
		if (setVenta != null)
			return new Context(Evento.RES_LISTAR_VENTAS_ALL_OK, setVenta);
		else
			return new Context(Evento.RES_LISTAR_VENTAS_ALL_KO, setVenta);
	}
}

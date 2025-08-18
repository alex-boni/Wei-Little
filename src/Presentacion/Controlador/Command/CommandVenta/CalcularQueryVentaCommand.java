package Presentacion.Controlador.Command.CommandVenta;

import Negocio.FactoriaNegocio.FactoriaServicioAplicacion;
import Presentacion.Controlador.Context;
import Presentacion.Controlador.Command.Command;
import Presentacion.FactoriaPresentacion.Evento;

public class CalcularQueryVentaCommand implements Command {

	@Override
	public Context execute(Object transfer) {
		int total = FactoriaServicioAplicacion.getInstancia().generaSAVenta()
				.calcularTotalTrabajadorQueMasVende((Integer) transfer);
		if (total != -1)
			return new Context(Evento.RES_CALCULAR_TOTAL_POR_PRODUCTOPLATAFORMA_QUERY_OK, total);
		else
			return new Context(Evento.RES_CALCULAR_TOTAL_POR_PRODUCTOPLATAFORMA_QUERY_KO, transfer);
	}

}

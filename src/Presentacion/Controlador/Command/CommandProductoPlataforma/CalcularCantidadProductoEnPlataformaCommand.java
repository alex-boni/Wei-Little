package Presentacion.Controlador.Command.CommandProductoPlataforma;

import Negocio.FactoriaNegocio.FactoriaServicioAplicacion;
import Presentacion.Controlador.Command.Command;
import Presentacion.Controlador.Context;
import Presentacion.FactoriaPresentacion.Evento;

public class CalcularCantidadProductoEnPlataformaCommand implements Command {

	@Override
	public Context execute(Object transfer) {
		int res = FactoriaServicioAplicacion.getInstancia().generaSAProductoEnPlataforma()
				.calcularCantidadProductoEnPlataforma((int) transfer);
		if (res == -1) {
			return new Context(Evento.RES_CALCULAR_CANTIDAD_PRODUCTO_EN_PLATAFORMA_KO, res);
		} else {
			return new Context(Evento.RES_CALCULAR_CANTIDAD_PRODUCTO_EN_PLATAFORMA_OK, res);
		}
	}

}

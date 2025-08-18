package Presentacion.Controlador.Command.CommandClienteJPA;

import Presentacion.Controlador.Command.Command;
import Presentacion.FactoriaPresentacion.Evento;
import Negocio.FactoriaNegocio.FactoriaServicioAplicacion;
import Presentacion.Controlador.Context;

public class BajaClienteCommand implements Command {

	public Context execute(Object data) {

		int res = FactoriaServicioAplicacion.getInstancia().generaSACliente().baja_cliente((int) data);

		if (res == -1)
			return new Context(Evento.RES_BAJA_CLIENTE_KO, null);
		else
			return new Context(Evento.RES_BAJA_CLIENTE_OK, res);
	}
}
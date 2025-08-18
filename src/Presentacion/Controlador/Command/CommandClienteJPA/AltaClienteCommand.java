package Presentacion.Controlador.Command.CommandClienteJPA;

import Presentacion.Controlador.Command.Command;
import Presentacion.FactoriaPresentacion.Evento;
import Negocio.ClienteJPA.TCliente;
import Negocio.FactoriaNegocio.FactoriaServicioAplicacion;
import Presentacion.Controlador.Context;

public class AltaClienteCommand implements Command {

	public Context execute(Object data) {

		int res = FactoriaServicioAplicacion.getInstancia().generaSACliente().alta_cliente((TCliente) data);

		if (res == -1)
			return new Context(Evento.RES_ALTA_CLIENTE_KO, null);
		else
			return new Context(Evento.RES_ALTA_CLIENTE_OK, res);
	}
}
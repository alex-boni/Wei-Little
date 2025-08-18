package Presentacion.Controlador.Command.CommandClienteJPA;

import Presentacion.Controlador.Command.Command;
import Presentacion.FactoriaPresentacion.Evento;
import Negocio.ClienteJPA.TCliente;
import Negocio.FactoriaNegocio.FactoriaServicioAplicacion;
import Presentacion.Controlador.Context;

public class BuscarClienteCommand implements Command {

	public Context execute(Object data) {

		TCliente tCliente = FactoriaServicioAplicacion.getInstancia().generaSACliente().buscar_cliente((int) data);

		if (tCliente == null)
			return new Context(Evento.RES_BUSCAR_CLIENTE_KO, null);
		else
			return new Context(Evento.RES_BUSCAR_CLIENTE_OK, tCliente);
	}
}
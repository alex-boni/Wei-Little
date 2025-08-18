package Presentacion.Controlador.Command.CommandClienteJPA;

import Presentacion.Controlador.Command.Command;
import Presentacion.FactoriaPresentacion.Evento;
import Negocio.ClienteJPA.TCliente;
import Negocio.FactoriaNegocio.FactoriaServicioAplicacion;
import Presentacion.Controlador.Context;

public class ModificarClienteCommand implements Command {

	public Context execute(Object data) {

		int res = FactoriaServicioAplicacion.getInstancia().generaSACliente().modificar_cliente((TCliente) data);

		if (res == -1)
			return new Context(Evento.RES_MODIFICAR_CLIENTE_KO, null);
		else
			return new Context(Evento.RES_MODIFICAR_CLIENTE_OK, res);
	}
}
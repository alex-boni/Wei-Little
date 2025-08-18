package Presentacion.Controlador.Command.CommandClienteJPA;

import Presentacion.Controlador.Command.Command;
import Presentacion.FactoriaPresentacion.Evento;

import java.util.Set;

import Negocio.ClienteJPA.TCliente;
import Negocio.FactoriaNegocio.FactoriaServicioAplicacion;
import Presentacion.Controlador.Context;

public class ListarTodoClienteCommand implements Command {

	public Context execute(Object data) {

		Set<TCliente> lista = FactoriaServicioAplicacion.getInstancia().generaSACliente().listar_clientes();

		if (lista == null)
			return new Context(Evento.RES_LISTAR_CLIENTES_ALL_KO, null);
		else
			return new Context(Evento.RES_LISTAR_CLIENTES_ALL_OK, lista);
	}
}
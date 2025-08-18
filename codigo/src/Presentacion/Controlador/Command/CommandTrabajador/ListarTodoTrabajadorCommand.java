package Presentacion.Controlador.Command.CommandTrabajador;

import java.util.Set;

import Negocio.FactoriaNegocio.FactoriaServicioAplicacion;
import Negocio.Trabajador.TTrabajador;
import Presentacion.Controlador.Context;
import Presentacion.Controlador.Command.Command;
import Presentacion.FactoriaPresentacion.Evento;

public class ListarTodoTrabajadorCommand implements Command {

	@Override
	public Context execute(Object transfer) {

		Set<TTrabajador> lista = FactoriaServicioAplicacion.getInstancia().generaSATrabajador().listar_trabajadores();

		Evento event;

		if (lista == null) {
			event = Evento.RES_LISTAR_TRABAJADOR_ALL_KO;
		} else {
			event = Evento.RES_LISTAR_TRABAJADOR_ALL_OK;
		}
		Context response = new Context(event, lista);

		return response;
	}

}

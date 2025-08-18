package Presentacion.Controlador.Command.CommandTrabajador;

import java.util.Set;

import Negocio.FactoriaNegocio.FactoriaServicioAplicacion;
import Negocio.Trabajador.TTrabajador;
import Presentacion.Controlador.Context;
import Presentacion.Controlador.Command.Command;
import Presentacion.FactoriaPresentacion.Evento;

public class ListarTrabajadorTipoCommand implements Command {

	@Override
	public Context execute(Object transfer) {
		String nombre = (String) transfer;

		Set<TTrabajador> res = FactoriaServicioAplicacion.getInstancia().generaSATrabajador().listar_por_tipo(nombre);
		Evento evResponse;
		if (res == null)
			evResponse = Evento.RES_LISTAR_TRABAJADOR_X_TIPO_KO;
		else if (nombre.equalsIgnoreCase("vendedor"))
			evResponse = Evento.RES_LISTAR_TRABAJADOR_X_TIPO_VENDEDOR_OK;
		else
			evResponse = Evento.RES_LISTAR_TRABAJADOR_X_TIPO_SUPERVISOR_OK;

		Context response = new Context(evResponse, res);
		return response;
	}

}

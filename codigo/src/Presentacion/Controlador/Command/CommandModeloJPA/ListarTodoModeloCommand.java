/**
 * 
 */
package Presentacion.Controlador.Command.CommandModeloJPA;

import Presentacion.Controlador.Command.Command;
import Presentacion.FactoriaPresentacion.Evento;

import java.util.Set;

import Negocio.FactoriaNegocio.FactoriaServicioAplicacion;
import Negocio.ModeloJPA.TModelo;
import Presentacion.Controlador.Context;

public class ListarTodoModeloCommand implements Command {

	public Context execute(Object data) {

		Set<TModelo> modelos = FactoriaServicioAplicacion.getInstancia().generaSAModelo().listar_todo_modelo();

		if (modelos != null && !modelos.isEmpty())
			return new Context(Evento.RES_LISTAR_MODELO_ALL_OK, modelos);
		else if (modelos != null && modelos.isEmpty())
			return new Context(Evento.RES_LISTAR_MODELO_OK_VACIO, modelos);
		else
			return new Context(Evento.RES_LISTAR_MODELO_ALL_KO, modelos);

	}
}
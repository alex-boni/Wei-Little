/**
 * 
 */
package Presentacion.Controlador.Command.CommandMaquinaJPA;

import Presentacion.Controlador.Command.Command;
import Presentacion.FactoriaPresentacion.Evento;

import java.util.Set;

import Negocio.FactoriaNegocio.FactoriaServicioAplicacion;
import Negocio.MaquinaJPA.TMaquina;
import Presentacion.Controlador.Context;

public class ListarMaquinaCommand implements Command {

	@Override
	public Context execute(Object transfer) {
		Set<TMaquina> res = FactoriaServicioAplicacion.getInstancia().generaSAMaquina().listarTodasMaquinas();
		if (res == null) {
			return new Context(Evento.RES_LISTAR_MAQUINAS_ALL_KO, res);
		} else {
			return new Context(Evento.RES_LISTAR_MAQUINAS_ALL_OK, res);
		}
	}
}
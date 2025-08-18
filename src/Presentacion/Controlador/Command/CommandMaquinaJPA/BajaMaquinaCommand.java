/**
 * 
 */
package Presentacion.Controlador.Command.CommandMaquinaJPA;

import Presentacion.Controlador.Command.Command;
import Presentacion.FactoriaPresentacion.Evento;
import Negocio.FactoriaNegocio.FactoriaServicioAplicacion;
import Presentacion.Controlador.Context;

public class BajaMaquinaCommand implements Command {

	@Override
	public Context execute(Object transfer) {
		int res = FactoriaServicioAplicacion.getInstancia().generaSAMaquina().bajaMaquina((int) transfer);
		if (res == -1) {
			return new Context(Evento.RES_BAJA_MAQUINA_KO, res);
		} else {
			return new Context(Evento.RES_BAJA_MAQUINA_OK, res);
		}
	}

}
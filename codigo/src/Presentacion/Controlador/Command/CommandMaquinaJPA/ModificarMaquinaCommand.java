/**
 * 
 */
package Presentacion.Controlador.Command.CommandMaquinaJPA;

import Presentacion.Controlador.Command.Command;
import Presentacion.FactoriaPresentacion.Evento;
import Negocio.FactoriaNegocio.FactoriaServicioAplicacion;
import Negocio.MaquinaJPA.TMaquina;
import Presentacion.Controlador.Context;

public class ModificarMaquinaCommand implements Command {

	@Override
	public Context execute(Object transfer) {
		int res = FactoriaServicioAplicacion.getInstancia().generaSAMaquina().modificarMaquina((TMaquina) transfer);
		if (res == -1) {
			return new Context(Evento.RES_MODIFICAR_MAQUINA_KO, res);
		} else {
			return new Context(Evento.RES_MODIFICAR_MAQUINA_OK, res);
		}
	}
}
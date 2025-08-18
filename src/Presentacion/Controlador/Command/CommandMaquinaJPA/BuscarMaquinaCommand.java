/**
 * 
 */
package Presentacion.Controlador.Command.CommandMaquinaJPA;

import Presentacion.Controlador.Command.Command;
import Presentacion.FactoriaPresentacion.Evento;
import Negocio.FactoriaNegocio.FactoriaServicioAplicacion;
import Negocio.MaquinaJPA.TMaquina;
import Presentacion.Controlador.Context;

public class BuscarMaquinaCommand implements Command {

	@Override
	public Context execute(Object transfer) {
		TMaquina res = FactoriaServicioAplicacion.getInstancia().generaSAMaquina().buscarMaquina((int) transfer);
		if (res == null) {
			return new Context(Evento.RES_BUSCAR_MAQUINA_KO, res);
		} else {
			return new Context(Evento.RES_BUSCAR_MAQUINA_OK, res);
		}
	}
}
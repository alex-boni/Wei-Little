/**
 * 
 */
package Presentacion.Controlador.Command.CommandModeloJPA;

import Presentacion.Controlador.Command.Command;
import Presentacion.FactoriaPresentacion.Evento;
import Negocio.FactoriaNegocio.FactoriaServicioAplicacion;
import Negocio.ModeloJPA.TModelo;
import Presentacion.Controlador.Context;

public class ModificarModeloCommand implements Command {

	public Context execute(Object data) {

		int res = FactoriaServicioAplicacion.getInstancia().generaSAModelo().modificar_modelo((TModelo) data);

		if (res != -1) {
			return new Context(Evento.RES_MODIFICAR_MODELO_OK, data);
		} else {
			return new Context(Evento.RES_MODIFICAR_MODELO_KO, data);
		}
	}
}
/**
 * 
 */
package Presentacion.Controlador.Command.CommandModeloJPA;

import Presentacion.Controlador.Command.Command;
import Presentacion.FactoriaPresentacion.Evento;
import Negocio.FactoriaNegocio.FactoriaServicioAplicacion;
import Negocio.ModeloJPA.TModelo;
import Presentacion.Controlador.Context;

public class BuscarModeloCommand implements Command {

	public Context execute(Object data) {

		TModelo modelo = FactoriaServicioAplicacion.getInstancia().generaSAModelo().buscar_modelo((int) data);

		if (modelo != null)
			return new Context(Evento.RES_BUSCAR_MODELO_OK, modelo);
		else
			return new Context(Evento.RES_BUSCAR_MODELO_KO, modelo);
	}
}
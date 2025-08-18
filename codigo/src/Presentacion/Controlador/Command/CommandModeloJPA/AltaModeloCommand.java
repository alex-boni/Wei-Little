/**
 * 
 */
package Presentacion.Controlador.Command.CommandModeloJPA;

import Presentacion.Controlador.Command.Command;
import Presentacion.FactoriaPresentacion.Evento;
import Negocio.FactoriaNegocio.FactoriaServicioAplicacion;
import Negocio.ModeloJPA.TModelo;
import Presentacion.Controlador.Context;

public class AltaModeloCommand implements Command {

	public Context execute(Object data) {

		// O devuelve -1 o bien devuelve el id del modelo insertado
		int res = FactoriaServicioAplicacion.getInstancia().generaSAModelo().insertar_modelo((TModelo) data);

		if (res > 0)
			return new Context(Evento.RES_ALTA_MODELO_OK, res);
		else
			return new Context(Evento.RES_ALTA_MODELO_KO, res);

	}
}
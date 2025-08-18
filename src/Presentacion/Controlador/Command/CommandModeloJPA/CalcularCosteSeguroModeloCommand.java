/**
 * 
 */
package Presentacion.Controlador.Command.CommandModeloJPA;

import Presentacion.Controlador.Command.Command;
import Presentacion.FactoriaPresentacion.Evento;
import Negocio.FactoriaNegocio.FactoriaServicioAplicacion;
import Presentacion.Controlador.Context;

public class CalcularCosteSeguroModeloCommand implements Command {

	public Context execute(Object data) {

		double res = FactoriaServicioAplicacion.getInstancia().generaSAModelo()
				.calcular_coste_seguro_modelo((int) data);

		if (res != -1) {
			return new Context(Evento.RES_CALCULAR_COSTE_SEGURO_MODELO_OK, res);
		} else {
			return new Context(Evento.RES_CALCULAR_COSTE_SEGURO_MODELO_KO, res);
		}
	}
}
/**
 * 
 */
package Presentacion.Controlador.Command.CommandModeloJPA;

import Presentacion.Controlador.Command.Command;
import Presentacion.FactoriaPresentacion.Evento;
import Negocio.FactoriaNegocio.FactoriaServicioAplicacion;
import Presentacion.Controlador.Context;

public class BajaModeloCommand implements Command {

	public Context execute(Object data) {

		int res = FactoriaServicioAplicacion.getInstancia().generaSAModelo().eliminar_modelo((int) data);

		if (res > 0)
			return new Context(Evento.RES_BAJA_MODELO_OK, res);
		else if (res == -2)
			return new Context(Evento.RES_BAJA_MODELO_MAQUINAS_ACTIVAS_KO, res);
		else if (res == -3)
			return new Context(Evento.RES_BAJA_MODELO_VINCULACIONES_PROVEEDOR_KO, res);
		else
			return new Context(Evento.RES_BAJA_MODELO_KO, res);
	}
}
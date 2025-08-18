/**
 * 
 */
package Presentacion.Controlador.Command.CommandModeloJPA;

import Presentacion.Controlador.Command.Command;
import Presentacion.FactoriaPresentacion.Evento;
import Negocio.FactoriaNegocio.FactoriaServicioAplicacion;
import Negocio.ModeloJPA.TVinculacionModeloProveedor;
import Presentacion.Controlador.Context;

public class DesvincularProveedorModeloCommand implements Command {

	public Context execute(Object data) {

		int res = FactoriaServicioAplicacion.getInstancia().generaSAModelo()
				.desvincular_modelo_proveedor((TVinculacionModeloProveedor) data);

		if (res != -1)
			return new Context(Evento.RES_DESVINCULAR_PROVEEDOR_MODELO_OK, data);
		else
			return new Context(Evento.RES_DESVINCULAR_PROVEEDOR_MODELO_KO, data);
	}
}
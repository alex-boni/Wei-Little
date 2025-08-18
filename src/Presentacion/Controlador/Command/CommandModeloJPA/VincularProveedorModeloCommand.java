/**
 * 
 */
package Presentacion.Controlador.Command.CommandModeloJPA;

import Presentacion.Controlador.Command.Command;
import Presentacion.FactoriaPresentacion.Evento;
import Negocio.FactoriaNegocio.FactoriaServicioAplicacion;
import Negocio.ModeloJPA.TVinculacionModeloProveedor;
import Presentacion.Controlador.Context;

public class VincularProveedorModeloCommand implements Command {

	public Context execute(Object data) {

		int res = FactoriaServicioAplicacion.getInstancia().generaSAModelo()
				.vincular_modelo_proveedor((TVinculacionModeloProveedor) data);

		if (res != -1) {
			return new Context(Evento.RES_VINCULAR_PROVEEDOR_MODELO_OK, res);
		} else {
			return new Context(Evento.RES_VINCULAR_PROVEEDOR_MODELO_KO, res);
		}
	}
}
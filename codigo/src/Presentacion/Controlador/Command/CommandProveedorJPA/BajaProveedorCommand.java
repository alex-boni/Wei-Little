/**
 * 
 */
package Presentacion.Controlador.Command.CommandProveedorJPA;

import Presentacion.Controlador.Command.Command;
import Presentacion.FactoriaPresentacion.Evento;
import Negocio.FactoriaNegocio.FactoriaServicioAplicacion;

import Presentacion.Controlador.Context;

public class BajaProveedorCommand implements Command {

	public Context execute(Object data) {
		int res = FactoriaServicioAplicacion.getInstancia().generaSAProveedor().baja_proveedor((int) data);

		if (res > 0)
			return new Context(Evento.RES_BAJA_PROVEEDOR_OK, res);

		else
			return new Context(Evento.RES_BAJA_PROVEEDOR_KO, res);
	}
}
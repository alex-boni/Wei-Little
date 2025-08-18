/**
 * 
 */
package Presentacion.Controlador.Command.CommandProveedorJPA;

import Presentacion.Controlador.Command.Command;
import Presentacion.FactoriaPresentacion.Evento;

import Negocio.FactoriaNegocio.FactoriaServicioAplicacion;
import Negocio.ProveedorJPA.TProveedor;
import Presentacion.Controlador.Context;

public class ModificarProveedorCommand implements Command {

	public Context execute(Object data) {

		int res = FactoriaServicioAplicacion.getInstancia().generaSAProveedor().modificar_proveedor((TProveedor) data);
		// Devolucion de id si todo correcto
		if (res == -1)
			return new Context(Evento.RES_MODIFICAR_PROVEEDOR_KO, null);

		else
			return new Context(Evento.RES_MODIFICAR_PROVEEDOR_OK, res);

	}
}
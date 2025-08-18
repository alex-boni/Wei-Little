/**
 * 
 */
package Presentacion.Controlador.Command.CommandProveedorJPA;

import Presentacion.Controlador.Command.Command;
import Presentacion.FactoriaPresentacion.Evento;

import Negocio.FactoriaNegocio.FactoriaServicioAplicacion;
import Negocio.ProveedorJPA.TProveedor;
import Presentacion.Controlador.Context;

public class AltaProveedorCommand implements Command {

	public Context execute(Object data) {

		int res = FactoriaServicioAplicacion.getInstancia().generaSAProveedor().alta_proveedor((TProveedor) data);
		// Devuleve - 1 si no inserta, id si inserta
		if (res > 0)
			return new Context(Evento.RES_ALTA_PROVEEDOR_OK, res);
		else
			return new Context(Evento.RES_ALTA_PROVEEDOR_KO, res);
	}
}
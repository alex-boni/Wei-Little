/**
 * 
 */
package Presentacion.Controlador.Command.CommandProveedorJPA;

import Presentacion.Controlador.Command.Command;
import Presentacion.FactoriaPresentacion.Evento;
import Negocio.FactoriaNegocio.FactoriaServicioAplicacion;
import Negocio.ProveedorJPA.TProveedor;
import Presentacion.Controlador.Context;

public class BuscarProveedorCommand implements Command {

	public Context execute(Object data) {

		TProveedor proveedor = FactoriaServicioAplicacion.getInstancia().generaSAProveedor()
				.buscar_proveedor((int) data);

		if (proveedor != null)
			return new Context(Evento.RES_BUSCAR_PROVEEDOR_OK, proveedor);

		else
			return new Context(Evento.RES_BUSCAR_PROVEEDOR_KO, null);
	}
}
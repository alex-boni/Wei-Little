/**
 * 
 */
package Presentacion.Controlador.Command.CommandProveedorJPA;

import Presentacion.Controlador.Command.Command;
import Presentacion.FactoriaPresentacion.Evento;

import java.util.Set;

import Negocio.FactoriaNegocio.FactoriaServicioAplicacion;
import Negocio.ProveedorJPA.TProveedor;
import Presentacion.Controlador.Context;

public class ListarProveedorCommand implements Command {

	public Context execute(Object data) {
		Set<TProveedor> proveedores = FactoriaServicioAplicacion.getInstancia().generaSAProveedor()
				.listar_proveedores();

		if (proveedores != null && !proveedores.isEmpty())
			return new Context(Evento.RES_LISTAR_PROVEEDORES_ALL_OK, proveedores);

		else
			return new Context(Evento.RES_LISTAR_PROVEEDORES_ALL_KO, proveedores);
	}
}
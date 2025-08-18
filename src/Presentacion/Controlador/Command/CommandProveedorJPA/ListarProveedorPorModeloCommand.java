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

public class ListarProveedorPorModeloCommand implements Command {

	public Context execute(Object data) {
		Set<TProveedor> proveedores = FactoriaServicioAplicacion.getInstancia().generaSAProveedor()
				.listar_proveedores_por_modelo((int) data);

		if (proveedores != null && !proveedores.isEmpty())
			return new Context(Evento.RES_LISTAR_PROVEEDORES_MODELO_ALL_OK, proveedores);

		else
			return new Context(Evento.RES_LISTAR_PROVEEDORES_MODELO_ALL_KO, null);
	}
}
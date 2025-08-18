/**
 * 
 */
package Negocio.ProveedorJPA;

import java.util.Set;

public interface SAProveedor {

	public int alta_proveedor(TProveedor tProveedor);

	public int baja_proveedor(int id_proveedor);

	public int modificar_proveedor(TProveedor tProveedor);

	public TProveedor buscar_proveedor(int id_proveedor);

	public Set<TProveedor> listar_proveedores();

	public Set<TProveedor> listar_proveedores_por_modelo(int id_modelo);
}
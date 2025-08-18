/**
 * 
 */
package Negocio.ModeloJPA;

import java.util.Set;

public interface SAModelo {

	public int insertar_modelo(TModelo model);

	public int eliminar_modelo(int id);

	public int modificar_modelo(TModelo model);

	public TModelo buscar_modelo(int id);

	public Set<TModelo> listar_todo_modelo();

	public Set<TModelo> listar_modelo_por_proveedor(int id_proveedor);

	public int vincular_modelo_proveedor(TVinculacionModeloProveedor vinculacion);

	public int desvincular_modelo_proveedor(TVinculacionModeloProveedor vinculacion);

	public double calcular_coste_seguro_modelo(int id_modelo);
}
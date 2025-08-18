
package Negocio.Producto;

import java.util.Set;

public interface SAProducto {

	public int altaProducto(TProducto tProducto);

	public int bajaProducto(int id_producto);

	public int modificarProducto(TProducto tProducto);

	public Set<TProducto> listarTodoProducto();

	public TProducto buscarProducto(int id_producto);

	public Set<TProducto> listarPorTipoProducto(String tipo);
}
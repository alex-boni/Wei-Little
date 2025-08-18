
package Integracion.Producto;

import java.util.Set;

import Negocio.Producto.TProducto;

public interface DAOProducto {

	public int create(TProducto tProducto);

	public TProducto read(int id_producto);

	public int update(TProducto tProducto);

	public int delete(int id_producto);

	public Set<TProducto> read_all();

	public Set<TProducto> read_by_type(String tipo);

	public TProducto read_by_name(String nombre);
}
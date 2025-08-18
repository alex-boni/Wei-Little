package Negocio.ProductoEnPlataforma;

import java.util.Set;

public interface SAProductoEnPlataforma {

	public int altaProductoEnPlataforma(TProductoEnPlataforma prodenplat);

	public int bajaProductoEnPlataforma(int id_producto_final);

	public int modificarProductoEnPlataforma(TProductoEnPlataforma prodenplat);

	public TProductoEnPlataforma buscarProductoEnPlataforma(int id_prodcuto_final);

	public Set<TProductoEnPlataforma> listarTodosProductoEnPlataforma();

	public Set<TProductoEnPlataforma> listarTodosProductoEnPlataforma(int aceptar);

	public Set<TProductoEnPlataforma> listarPorPlataforma(int id_plataforma);

	public Set<TProductoEnPlataforma> listarPorProducto(int id_producto);

	public void calcularCantidadProductoenPlataforma();

	public int calcularCantidadProductoEnPlataforma(int id_producto_final);
}

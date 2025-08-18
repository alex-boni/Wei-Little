package Integracion.ProductoEnPlataforma;

import Negocio.ProductoEnPlataforma.TProductoEnPlataforma;
import java.util.Set;

public interface DAOProductoEnPlataforma {

	public int update(TProductoEnPlataforma prodActual);

	public TProductoEnPlataforma read(int id_producto_plataforma);

	public int delete(int id_producto_plataforma);

	public int create(TProductoEnPlataforma nuevoProd);

	public Set<TProductoEnPlataforma> read_all();

	public Set<TProductoEnPlataforma> read_by_platform(int id_plataforma);

	public Set<TProductoEnPlataforma> read_by_product(int id_producto);


}

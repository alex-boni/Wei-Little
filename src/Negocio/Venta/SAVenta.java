package Negocio.Venta;

import java.util.Set;

public interface SAVenta {

	public TCarrito abrir_venta(int id_trabajador);

	public int pasar_carrito(TCarrito carrito);

	public int insertar_producto_carrito(TCarrito carrito);

	public int eliminar_producto_carrito(TCarrito carrito);

	public int cerrar_venta(TCarrito carrito);

	public int eliminar_venta(int factura);

	public TVentaCompletaTOA buscar_venta(int id_factura);

	public int modificar_venta(TVenta tVenta);

	public int devolver_venta(TLineaVenta tLineaVenta);

	public int calcularTotalTrabajadorQueMasVende(Integer idProductoPlataforma);

	public Set<TVenta> listar_todo_venta();

	public Set<TVenta> listar_por_trabajador_venta(int id_trabajador);

	public Set<TLineaVenta> listar_por_producto_en_plataforma_venta(int id_producto_final);
}
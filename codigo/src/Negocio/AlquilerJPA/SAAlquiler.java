/**
 * 
 */
package Negocio.AlquilerJPA;

import java.util.Set;

public interface SAAlquiler {

	public TCarritoAlquiler abrir_alquiler(TAlquiler t_alquiler);

	public int anyadir_maquina(TCarritoAlquiler tCarrito);

	public int eliminar_maquina(TCarritoAlquiler tCarrito);

	public int cerrar_alquiler(TCarritoAlquiler tCarrito);

	public Set<TAlquiler> listar_alquileres();

	public TAlquilerTOA buscar_alquiler(int id_alquiler);

	public int modificar_alquiler(TAlquiler tAlquiler);

	public Set<TAlquiler> listar_alquileres_por_empleado(int id_empleado);

	public Set<TAlquiler> listar_alquileres_por_cliente(int id_cliente);

	public int cancelar_alquiler(TLineaAlquiler tLineaAlquiler);

	public int pasar_carrito(TCarritoAlquiler carrito);
}
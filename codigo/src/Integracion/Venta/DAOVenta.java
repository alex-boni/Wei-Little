package Integracion.Venta;

import java.util.Set;

import Negocio.Venta.TVenta;

public interface DAOVenta {

	public int create(TVenta tVenta);

	public TVenta read(int id_factura);

	public int update(TVenta tVenta);

	public int delete(int id_factura);

	public Set<TVenta> read_all();

	public Set<TVenta> readVentaPorTrabajador(int id_trabajador);

}
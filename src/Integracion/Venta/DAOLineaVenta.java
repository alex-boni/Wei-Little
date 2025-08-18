package Integracion.Venta;

import java.util.Set;

import Negocio.Venta.TLineaVenta;

public interface DAOLineaVenta {

	public int create(TLineaVenta tLineaVenta);

	public int delete(TLineaVenta tLineaVenta);

	public int update(TLineaVenta tLineaVenta);

	/**
	 * <!-- begin-UML-doc --> <!-- end-UML-doc -->
	 * 
	 * @param id_producto_final
	 * @return
	 * @generated "UML a JPA
	 *            (com.ibm.xtools.transform.uml2.ejb3.java.jpa.internal.UML2JPATransform)"
	 */
	public TLineaVenta read_all_by_Producto_en_Plataforma(int id_producto_final);

	public TLineaVenta read(TLineaVenta tLineaVenta);

	public Set<TLineaVenta> read_all(int id_factura);

	public Set<TLineaVenta> readVentaPorProductoPlataforma(int id_producto_final);
}
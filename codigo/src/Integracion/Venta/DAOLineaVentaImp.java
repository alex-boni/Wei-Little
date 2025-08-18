package Integracion.Venta;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Set;
import java.util.LinkedHashSet;

import Integracion.Transaction.TManager;
import Integracion.Transaction.Transaction;
import Negocio.Venta.TLineaVenta;

public class DAOLineaVentaImp implements DAOLineaVenta {

	public int create(TLineaVenta tLineaVenta) {

		try {

			TManager tm = TManager.getInstance();
			Transaction tr = tm.getTransaction();
			Connection c = (Connection) tr.getResource();

			PreparedStatement s = c.prepareStatement(
					"INSERT INTO LINEAVENTA(precio_venta, id_producto_final ,cantidadVenta, id_venta, activo) VALUES (?, ?, ?, ?, ?)");

			s.setDouble(1, tLineaVenta.get_precio_cantidad());
			s.setInt(2, tLineaVenta.get_producto());
			s.setInt(3, tLineaVenta.get_cantidad());
			s.setInt(4, tLineaVenta.get_factura());
			s.setInt(5, 1);

			s.executeUpdate();
			s.close();

			return 1;

		} catch (Exception e) {
			e.printStackTrace();
		}

		return -1;
	}

	@Override
	public int delete(TLineaVenta tLineaVenta) {

		try {

			TManager tm = TManager.getInstance();
			Transaction tr = tm.getTransaction();
			Connection c = (Connection) tr.getResource();

			PreparedStatement s = c
					.prepareStatement("UPDATE LINEAVENTA SET activo = 0 WHERE id_producto_final = ? AND id_venta = ?");

			s.setInt(1, tLineaVenta.get_producto());
			s.setInt(2, tLineaVenta.get_factura());

			s.executeUpdate();
			s.close();

			return 1;

		} catch (Exception e) {
			e.printStackTrace();
		}

		return -1;
	}

	@Override
	public TLineaVenta read(TLineaVenta tLineaVenta) {

		try {

			TManager tm = TManager.getInstance();
			Transaction tr = tm.getTransaction();
			Connection c = (Connection) tr.getResource();

			PreparedStatement s = c.prepareStatement(
					"SELECT precio_venta, id_producto_final, cantidadVenta, id_venta, activo FROM LINEAVENTA WHERE id_producto_final = ? AND id_venta = ? FOR UPDATE");

			s.setInt(1, tLineaVenta.get_producto());
			s.setInt(2, tLineaVenta.get_factura());

			ResultSet r = s.executeQuery();

			TLineaVenta lineaVenta = null;

			if (r.next()) {

				lineaVenta = new TLineaVenta();

				lineaVenta.set_precio_cantidad(r.getDouble(1));
				lineaVenta.set_producto(r.getInt(2));
				lineaVenta.set_cantidad(r.getInt(3));
				lineaVenta.set_factura(r.getInt(4));
				lineaVenta.set_activo(r.getInt(5));
			}

			r.close();
			s.close();

			return lineaVenta;

		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	@Override
	public int update(TLineaVenta tProductoCantidad) {

		try {

			TManager tm = TManager.getInstance();
			Transaction tr = tm.getTransaction();
			Connection c = (Connection) tr.getResource();

			PreparedStatement s = c.prepareStatement(
					"UPDATE LINEAVENTA SET precio_venta = ?, cantidadVenta = ? WHERE id_producto_final = ? AND id_venta = ?");

			s.setDouble(1, tProductoCantidad.get_precio_cantidad());
			s.setInt(2, tProductoCantidad.get_cantidad());
			s.setInt(3, tProductoCantidad.get_producto());
			s.setInt(4, tProductoCantidad.get_factura());

			s.executeUpdate();
			s.close();

			return 1;

		} catch (Exception e) {
			e.printStackTrace();
		}

		return -1;
	}

	/**
	 * (non-Javadoc)
	 * 
	 * @see DAOLineaVenta#read_all_by_Producto_en_Plataforma(int id_producto_final)
	 * @generated "UML a JPA
	 *            (com.ibm.xtools.transform.uml2.ejb3.java.jpa.internal.UML2JPATransform)"
	 */
	public TLineaVenta read_all_by_Producto_en_Plataforma(int id_producto_final) {
		// begin-user-code
		// TODO Auto-generated method stub
		return null;
		// end-user-code
	}

	public Set<TLineaVenta> read_all(int id_factura) {

		Set<TLineaVenta> lista = new LinkedHashSet<>();

		try {

			TManager tm = TManager.getInstance();
			Transaction tr = tm.getTransaction();
			Connection c = (Connection) tr.getResource();

			PreparedStatement s = c.prepareStatement(
					"SELECT precio_venta, id_producto_final, cantidadVenta, id_venta, activo FROM LINEAVENTA WHERE id_venta = ? FOR UPDATE");

			s.setInt(1, id_factura);

			ResultSet r = s.executeQuery();

			while (r.next()) {

				TLineaVenta lineaVenta = new TLineaVenta();

				lineaVenta.set_precio_cantidad(r.getDouble(1));
				lineaVenta.set_producto(r.getInt(2));
				lineaVenta.set_cantidad(r.getInt(3));
				lineaVenta.set_factura(r.getInt(4));
				lineaVenta.set_activo(r.getInt(5));

				lista.add(lineaVenta);
			}

			r.close();
			s.close();

			return lista;

		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	public Set<TLineaVenta> readVentaPorProductoPlataforma(int id_producto_final) {

		Set<TLineaVenta> lista = new LinkedHashSet<>();

		try {

			TManager tm = TManager.getInstance();
			Transaction tr = tm.getTransaction();
			Connection c = (Connection) tr.getResource();

			PreparedStatement s = c.prepareStatement(
					"SELECT precio_venta, id_producto_final, cantidadVenta, id_venta, activo FROM LINEAVENTA WHERE id_producto_final = ? FOR UPDATE");

			s.setInt(1, id_producto_final);

			ResultSet r = s.executeQuery();

			while (r.next()) {

				TLineaVenta lineaVenta = new TLineaVenta();

				lineaVenta.set_precio_cantidad(r.getDouble(1));
				lineaVenta.set_producto(r.getInt(2));
				lineaVenta.set_cantidad(r.getInt(3));
				lineaVenta.set_factura(r.getInt(4));
				lineaVenta.set_activo(r.getInt(5));

				lista.add(lineaVenta);
			}

			r.close();
			s.close();

			return lista;

		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;
	}

}
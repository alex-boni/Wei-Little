package Integracion.Venta;

import java.util.Set;
import java.util.LinkedHashSet;

import Integracion.Transaction.TManager;
import Integracion.Transaction.Transaction;
import Negocio.Venta.TVenta;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

public class DAOVentaImp implements DAOVenta {

	public int create(TVenta tVenta) {

		try {

			TManager tm = TManager.getInstance();
			Transaction tr = tm.getTransaction();
			Connection c = (Connection) tr.getResource();

			PreparedStatement s = c.prepareStatement(
					"INSERT INTO VENTAS(total_factura, id_trabajador ,activo) VALUES (?, ?,?)",
					Statement.RETURN_GENERATED_KEYS);

			s.setDouble(1, tVenta.get_total_factura());
			s.setInt(2, tVenta.get_trabajador());
			s.setInt(3, 1);
			s.executeUpdate();

			ResultSet r = s.getGeneratedKeys();

			r.next();
			int id = r.getInt(1);

			r.close();
			s.close();

			return id;

		} catch (Exception e) {
			e.printStackTrace();
		}

		return -1;
	}

	public TVenta read(int id_venta) {

		try {

			TManager tm = TManager.getInstance();
			Transaction tr = tm.getTransaction();
			Connection c = (Connection) tr.getResource();

			PreparedStatement s = c.prepareStatement(
					"SELECT total_factura, id_trabajador, activo, id_venta FROM VENTAS WHERE id_venta = ? FOR UPDATE");

			s.setInt(1, id_venta);

			ResultSet r = s.executeQuery();

			TVenta venta = null;

			if (r.next()) {

				venta = new TVenta();

				venta.set_total_factura(r.getDouble(1));
				venta.set_trabajador(r.getInt(2));
				venta.set_activo(r.getInt(3));
				venta.set_id(r.getInt(4));
			}

			r.close();
			s.close();

			return venta;

		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	public int update(TVenta tVenta) {

		try {

			TManager tm = TManager.getInstance();
			Transaction tr = tm.getTransaction();
			Connection c = (Connection) tr.getResource();

			PreparedStatement s = c
					.prepareStatement("UPDATE VENTAS SET total_factura = ?, id_trabajador = ? WHERE id_venta = ?");

			s.setDouble(1, tVenta.get_total_factura());
			s.setInt(2, tVenta.get_trabajador());
			s.setInt(3, tVenta.get_id());

			s.executeUpdate();
			s.close();

			return 1;

		} catch (Exception e) {
			e.printStackTrace();
		}

		return -1;
	}

	public int delete(int id_venta) {

		try {

			TManager tm = TManager.getInstance();
			Transaction tr = tm.getTransaction();
			Connection c = (Connection) tr.getResource();

			PreparedStatement s = c.prepareStatement("UPDATE VENTAS SET activo = ? WHERE id_venta = ?");

			s.setInt(1, 0);
			s.setInt(2, id_venta);

			s.executeUpdate();
			s.close();

			return 1;

		} catch (Exception e) {
			e.printStackTrace();
		}

		return -1;
	}

	public Set<TVenta> read_all() {

		Set<TVenta> lista = new LinkedHashSet<>();

		try {

			TManager tm = TManager.getInstance();
			Transaction tr = tm.getTransaction();
			Connection c = (Connection) tr.getResource();

			PreparedStatement s = c
					.prepareStatement("SELECT total_factura, id_trabajador, activo, id_venta FROM VENTAS FOR UPDATE");

			ResultSet r = s.executeQuery();

			while (r.next()) {

				TVenta tVenta = new TVenta();

				tVenta.set_total_factura(r.getDouble(1));
				tVenta.set_trabajador(r.getInt(2));
				tVenta.set_activo(r.getInt(3));
				tVenta.set_id(r.getInt(4));

				lista.add(tVenta);
			}

			r.close();
			s.close();

			return lista;

		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	public Set<TVenta> readVentaPorTrabajador(int id_trabajador) {

		Set<TVenta> lista = new LinkedHashSet<>();

		try {

			TManager tm = TManager.getInstance();
			Transaction tr = tm.getTransaction();
			Connection c = (Connection) tr.getResource();

			PreparedStatement s = c.prepareStatement(
					"SELECT total_factura, id_trabajador, activo, id_venta FROM VENTAS WHERE id_trabajador = ? FOR UPDATE");

			s.setInt(1, id_trabajador);

			ResultSet r = s.executeQuery();

			while (r.next()) {

				TVenta tVenta = new TVenta();

				tVenta.set_total_factura(r.getDouble(1));
				tVenta.set_trabajador(r.getInt(2));
				tVenta.set_activo(r.getInt(3));
				tVenta.set_id(r.getInt(4));

				lista.add(tVenta);
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
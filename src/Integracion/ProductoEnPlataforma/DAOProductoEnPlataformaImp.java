package Integracion.ProductoEnPlataforma;

import Integracion.Transaction.TManager;
import Integracion.Transaction.Transaction;
import Negocio.ProductoEnPlataforma.TProductoEnPlataforma;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.LinkedHashSet;
import java.util.Set;

public class DAOProductoEnPlataformaImp implements DAOProductoEnPlataforma {

	@Override
	public int update(TProductoEnPlataforma prodActual) {

		int exito = -1;
		try {

			TManager tm = TManager.getInstance();
			Transaction tr = tm.getTransaction();
			Connection c = (Connection) tr.getResource();

			// c = DriverManager.getConnection(ConfgBD.URL, ConfgBD.USER, ConfgBD.PASSWORD);
			PreparedStatement s = c.prepareStatement(
					"UPDATE PRODUCTOENPLATAFORMA SET precio_venta_actual=?, activo=?, cantidad=? WHERE id_producto_plataforma=?");

			s.setDouble(1, prodActual.get_precio());
			s.setInt(2, prodActual.get_activo());
			s.setInt(3, prodActual.get_cantidad());
			s.setInt(4, prodActual.get_id());

			exito = s.executeUpdate();

			s.close();
		} catch (Exception e) {
			return -1;
		}

		if (exito == 0) {
			return -1;
		}

		return exito;
	}

	@Override
	public TProductoEnPlataforma read(int id_producto_plataforma) {

		TProductoEnPlataforma tpp = null;

		try {
			TManager tm = TManager.getInstance();
			Transaction tr = tm.getTransaction();
			Connection c = (Connection) tr.getResource();

			// c = DriverManager.getConnection(ConfgBD.URL, ConfgBD.USER, ConfgBD.PASSWORD);
			PreparedStatement s = c
					.prepareStatement("SELECT * FROM PRODUCTOENPLATAFORMA WHERE id_producto_plataforma=? FOR UPDATE");
			s.setInt(1, id_producto_plataforma);
			ResultSet r = s.executeQuery();
			r.next();
			if (r != null) {
				tpp = new TProductoEnPlataforma();
				tpp.set_id(r.getInt("id_producto_plataforma"));
				tpp.colocar_datos(r.getInt("activo"), r.getDouble("precio_venta_actual"), r.getInt("cantidad"),
						r.getInt("id_producto"), r.getInt("id_plataforma"));
			}
			r.close();
			s.close();
		} catch (Exception e) {
			return null;
		}

		return tpp;
	}

	@Override
	public int delete(int id_producto_plataforma) {
		int exito = -1;
		try {
			TManager tm = TManager.getInstance();
			Transaction tr = tm.getTransaction();
			Connection c = (Connection) tr.getResource();

			// Connection c = DriverManager.getConnection(ConfgBD.URL, ConfgBD.USER,
			// ConfgBD.PASSWORD);
			PreparedStatement s = c
					.prepareStatement("UPDATE PRODUCTOENPLATAFORMA SET activo = 0 WHERE id_producto_plataforma=?");

			s.setInt(1, id_producto_plataforma);

			exito = s.executeUpdate();

			s.close();

		} catch (Exception e) {
			return -1;
		}

		if (exito == 0) {
			return -1;
		}

		return exito;
	}

	@Override
	public int create(TProductoEnPlataforma nuevoProd) {
		try {

			TManager tm = TManager.getInstance();
			Transaction tr = tm.getTransaction();
			Connection c = (Connection) tr.getResource();

			// c = DriverManager.getConnection(ConfgBD.URL, ConfgBD.USER, ConfgBD.PASSWORD);
			PreparedStatement s = c.prepareStatement(
					"INSERT INTO PRODUCTOENPLATAFORMA(precio_venta_actual,cantidad,activo,id_producto,id_plataforma) VALUES (?,?,?,?,?)",
					Statement.RETURN_GENERATED_KEYS);
			s.setDouble(1, nuevoProd.get_precio());
			s.setInt(2, nuevoProd.get_cantidad());
			s.setInt(3, nuevoProd.get_activo());
			s.setInt(4, nuevoProd.get_id_producto());
			s.setInt(5, nuevoProd.get_id_plataforma());
			s.executeUpdate();
			ResultSet r = s.getGeneratedKeys();
			r.next();
			int id = r.getInt(1);
			s.close();
			r.close();
			return id;
		} catch (Exception e) {
			return -1;
		}
	}

	@Override
	public Set<TProductoEnPlataforma> read_all() {

		Set<TProductoEnPlataforma> tpps = new LinkedHashSet<>();
		TProductoEnPlataforma tpp = null;

		try {
			TManager tm = TManager.getInstance();
			Transaction tr = tm.getTransaction();
			Connection c = (Connection) tr.getResource();

			// Connection c = DriverManager.getConnection(ConfgBD.URL, ConfgBD.USER,
			// ConfgBD.PASSWORD);
			PreparedStatement s = c.prepareStatement("SELECT * FROM PRODUCTOENPLATAFORMA FOR UPDATE");
			ResultSet r = s.executeQuery();
			while (r.next()) {
				tpp = new TProductoEnPlataforma();
				tpp.colocar_datos(r.getInt("activo"), r.getDouble("precio_venta_actual"), r.getInt("cantidad"),
						r.getInt("id_producto"), r.getInt("id_plataforma"));
				tpp.set_id(r.getInt("id_producto_plataforma"));
				tpps.add(tpp);
			}
			s.close();
			r.close();
		} catch (Exception e) {
			return null;
		}
		return tpps;
	}

	@Override
	public Set<TProductoEnPlataforma> read_by_platform(int id_plataforma) {

		Set<TProductoEnPlataforma> tpps = new LinkedHashSet<>();
		TProductoEnPlataforma tpp = null;

		try {
			TManager tm = TManager.getInstance();
			Transaction tr = tm.getTransaction();
			Connection c = (Connection) tr.getResource();

			// Connection c = DriverManager.getConnection(ConfgBD.URL, ConfgBD.USER,
			// ConfgBD.PASSWORD);
			PreparedStatement s = c
					.prepareStatement("SELECT * FROM PRODUCTOENPLATAFORMA WHERE id_plataforma=? FOR UPDATE");
			s.setInt(1, id_plataforma);
			ResultSet r = s.executeQuery();
			while (r.next()) {
				tpp = new TProductoEnPlataforma();
				tpp.colocar_datos(r.getInt("activo"), r.getDouble("precio_venta_actual"), r.getInt("cantidad"),
						r.getInt("id_producto"), r.getInt("id_plataforma"));
				tpp.set_id(r.getInt("id_producto_plataforma"));
				tpps.add(tpp);
			}
			s.close();
			r.close();
		} catch (Exception e) {
			return null;
		}
		return tpps;
	}

	@Override
	public Set<TProductoEnPlataforma> read_by_product(int id_producto) {
		Set<TProductoEnPlataforma> tpps = new LinkedHashSet<>();
		TProductoEnPlataforma tpp = null;

		try {
			TManager tm = TManager.getInstance();
			Transaction tr = tm.getTransaction();
			Connection c = (Connection) tr.getResource();

			// Connection c = DriverManager.getConnection(ConfgBD.URL, ConfgBD.USER,
			// ConfgBD.PASSWORD);
			PreparedStatement s = c
					.prepareStatement("SELECT * FROM PRODUCTOENPLATAFORMA WHERE id_producto=? FOR UPDATE");
			s.setInt(1, id_producto);
			ResultSet r = s.executeQuery();
			while (r.next()) {
				tpp = new TProductoEnPlataforma();
				tpp.colocar_datos(r.getInt("activo"), r.getDouble("precio_venta_actual"), r.getInt("cantidad"),
						r.getInt("id_producto"), r.getInt("id_plataforma"));
				tpp.set_id(r.getInt("id_producto_plataforma"));
				tpps.add(tpp);
			}
			s.close();
			r.close();
		} catch (Exception e) {
			return null;
		}
		return tpps;

	}

}

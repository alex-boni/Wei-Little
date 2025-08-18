
package Integracion.Producto;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.LinkedHashSet;
import java.util.Set;

import Integracion.Transaction.TManager;
import Integracion.Transaction.Transaction;
import Negocio.Producto.TComplemento;
import Negocio.Producto.TProducto;
import Negocio.Producto.TVideojuego;

public class DAOProductoImp implements DAOProducto {

	public int create(TProducto tProducto) {
		try {
			TManager tManager = TManager.getInstance();
			Transaction t = tManager.getTransaction();
			Connection c = (Connection) t.getResource();
			PreparedStatement sC = null;
			PreparedStatement sV = null;
			PreparedStatement s = c.prepareStatement("INSERT INTO PRODUCTO(nombre, marca, activo) VALUES (?,?,?)",
					Statement.RETURN_GENERATED_KEYS);
			s.setString(1, tProducto.get_nombre());
			s.setString(2, tProducto.get_marca());
			s.setInt(3, tProducto.get_activo());
			s.executeUpdate();

			ResultSet r = s.getGeneratedKeys();
			int id = -1;
			while (r.next()) {
				id = r.getInt(1);
			}
			if (tProducto instanceof TComplemento) {
				sC = c.prepareStatement("INSERT INTO COMPLEMENTO(id_producto, peso, activo) VALUES (?,?,?)",
						Statement.RETURN_GENERATED_KEYS);
				sC.setInt(1, id);
				sC.setDouble(2, ((TComplemento) tProducto).get_peso());
				sC.setInt(3, tProducto.get_activo());
				sC.executeUpdate();
				sC.close();

			} else if (tProducto instanceof TVideojuego) {
				sV = c.prepareStatement(
						"INSERT INTO VIDEOJUEGO(id_producto, restriccion_edad, activo) VALUES (?, ?, ?)",
						Statement.RETURN_GENERATED_KEYS);
				sV.setInt(1, id);
				sV.setInt(2, ((TVideojuego) tProducto).get_restriccionEdad());
				sV.setInt(3, tProducto.get_activo());
				sV.executeUpdate();
				sV.close();
			}

			s.close();
			r.close();
			return id;
		} catch (Exception e) {
			e.printStackTrace();

			return -1;
		}
	}

	public TProducto read(int id_producto) {
		TProducto tp = null;
		try {
			TManager tManager = TManager.getInstance();
			Transaction t = tManager.getTransaction();
			Connection c = (Connection) t.getResource();
			PreparedStatement s = c.prepareStatement("SELECT * FROM PRODUCTO WHERE id_producto=? FOR UPDATE");
			s.setInt(1, id_producto);
			ResultSet r = s.executeQuery();
			if (r.next()) {
				String nombre = r.getString("nombre");
				String marca = r.getString("marca");
				int activo = r.getInt("activo");
				PreparedStatement sC = c.prepareStatement("SELECT * FROM COMPLEMENTO WHERE id_producto=? FOR UPDATE");
				sC.setInt(1, id_producto);
				ResultSet rsC = sC.executeQuery();
				if (rsC.next()) {
					double peso = rsC.getDouble("peso");
					tp = new TComplemento(nombre, marca, activo, peso);
					tp.set_id(id_producto);
				} else {
					PreparedStatement sV = c
							.prepareStatement("SELECT * FROM VIDEOJUEGO WHERE id_producto=? FOR UPDATE");
					sV.setInt(1, id_producto);
					ResultSet rsV = sV.executeQuery();
					if (rsV.next()) {
						int restriccion_edad = rsV.getInt("restriccion_edad");
						tp = new TVideojuego(nombre, marca, activo, restriccion_edad);
						tp.set_id(id_producto);
					} else {
						tp = new TProducto(nombre, marca, activo);
						tp.set_id(id_producto);
					}
					sV.close();
				}
				sC.close();
			}
			s.close();
			r.close();
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		return tp;
	}

	public int update(TProducto tProducto) {
		int exito = -1;
		try {
			TManager tManager = TManager.getInstance();
			Transaction t = tManager.getTransaction();
			Connection c = (Connection) t.getResource();
			PreparedStatement s = c
					.prepareStatement("UPDATE PRODUCTO SET nombre=?, marca=?, activo=? WHERE id_producto=?");
			s.setString(1, tProducto.get_nombre());
			s.setString(2, tProducto.get_marca());
			s.setInt(3, tProducto.get_activo());
			s.setInt(4, tProducto.get_id());
			exito = s.executeUpdate();

			if (tProducto instanceof TComplemento) {
				PreparedStatement sC = c
						.prepareStatement("UPDATE COMPLEMENTO SET peso=?, activo=? WHERE id_producto=?");
				sC.setDouble(1, ((TComplemento) tProducto).get_peso());
				sC.setInt(2, tProducto.get_activo());
				sC.setInt(3, tProducto.get_id());
				exito = sC.executeUpdate();
				sC.close();
			} else if (tProducto instanceof TVideojuego) {
				PreparedStatement sV = c
						.prepareStatement("UPDATE VIDEOJUEGO SET restriccion_edad=?, activo=? WHERE id_producto=?");
				sV.setInt(1, ((TVideojuego) tProducto).get_restriccionEdad());
				sV.setInt(2, tProducto.get_activo());
				sV.setInt(3, tProducto.get_id());
				exito = sV.executeUpdate();
				sV.close();
			}

			s.close();
		} catch (Exception e) {
			e.printStackTrace();
			return -1;
		}
		if (exito == 0)
			return -1;
		else
			return exito;
	}

	public int delete(int id_producto) {
		int exito = -1;
		try {
			TManager tManager = TManager.getInstance();
			Transaction t = tManager.getTransaction();
			Connection c = (Connection) t.getResource();

			PreparedStatement s = c.prepareStatement("UPDATE PRODUCTO SET activo = 0 WHERE id_producto=?");
			s.setInt(1, id_producto);
			exito = s.executeUpdate();
			s.close();

			PreparedStatement sC = c.prepareStatement("SELECT * FROM COMPLEMENTO WHERE id_producto=? FOR UPDATE");
			sC.setInt(1, id_producto);
			ResultSet rsC = sC.executeQuery();
			if (rsC.next()) {
				sC = c.prepareStatement("UPDATE COMPLEMENTO SET activo = 0 WHERE id_producto=?");
				sC.setInt(1, id_producto);
				exito = sC.executeUpdate();
			}
			sC.close();
			PreparedStatement sV = c.prepareStatement("SELECT * FROM VIDEOJUEGO WHERE id_producto=? FOR UPDATE");
			sV.setInt(1, id_producto);
			ResultSet rsV = sV.executeQuery();
			if (rsV.next()) {
				sV = c.prepareStatement("UPDATE VIDEOJUEGO SET activo = 0 WHERE id_producto=?");
				sV.setInt(1, id_producto);
				exito = sV.executeUpdate();
			}
			sV.close();

		} catch (Exception e) {
			e.printStackTrace();

			return -1;
		}
		if (exito == 0)
			return -1;
		else
			return exito;
	}

	public Set<TProducto> read_all() {

		Set<TProducto> tpr = new LinkedHashSet<>();
		try {
			TManager tManager = TManager.getInstance();
			Transaction t = tManager.getTransaction();
			Connection c = (Connection) t.getResource();
			PreparedStatement s = c.prepareStatement("SELECT * FROM PRODUCTO FOR UPDATE");
			ResultSet r = s.executeQuery();
			while (r.next()) {
				TProducto tp = null;
				int id_producto = r.getInt("id_producto");
				String nombre = r.getString("nombre");
				String marca = r.getString("marca");
				int activo = r.getInt("activo");

				PreparedStatement sC = c.prepareStatement("SELECT * FROM COMPLEMENTO WHERE id_producto=? FOR UPDATE");
				sC.setInt(1, id_producto);
				ResultSet rsC = sC.executeQuery();
				if (rsC.next()) {
					double peso = rsC.getDouble("peso");
					tp = new TComplemento(nombre, marca, activo, peso);
					tp.set_id(id_producto);
				} else {
					PreparedStatement sV = c
							.prepareStatement("SELECT * FROM VIDEOJUEGO WHERE id_producto=? FOR UPDATE");
					sV.setInt(1, id_producto);
					ResultSet rsV = sV.executeQuery();
					if (rsV.next()) {
						int restriccion_edad = rsV.getInt("restriccion_edad");
						tp = new TVideojuego(nombre, marca, activo, restriccion_edad);
						tp.set_id(id_producto);
					}
					sV.close();
				}
				sC.close();

				if (tp != null)
					tpr.add(tp);
			}
			s.close();
			r.close();
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		return tpr;

	}

	public Set<TProducto> read_by_type(String nombre) {
		Set<TProducto> tpSET = new LinkedHashSet<>();
		try {
			TManager tManager = TManager.getInstance();
			Transaction t = tManager.getTransaction();
			Connection c = (Connection) t.getResource();
			PreparedStatement s = null;
			ResultSet r = null;

			if (nombre.equals("Videojuego")) {
				s = c.prepareStatement(
						"SELECT p.id_producto, p.nombre, p.marca, p.activo, v.restriccion_edad FROM VIDEOJUEGO v INNER JOIN PRODUCTO p ON v.id_producto = p.id_producto FOR UPDATE");
				r = s.executeQuery();
				while (r.next()) {
					TVideojuego tV = new TVideojuego(r.getString("nombre"), r.getString("marca"), r.getInt("activo"),
							r.getInt("restriccion_edad"));
					tV.set_id(r.getInt("id_producto"));
					tpSET.add(tV);
				}
			} else if (nombre.equals("Complemento")) {
				s = c.prepareStatement(
						"SELECT p.id_producto, p.nombre, p.marca, p.activo, c.peso FROM COMPLEMENTO c INNER JOIN PRODUCTO p ON c.id_producto = p.id_producto FOR UPDATE");
				r = s.executeQuery();
				while (r.next()) {
					TComplemento tC = new TComplemento(r.getString("nombre"), r.getString("marca"), r.getInt("activo"),
							r.getDouble("peso"));
					tC.set_id(r.getInt("id_producto"));
					tpSET.add(tC);
				}
			} else {
				return null;
			}
			r.close();
			s.close();

		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		return tpSET;
	}

	public TProducto read_by_name(String nombre) {
		TProducto tp = null;
		try {
			TManager tManager = TManager.getInstance();
			Transaction t = tManager.getTransaction();
			Connection c = (Connection) t.getResource();
			PreparedStatement s = c.prepareStatement("SELECT * FROM PRODUCTO WHERE nombre=? FOR UPDATE");
			s.setString(1, nombre);
			ResultSet r = s.executeQuery();
			if (r.next()) {
				tp = new TProducto(r.getString("nombre"), r.getString("marca"), r.getInt("activo"));
				tp.set_id(r.getInt("id_producto"));
			}
			s.close();
			r.close();
		} catch (Exception e) {
			e.printStackTrace();

			return null;
		}
		return tp;
	}

}

package Integracion.Plataforma;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.LinkedHashSet;
import java.util.Set;

import Integracion.Transaction.TManager;
import Integracion.Transaction.Transaction;
import Negocio.Plataforma.TPlataforma;

public class DAOPlataformaImp implements DAOPlataforma {

	@Override
	public int create(TPlataforma tPlataforma) {
		try {
			TManager tManager = TManager.getInstance();
			Transaction t = tManager.getTransaction();
			Connection c = (Connection) t.getResource();
			PreparedStatement s = c.prepareStatement("INSERT INTO PLATAFORMA(nombre, activo) VALUES (?, ?)",
					Statement.RETURN_GENERATED_KEYS);

			s.setString(1, tPlataforma.get_nombre());
			s.setInt(2, tPlataforma.get_activo());
			s.executeUpdate();
			ResultSet r = s.getGeneratedKeys();
			int id = -1;
			if (r.next())
				id = r.getInt(1);
			r.close();
			s.close();

			return id;
		} catch (Exception e) {
			e.printStackTrace();

			return -1;
		}

	}

	@Override
	public TPlataforma read(int id_plataforma) {
		TPlataforma tp = null;
		try {
			TManager tManager = TManager.getInstance();
			Transaction t = tManager.getTransaction();
			Connection c = (Connection) t.getResource();
			PreparedStatement s = c.prepareStatement("SELECT * FROM PLATAFORMA WHERE id_plataforma=? FOR UPDATE");
			s.setInt(1, id_plataforma);
			ResultSet r = s.executeQuery();

			if (r.next()) {
				tp = new TPlataforma();
				tp.set_id(r.getInt("id_plataforma"));
				tp.set_nombre(r.getString("nombre"));
				tp.set_activo(r.getInt("activo"));
			}
			r.close();
			s.close();

		} catch (Exception e) {
			e.printStackTrace();

			return null;
		}
		return tp;
	}

	@Override
	public int update(TPlataforma tPlataforma) {
		int exito = -1;
		try {
			TManager tManager = TManager.getInstance();
			Transaction t = tManager.getTransaction();
			Connection c = (Connection) t.getResource();
			PreparedStatement s = c.prepareStatement("UPDATE PLATAFORMA SET nombre=?, activo=? WHERE id_plataforma=?");
			s.setString(1, tPlataforma.get_nombre());
			s.setInt(2, tPlataforma.get_activo());
			s.setInt(3, tPlataforma.get_id());
			exito = s.executeUpdate();
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

	@Override
	public int delete(int id_plataforma) {
		int exito = -1;
		try {

			TManager tManager = TManager.getInstance();
			Transaction t = tManager.getTransaction();
			Connection c = (Connection) t.getResource();

			PreparedStatement s = c.prepareStatement("UPDATE PLATAFORMA SET activo = 0 WHERE id_plataforma=?");
			s.setInt(1, id_plataforma);
			// SI no afecta a ninguna fila, devolvemos -
			exito = s.executeUpdate();
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

	@Override
	public Set<TPlataforma> read_all() {
		Set<TPlataforma> tps = new LinkedHashSet<>();
		TPlataforma tp = null;
		try {
			TManager tManager = TManager.getInstance();
			Transaction t = tManager.getTransaction();
			Connection c = (Connection) t.getResource();
			PreparedStatement s = c.prepareStatement("SELECT * FROM PLATAFORMA FOR UPDATE");
			ResultSet r = s.executeQuery();
			while (r.next()) {
				tp = new TPlataforma();
				tp.set_id(r.getInt("id_plataforma"));
				tp.set_nombre(r.getString("nombre"));
				tp.set_activo(r.getInt("activo"));
				tps.add(tp);
			}
			s.close();
			r.close();

		} catch (Exception e) {
			e.printStackTrace();

			return null;
		}
		return tps;
	}

	public TPlataforma read_by_name(String nombre) {
		TPlataforma tp = null;

		try {
			TManager tManager = TManager.getInstance();
			Transaction t = tManager.getTransaction();
			Connection c = (Connection) t.getResource();
			PreparedStatement s = c.prepareStatement("SELECT * FROM PLATAFORMA WHERE nombre=? FOR UPDATE");
			s.setString(1, nombre);
			ResultSet r = s.executeQuery();

			if (r.next()) {
				tp = new TPlataforma();
				tp.set_id(r.getInt("id_plataforma"));
				tp.set_nombre(r.getString("nombre"));
				tp.set_activo(r.getInt("activo"));
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
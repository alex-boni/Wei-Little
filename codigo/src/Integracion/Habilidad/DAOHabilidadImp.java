/**
 * 
 */
package Integracion.Habilidad;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.LinkedHashSet;
import java.util.Set;

import Integracion.Transaction.TManager;
import Integracion.Transaction.Transaction;
import Negocio.Habilidad.THabilidad;

public class DAOHabilidadImp implements DAOHabilidad {

	public int create(THabilidad tHabilidad) {
		try {
			TManager tm = TManager.getInstance();
			Transaction tr = tm.getTransaction();
			Connection c = (Connection) tr.getResource();
			PreparedStatement s = c.prepareStatement("INSERT INTO HABILIDAD(nombre, nivel, activo) VALUES (?, ?, ?)",
					Statement.RETURN_GENERATED_KEYS);
			s.setString(1, tHabilidad.get_nombre());
			s.setInt(2, tHabilidad.get_nivel());
			s.setInt(3, tHabilidad.get_activo());
			s.executeUpdate();

			ResultSet r = s.getGeneratedKeys();
			r.next();
			int id = r.getInt(1);
			r.close();
			s.close();

			return id;

		} catch (Exception e) {
			e.printStackTrace();
			return -1;
		}
	}

	public THabilidad read(int id_habilidad) {
		THabilidad tHabilidad = null;

		try {
			TManager tm = TManager.getInstance();
			Transaction tr = tm.getTransaction();
			Connection c = (Connection) tr.getResource();

			// c = DriverManager.getConnection(ConfgBD.URL, ConfgBD.USER, ConfgBD.PASSWORD);
			PreparedStatement s = c.prepareStatement("SELECT * FROM HABILIDAD WHERE id_habilidad = ? FOR UPDATE");
			s.setInt(1, id_habilidad);
			ResultSet r = s.executeQuery();

			if (r.next()) {
				tHabilidad = new THabilidad();
				tHabilidad.set_id(r.getInt("id_habilidad"));
				tHabilidad.set_nombre(r.getString("nombre"));
				tHabilidad.set_nivel(r.getInt("nivel"));
				tHabilidad.set_activo(r.getInt("activo"));
			}
			r.close();
			s.close();

		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}

		return tHabilidad;
	}

	public int update(THabilidad tHabilidad) {
		int exito = -1;

		try {
			TManager tm = TManager.getInstance();
			Transaction tr = tm.getTransaction();
			Connection c = (Connection) tr.getResource();
			PreparedStatement s = c
					.prepareStatement("UPDATE HABILIDAD SET nombre = ?, nivel = ?, activo = ? WHERE id_habilidad = ?");
			s.setString(1, tHabilidad.get_nombre());
			s.setInt(2, tHabilidad.get_nivel());
			s.setInt(3, tHabilidad.get_activo());
			s.setInt(4, tHabilidad.get_id());
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

	public int delete(int id_habilidad) {
		int exito = -1;

		try {
			TManager tm = TManager.getInstance();
			Transaction tr = tm.getTransaction();
			Connection c = (Connection) tr.getResource();

			PreparedStatement s = c.prepareStatement("UPDATE HABILIDAD SET activo = 0 WHERE id_habilidad = ?");
			s.setInt(1, id_habilidad);
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

	public Set<THabilidad> read_all() {
		Set<THabilidad> tListaHabilidades = new LinkedHashSet<>();
		THabilidad tHabilidad = null;

		try {
			TManager tm = TManager.getInstance();
			Transaction tr = tm.getTransaction();
			Connection c = (Connection) tr.getResource();

			// Bloqueo demasiado pesimista, puede causar bloqueos prolongados de toda la
			// tabla habilidad
			PreparedStatement s = c.prepareStatement("SELECT * FROM HABILIDAD FOR UPDATE");
			ResultSet r = s.executeQuery();
			while (r.next()) {
				tHabilidad = new THabilidad();
				tHabilidad.set_id(r.getInt("id_habilidad"));
				tHabilidad.set_nombre(r.getString("nombre"));
				tHabilidad.set_nivel(r.getInt("nivel"));
				tHabilidad.set_activo(r.getInt("activo"));

				tListaHabilidades.add(tHabilidad);
			}

			r.close();
			s.close();

		} catch (Exception e) {
			e.printStackTrace();

			return null;
		}

		return tListaHabilidades;
	}

	@Override
	public THabilidad read_by_name(String name) {
		THabilidad tHabilidad = null;
		try {
			TManager tm = TManager.getInstance();
			Transaction tr = tm.getTransaction();
			Connection c = (Connection) tr.getResource();
			PreparedStatement s = c.prepareStatement("SELECT * FROM HABILIDAD WHERE nombre = ? FOR UPDATE");
			s.setString(1, name);
			ResultSet r = s.executeQuery();

			if (r.next()) {
				tHabilidad = new THabilidad(r.getString("nombre"), r.getInt("nivel"), r.getInt("activo"));
				tHabilidad.set_id(r.getInt("id_habilidad"));
			}

		} catch (Exception e) {
			e.printStackTrace();

			return null;
		}

		return tHabilidad;
	}
}
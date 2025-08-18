
package Integracion.Trabajador;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.LinkedHashSet;
import java.util.Set;

import Integracion.Transaction.TManager;
import Integracion.Transaction.Transaction;
import Negocio.Trabajador.TVinculacionTrabHab;

public class DAOVinculacionTrabajadorHabilidadImp implements DAOVinculacionTrabajadorHabilidad {

	public TVinculacionTrabHab create(TVinculacionTrabHab tVinculacionTrabHab) {
		try {
			TManager m = TManager.getInstance();
			Transaction tr = m.getTransaction();

			Connection c = (Connection) tr.getResource();
			PreparedStatement s = c.prepareStatement(
					"INSERT INTO TRABAJADORHABILIDAD(id_trabajador,id_habilidad,activo) VALUES (?,?,?)");
			s.setInt(1, tVinculacionTrabHab.get_id_trabajador());
			s.setInt(2, tVinculacionTrabHab.get_id_habilidad());
			s.setInt(3, tVinculacionTrabHab.get_activo());
			s.executeUpdate();
			s.close();
			return tVinculacionTrabHab;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	public TVinculacionTrabHab read(int id_trabajador, int id_habilidad) {
		TVinculacionTrabHab vinculacion = null;
		try {
			TManager m = TManager.getInstance();
			Transaction tr = m.getTransaction();

			Connection c = (Connection) tr.getResource();
			PreparedStatement s = c.prepareStatement(
					"SELECT * FROM TRABAJADORHABILIDAD WHERE id_trabajador = ? AND id_habilidad = ? FOR UPDATE");
			s.setInt(1, id_trabajador);
			s.setInt(2, id_habilidad);
			ResultSet r = s.executeQuery();

			if (r.next()) {
				vinculacion = new TVinculacionTrabHab();
				vinculacion.set_id_trabajador(r.getInt("id_trabajador"));
				vinculacion.set_id_habilidad(r.getInt("id_habilidad"));
				vinculacion.set_activo(r.getInt("activo"));
			}
			s.close();
		} catch (Exception e) {
			e.printStackTrace();

			return null;
		}
		return vinculacion;
	}

	public int delete(int id_trabajador, int id_habilidad) {
		int exito = -1;
		try {
			TManager m = TManager.getInstance();
			Transaction tr = m.getTransaction();

			Connection c = (Connection) tr.getResource();
			PreparedStatement s = c.prepareStatement(
					"UPDATE TRABAJADORHABILIDAD SET activo = 0 WHERE id_trabajador = ? AND id_habilidad = ?");
			s.setInt(1, id_trabajador);
			s.setInt(2, id_habilidad);
			exito = s.executeUpdate();
			s.close();

		} catch (Exception e) {
			e.printStackTrace();
			return -1;
		}
		return exito;
	}

	public int update(TVinculacionTrabHab tVinculacionTrabHab) {
		int filas = 0;
		try {

			TManager m = TManager.getInstance();
			Transaction tr = m.getTransaction();

			Connection c = (Connection) tr.getResource();
			PreparedStatement s = c.prepareStatement(
					"UPDATE TRABAJADORHABILIDAD SET activo = 1 WHERE id_trabajador = ? AND id_habilidad = ?");
			s.setInt(1, tVinculacionTrabHab.get_id_trabajador());
			s.setInt(2, tVinculacionTrabHab.get_id_habilidad());
			filas = s.executeUpdate();
			s.close();

			if (filas > 0) {

				// c.close();
				return 1;
			} else {

				// c.close();
				return 0;
			}
		} catch (Exception e) {
			e.printStackTrace();
			return -1;
		}
	}

	public Set<TVinculacionTrabHab> read_all() {
		Set<TVinculacionTrabHab> vinculaciones = new LinkedHashSet<>();
		try {
			TManager m = TManager.getInstance();
			Transaction tr = m.getTransaction();

			Connection c = (Connection) tr.getResource();
			PreparedStatement s = c.prepareStatement("SELECT * FROM TRABAJADORHABILIDAD FOR UPDATE");
			ResultSet r = s.executeQuery();
			while (r.next()) {
				TVinculacionTrabHab vinculacion = new TVinculacionTrabHab();
				int id_habilidad = r.getInt("id_habilidad");
				int id_trabajador = r.getInt("id_trabajador");
				int activo = r.getInt("activo");

				vinculacion.set_activo(activo);
				vinculacion.set_id_habilidad(id_habilidad);
				vinculacion.set_id_trabajador(id_trabajador);

				vinculaciones.add(vinculacion);
			}
			s.close();
			r.close();
		} catch (Exception e) {
			e.printStackTrace();

			return null;
		}
		return vinculaciones;
	}


	public Set<TVinculacionTrabHab> read_all_by_habilidad(int id_habilidad) {
		Set<TVinculacionTrabHab> vinculacionesporhabilidad = new LinkedHashSet<>();
		try {
			TManager m = TManager.getInstance();
			Transaction tr = m.getTransaction();

			Connection c = (Connection) tr.getResource();
			PreparedStatement s = c
					.prepareStatement("SELECT * FROM TRABAJADORHABILIDAD WHERE id_habilidad=? FOR UPDATE");
			s.setInt(1, id_habilidad);
			ResultSet r = s.executeQuery();
			while (r.next()) {
				TVinculacionTrabHab vinculacion = new TVinculacionTrabHab();
				int id_trabajador = r.getInt("id_trabajador");
				int activo = r.getInt("activo");

				vinculacion.set_activo(activo);
				vinculacion.set_id_habilidad(id_habilidad);
				vinculacion.set_id_trabajador(id_trabajador);

				vinculacionesporhabilidad.add(vinculacion);
			}
			s.close();
			r.close();
		} catch (Exception e) {
			e.printStackTrace();

			return null;
		}
		return vinculacionesporhabilidad;
	}

	public Set<TVinculacionTrabHab> read_all_by_trabajador(int id_trabajador) {
		Set<TVinculacionTrabHab> vinculacionesporhabilidad = new LinkedHashSet<>();
		try {
			TManager m = TManager.getInstance();
			Transaction tr = m.getTransaction();

			Connection c = (Connection) tr.getResource();
			PreparedStatement s = c
					.prepareStatement("SELECT * FROM TRABAJADORHABILIDAD WHERE id_trabajador=? FOR UPDATE");
			s.setInt(1, id_trabajador);
			ResultSet r = s.executeQuery();
			while (r.next()) {
				TVinculacionTrabHab vinculacion = new TVinculacionTrabHab();
				int id_habilidad = r.getInt("id_habilidad");
				int activo = r.getInt("activo");

				vinculacion.set_activo(activo);
				vinculacion.set_id_habilidad(id_habilidad);
				vinculacion.set_id_trabajador(id_trabajador);

				vinculacionesporhabilidad.add(vinculacion);
			}
			s.close();
			r.close();
		} catch (Exception e) {
			e.printStackTrace();

			return null;
		}
		return vinculacionesporhabilidad;
	}
}
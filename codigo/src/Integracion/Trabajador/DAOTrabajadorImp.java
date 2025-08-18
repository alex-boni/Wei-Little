package Integracion.Trabajador;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.HashSet;
import java.util.Set;

import Integracion.Transaction.TManager;
import Integracion.Transaction.Transaction;
import Negocio.Trabajador.TSupervisor;
import Negocio.Trabajador.TTrabajador;
import Negocio.Trabajador.TVendedor;

public class DAOTrabajadorImp implements DAOTrabajador {

	public int create(TTrabajador tTrabajador) {
		try {
			TManager m = TManager.getInstance();
			Transaction tr = m.getTransaction();

			Connection c = (Connection) tr.getResource();

			PreparedStatement s = c.prepareStatement("INSERT INTO TRABAJADOR(dni,nombre,activo) VALUES (?,?,?)",
					Statement.RETURN_GENERATED_KEYS);
			s.setString(1, tTrabajador.get_dni().toUpperCase());
			s.setString(2, tTrabajador.get_nombre().toUpperCase());
			s.setInt(3, tTrabajador.get_activo());
			s.executeUpdate();
			ResultSet r = s.getGeneratedKeys();
			r.next();
			int id = r.getInt(1);

			if (tTrabajador instanceof TSupervisor) {
				TSupervisor supervisor = (TSupervisor) tTrabajador;
				PreparedStatement s1 = c.prepareStatement(
						"INSERT INTO SUPERVISOR(id_trabajador, experiencia, activo) VALUES (?, ?, ?)");
				s1.setInt(1, id);
				s1.setString(2, supervisor.get_experiencia().toUpperCase());
				s1.setInt(3, supervisor.get_activo());
				s1.executeUpdate();
				s1.close();
			} else if (tTrabajador instanceof TVendedor) {
				TVendedor vendedor = (TVendedor) tTrabajador;
				PreparedStatement s2 = c
						.prepareStatement("INSERT INTO VENDEDOR(id_trabajador, idioma, activo) VALUES (?, ?, ?)");
				s2.setInt(1, id);
				s2.setString(2, vendedor.get_idioma().toUpperCase());
				s2.setInt(3, vendedor.get_activo());
				s2.executeUpdate();
				s2.close();
			}
			s.close();
			r.close();

			return id;
		} catch (Exception e) {
			e.printStackTrace();
			return -1;
		}
	}

	public TTrabajador read(int id_trabajador) {
		TTrabajador trabajador = null;
		try {
			TManager m = TManager.getInstance();
			Transaction tr = m.getTransaction();

			Connection c = (Connection) tr.getResource();
			PreparedStatement s = c.prepareStatement("SELECT * FROM TRABAJADOR WHERE id_trabajador = ? FOR UPDATE");
			s.setInt(1, id_trabajador);
			ResultSet rs = s.executeQuery();

			if (rs.next()) {
				String dni = rs.getString("dni");
				String nombre = rs.getString("nombre");
				int activo = rs.getInt("activo");

				PreparedStatement s1 = c
						.prepareStatement("SELECT * FROM SUPERVISOR WHERE id_trabajador = ? FOR UPDATE");
				s1.setInt(1, id_trabajador);
				ResultSet rs1 = s1.executeQuery();
				if (rs1.next()) {
					trabajador = new TSupervisor();
					((TSupervisor) trabajador).set_experiencia(rs1.getString("experiencia").toUpperCase());
				} else {
					PreparedStatement s2 = c
							.prepareStatement("SELECT * FROM VENDEDOR WHERE id_trabajador = ? FOR UPDATE");
					s2.setInt(1, id_trabajador);
					ResultSet rs2 = s2.executeQuery();
					if (rs2.next()) {
						trabajador = new TVendedor();
						((TVendedor) trabajador).set_idioma(rs2.getString("idioma").toUpperCase());
					}
					s2.close();
				}
				if (trabajador != null) {
					trabajador.set_dni(dni.toUpperCase());
					trabajador.set_nombre(nombre.toUpperCase());
					trabajador.set_activo(activo);
					trabajador.set_id(id_trabajador);
				}

				s1.close();

			}
			rs.close();
			s.close();
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		return trabajador;
	}

	public int update(TTrabajador tTrabajador) {
		int filas = 0;
		try {
			TManager m = TManager.getInstance();
			Transaction tr = m.getTransaction();

			Connection c = (Connection) tr.getResource();
			PreparedStatement s = c
					.prepareStatement("UPDATE TRABAJADOR SET dni = ?, nombre = ?, activo = ? WHERE id_trabajador = ?");
			s.setString(1, tTrabajador.get_dni().toUpperCase());
			s.setString(2, tTrabajador.get_nombre().toUpperCase());
			s.setInt(3, tTrabajador.get_activo());
			s.setInt(4, tTrabajador.get_id());
			filas = s.executeUpdate();

			if (filas > 0) {
				if (tTrabajador instanceof TSupervisor) {
					PreparedStatement s1 = c.prepareStatement(
							"INSERT INTO SUPERVISOR(id_trabajador, experiencia, activo) VALUES(?,?,?) "
									+ "ON DUPLICATE KEY UPDATE experiencia = VALUES(experiencia), activo = VALUES(activo)");
					s1.setString(2, ((TSupervisor) tTrabajador).get_experiencia().toUpperCase());
					s1.setInt(3, tTrabajador.get_activo());
					s1.setInt(1, tTrabajador.get_id());
					s1.executeUpdate();
					s1.close();
				} else if (tTrabajador instanceof TVendedor) {
					PreparedStatement s2 = c
							.prepareStatement("INSERT INTO VENDEDOR(id_trabajador, idioma, activo) VALUES(?,?,?) "
									+ "ON DUPLICATE KEY UPDATE idioma = VALUES(idioma), activo = VALUES(activo)");
					s2.setString(2, ((TVendedor) tTrabajador).get_idioma().toUpperCase());
					s2.setInt(3, tTrabajador.get_activo());
					s2.setInt(1, tTrabajador.get_id());
					s2.executeUpdate();
					s2.close();
				}
				PreparedStatement s3 = c
						.prepareStatement("UPDATE TRABAJADORHABILIDAD SET activo = ? WHERE id_trabajador = ?");
				s3.setInt(1, tTrabajador.get_activo());
				s3.setInt(2, tTrabajador.get_id());
				s3.executeUpdate();
				s3.close();
			}
			s.close();

			return filas > 0 ? 1 : 0;
		} catch (Exception e) {
			e.printStackTrace();
			return -1;
		}
	}

	public int delete(int id_trabajador) {
		int filas = 0;
		try {
			TManager m = TManager.getInstance();
			Transaction tr = m.getTransaction();

			Connection c = (Connection) tr.getResource();
			PreparedStatement s = c.prepareStatement("UPDATE TRABAJADOR SET activo = 0 WHERE id_trabajador = ?");
			s.setInt(1, id_trabajador);
			filas = s.executeUpdate();

			if (filas > 0) {
				PreparedStatement s1 = c.prepareStatement("UPDATE SUPERVISOR SET activo = 0 WHERE id_trabajador = ?");
				s1.setInt(1, id_trabajador);
				s1.executeUpdate();
				PreparedStatement s2 = c.prepareStatement("UPDATE VENDEDOR SET activo = 0 WHERE id_trabajador = ?");
				s2.setInt(1, id_trabajador);
				s2.executeUpdate();
				PreparedStatement s3 = c
						.prepareStatement("UPDATE TRABAJADORHABILIDAD SET activo = 0 WHERE id_trabajador = ?");
				s3.setInt(1, id_trabajador);
				s3.executeUpdate();
				s.close();
				return 1;
			} else {
				s.close();

				return 0;
			}
		} catch (Exception e) {
			e.printStackTrace();
			return -1;
		}
	}

	public Set<TTrabajador> read_all() {
		Set<TTrabajador> trabajadores = null;
		try {
			TManager m = TManager.getInstance();
			Transaction tr = m.getTransaction();

			Connection c = (Connection) tr.getResource();
			PreparedStatement s = c.prepareStatement("SELECT * FROM TRABAJADOR FOR UPDATE");
			ResultSet rs = s.executeQuery();
			trabajadores = new HashSet<>();
			while (rs.next()) {
				int id = rs.getInt("id_trabajador");
				String dni = rs.getString("dni");
				String nombre = rs.getString("nombre");
				int activo = rs.getInt("activo");

				TTrabajador trabajador = new TTrabajador();

				// PreparedStatement s1 = c.prepareStatement("SELECT * FROM SUPERVISOR WHERE
				// id_trabajador = ?");
				// s1.setInt(1, id);
				// ResultSet rs1 = s1.executeQuery();
				// if (rs1.next()) {
				// trabajador = new TSupervisor();
				// ((TSupervisor)
				// trabajador).set_experiencia(rs1.getString("experiencia").toUpperCase());
				// } else {
				// PreparedStatement s2 = c.prepareStatement("SELECT * FROM VENDEDOR WHERE
				// id_trabajador = ?");
				// s2.setInt(1, id);
				// ResultSet rs2 = s2.executeQuery();
				// if (rs2.next()) {
				// trabajador = new TVendedor();
				// ((TVendedor) trabajador).set_idioma(rs2.getString("idioma").toUpperCase());
				// }
				// s2.close();
				// }

				trabajador.set_dni(dni.toUpperCase());
				trabajador.set_nombre(nombre.toUpperCase());
				trabajador.set_activo(activo);
				trabajador.set_id(id);
				trabajadores.add(trabajador);
				// s1.close();

			}
			rs.close();
			s.close();

		} catch (Exception e) {
			e.printStackTrace();
			trabajadores = null;
		}
		return trabajadores;
	}

	public TTrabajador read_by_dni(String dni) {
		TTrabajador trabajador = null;
		try {
			TManager m = TManager.getInstance();
			Transaction tr = m.getTransaction();

			Connection c = (Connection) tr.getResource();
			PreparedStatement s = c.prepareStatement("SELECT * FROM TRABAJADOR WHERE dni = ? FOR UPDATE");
			s.setString(1, dni);
			ResultSet rs = s.executeQuery();

			if (rs.next()) {
				int id = rs.getInt("id_trabajador");
				String nombre = rs.getString("nombre");
				int activo = rs.getInt("activo");

				PreparedStatement s1 = c
						.prepareStatement("SELECT * FROM SUPERVISOR WHERE id_trabajador = ? FOR UPDATE");
				s1.setInt(1, id);
				ResultSet rs1 = s1.executeQuery();
				if (rs1.next()) {
					trabajador = new TSupervisor();
					((TSupervisor) trabajador).set_experiencia(rs1.getString("experiencia").toUpperCase());
				} else {
					PreparedStatement s2 = c
							.prepareStatement("SELECT * FROM VENDEDOR WHERE id_trabajador = ? FOR UPDATE");
					s2.setInt(1, id);
					ResultSet rs2 = s2.executeQuery();
					if (rs2.next()) {
						trabajador = new TVendedor();
						((TVendedor) trabajador).set_idioma(rs2.getString("idioma").toUpperCase());
					}
					s2.close();
				}

				trabajador.set_dni(dni.toUpperCase());
				trabajador.set_nombre(nombre.toUpperCase());
				trabajador.set_activo(activo);
				trabajador.set_id(id);
				s1.close();

			}
			rs.close();
			s.close();
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		return trabajador;
	}


	public Set<TTrabajador> read_by_tipo(String tipo) {
		Set<TTrabajador> trabajadores = null;
		ResultSet rs = null;
		PreparedStatement s = null;
		try {

			TManager m = TManager.getInstance();
			Transaction tr = m.getTransaction();

			Connection c = (Connection) tr.getResource();
			trabajadores = new HashSet<>();
			if ("supervisor".equalsIgnoreCase(tipo)) {
				s = c.prepareStatement("SELECT t.id_trabajador, t.dni, t.nombre, t.activo, s.experiencia "
						+ "FROM TRABAJADOR t INNER JOIN SUPERVISOR s ON t.id_trabajador = s.id_trabajador and t.activo=s.activo FOR UPDATE");
				rs = s.executeQuery();
				while (rs.next()) {
					int id = rs.getInt("id_trabajador");
					String dni = rs.getString("dni");
					String nombre = rs.getString("nombre");
					int activo = rs.getInt("activo");
					String experiencia = rs.getString("experiencia");

					TSupervisor supervisor = new TSupervisor();
					supervisor.set_id(id);
					supervisor.set_dni(dni.toUpperCase());
					supervisor.set_nombre(nombre.toUpperCase());
					supervisor.set_activo(activo);
					supervisor.set_experiencia(experiencia.toUpperCase());
					trabajadores.add(supervisor);
				}
			} else if ("vendedor".equalsIgnoreCase(tipo)) {
				s = c.prepareStatement("SELECT t.id_trabajador, t.dni, t.nombre, t.activo, v.idioma "
						+ "FROM TRABAJADOR t INNER JOIN VENDEDOR v ON t.id_trabajador = v.id_trabajador and t.activo=v.activo FOR UPDATE");
				rs = s.executeQuery();
				while (rs.next()) {
					int id = rs.getInt("id_trabajador");
					String dni = rs.getString("dni");
					String nombre = rs.getString("nombre");
					int activo = rs.getInt("activo");
					String idioma = rs.getString("idioma");

					TVendedor vendedor = new TVendedor();
					vendedor.set_id(id);
					vendedor.set_dni(dni.toUpperCase());
					vendedor.set_nombre(nombre.toUpperCase());
					vendedor.set_activo(activo);
					vendedor.set_idioma(idioma.toUpperCase());
					trabajadores.add(vendedor);
				}
			}
			rs.close();
			s.close();
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		return trabajadores;
	}
}

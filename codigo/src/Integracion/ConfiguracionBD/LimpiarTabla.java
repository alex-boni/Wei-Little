package Integracion.ConfiguracionBD;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class LimpiarTabla {
	public static void limpiarTabla(String nombreTabla) {

		try {
			Connection c = DriverManager.getConnection(ConfgBD.URL, ConfgBD.USER, ConfgBD.PASSWORD);
			PreparedStatement ps;
			// ps = conexion.prepareStatement("TRUNCATE TABLE " + nombreTabla);
			ps = c.prepareStatement("DELETE FROM " + nombreTabla);
			ps.execute();

			ps = c.prepareStatement("ALTER TABLE " + nombreTabla + " AUTO_INCREMENT = 1");
			ps.execute();

			ps.close();
			c.close();
		} catch (SQLException ex) {
			ex.printStackTrace();
		}
	}
}

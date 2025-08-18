package Integracion.FactoriaQuery;

import Integracion.Transaction.TManager;
import Integracion.Transaction.Transaction;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class CalcularCantidadProductoEnPlataforma implements Query {

	@Override
	public Object execute(Object param) {
		Integer edad = (Integer) param;
		return CalcularCantidadProductoEnPlataformaEdad(edad);
	}

	private int CalcularCantidadProductoEnPlataformaEdad(int edad) {
		int cantidad = 0;

		try {
			TManager tmanager = TManager.getInstance();
			Transaction t = tmanager.getTransaction();
			Connection c = (Connection) t.getResource();
			String query = "SELECT SUM(pep.cantidad) " + "FROM PRODUCTOENPLATAFORMA pep "
					+ "JOIN PRODUCTO p ON pep.id_producto = p.id_producto "
					+ "JOIN VIDEOJUEGO vj ON p.id_producto = vj.id_producto "
					+ "JOIN PLATAFORMA pl ON pep.id_plataforma = pl.id_plataforma " + "WHERE vj.restriccion_edad >= ? "
					+ "AND pep.activo = 1 " + "AND p.activo = 1 " + "AND pl.activo = 1 "
					+ "AND vj.activo = 1 FOR UPDATE";

			PreparedStatement statement = c.prepareStatement(query);
			statement.setInt(1, edad);

			ResultSet rs = statement.executeQuery();

			if (rs.next()) {
				cantidad = rs.getInt(1);
			}

			rs.close();
			statement.close();

		} catch (Exception e) {
			e.printStackTrace();
		}

		return cantidad;
	}
}

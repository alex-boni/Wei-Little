package Integracion.FactoriaQuery;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import Integracion.Transaction.TManager;
import Integracion.Transaction.Transaction;

public class CalcularTotalTrabajadorQueMasVende implements Query {

	@Override
	public Integer execute(Object param) {
		Integer id = (Integer) param;

		int total = -1;

		try {

			TManager tmanager = TManager.getInstance();
			Transaction t = tmanager.getTransaction();
			Connection c = (Connection) t.getResource();

			String query = "SELECT SUM(lv.precio_venta) AS total_cantidad_vendida" + " FROM VENTAS v"
					+ " JOIN LINEAVENTA lv ON v.id_venta = lv.id_venta" + " WHERE v.id_trabajador = ("
					+ "    SELECT id_trabajador" + " 	 FROM LINEAVENTA lv_in"
					+ "    JOIN VENTAS v_in ON lv_in.id_venta = v_in.id_venta" + "    WHERE lv_in.id_producto_final = ?"
					+ " 	 AND lv_in.activo = 1" + "		 GROUP BY v_in.id_trabajador"
					+ "    ORDER BY SUM(lv_in.cantidadVenta) DESC" + "    LIMIT 1"
					+ ") AND lv.id_producto_final = ? FOR UPDATE";

			PreparedStatement statement = c.prepareStatement(query);
			statement.setInt(1, id);
			statement.setInt(2, id);

			ResultSet rs = statement.executeQuery();

			if (rs.next()) {
				total = rs.getInt(1);
			} else
				total = -1;

			if (total == 0)
				total = -1;

			rs.close();
			statement.close();

		} catch (Exception e) {
			e.printStackTrace();
		}

		return total;
	}
}

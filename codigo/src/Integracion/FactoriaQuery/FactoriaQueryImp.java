package Integracion.FactoriaQuery;

public class FactoriaQueryImp extends FactoriaQuery {

	@Override
	public Query getNewQuery(String nombre) {

		switch (nombre) {

		case "CalcularCantidadProductoEnPlataforma" -> {
			return new CalcularCantidadProductoEnPlataforma();
		}

		case "CalcularTotalTrabajadorQueMasVende" -> {
			return new CalcularTotalTrabajadorQueMasVende();
		}
		}

		return null;
	}
}

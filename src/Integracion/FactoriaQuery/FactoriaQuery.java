package Integracion.FactoriaQuery;

public abstract class FactoriaQuery {

	private static FactoriaQuery instance;

	public static synchronized FactoriaQuery getInstance() {

		if (instance == null) {
			instance = new FactoriaQueryImp();
		}
		return instance;
	}

	public abstract Query getNewQuery(String nombre);

}

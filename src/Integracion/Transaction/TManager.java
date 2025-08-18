package Integracion.Transaction;

public abstract class TManager {

	private static TManager instance;

	public static synchronized TManager getInstance() {

		if (instance == null) {
			instance = new TManagerImp();
		}
		return instance;
	}

	public abstract Transaction createTransaction();

	public abstract Transaction getTransaction();

	public abstract void deleteTransaction();

}

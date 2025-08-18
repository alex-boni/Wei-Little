package Integracion.Transaction;

import java.util.concurrent.ConcurrentHashMap;

public class TManagerImp extends TManager {

	private ConcurrentHashMap<Thread, Transaction> transactions;

	public TManagerImp() {
		this.transactions = new ConcurrentHashMap<>();
	}

	@Override
	public Transaction createTransaction() {

		if (this.transactions.get(Thread.currentThread()) == null) {
			Transaction tr = TransactionFactory.getInstance().getTransaction();
			this.transactions.put(Thread.currentThread(), tr);
			return tr;
		}
		return null;

	}

	@Override
	public Transaction getTransaction() {
		return this.transactions.get(Thread.currentThread());
	}

	@Override
	public void deleteTransaction() {

		if (this.transactions.get(Thread.currentThread()) != null) {
			this.transactions.remove(Thread.currentThread());
		}

	}

}
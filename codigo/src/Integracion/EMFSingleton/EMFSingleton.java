
package Integracion.EMFSingleton;

import javax.persistence.EntityManagerFactory;

public abstract class EMFSingleton {

	private static EMFSingleton instancia;

	public static EMFSingleton getInstancia() {

		if (instancia == null)
			instancia = new EMFSingletonImp();

		return instancia;
	}

	public abstract EntityManagerFactory getEntityManagerFactory();
}
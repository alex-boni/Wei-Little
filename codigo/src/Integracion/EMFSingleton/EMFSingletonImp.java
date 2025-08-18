
package Integracion.EMFSingleton;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class EMFSingletonImp extends EMFSingleton {

	private EntityManagerFactory EMF;

	public EMFSingletonImp() {
		EMF = Persistence.createEntityManagerFactory("arcade");
	}

	@Override
	public EntityManagerFactory getEntityManagerFactory() {
		return EMF;
	}
}
package se.ningor.movies.business.base;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class PersistenceHelper {
	
	private EntityManagerFactory emf;
	
	public static final PersistenceHelper INSTANCE = new PersistenceHelper();
	
	private PersistenceHelper() {
		super();
		emf = Persistence.createEntityManagerFactory("test");
	}
	
	public EntityManager getEntityManager() {
		return emf.createEntityManager();
	}

}

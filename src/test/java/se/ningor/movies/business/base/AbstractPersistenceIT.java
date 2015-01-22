package se.ningor.movies.business.base;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

import org.junit.After;
import org.junit.BeforeClass;

public class AbstractPersistenceIT {

	protected EntityManager em;
	protected EntityTransaction et;
	
	public void setUp() {
		em = PersistenceHelper.INSTANCE.getEntityManager();
		et = em.getTransaction();
	}
	
	@After
    public void rollbackTransaction() {   
//        if (et.isActive()) {
//            et.rollback();
//        }

        if (em.isOpen()) {
            em.close();
        }
    }
}

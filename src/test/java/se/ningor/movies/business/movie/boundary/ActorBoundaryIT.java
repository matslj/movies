package se.ningor.movies.business.movie.boundary;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;

import se.ningor.movies.business.base.AbstractPersistenceIT;
import se.ningor.movies.business.movie.entity.Actor;

/**
 * Integration test of FilmBoundary.
 * <p>
 * Execute integration tests with
 * 
 * <pre>
 * <code>mvn failsafe:integration-test</code>
 * </pre>
 * <p>
 * Also see Adam Biens blog <a href=
 * "http://www.adam-bien.com/roller/abien/entry/a_natural_separation_of_junit"
 * >http
 * ://www.adam-bien.com/roller/abien/entry/a_natural_separation_of_junit</a>
 * 
 * @author mats
 * 
 */
public class ActorBoundaryIT extends AbstractPersistenceIT {

	private ActorBoundary ab;

	@Before
	public void initDependencyInjection() {
		super.setUp();
		this.ab = new ActorBoundary();
		this.ab.em = em;
	}
	
	@Test
	public void testFindActorNoHit() {
		et.begin();
		String actorName = "Arnold Schwartzenegger";
		Actor a = ab.findActor(actorName);
		et.commit();
		Assert.assertNull("Actor '" + actorName + "' not found", a);
	}

	@Test
	public void testFindActor() {
		String actorName = "Jennifer Connelly";
		et.begin();
		Actor a = new Actor(actorName);
		em.persist(a);
		et.commit();
		et.begin();
		Actor af = ab.findActor(actorName);
		et.commit();
		Assert.assertNotNull(af);
		Assert.assertEquals("Hej", actorName, af.getName());
	}

}

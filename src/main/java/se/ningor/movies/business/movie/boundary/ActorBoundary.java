package se.ningor.movies.business.movie.boundary;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;

import se.ningor.movies.business.movie.entity.Actor;

@Stateless
public class ActorBoundary {
	
	@Inject
	EntityManager em;

	public Actor findActor(String name) {
		try {
			return em.createNamedQuery("actor.findActorByName", Actor.class)
	        .setParameter("name", name)
	        .getSingleResult();
		} catch(NoResultException nre) {
			return null;
		}
	}
}

package se.ningor.movies.business.movie.boundary;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;

import se.ningor.movies.business.movie.entity.Genre;

@Stateless
public class GenreBoundary {
	
	@Inject
	EntityManager em;

	public Genre findGenre(String name) {
		try {
			return em.createNamedQuery("genre.findGenreByName", Genre.class)
	        .setParameter("name", name)
	        .getSingleResult();
		} catch(NoResultException nre) {
			return null;
		}
	}
}

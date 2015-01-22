package se.ningor.movies.business.movie.boundary;

import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Set;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

import org.slf4j.Logger;

import se.ningor.movies.business.movie.entity.Actor;
import se.ningor.movies.business.movie.entity.Genre;
import se.ningor.movies.business.movie.entity.Movie;
import se.ningor.movies.business.movie.entity.MovieImdb;

import com.google.gson.Gson;

@Stateless
public class FilmBoundary {
	
	@Inject
	EntityManager em;
	
	@Inject
	ActorBoundary ab;
	
	@Inject
	GenreBoundary gb;
	
	@Inject
	Logger logger;
	
	
	public Movie sparaFilm(Movie m) {
		m.setEntryCreatedDate(new Date());
		return em.merge(m);
	}
	
	public Movie sparaFilm(String imdbJSON) {
		Gson gson = new Gson();
		MovieImdb mi = gson.fromJson(imdbJSON, MovieImdb.class);
		
		ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
		Validator validator = factory.getValidator();
		Set<ConstraintViolation<MovieImdb>> constraintViolations = validator.validate(mi);
		if (constraintViolations != null && constraintViolations.size() > 0) {
			throw new IllegalStateException("Validation errors on MovieImdb instance exist.");
		}
		
		Movie m = findMovie(mi.getTitle());
		if (m == null) { // no doublettes wanted in db, so new movie's only created if title does not already exist
			m = new Movie(mi);
		
			if (mi.getGenre() != null) {
				List<String> genreList = Arrays.asList(mi.getGenre().split("\\s*,\\s*"));
				if (genreList != null && !genreList.isEmpty()) {
					for (String s : genreList) {
						Genre g = gb.findGenre(s);
						if (g == null) {
							g = new Genre(s);
						}
						m.addGenre(g);
						// System.out.println("Genres---- M: " + m.getGenres().size() + " g: " + g.getMovies().size());
					}
				}
			}
			
			if (mi.getActors() != null) {
				List<String> actorsList = Arrays.asList(mi.getActors().split("\\s*,\\s*"));
				if (actorsList != null && !actorsList.isEmpty()) {
					for (String s : actorsList) {
						Actor a = ab.findActor(s);
						if (a == null) {
							a = new Actor(s);
						}
						m.addActor(a);
						// System.out.println("Actors---- M: " + m.getActors().size() + " a: " + a.getMovies().size());
					}
				}
			}
		}
		
		return em.merge(m);
	}
	
	public Movie findMovie(String title) {
		try {
			return em.createNamedQuery("movie.findMovieByTitle", Movie.class)
	        .setParameter("title", title)
	        .getSingleResult();
		} catch(NoResultException nre) {
			return null;
		}
	}
	
	public Movie findMovie(Long id) {
		return em.find(Movie.class, id);
	}
	
	public List<Movie> listaFilmer() {
		return em.createNamedQuery("movie.listAllMovies", Movie.class).getResultList();
	}

}

package se.ningor.movies.business.movie.boundary;

import java.util.List;

import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;

import se.ningor.movies.business.base.AbstractPersistenceIT;
import se.ningor.movies.business.movie.entity.Movie;
import se.ningor.movies.business.movie.entity.Movie_;

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
public class FilmBoundaryIT extends AbstractPersistenceIT {

	private FilmBoundary fb;
	private ActorBoundary ab;
	private GenreBoundary gb;

	private static final String MOVIE_JSON = "{\"Title\":\"Hackers\",\"Year\":\"1995\",\"Rated\":\"PG-13\",\"Released\":\"15 Sep 1995\",\"Runtime\":\"107 min\",\"Genre\":\"Comedy, Crime, Drama\",\"Director\":\"Iain Softley\",\"Writer\":\"Rafael Moreu\",\"Actors\":\"Jonny Lee Miller, Angelina Jolie, Jesse Bradford, Matthew Lillard\",\"Plot\":\"A young boy is arrested by the U.S. Secret Service for writing a computer virus and is banned from using a computer until his 18th birthday. Years later, he and his new-found friends ...\",\"Language\":\"English, Italian, Japanese, Russian\",\"Country\":\"USA\",\"Awards\":\"N/A\",\"Poster\":\"http://ia.media-imdb.com/images/M/MV5BODg0NjQ5ODQ3OF5BMl5BanBnXkFtZTcwNjU4MjkzNA@@._V1_SX300.jpg\",\"Metascore\":\"46\",\"imdbRating\":\"6.2\",\"imdbVotes\":\"48,134\",\"imdbID\":\"tt0113243\",\"Type\":\"movie\",\"Response\":\"True\"}";

	@Before
	public void initDependencyInjection() {
		super.setUp();
		this.fb = new FilmBoundary();
		this.fb.em = em;
		this.fb.ab = new ActorBoundary();
		this.fb.ab.em = em;
		this.fb.gb = new GenreBoundary();
		this.fb.gb.em = em;
	}

	@Test
	public void testSparaFilmString() {
		et.begin();
		Movie m = fb.sparaFilm(MOVIE_JSON);
		et.commit();
		Assert.assertEquals("Hackers", m.getTitle());
		Assert.assertEquals("1995", m.getYear());
		Assert.assertEquals("Fri Sep 15 00:00:00 CEST 1995", m.getReleased().toString());
		Assert.assertEquals(Long.valueOf(48134l), m.getImdbVotes());
		Assert.assertTrue(m.getGenres().size() == 3);
		Assert.assertTrue(m.getActors().size() == 4);
	}
	
	@Test
	public void testNoDoublettes() {
		et.begin();
		fb.sparaFilm(MOVIE_JSON);
		et.commit();
		et.begin();
		fb.sparaFilm(MOVIE_JSON);
		et.commit();
		et.begin();
		fb.sparaFilm(MOVIE_JSON);
		et.commit();
		et.begin();
		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<Movie> cq = cb.createQuery(Movie.class);
		Root<Movie> movie = cq.from(Movie.class);
		cq.where(cb.equal(movie.get(Movie_.title), "Hackers"));
		TypedQuery<Movie> q = em.createQuery(cq);
		List<Movie> results = q.getResultList();
		et.commit();
		Assert.assertTrue(results.size() == 1);
	}

}

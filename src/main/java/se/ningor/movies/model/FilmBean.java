package se.ningor.movies.model;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.enterprise.inject.Model;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.context.Flash;
import javax.faces.event.ComponentSystemEvent;
import javax.inject.Inject;

import org.slf4j.Logger;

import se.ningor.movies.business.movie.boundary.FilmBoundary;
import se.ningor.movies.business.movie.entity.Movie;
import se.ningor.movies.utils.Resources;

@Model
public class FilmBean {
	
	@Inject
	FilmBoundary fb;
	
	@Inject
	Logger logger;
	
	private List<Movie> filmer;
	
	private Movie selectedFilm;
	
	private Long movieId;

	public List<Movie> getFilmer() {
		return filmer;
	}

	public void setFilmer(List<Movie> filmer) {
		this.filmer = filmer;
	}
	
	public Movie getSelectedFilm() {
		return selectedFilm;
	}

	public void setSelectedFilm(Movie selectedFilm) {
		this.selectedFilm = selectedFilm;
	}

	public Long getMovieId() {
		return movieId;
	}

	public void setMovieId(Long movieId) {
		this.movieId = movieId;
	}

	public String sparaNyFilm() {
		
		FacesContext context = FacesContext.getCurrentInstance(); 
		Flash flash = context.getExternalContext().getFlash();
		Boolean massregistrering = (Boolean) flash.get("massreg");
		String imdbJSON = (String) flash.get("imdbJSON");
		
		if (imdbJSON != null && imdbJSON.length() > 0) {
			logger.info("I sparaNyFilm(): följande imdb-data kommer att sparas: {}", imdbJSON);
			fb.sparaFilm(imdbJSON);
		} else {
			logger.error("I sparaNyFilm(): Flash attribute 'imdbJSON' saknar innehåll");
			context.addMessage("addMovieForm", new FacesMessage(FacesMessage.SEVERITY_ERROR, 
					Resources.getMessageFromBundle("application.addmovie.imdbjsonmissing"), 
					Resources.getMessageFromBundle("application.addmovie.imdbjsonmissing")));
			return null;
		}
		
		// Mass registration? if yes: stay on page
		if (massregistrering != null && massregistrering.booleanValue()) {
			flash.remove("title");
			return null;
		}
		return "movies?faces-redirect=true";
	}
	
	public void initSelectedMovieFromId(ComponentSystemEvent event) {
		logger.info("I initSelectedMovieFromId() - med inputparameter '{}'", movieId);
		selectedFilm = fb.findMovie(movieId);
		logger.info("movie object: {}", selectedFilm);
	}

	@PostConstruct
	public void init() {
		logger.info("Initializing FilmBean (in init())");
		filmer = fb.listaFilmer();
	}
}

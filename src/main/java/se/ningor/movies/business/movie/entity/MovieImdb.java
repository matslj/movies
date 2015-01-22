package se.ningor.movies.business.movie.entity;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotBlank;

/**
 * This is a model of the movie data provided by <a href="http://www.omdbapi.com">http://www.omdbapi.com</a>.
 * The data is set using reflection (hence no setters).
 * 
 * @author mats
 *
 */
public class MovieImdb {
	
	@NotNull
	@NotBlank
	private String Title;
	
	@NotNull
	private String Year;
	
	private String Rated;
	
	private String Released;
	
	private String Runtime;
	
	private String Genre;

	private String Director;
	
	private String Writer;

	private String Actors;

	private String Plot;
	
	private String Country;

	private String Poster;

	private String Metascore;

	private Double imdbRating;
	
	private String imdbVotes;

	private String imdbID;
	
	private String Type;

	public String getTitle() {
		return Title;
	}

	public String getYear() {
		return Year;
	}

	public String getRated() {
		return Rated;
	}

	public String getReleased() {
		return Released;
	}

	public String getRuntime() {
		return Runtime;
	}

	public String getGenre() {
		return Genre;
	}

	public String getDirector() {
		return Director;
	}

	public String getWriter() {
		return Writer;
	}

	public String getActors() {
		return Actors;
	}

	public String getPlot() {
		return Plot;
	}

	public String getCountry() {
		return Country;
	}

	public String getPoster() {
		return Poster;
	}

	public String getMetascore() {
		return Metascore;
	}

	public Double getImdbRating() {
		return imdbRating;
	}

	public String getImdbVotes() {
		return imdbVotes;
	}

	public String getImdbID() {
		return imdbID;
	}

	public String getType() {
		return Type;
	}

}

package se.ningor.movies.business.movie.entity;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashSet;
import java.util.Locale;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotBlank;

/**
 * Model for movies. Some originates from imdb (using an unofficial webservice) 
 * and that data is set using reflection (no setters).
 * 
 * @author mats
 *
 */
@NamedQueries({
	@NamedQuery(name="movie.listAllMovies", query="SELECT m FROM Movie m"),
	@NamedQuery(name="movie.findMovieByTitle", query="SELECT m FROM Movie m WHERE m.title = :title")
})
@Entity
public class Movie {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;

	@NotNull
	@NotBlank
	private String title;
	
	private String year;
	
	private String rated;
	
	@Temporal(TemporalType.TIMESTAMP)
	private Date released;
	
	private String runtime;
	
	@ManyToMany(targetEntity=Genre.class, fetch=FetchType.EAGER, cascade=CascadeType.ALL)
	@JoinTable(name="relMovieGenre", joinColumns={@JoinColumn(name="movie_id", referencedColumnName="id")},
		inverseJoinColumns={@JoinColumn(name="genre_id", referencedColumnName="id")})
	private Set<Genre> genres;
	
	private String director;
	
	private String writer;
	
	@ManyToMany(targetEntity=Actor.class, fetch=FetchType.EAGER, cascade=CascadeType.ALL)
	@JoinTable(name="relMovieActor", joinColumns={@JoinColumn(name="movie_id", referencedColumnName="id")},
		inverseJoinColumns={@JoinColumn(name="actor_id", referencedColumnName="id")})
	private Set<Actor> actors;

	private String plot;

	private String poster;
	
	private String localImdbPoster;

	private Double imdbRating;
	
	private Long imdbVotes;

	private String imdbID;
	
	private String type;
	
	private String borrowedTo;
	
	@Temporal(TemporalType.TIMESTAMP)
	private Date entryCreatedDate;

	public Long getId() {
		return id;
	}
	
	public Movie() {
		super();
	}

	public Movie(MovieImdb mi)  {
		super();
		entryCreatedDate = new Date();
		
		this.title = mi.getTitle();
		this.year = mi.getYear();
		this.rated = mi.getRated();
		
		DateFormat format = new SimpleDateFormat("d MMM yyyy", Locale.ENGLISH);
		try {
			this.released = format.parse(mi.getReleased());
		} catch (ParseException e) {
			throw new IllegalArgumentException(e);
		}
		
		this.runtime = mi.getRuntime();
		this.director = mi.getDirector();
		this.writer = mi.getWriter();
		
		this.genres = new HashSet<Genre>();
		this.actors = new HashSet<Actor>();
		
		this.plot = mi.getPlot();
		this.poster = mi.getPoster();
		this.imdbRating = mi.getImdbRating();
		if (mi.getImdbVotes() != null) {
			this.imdbVotes = Long.valueOf(mi.getImdbVotes().replaceAll(",", "").toString());
		}
		this.imdbID = mi.getImdbID();
		this.type = mi.getType();
	}
	
	public void addActor(Actor a) {
		this.getActors().add(a);
		a.getMovies().add(this);
	}
	
	public void addGenre(Genre g) {
		this.getGenres().add(g);
		g.getMovies().add(this);
	}

	public String getLocalImdbPoster() {
		return localImdbPoster;
	}

	public void setLocalImdbPoster(String localImdbPoster) {
		this.localImdbPoster = localImdbPoster;
	}

	public String getBorrowedTo() {
		return borrowedTo;
	}

	public void setBorrowedTo(String borrowedTo) {
		this.borrowedTo = borrowedTo;
	}

	public Date getEntryCreatedDate() {
		return entryCreatedDate;
	}

	public void setEntryCreatedDate(Date entryCreatedDate) {
		this.entryCreatedDate = entryCreatedDate;
	}

	public String getTitle() {
		return title;
	}

	public String getYear() {
		return year;
	}

	public String getRated() {
		return rated;
	}

	public Date getReleased() {
		return released;
	}

	public String getRuntime() {
		return runtime;
	}

	public Set<Genre> getGenres() {
		return genres;
	}

	public String getDirector() {
		return director;
	}

	public String getWriter() {
		return writer;
	}

	public Set<Actor> getActors() {
		return actors;
	}

	public String getPlot() {
		return plot;
	}

	public String getPoster() {
		return poster;
	}

	public Double getImdbRating() {
		return imdbRating;
	}

	public Long getImdbVotes() {
		return imdbVotes;
	}

	public String getImdbID() {
		return imdbID;
	}

	public String getType() {
		return type;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((title == null) ? 0 : title.hashCode());
		result = prime * result + ((year == null) ? 0 : year.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Movie other = (Movie) obj;
		if (title == null) {
			if (other.title != null)
				return false;
		} else if (!title.equals(other.title))
			return false;
		if (year == null) {
			if (other.year != null)
				return false;
		} else if (!year.equals(other.year))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Movie [id=" + id + ", title=" + title + ", year=" + year
				+ ", rated=" + rated + ", released=" + released + ", runtime="
				+ runtime + ", genres=" + genres + ", director=" + director
				+ ", writer=" + writer + ", actors=" + actors + ", plot="
				+ plot + ", poster=" + poster + ", localImdbPoster="
				+ localImdbPoster + ", imdbRating=" + imdbRating
				+ ", imdbVotes=" + imdbVotes + ", imdbID=" + imdbID + ", type="
				+ type + ", borrowedTo=" + borrowedTo + ", entryCreatedDate="
				+ entryCreatedDate + "]";
	}
	
}

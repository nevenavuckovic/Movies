package rs.ac.ni.pmf.movies.model;

import androidx.databinding.BaseObservable;
import androidx.databinding.Bindable;

import java.util.List;

public class Movie extends BaseObservable {
    private final long id;
    private String name;
    private String image;
    private String director;
    private List<String> actors;
    private long year;
    private List<String> genres;
    private String description;

    public Movie(long id, String name, String image, String director, List<String> actors,
                 long year, List<String> genres, String description) {
        this.id = id;
        this.name = name;
        this.image = image;
        this.director = director;
        this.actors = actors;
        this.year = year;
        this.genres = genres;
        this.description = description;
    }

    public long getId() {
        return id;
    }

    @Bindable
    public String getName() {
        return name;
    }

    @Bindable
    public String getImage() {
        return image;
    }

    @Bindable
    public String getDirector() {
        return director;
    }

    @Bindable
    public List<String> getActors() {
        return actors;
    }

    @Bindable
    public long getYear() {
        return year;
    }

    @Bindable
    public List<String> getGenres() {
        return genres;
    }

    @Bindable
    public String getDescription() {
        return description;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public void setDirector(String director) {
        this.director = director;
    }

    public void setActors(List<String> actors) {
        this.actors = actors;
    }

    public void setYear(long year) {
        this.year = year;
    }

    public void setGenres(List<String> genres) {
        this.genres = genres;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}

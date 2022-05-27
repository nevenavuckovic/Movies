package rs.ac.ni.pmf.movies.model;

import androidx.databinding.BaseObservable;
import androidx.databinding.Bindable;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.List;
@Entity(tableName = "movies")
public class Movie extends BaseObservable {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "movie_id")
    private long movie_id;
    @ColumnInfo(name = "title")
    private String title;
    @ColumnInfo(name = "image")
    private String image;
    @ColumnInfo(name = "director")
    private String director;
    @ColumnInfo(name = "year")
    private long year;
    @ColumnInfo(name = "description")
    private String description;

    public Movie(long movie_id, String title, String image, String director,
                 long year, String description) {
        this.movie_id = movie_id;
        this.title = title;
        this.image = image;
        this.director = director;
        this.year = year;
        this.description = description;
    }

    @Bindable
    public long getMovie_id() {
        return movie_id;
    }

    @Bindable
    public String getTitle() {
        return title;
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
    public long getYear() {
        return year;
    }

    @Bindable
    public String getDescription() {
        return description;
    }

    public void setMovie_id(long movie_id) {
        this.movie_id = movie_id;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public void setDirector(String director) {
        this.director = director;
    }

    public void setYear(long year) {
        this.year = year;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}

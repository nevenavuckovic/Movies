package rs.ac.ni.pmf.movies.model;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.databinding.BaseObservable;
import androidx.databinding.Bindable;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "movies")
public class Movie extends BaseObservable implements Parcelable {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "movie_id")
    private long movie_id;
    @ColumnInfo(name = "title")
    private String title;
    @ColumnInfo(name = "image")
    private byte[] image;
    @ColumnInfo(name = "director")
    private String director;
    @ColumnInfo(name = "year")
    private long year;
    @ColumnInfo(name = "description")
    private String description;

    public Movie(String title, byte[] image, String director,
                 long year, String description) {
        this.title = title;
        this.image = image;
        this.director = director;
        this.year = year;
        this.description = description;
    }

    public Movie(Movie movie){
        this.movie_id = movie.getMovie_id();
        this.title = movie.getTitle();
        this.image = movie.getImage();
        this.director = movie.getDirector();
        this.year = movie.getYear();
        this.description = movie.getDescription();
    }

    protected Movie(Parcel in) {
        movie_id = in.readLong();
        title = in.readString();
        image = in.createByteArray();
        director = in.readString();
        year = in.readLong();
        description = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(movie_id);
        dest.writeString(title);
        dest.writeByteArray(image);
        dest.writeString(director);
        dest.writeLong(year);
        dest.writeString(description);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<Movie> CREATOR = new Creator<Movie>() {
        @Override
        public Movie createFromParcel(Parcel in) {
            return new Movie(in);
        }

        @Override
        public Movie[] newArray(int size) {
            return new Movie[size];
        }
    };

    @Bindable
    public long getMovie_id() {
        return movie_id;
    }

    @Bindable
    public String getTitle() {
        return title;
    }

    @Bindable
    public byte[] getImage() {
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

    public void setImage(byte[] image) {
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

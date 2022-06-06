package rs.ac.ni.pmf.movies.model;

import android.os.Parcel;
import android.os.Parcelable;
import android.text.TextUtils;

import androidx.databinding.BaseObservable;
import androidx.databinding.Bindable;
import androidx.room.Embedded;
import androidx.room.Junction;
import androidx.room.Relation;

import java.util.ArrayList;
import java.util.List;

public class MovieWithGenres extends BaseObservable implements Parcelable {
    @Embedded
    @Bindable
    public Movie movie;
    @Relation(
            entity = Genre.class,
            parentColumn = "movie_id",
            entityColumn = "genre_id",
            associateBy = @Junction(value = MovieGenreCrossRef.class,
                    parentColumn = "movie_id",
                    entityColumn = "genre_id")
    )

    @Bindable
    public List<Genre> genres;

    public MovieWithGenres(Movie movie, List<Genre> genres) {
        this.movie = movie;
        this.genres = genres;
    }

    protected MovieWithGenres(Parcel in) {
        movie = in.readParcelable(Movie.class.getClassLoader());
        genres = in.createTypedArrayList(Genre.CREATOR);
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeParcelable(movie, flags);
        dest.writeTypedList(genres);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<MovieWithGenres> CREATOR = new Creator<MovieWithGenres>() {
        @Override
        public MovieWithGenres createFromParcel(Parcel in) {
            return new MovieWithGenres(in);
        }

        @Override
        public MovieWithGenres[] newArray(int size) {
            return new MovieWithGenres[size];
        }
    };

    @Override
    public String toString() {
        List<String> genreNames = new ArrayList<>();
        for (Genre genre: genres){
            genreNames.add(genre.getGenre());
        }
        return TextUtils.join(", ", genreNames);
    }

}

package rs.ac.ni.pmf.movies.model;

import android.text.TextUtils;

import androidx.databinding.BaseObservable;
import androidx.databinding.Bindable;
import androidx.room.Embedded;
import androidx.room.Junction;
import androidx.room.Relation;

import java.util.ArrayList;
import java.util.List;

public class MovieWithGenres extends BaseObservable {
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

    @Override
    public String toString() {
        List<String> genreNames = new ArrayList<>();
        for(int i = 0; i < genres.size(); i++){
            genreNames.add(genres.get(i).getGenre());
        }
        return TextUtils.join(", ", genreNames);
    }

}

package rs.ac.ni.pmf.movies.model;

import androidx.databinding.Bindable;
import androidx.room.Embedded;
import androidx.room.Junction;
import androidx.room.Relation;

import java.util.List;

public class MovieWithGenres {
    @Embedded
    public Movie movie;
    @Relation(
            parentColumn = "movie_id",
            entityColumn = "genre_id",
            associateBy = @Junction(MovieGenreCrossRef.class)
    )

    public List<Genre> genres;
}

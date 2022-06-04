package rs.ac.ni.pmf.movies.model;

import androidx.room.Embedded;
import androidx.room.Junction;
import androidx.room.Relation;

import java.util.List;

public class GenreWithMovies {
    @Embedded
    public Genre genre;
    @Relation(
            parentColumn = "genre_id",
            entityColumn = "movie_id",
            associateBy = @Junction(MovieGenreCrossRef.class)
    )

    public List<Movie> movies;
}

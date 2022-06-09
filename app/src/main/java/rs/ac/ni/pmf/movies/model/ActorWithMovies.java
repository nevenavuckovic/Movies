package rs.ac.ni.pmf.movies.model;

import androidx.room.Embedded;
import androidx.room.Junction;
import androidx.room.Relation;

import java.util.List;

public class ActorWithMovies {
    @Embedded
    public Actor actor;
    @Relation(
            parentColumn = "actor_id",
            entityColumn = "movie_id",
            associateBy = @Junction(MovieActorCrossRef.class)
    )
    public List<Movie> movies;
}

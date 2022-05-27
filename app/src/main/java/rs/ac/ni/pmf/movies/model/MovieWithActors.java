package rs.ac.ni.pmf.movies.model;

import androidx.databinding.Bindable;
import androidx.room.Embedded;
import androidx.room.Junction;
import androidx.room.Relation;

import java.util.List;

public class MovieWithActors {
    @Embedded
    public Movie movie;
    @Relation(
            parentColumn = "movie_id",
            entityColumn = "actor_id",
            associateBy = @Junction(MovieActorCrossRef.class)
    )

    public List<Actor> actors;
}

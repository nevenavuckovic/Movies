package rs.ac.ni.pmf.movies.model;

import android.text.TextUtils;

import androidx.databinding.BaseObservable;
import androidx.databinding.Bindable;
import androidx.room.Embedded;
import androidx.room.Junction;
import androidx.room.Relation;

import java.util.ArrayList;
import java.util.List;

public class MovieWithActors extends BaseObservable {
    @Embedded
    @Bindable
    public Movie movie;
    @Relation(
            entity = Actor.class,
            parentColumn = "movie_id",
            entityColumn = "actor_id",
            associateBy = @Junction(value = MovieActorCrossRef.class,
                    parentColumn = "movie_id",
                    entityColumn = "actor_id")
    )

    @Bindable
    public List<Actor> actors;

    @Override
    public String toString() {
        List<String> actorNames = new ArrayList<>();
        for(int i = 0; i < actors.size(); i++){
            actorNames.add(actors.get(i).getActor());
        }
        return TextUtils.join(", ", actorNames);
    }
}

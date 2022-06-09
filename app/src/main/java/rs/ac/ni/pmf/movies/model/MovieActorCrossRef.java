package rs.ac.ni.pmf.movies.model;

import androidx.room.ColumnInfo;
import androidx.room.Entity;

@Entity(tableName = "movies_actors", primaryKeys = {"movie_id", "actor_id"})
public class MovieActorCrossRef {
    @ColumnInfo(name = "movie_id")
    public long movie_id;
    @ColumnInfo(name = "actor_id")
    public long actor_id;

    public MovieActorCrossRef(long movie_id, long actor_id) {
        this.movie_id = movie_id;
        this.actor_id = actor_id;
    }

    public long getMovie_id() { return movie_id; }

    public void setMovie_id(long movie_id) {
        this.movie_id = movie_id;
    }

    public long getActor_id() {
        return actor_id;
    }

    public void setActor_id(long actor_id) {
        this.actor_id = actor_id;
    }
}

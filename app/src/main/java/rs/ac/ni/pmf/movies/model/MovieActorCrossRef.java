package rs.ac.ni.pmf.movies.model;

import androidx.room.ColumnInfo;
import androidx.room.Entity;

@Entity(tableName = "movies_actors", primaryKeys = {"movie_id", "actor_id"})
public class MovieActorCrossRef {
    @ColumnInfo(name = "movie_id")
    public long movie_id;
    @ColumnInfo(name = "actor_id")
    public long actor_id;
}

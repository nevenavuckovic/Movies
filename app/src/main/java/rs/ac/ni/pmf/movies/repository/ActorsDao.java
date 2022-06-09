package rs.ac.ni.pmf.movies.repository;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import rs.ac.ni.pmf.movies.model.Actor;

@Dao
public interface ActorsDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    long insertActor(Actor actor);

    @Query("SELECT actor_id FROM actors WHERE actor LIKE :s")
    long getActor(String s);
}

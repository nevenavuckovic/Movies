package rs.ac.ni.pmf.movies.repository;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Transaction;

import rs.ac.ni.pmf.movies.model.MovieActorCrossRef;

@Dao
public interface MoviesActorsDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertMovieActorCrossRef(MovieActorCrossRef movieActorCrossRef);

    @Transaction
    @Query("DELETE FROM movies_actors WHERE movie_id = :movie_id")
    void deleteMovieActor(long movie_id);
}

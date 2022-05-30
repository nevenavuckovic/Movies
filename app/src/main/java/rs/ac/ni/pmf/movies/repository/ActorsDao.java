package rs.ac.ni.pmf.movies.repository;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Transaction;
import androidx.room.Update;

import java.util.List;

import rs.ac.ni.pmf.movies.model.Actor;
import rs.ac.ni.pmf.movies.model.ActorWithMovies;
import rs.ac.ni.pmf.movies.model.MovieWithActors;


@Dao
public interface ActorsDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertActor(Actor actor);

    @Query("SELECT * FROM actors")
    List<Actor> getAllActors();

    @Transaction
    @Query("SELECT * FROM movies")
    LiveData<List<MovieWithActors>> getMovieActor();

    @Transaction
    @Query("SELECT * FROM actors WHERE actor LIKE :actor ")
    List<ActorWithMovies> getMoviesByActor(String actor);

}
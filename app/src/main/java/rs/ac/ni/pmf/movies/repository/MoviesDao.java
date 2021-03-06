package rs.ac.ni.pmf.movies.repository;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Transaction;
import androidx.room.Update;

import java.util.List;

import rs.ac.ni.pmf.movies.model.Movie;
import rs.ac.ni.pmf.movies.model.MovieWithActors;
import rs.ac.ni.pmf.movies.model.MovieWithGenres;

@Dao
public interface MoviesDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    long insertMovie(Movie movie);

    @Update
    void updateMovie(Movie movie);

    @Transaction
    @Query("DELETE FROM movies WHERE movie_id = :id")
    void deleteMovieById(long id);

    @Transaction
    @Query("SELECT * FROM movies WHERE movies.movie_id = :id")
    LiveData<MovieWithActors> getActorsByMovie(long id);

    @Transaction
    @Query("SELECT * FROM movies")
    LiveData<List<MovieWithGenres>> getMoviesWithGenres();

    @Transaction
    @Query("SELECT * FROM movies WHERE movie_id = :id")
    LiveData<MovieWithActors> getMovieWithActors(long id);

    @Query("SELECT count(*) FROM movies")
    long getMovieCount();

    @Query("SELECT movie_id FROM movies WHERE title LIKE :title")
    long getMovieId(String title);

}

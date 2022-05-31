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

import rs.ac.ni.pmf.movies.model.Movie;
import rs.ac.ni.pmf.movies.model.MovieWithActors;
import rs.ac.ni.pmf.movies.model.MovieWithGenres;

@Dao
public interface MoviesDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertMovie(Movie movie);

    @Update
    void updateMovie(Movie movie);

    @Delete
    void deleteMovie(Movie movie);

    @Query("DELETE FROM movies WHERE movie_id = :id")
    void deleteMovieById(long id);

    @Query("SELECT * FROM movies")
    LiveData<List<Movie>> getAllMovies();

    @Query("SELECT movies.director FROM movies WHERE movies.title like :title")
    String getDirector(String title);


    @Transaction
    @Query("SELECT * FROM movies " +
            "WHERE movies.title like :title")
    LiveData<MovieWithActors> getActorsByMovie(String title);


    @Transaction
    @Query("SELECT * FROM movies")
    LiveData<List<MovieWithGenres>> getMoviesWithGenres();

    @Transaction
    @Query("SELECT * FROM movies")
    LiveData<List<MovieWithActors>> getMoviesWithActors();

    @Query("SELECT * FROM movies WHERE year = :year")
    LiveData<List<Movie>> getMoviesYear(long year);

    @Query("SELECT * FROM movies WHERE title LIKE :text ")
    LiveData<List<Movie>> getMoviesSearch(String text);


}

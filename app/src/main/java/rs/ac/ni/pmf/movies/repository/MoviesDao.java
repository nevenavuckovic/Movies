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

    @Query("SELECT * FROM movies")
    LiveData<List<Movie>> getAllMovies();

    @Transaction
    @Query("SELECT g.genre FROM genres g INNER JOIN movies_genres mg ON g.genre_id = mg.genre_id  " +
            "INNER JOIN movies m ON mg.movie_id = m.movie_id " +
            "WHERE m.title LIKE :title")
    LiveData<List<String>> getGenresByMovie(String title);


    @Transaction
    @Query("SELECT * FROM movies WHERE title LIKE :title ")
    LiveData<List<MovieWithActors>> getActorsByMovie(String title);

    @Query("SELECT * FROM movies WHERE year = :year")
    LiveData<List<Movie>> getMoviesYear(long year);

    @Query("SELECT * FROM movies WHERE title LIKE :text ")
    LiveData<List<Movie>> getMoviesSearch(String text);

}

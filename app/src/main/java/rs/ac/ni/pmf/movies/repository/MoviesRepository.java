package rs.ac.ni.pmf.movies.repository;

import android.content.Context;

import androidx.lifecycle.LiveData;

import java.util.List;

import rs.ac.ni.pmf.movies.model.Genre;
import rs.ac.ni.pmf.movies.model.Movie;
import rs.ac.ni.pmf.movies.model.MovieGenreCrossRef;

public class MoviesRepository {

    private final MoviesDao moviesDao;
    private final ActorsDao actorsDao;
    private final GenresDao genresDao;
    private final MoviesGenresDao moviesGenresDao;

    public MoviesRepository(Context context){
        final MoviesDatabase moviesDatabase = MoviesDatabase.getInstance(context);
        this.moviesDao = moviesDatabase.moviesDao();
        this.actorsDao = moviesDatabase.actorsDao();
        this.genresDao = moviesDatabase.genresDao();
        this.moviesGenresDao = moviesDatabase.moviesGenresDao();

    }

    public LiveData<List<Movie>> getMovies(){
        return moviesDao.getAllMovies();
    }

    public void addMovie(Movie movie){
        MoviesDatabase.databaseExecutor.execute(()->moviesDao.insertMovie(movie));
    }

    public void addGenre(Genre genre){
        MoviesDatabase.databaseExecutor.execute(()->genresDao.insertGenre(genre));
    }
    public void addMovieGenre(MovieGenreCrossRef movieGenreCrossRef){
        MoviesDatabase.databaseExecutor.execute(()->moviesGenresDao.insertMovieGenreCrossRef(movieGenreCrossRef));
    }

}

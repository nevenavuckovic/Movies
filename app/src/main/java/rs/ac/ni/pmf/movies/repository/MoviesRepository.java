package rs.ac.ni.pmf.movies.repository;

import android.content.Context;

import androidx.lifecycle.LiveData;

import java.text.AttributedCharacterIterator;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

import rs.ac.ni.pmf.movies.model.Actor;
import rs.ac.ni.pmf.movies.model.Genre;
import rs.ac.ni.pmf.movies.model.Movie;
import rs.ac.ni.pmf.movies.model.MovieActorCrossRef;
import rs.ac.ni.pmf.movies.model.MovieGenreCrossRef;
import rs.ac.ni.pmf.movies.model.MovieWithActors;
import rs.ac.ni.pmf.movies.model.MovieWithGenres;

public class MoviesRepository {

    private final MoviesDao moviesDao;
    private final ActorsDao actorsDao;
    private final GenresDao genresDao;
    private final MoviesGenresDao moviesGenresDao;
    private final MoviesActorsDao moviesActorsDao;

    public MoviesRepository(Context context){
        final MoviesDatabase moviesDatabase = MoviesDatabase.getInstance(context);
        this.moviesDao = moviesDatabase.moviesDao();
        this.actorsDao = moviesDatabase.actorsDao();
        this.genresDao = moviesDatabase.genresDao();
        this.moviesGenresDao = moviesDatabase.moviesGenresDao();
        this.moviesActorsDao = moviesDatabase.moviesActorsDao();

    }

    public LiveData<List<MovieWithGenres>> getMoviesWithGenres(){
        return moviesDao.getMoviesWithGenres();
    }

    public LiveData<MovieWithActors> getMoviesWithActors(String title){
        return moviesDao.getActorsByMovie(title);
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

    public void addActor(Actor actor) {
        MoviesDatabase.databaseExecutor.execute(()->actorsDao.insertActor(actor));
    }

    public void addMovieActor(MovieActorCrossRef movieActorCrossRef) {
        MoviesDatabase.databaseExecutor.execute(()->moviesActorsDao.insertMovieActorCrossRef(movieActorCrossRef));

    }
}

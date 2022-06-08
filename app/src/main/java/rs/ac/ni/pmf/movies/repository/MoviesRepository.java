package rs.ac.ni.pmf.movies.repository;

import android.content.Context;

import androidx.lifecycle.LiveData;

import java.util.List;

import rs.ac.ni.pmf.movies.model.Actor;
import rs.ac.ni.pmf.movies.model.Genre;
import rs.ac.ni.pmf.movies.model.Movie;
import rs.ac.ni.pmf.movies.model.MovieActorCrossRef;
import rs.ac.ni.pmf.movies.model.MovieGenreCrossRef;
import rs.ac.ni.pmf.movies.model.MovieWithActors;
import rs.ac.ni.pmf.movies.model.MovieWithGenres;

public class MoviesRepository {

    private final MoviesDatabase moviesDatabase;
    private final MoviesDao moviesDao;
    private final ActorsDao actorsDao;
    private final GenresDao genresDao;
    private final MoviesGenresDao moviesGenresDao;
    private final MoviesActorsDao moviesActorsDao;

    public MoviesRepository(Context context){
        moviesDatabase = MoviesDatabase.getInstance(context);
        this.moviesDao = moviesDatabase.moviesDao();
        this.actorsDao = moviesDatabase.actorsDao();
        this.genresDao = moviesDatabase.genresDao();
        this.moviesGenresDao = moviesDatabase.moviesGenresDao();
        this.moviesActorsDao = moviesDatabase.moviesActorsDao();

    }

    public LiveData<List<MovieWithGenres>> getMoviesWithGenres(){
        return moviesDatabase.submit(moviesDao::getMoviesWithGenres);
    }

    public LiveData<MovieWithActors> getMoviesWithActors(long id){
        return moviesDatabase.submit(() -> moviesDao.getActorsByMovie(id));
    }

    public long addMovie(Movie movie){
        return moviesDatabase.submit(() -> moviesDao.insertMovie(movie));
    }

    public long addGenre(Genre genre){
        return moviesDatabase.submit(() -> genresDao.insertGenre(genre));
    }
    public void addMovieGenre(MovieGenreCrossRef movieGenreCrossRef){
        moviesDatabase.execute(() -> moviesGenresDao.insertMovieGenreCrossRef(movieGenreCrossRef));
    }

    public long addActor(Actor actor) {
        return moviesDatabase.submit(() -> actorsDao.insertActor(actor));
    }

    public void addMovieActor(MovieActorCrossRef movieActorCrossRef) {
        moviesDatabase.execute(() -> moviesActorsDao.insertMovieActorCrossRef(movieActorCrossRef));

    }

    public void deleteMovie(long id) {
        moviesDatabase.execute(()->moviesDao.deleteMovieById(id));
    }

    public LiveData<List<Genre>> getGenres() {
        return moviesDatabase.submit(genresDao::getAllGenres);
    }

    public LiveData<MovieWithActors> getMovieWithActors(long id) {
        return moviesDatabase.submit(() -> moviesDao.getMovieWithActors(id));
    }

    public long getMovieCount(){
        return moviesDatabase.submit(moviesDao::getMovieCount);
    }

    public void updateMovie(Movie movie) {
        moviesDatabase.execute(() -> moviesDao.updateMovie(movie));
    }

    public long getActorId(String s) {
        return moviesDatabase.submit(() -> actorsDao.getActor(s));
    }

    public long getGenreId(String s) {
        return moviesDatabase.submit(() -> genresDao.getGenre(s));
    }

    public long getMovieId(String title) {
        return moviesDatabase.submit(() -> moviesDao.getMovieId(title));
    }

    public void deleteMovieGenre(long movie_id) {
        moviesDatabase.execute(() -> moviesGenresDao.deleteMovieGenre(movie_id));
    }

    public void deleteMovieActor(long movie_id) {
        moviesDatabase.execute(() -> moviesActorsDao.deleteMovieActor(movie_id));
    }

    public LiveData<List<MovieWithGenres>> getAllMoviesSorted(String s) {
        if (s.equals("ASC")) {
            return moviesDatabase.submit(moviesDao::getAllMoviesSortedASC);
        } else {
            return moviesDatabase.submit(moviesDao::getAllMoviesSortedDESC);
        }
    }
}

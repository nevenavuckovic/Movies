package rs.ac.ni.pmf.movies.repository;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import rs.ac.ni.pmf.movies.model.Actor;
import rs.ac.ni.pmf.movies.model.Genre;
import rs.ac.ni.pmf.movies.model.Movie;
import rs.ac.ni.pmf.movies.model.MovieActorCrossRef;
import rs.ac.ni.pmf.movies.model.MovieGenreCrossRef;

@Database(entities = {Movie.class, Actor.class, Genre.class, MovieGenreCrossRef.class,
        MovieActorCrossRef.class}, version = 1, exportSchema = false)
public abstract class MoviesDatabase extends RoomDatabase {

    public static MoviesDatabase INSTANCE;
    public static final ExecutorService databaseExecutor = Executors.newSingleThreadExecutor();

    public static MoviesDatabase getInstance(final Context context){
        if(INSTANCE == null){
            synchronized (MoviesDatabase.class){
                if(INSTANCE == null){
                    INSTANCE = Room
                            .databaseBuilder(context.getApplicationContext(), MoviesDatabase.class, "movies_database")
                            .setQueryExecutor(databaseExecutor)
                            .build();
                }
            }
        }
        return INSTANCE;
    }

    public abstract MoviesDao moviesDao();
    public abstract ActorsDao actorsDao();
    public abstract GenresDao genresDao();
    public abstract MoviesGenresDao moviesGenresDao();
    public abstract MoviesActorsDao moviesActorsDao();

    public void execute(final Runnable task){
        databaseExecutor.execute(task);
    }

    public <T> T submit(Callable<T> task){
        try {
            return databaseExecutor.submit(task).get();
        } catch (ExecutionException | InterruptedException e){
            e.fillInStackTrace();
        }
        return null;
    }
}

package rs.ac.ni.pmf.movies.model;

import android.app.Application;
import android.util.Pair;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import androidx.recyclerview.widget.RecyclerView;

import java.util.Iterator;
import java.util.List;
import java.util.Objects;

import rs.ac.ni.pmf.movies.fragment.MoviesRecyclerViewAdapter;
import rs.ac.ni.pmf.movies.repository.MoviesRepository;

public class MoviesViewModel extends AndroidViewModel {
    private LiveData<List<MovieWithGenres>> movies = new MutableLiveData<>();
    private MutableLiveData<MovieWithGenres> selectedMovieWithGenres = new MutableLiveData<>();
    private LiveData<MovieWithActors> selectedMovieWithActors = new MutableLiveData<>();

    private MoviesRepository moviesRepository;

    public MoviesViewModel(@NonNull Application application) {
        super(application);
        moviesRepository = new MoviesRepository(application);
        moviesRepository.addMovie(
                new Movie(1, "Iron Man", "iron_man", "nekko",
                        2008, "nessto"));
        moviesRepository.addMovie(
                new Movie(2, "The Avengers", "", "niko",
                        2012, "nista"));
        moviesRepository.addGenre(new Genre(1, "Action"));
        moviesRepository.addGenre(new Genre(2, "Adventure"));
        moviesRepository.addGenre(new Genre(3, "Sci-fi"));

        moviesRepository.addMovieGenre(new MovieGenreCrossRef(1,1));
        moviesRepository.addMovieGenre(new MovieGenreCrossRef(1,2));
        moviesRepository.addMovieGenre(new MovieGenreCrossRef(2,1));
        moviesRepository.addMovieGenre(new MovieGenreCrossRef(2,3));

        moviesRepository.addActor(new Actor(1, "Chris"));
        moviesRepository.addActor(new Actor(2, "Robert"));
        moviesRepository.addMovieActor(new MovieActorCrossRef(1,1));
        moviesRepository.addMovieActor(new MovieActorCrossRef(1,2));
        moviesRepository.addMovieActor(new MovieActorCrossRef(2,2));

        setMovies();
    }


    public LiveData<List<MovieWithGenres>> getMovies() {
        return movies;
    }

    public void setMovies() {
        movies = moviesRepository.getMoviesWithGenres();

    }

    public void setMovies(String text) {
        List<MovieWithGenres> list =  moviesRepository.getMoviesWithSearch(text).getValue();
        movies = moviesRepository.getMoviesWithSearch(text);
    }

    public MutableLiveData<MovieWithGenres> getSelectedMovieWithGenres() {
        return selectedMovieWithGenres;
    }

    public LiveData<MovieWithActors> getSelectedMovieWithActors() {
        if (selectedMovieWithGenres.getValue() != null) {
            selectedMovieWithActors = moviesRepository.getMoviesWithActors(selectedMovieWithGenres.getValue().movie.getTitle());
        }
        return selectedMovieWithActors;
    }

    public void setSelectedMovie(MovieWithGenres movieWithGenres) {
        selectedMovieWithGenres.setValue(movieWithGenres);
    }

    public void deleteMovie(long id) {
        moviesRepository.deleteMovie(id);
    }

    public LiveData<List<Genre>> getGenres() {
        return moviesRepository.getGenres();
    }
}

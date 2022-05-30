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
    private MutableLiveData<List<MovieWithGenres>> movies = new MutableLiveData<>();
    private MutableLiveData<MovieWithGenres> selectedMovieWithGenres = new MutableLiveData<>();
    private LiveData<MovieWithActors> selectedMovieWithActors = new MutableLiveData<>();

    private MoviesRepository moviesRepository;

    public MoviesViewModel(@NonNull Application application) {
        super(application);
        moviesRepository = new MoviesRepository(application);
        moviesRepository.addMovie(
                new Movie(1, "Iron Man", "iron_man", "nekko",
                        2008, "nessto"));
        moviesRepository.addGenre(new Genre(1, "Action"));
        moviesRepository.addGenre(new Genre(2, "Adventure"));
        moviesRepository.addMovieGenre(new MovieGenreCrossRef(1,1));
        moviesRepository.addMovieGenre(new MovieGenreCrossRef(1,2));

        moviesRepository.addActor(new Actor(1, "Chris"));
        moviesRepository.addActor(new Actor(2, "Robert"));
        moviesRepository.addMovieActor(new MovieActorCrossRef(1,1));
        moviesRepository.addMovieActor(new MovieActorCrossRef(1,2));
    }


    public LiveData<List<MovieWithGenres>> getMovies() {
        return moviesRepository.getMoviesWithGenres();
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

}

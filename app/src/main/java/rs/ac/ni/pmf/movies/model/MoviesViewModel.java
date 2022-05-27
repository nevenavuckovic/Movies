package rs.ac.ni.pmf.movies.model;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import rs.ac.ni.pmf.movies.fragment.MoviesRecyclerViewAdapter;
import rs.ac.ni.pmf.movies.repository.MoviesRepository;

public class MoviesViewModel extends AndroidViewModel {
    private MutableLiveData<List<Movie>> movies = new MutableLiveData<>();
    private MutableLiveData<Movie> selectedMovie = new MutableLiveData<>();

    private MoviesRepository moviesRepository;

    public MoviesViewModel(@NonNull Application application) {
        super(application);
        moviesRepository = new MoviesRepository(application);
        moviesRepository.addMovie(
                new Movie(1, "Iron Man", "ironman", "nekko",
                        2008, "nessto"));
        moviesRepository.addGenre(new Genre(1, "Action"));
        moviesRepository.addGenre(new Genre(2, "Adventure"));
        moviesRepository.addMovieGenre(new MovieGenreCrossRef(1,1));
        moviesRepository.addMovieGenre(new MovieGenreCrossRef(1,2));

    }


    public LiveData<List<Movie>> getMovies() {
        return moviesRepository.getMovies();
    }

    public MutableLiveData<Movie> getSelectedMovie() {
        return selectedMovie;
    }

    public void setSelectedMovie(Movie movie) {
        selectedMovie.setValue(movie);
    }
}

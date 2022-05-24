package rs.ac.ni.pmf.movies.model;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.List;

import rs.ac.ni.pmf.movies.repository.MoviesRepository;

public class MoviesViewModel extends ViewModel {
    private MutableLiveData<List<Movie>> movies = new MutableLiveData<>();
    private MutableLiveData<Movie> selectedMovie = new MutableLiveData<>();

    private MoviesRepository repository = MoviesRepository.INSTANCE;

    public MutableLiveData<List<Movie>> getMovies() {
        movies.setValue(repository.getMovies());
        return movies;
    }

    public MutableLiveData<Movie> getSelectedMovie() {
        return selectedMovie;
    }

    public void setSelectedMovie(Movie movie) {
        selectedMovie.setValue(movie);
    }
}

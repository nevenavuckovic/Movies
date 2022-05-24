package rs.ac.ni.pmf.movies;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import rs.ac.ni.pmf.movies.fragment.MovieDetailsFragment;
import rs.ac.ni.pmf.movies.fragment.MoviesRecyclerViewAdapter;
import rs.ac.ni.pmf.movies.model.Movie;
import rs.ac.ni.pmf.movies.model.MoviesViewModel;

public class MainActivity extends AppCompatActivity implements MoviesRecyclerViewAdapter.MovieSelectedListener {

    private MoviesViewModel moviesViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        moviesViewModel = new ViewModelProvider(this).get(MoviesViewModel.class);
    }

    @Override
    public void onMovieSelected(Movie movie) {
        moviesViewModel.setSelectedMovie(movie);
        if(findViewById(R.id.vertical_fragment_container) != null){
            getSupportFragmentManager().beginTransaction()
                    .setReorderingAllowed(true)
                    .replace(R.id.vertical_fragment_container, MovieDetailsFragment.class, null)
                    .addToBackStack(null)
                    .commit();
        }
    }
}
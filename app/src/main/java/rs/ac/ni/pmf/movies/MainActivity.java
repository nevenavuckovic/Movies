package rs.ac.ni.pmf.movies;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.inputmethod.EditorInfo;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import rs.ac.ni.pmf.movies.dialog.SelectGenresDialog;
import rs.ac.ni.pmf.movies.fragment.MovieDetailsFragment;
import rs.ac.ni.pmf.movies.fragment.MoviesRecyclerViewAdapter;
import rs.ac.ni.pmf.movies.model.Genre;
import rs.ac.ni.pmf.movies.model.MovieWithGenres;
import rs.ac.ni.pmf.movies.model.MoviesViewModel;

public class MainActivity extends AppCompatActivity implements MoviesRecyclerViewAdapter.MovieSelectedListener,
        SelectGenresDialog.SelectGenresDialogListener {

    private MoviesViewModel moviesViewModel;
    private static MovieWithGenres movie = null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        moviesViewModel = new ViewModelProvider(this).get(MoviesViewModel.class);
    }


    @Override
    public void onMovieSelected(MovieWithGenres movie) {
        MainActivity.movie = movie;
        moviesViewModel.setSelectedMovie(movie);
        if(findViewById(R.id.vertical_fragment_container) != null && movie!= null){
            getSupportFragmentManager().beginTransaction()
                    .setReorderingAllowed(true)
                    .replace(R.id.vertical_fragment_container, MovieDetailsFragment.class, null)
                    .addToBackStack(null)
                    .commit();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        moviesViewModel.setSelectedMovie(movie);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.movies_main_menu, menu);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        if (item.getItemId() == R.id.menu_search){
            SearchView searchView = (SearchView) item.getActionView();
            searchView.setMaxWidth(Integer.MAX_VALUE);
            searchView.setImeOptions(EditorInfo.IME_ACTION_DONE);
            searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
                @Override
                public boolean onQueryTextSubmit(String s) {
                    return false;
                }

                @Override
                public boolean onQueryTextChange(String s) {
                    RecyclerView recyclerView = findViewById(R.id.list);
                    if (recyclerView != null) {
                        MoviesRecyclerViewAdapter adapter = (MoviesRecyclerViewAdapter) recyclerView.getAdapter();
                        adapter.getFilter().filter(s);
                        onMovieSelected(null);
                    } else {

                    }

                    return false;
                }
            });
            return true;
        }
        if (item.getItemId() == R.id.menu_select_genres){
            moviesViewModel.getGenres().observe(this, genres -> {
                SelectGenresDialog selectGenresDialog = new SelectGenresDialog(genres);
                selectGenresDialog.show(getSupportFragmentManager(), "SELECT_GENRES_DIALOG");
            });
            return true;
        }
        if (item.getItemId() == R.id.menu_add_movie){
            return true;
        }
        return false;
    }

    @Override
    public void onSearch(List<Genre> checkedGenres) {
        RecyclerView recyclerView = findViewById(R.id.list);
        if (recyclerView != null) {
            MoviesRecyclerViewAdapter adapter = (MoviesRecyclerViewAdapter) recyclerView.getAdapter();
            adapter.setFilteredMoviesWithGenres(checkedGenres);

            onMovieSelected(null);
        } else {

        }
    }

    @Override
    public void onCancel() {

    }
}
package rs.ac.ni.pmf.movies;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.inputmethod.EditorInfo;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import rs.ac.ni.pmf.movies.dialog.AddMovieDialog;
import rs.ac.ni.pmf.movies.dialog.EditMovieDialog;
import rs.ac.ni.pmf.movies.dialog.SelectGenresDialog;
import rs.ac.ni.pmf.movies.dialog.SortDialog;
import rs.ac.ni.pmf.movies.fragment.MovieDetailsFragment;
import rs.ac.ni.pmf.movies.fragment.MoviesListFragment;
import rs.ac.ni.pmf.movies.fragment.MoviesRecyclerViewAdapter;
import rs.ac.ni.pmf.movies.model.Genre;
import rs.ac.ni.pmf.movies.model.Movie;
import rs.ac.ni.pmf.movies.model.MovieWithGenres;
import rs.ac.ni.pmf.movies.model.MoviesViewModel;

public class MainActivity extends AppCompatActivity implements MoviesRecyclerViewAdapter.MovieSelectedListener,
        SelectGenresDialog.SelectGenresDialogListener, AddMovieDialog.AddMovieDialogListener,
        EditMovieDialog.EditMovieDialogListener, SortDialog.SortDialogListener {

    private MoviesViewModel moviesViewModel;
    private static MovieWithGenres movie = null;
    public static List<Genre> checkedGenres = new ArrayList<>();
    public static String checkedSort = "None"; //todo fix, check when on details

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelable("MOVIE", movie);
    }

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
                        if (adapter != null) {
                            adapter.getFilter().filter(s);
                            onMovieSelected(null);
                        }
                    }

                    return false;
                }
            });
            return true;
        }
        if (item.getItemId() == R.id.menu_select_genres){
            moviesViewModel.getGenres().observe(this, genres -> {
                SelectGenresDialog selectGenresDialog = new SelectGenresDialog(genres, checkedGenres);
                selectGenresDialog.show(getSupportFragmentManager(), "SELECT_GENRES_DIALOG");
            });
            return true;
        }
        if (item.getItemId() == R.id.menu_sort){
            SortDialog sortDialog = new SortDialog(checkedSort);
            sortDialog.show(getSupportFragmentManager(), "SORT_DIALOG");
            return true;
        }
        if (item.getItemId() == R.id.menu_add_movie){
            AddMovieDialog addMovieDialog = new AddMovieDialog();
            addMovieDialog.show(getSupportFragmentManager(), "ADD_MOVIE_DIALOG");
            return true;
        }
        return false;
    }

    @Override
    public void onSearch(List<Genre> checkedGenres) {
        RecyclerView recyclerView = findViewById(R.id.list);
        if (recyclerView != null) {
            MoviesRecyclerViewAdapter adapter = (MoviesRecyclerViewAdapter) recyclerView.getAdapter();
            if (adapter != null) {
                MainActivity.checkedGenres = checkedGenres;
                adapter.setMovies();
                MoviesRecyclerViewAdapter.selectedPosition = RecyclerView.NO_POSITION;
                onMovieSelected(null);
            }
        } else {
            MainActivity.checkedGenres = checkedGenres;
            MoviesRecyclerViewAdapter.selectedPosition = RecyclerView.NO_POSITION;
            onMovieSelected(null);
        }
    }

    @Override
    public void onAddMovie(Movie movie, List<String> genres, List<String> actors, boolean resultOk) {
        if (resultOk) {
            if (moviesViewModel.getMovieId(movie.getTitle()) != 0L) {
                Toast.makeText(this, "Movie with this title already exist!", Toast.LENGTH_LONG).show();
                AddMovieDialog addMovieDialog = new AddMovieDialog(movie, genres, actors);
                addMovieDialog.show(getSupportFragmentManager(), "ADD_MOVIE_DIALOG");
            } else {
                moviesViewModel.addMovie(movie, genres, actors);
            }
        } else {
            AddMovieDialog addMovieDialog = new AddMovieDialog(movie, genres, actors);
            addMovieDialog.show(getSupportFragmentManager(), "ADD_MOVIE_DIALOG");
        }
    }

    @Override
    public void onDone(Movie movie, List<String> genres, List<String> actors, boolean resultOk) {
        if (resultOk) {
            RecyclerView recyclerView = findViewById(R.id.list);
            if (recyclerView != null) {
                MoviesRecyclerViewAdapter adapter = (MoviesRecyclerViewAdapter) recyclerView.getAdapter();
                if (adapter != null) {
                    moviesViewModel.updateMovie(movie, genres, actors);
                    onMovieSelected(null);
                    MoviesRecyclerViewAdapter.selectedPosition = RecyclerView.NO_POSITION;
                }
            }
        } else {
            EditMovieDialog editMovieDialog = new EditMovieDialog(movie, genres, actors);
            editMovieDialog.show(getSupportFragmentManager(), "EDIT_MOVIE_DIALOG");
        }
    }

    @Override
    public void onCancel() {
        onMovieSelected(null);
        MoviesRecyclerViewAdapter.selectedPosition = RecyclerView.NO_POSITION;
    }

    @Override
    public void onSort(String checkedSort) {
        RecyclerView recyclerView = findViewById(R.id.list);
        if (recyclerView != null) {
            MoviesRecyclerViewAdapter adapter = (MoviesRecyclerViewAdapter) recyclerView.getAdapter();
            if (adapter != null) {
                MainActivity.checkedSort = checkedSort;
                adapter.setMovies();
                MoviesRecyclerViewAdapter.selectedPosition = RecyclerView.NO_POSITION;
                onMovieSelected(null);
            }
        } else {
            MainActivity.checkedSort = checkedSort;
            MoviesRecyclerViewAdapter.selectedPosition = RecyclerView.NO_POSITION;
            onMovieSelected(null);
        }
    }
}
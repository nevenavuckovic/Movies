package rs.ac.ni.pmf.movies.fragment;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import java.util.Arrays;

import rs.ac.ni.pmf.movies.R;
import rs.ac.ni.pmf.movies.dialog.EditMovieDialog;
import rs.ac.ni.pmf.movies.model.MovieWithGenres;
import rs.ac.ni.pmf.movies.model.MoviesViewModel;

public class MoviesListFragment extends Fragment{

    private MoviesViewModel moviesViewModel;
    private MoviesRecyclerViewAdapter.MovieSelectedListener movieSelectedListener;
    private MoviesRecyclerViewAdapter moviesRecyclerViewAdapter;
    public RecyclerView recyclerView;

    private static final String ARG_COLUMN_COUNT = "column-count";
    private int mColumnCount = 1;

    public static boolean menu = false;

    public MoviesListFragment() {}

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mColumnCount = getArguments().getInt(ARG_COLUMN_COUNT);
        }
        moviesViewModel = new ViewModelProvider(this).get(MoviesViewModel.class);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_movies_list, container, false);

        if (view instanceof RecyclerView) {
            Context context = view.getContext();
            RecyclerView recyclerView = (RecyclerView) view;
            if (mColumnCount <= 1) {
                recyclerView.setLayoutManager(new LinearLayoutManager(context));
            } else {
                recyclerView.setLayoutManager(new GridLayoutManager(context, mColumnCount));
            }

            this.recyclerView = recyclerView;
            registerForContextMenu(recyclerView);
            moviesViewModel.getMovies().observe(requireActivity(),
                    movies -> {
                        moviesRecyclerViewAdapter = new MoviesRecyclerViewAdapter(movies,
                                movieSelectedListener, requireActivity());
                        recyclerView.setAdapter(moviesRecyclerViewAdapter);
                    });
        }
        return view;
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        movieSelectedListener = (MoviesRecyclerViewAdapter.MovieSelectedListener) context;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public boolean onContextItemSelected(@NonNull MenuItem item) {
        MovieWithGenres movieWithGenres = moviesRecyclerViewAdapter.getMenuSelectedMovie();

        if (item.getItemId() == R.id.menu_edit_movie){
            menu = true;
            moviesViewModel.getMovie(movieWithGenres.movie.getMovie_id()).observe(this,
                    movieWithActors -> {
                        if (menu) {
                            EditMovieDialog editMovieDialog = new EditMovieDialog(movieWithGenres.movie,
                                    Arrays.asList(movieWithGenres.toString().split("\\s*,\\s*")),
                                    Arrays.asList(movieWithActors.toString().split("\\s*,\\s*")));
                            editMovieDialog.show(getParentFragmentManager(), "EDIT_MOVIE_DIALOG");
                        }
                    });
            MoviesRecyclerViewAdapter.selectedPosition = RecyclerView.NO_POSITION;
            return true;
        }
        if (item.getItemId() == R.id.menu_delete_movie){
            moviesViewModel.deleteMovie(movieWithGenres.movie.getMovie_id());
            MoviesRecyclerViewAdapter.selectedPosition = RecyclerView.NO_POSITION;
            movieSelectedListener.onMovieSelected(null);
            return true;
        }
        return false;
    }
}
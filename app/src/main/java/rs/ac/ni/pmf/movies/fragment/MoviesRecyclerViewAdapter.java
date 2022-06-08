package rs.ac.ni.pmf.movies.fragment;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;

import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;

import rs.ac.ni.pmf.movies.MainActivity;
import rs.ac.ni.pmf.movies.R;
import rs.ac.ni.pmf.movies.databinding.FragmentMovieBinding;
import rs.ac.ni.pmf.movies.model.Genre;
import rs.ac.ni.pmf.movies.model.MovieWithGenres;
import rs.ac.ni.pmf.movies.model.MoviesViewModel;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class MoviesRecyclerViewAdapter extends RecyclerView.Adapter<MoviesRecyclerViewAdapter.ViewHolder>
        implements Filterable {

    public static int selectedPosition = RecyclerView.NO_POSITION;
    private List<MovieWithGenres> moviesWithGenres;
    private List<MovieWithGenres> moviesWithGenresSortedAndFiltered;
    private List<MovieWithGenres> moviesWithGenresFull;
    private final MovieSelectedListener movieSelectedListener;
    private final FragmentActivity fragmentActivity;
    private static MovieWithGenres menuSelectedMovie;

    public interface MovieSelectedListener{
        void onMovieSelected(MovieWithGenres movie);
    }


    public MoviesRecyclerViewAdapter(List<MovieWithGenres> movies ,
                                     MovieSelectedListener movieSelectedListener,
                                     FragmentActivity fragmentActivity) {
        this.fragmentActivity = fragmentActivity;
        this.moviesWithGenres = movies;
        this.moviesWithGenresSortedAndFiltered = new ArrayList<>(movies);
        this.moviesWithGenresFull = new ArrayList<>(movies);
        this.movieSelectedListener = movieSelectedListener;
        setMovies();
    }

    public void setMovies() {
        List<Genre> checkedGenres = MainActivity.checkedGenres;
        moviesWithGenres = new ArrayList<>(moviesWithGenresFull);
        if (MainActivity.checkedSort.equals("None")){
            if (checkedGenres.size() != 0){
                List<MovieWithGenres> movies = new ArrayList<>();
                for (MovieWithGenres movie: moviesWithGenres){
                    if (movie.genres.containsAll(checkedGenres)){
                        movies.add(movie);
                    }
                }
                moviesWithGenres = movies;
            }
        } else if (MainActivity.checkedSort.equals("Ascending")) {
            moviesWithGenres.sort((movie1, movie2) ->
                    movie1.movie.getTitle().compareToIgnoreCase(movie2.movie.getTitle()));
            if (checkedGenres.size() != 0){
                List<MovieWithGenres> movies = new ArrayList<>();
                for (MovieWithGenres movie: moviesWithGenres){
                    if (movie.genres.containsAll(checkedGenres)){
                        movies.add(movie);
                    }
                }
                moviesWithGenres = movies;
            }
        } else {
            moviesWithGenres.sort((movie1, movie2) ->
                    movie1.movie.getTitle().compareToIgnoreCase(movie2.movie.getTitle()));
            Collections.reverse(moviesWithGenres);
            if (checkedGenres.size() != 0){
                List<MovieWithGenres> movies = new ArrayList<>();
                for (MovieWithGenres movie: moviesWithGenres){
                    if (movie.genres.containsAll(checkedGenres)){
                        movies.add(movie);
                    }
                }
                moviesWithGenres = movies;
            }
        }
        moviesWithGenresSortedAndFiltered = new ArrayList<>(moviesWithGenres);
        notifyDataSetChanged();

    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(FragmentMovieBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false));
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.setSelection(position);
    }


    public MovieWithGenres getMenuSelectedMovie(){
        return menuSelectedMovie;
    }

    @Override
    public int getItemCount() {
        return moviesWithGenres.size();
    }

    @Override
    public Filter getFilter() {
        return movieFilter;
    }

    private final Filter movieFilter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence charSequence) {
            List<MovieWithGenres> filteredList = new ArrayList<>();
            if (charSequence == null || charSequence.length() == 0){
                filteredList.addAll(moviesWithGenresSortedAndFiltered);
            } else {
                String filterPatter = charSequence.toString().toLowerCase().trim();
                for (MovieWithGenres movie: moviesWithGenresSortedAndFiltered){
                    if (movie.movie.getTitle().toLowerCase().contains(filterPatter)){
                        filteredList.add(movie);
                    }
                }
            }

            FilterResults results = new FilterResults();
            results.values = filteredList;
            return results;
        }

        @Override
        protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
            moviesWithGenres.clear();
            moviesWithGenres.addAll((List) filterResults.values);
            notifyDataSetChanged();
            selectedPosition = RecyclerView.NO_POSITION;
        }
    };

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnCreateContextMenuListener,
            View.OnLongClickListener{
        private MovieWithGenres movieWithGenres;
        private final FragmentMovieBinding binding;

        public ViewHolder(FragmentMovieBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
            this.itemView.setOnClickListener(this);
            this.itemView.setOnCreateContextMenuListener(this);
            this.itemView.setOnLongClickListener(this);
        }

        public void setSelection(int position) {
            this.movieWithGenres = moviesWithGenres.get(position);
            this.binding.setMovieWithGenres(this.movieWithGenres);
            this.itemView.setSelected(position == selectedPosition);
        }

        @Override
        public void onClick(View view) {
            selectedPosition = getBindingAdapterPosition();
            movieSelectedListener.onMovieSelected(movieWithGenres);
        }

        @Override
        public void onCreateContextMenu(ContextMenu contextMenu, View view, ContextMenu.ContextMenuInfo contextMenuInfo) {
            fragmentActivity.getMenuInflater().inflate(R.menu.list_context_menu, contextMenu);
        }

        @Override
        public boolean onLongClick(View view) {
            menuSelectedMovie = moviesWithGenres.get(getBindingAdapterPosition());
            return false;
        }


    }
}
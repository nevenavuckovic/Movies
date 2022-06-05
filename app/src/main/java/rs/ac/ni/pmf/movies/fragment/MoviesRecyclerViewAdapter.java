package rs.ac.ni.pmf.movies.fragment;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;

import rs.ac.ni.pmf.movies.R;
import rs.ac.ni.pmf.movies.databinding.FragmentMovieBinding;
import rs.ac.ni.pmf.movies.model.Genre;
import rs.ac.ni.pmf.movies.model.MovieWithGenres;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class MoviesRecyclerViewAdapter extends RecyclerView.Adapter<MoviesRecyclerViewAdapter.ViewHolder>
        implements Filterable {

    private static int selectedPosition = RecyclerView.NO_POSITION;
    private List<MovieWithGenres> moviesWithGenres;
    private final List<MovieWithGenres> moviesWithGenresFull;
    private List<MovieWithGenres> filteredMoviesWithGenres;
    private final MovieSelectedListener movieSelectedListener;
    private final FragmentActivity fragmentActivity;
    private static MovieWithGenres menuSelectedMovie;
    private static int menuSelectedPosition = RecyclerView.NO_POSITION;

    public interface MovieSelectedListener{
        void onMovieSelected(MovieWithGenres movie);
    }


    public MoviesRecyclerViewAdapter(List<MovieWithGenres> movies,
                                     MovieSelectedListener movieSelectedListener,
                                     FragmentActivity fragmentActivity) {
        this.moviesWithGenres = movies;
        this.moviesWithGenresFull = new ArrayList<>(movies);
        this.filteredMoviesWithGenres = new ArrayList<>(movies);
        this.movieSelectedListener = movieSelectedListener;
        this.fragmentActivity = fragmentActivity;

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

    public void resetSelectedPosition(){
        selectedPosition = RecyclerView.NO_POSITION;
        notifyItemChanged(selectedPosition);
    }

    public void setFilteredMoviesWithGenres(List<Genre> genres) {
        List<MovieWithGenres> filteredList = new ArrayList<>();
        if (genres == null || genres.size() == 0){
            filteredList.addAll(moviesWithGenresFull);
        } else {
            for (MovieWithGenres movie: moviesWithGenresFull){
                if (movie.genres.containsAll(genres)){
                    filteredList.add(movie);
                }
            }
        }
        filteredMoviesWithGenres = filteredList;
        moviesWithGenres = new ArrayList<>(filteredList);
        notifyDataSetChanged();
        resetSelectedPosition();
    }

    public MovieWithGenres getMenuSelectedMovie(){
        return menuSelectedMovie;
    }

    public boolean menuAndSelected(){
        return selectedPosition == menuSelectedPosition;
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
                filteredList.addAll(filteredMoviesWithGenres);
            } else {
                String filterPatter = charSequence.toString().toLowerCase().trim();
                for (MovieWithGenres movie: filteredMoviesWithGenres){
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
            resetSelectedPosition();
        }
    };

    public void sort(String checkedSort) {
        switch (checkedSort) {
            case "None":
                moviesWithGenres = new ArrayList<>(filteredMoviesWithGenres);
                break;
            case "Ascending":
                moviesWithGenres.sort((movie1, movie2) ->
                        movie1.movie.getTitle().compareToIgnoreCase(movie2.movie.getTitle()));
                break;
            case "Descending":
                moviesWithGenres.sort((movie1, movie2) ->
                        movie1.movie.getTitle().compareToIgnoreCase(movie2.movie.getTitle()));
                Collections.reverse(moviesWithGenres);
                break;
        }
        notifyDataSetChanged();
        resetSelectedPosition();
    }

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
            notifyItemChanged(selectedPosition);
            selectedPosition = getBindingAdapterPosition();
            notifyItemChanged(selectedPosition);
            movieSelectedListener.onMovieSelected(movieWithGenres);
        }

        @Override
        public void onCreateContextMenu(ContextMenu contextMenu, View view, ContextMenu.ContextMenuInfo contextMenuInfo) {
            fragmentActivity.getMenuInflater().inflate(R.menu.list_context_menu, contextMenu);
        }

        @Override
        public boolean onLongClick(View view) {
            menuSelectedPosition = getBindingAdapterPosition();
            menuSelectedMovie = moviesWithGenres.get(menuSelectedPosition);
            return false;
        }


    }
}
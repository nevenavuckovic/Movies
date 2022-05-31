package rs.ac.ni.pmf.movies.fragment;

import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.LiveData;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.util.Pair;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import rs.ac.ni.pmf.movies.R;
import rs.ac.ni.pmf.movies.databinding.FragmentMovieBinding;
import rs.ac.ni.pmf.movies.model.Movie;
import rs.ac.ni.pmf.movies.model.MovieWithActors;
import rs.ac.ni.pmf.movies.model.MovieWithGenres;

import java.util.List;

public class MoviesRecyclerViewAdapter extends RecyclerView.Adapter<MoviesRecyclerViewAdapter.ViewHolder> {

    private static int selectedPosition = RecyclerView.NO_POSITION;
    private final List<MovieWithGenres> moviesWithGenres;
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
        this.movieSelectedListener = movieSelectedListener;
        this.fragmentActivity = fragmentActivity;

    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
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

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnCreateContextMenuListener,
            View.OnLongClickListener{
        private MovieWithGenres movieWithGenres;
        private FragmentMovieBinding binding;

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
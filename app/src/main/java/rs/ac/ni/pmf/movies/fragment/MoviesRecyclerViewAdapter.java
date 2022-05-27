package rs.ac.ni.pmf.movies.fragment;

import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import rs.ac.ni.pmf.movies.databinding.FragmentMovieBinding;
import rs.ac.ni.pmf.movies.model.Movie;

import java.util.List;

public class MoviesRecyclerViewAdapter extends RecyclerView.Adapter<MoviesRecyclerViewAdapter.ViewHolder> {

    private static int selectedPosition = RecyclerView.NO_POSITION;
    private final List<Movie> movies;
    private final MovieSelectedListener movieSelectedListener;

    public interface MovieSelectedListener{
        void onMovieSelected(Movie movie);
    }

    public MoviesRecyclerViewAdapter(List<Movie> movies, MovieSelectedListener movieSelectedListener) {
        this.movies = movies;
        this.movieSelectedListener = movieSelectedListener;

    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(FragmentMovieBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false));
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.setSelection(position);
    }

    @Override
    public int getItemCount() {
        return movies.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private Movie movie;
        private FragmentMovieBinding binding;

        public ViewHolder(FragmentMovieBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
            this.itemView.setOnClickListener(this);

        }

        public void setSelection(int position) {
            this.movie = movies.get(position);
            this.binding.setMovie(this.movie);
            this.itemView.setSelected(position == selectedPosition);
        }

        @Override
        public void onClick(View view) {
            notifyItemChanged(selectedPosition);
            selectedPosition = getBindingAdapterPosition();
            notifyItemChanged(selectedPosition);
            movieSelectedListener.onMovieSelected(movie);
        }
    }
}
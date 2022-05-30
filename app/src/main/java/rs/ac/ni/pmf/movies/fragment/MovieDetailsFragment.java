package rs.ac.ni.pmf.movies.fragment;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import rs.ac.ni.pmf.movies.databinding.FragmentMovieDetailsBinding;
import rs.ac.ni.pmf.movies.model.MovieWithActors;
import rs.ac.ni.pmf.movies.model.MovieWithGenres;
import rs.ac.ni.pmf.movies.model.MoviesViewModel;

public class MovieDetailsFragment extends Fragment {

    private MoviesViewModel moviesViewModel;
    private FragmentMovieDetailsBinding binding;
    private Context context;


    public static MovieDetailsFragment newInstance() {
        return new MovieDetailsFragment();
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        moviesViewModel = new ViewModelProvider(requireActivity()).get(MoviesViewModel.class);
        this.context = context;
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = FragmentMovieDetailsBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        moviesViewModel.getSelectedMovieWithGenres().observe(requireActivity(), movieWithGenres -> {
            binding.setMovieWithGenres(movieWithGenres);
            if (movieWithGenres != null) {
                binding.movieImage.setImageResource(context.getResources().getIdentifier(movieWithGenres.movie.getImage(),
                        "drawable", context.getPackageName()));
                moviesViewModel.getSelectedMovieWithActors().observe(requireActivity(), movie -> binding.setMovieWithActors(movie));
            }

        });

    }
}
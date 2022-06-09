package rs.ac.ni.pmf.movies.fragment;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.ViewModelProvider;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import rs.ac.ni.pmf.movies.databinding.FragmentMovieDetailsBinding;
import rs.ac.ni.pmf.movies.model.MoviesViewModel;

public class MovieDetailsFragment extends Fragment {

    private MoviesViewModel moviesViewModel;
    private FragmentMovieDetailsBinding binding;
    private Context context;
    private FragmentActivity fragmentActivity;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        moviesViewModel = new ViewModelProvider(requireActivity()).get(MoviesViewModel.class);
        this.context = context;
        fragmentActivity = requireActivity();
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
        moviesViewModel.getSelectedMovieWithGenres().observe(fragmentActivity, movieWithGenres -> {
            binding.setMovieWithGenres(movieWithGenres);
            if (movieWithGenres != null) {
                if (movieWithGenres.movie.getImage() != null) {
                    Bitmap bmp = BitmapFactory.decodeByteArray(movieWithGenres.movie.getImage(), 0,
                            movieWithGenres.movie.getImage().length);
                    binding.movieImage.setImageBitmap(Bitmap.createScaledBitmap(bmp,
                            200, 250, false));
                } else {
                    binding.movieImage.setImageResource(context.getResources().getIdentifier("no_image",
                            "drawable", context.getPackageName()));
                }
                moviesViewModel.getSelectedMovieWithActors().observe(fragmentActivity,
                        movie -> binding.setMovieWithActors(movie));
            }
        });
    }
}
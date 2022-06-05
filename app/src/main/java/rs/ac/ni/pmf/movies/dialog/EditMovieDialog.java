package rs.ac.ni.pmf.movies.dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;

import java.util.Arrays;
import java.util.List;

import rs.ac.ni.pmf.movies.R;
import rs.ac.ni.pmf.movies.databinding.EditMovieDialogBinding;
import rs.ac.ni.pmf.movies.model.MovieWithActors;
import rs.ac.ni.pmf.movies.model.MovieWithGenres;

public class EditMovieDialog extends DialogFragment {

    public interface EditMovieDialogListener {
        void onDone(MovieWithGenres movieWithGenres, MovieWithActors movieWithActors,
                    List<String> genres, List<String> actors);
    }

    private EditMovieDialog.EditMovieDialogListener listener;
    private MovieWithGenres movieWithGenres;
    private MovieWithActors movieWithActors;
    private EditMovieDialogBinding binding;

    public EditMovieDialog(){
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelable("MOVIE_WITH_GENRES", movieWithGenres);
        outState.putParcelable("MOVIE_WITH_ACTORS", movieWithActors);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (savedInstanceState != null){
            movieWithGenres = savedInstanceState.getParcelable("MOVIE_WITH_GENRES");
            movieWithActors = savedInstanceState.getParcelable("MOVIE_WITH_ACTORS");
        }
    }

    public EditMovieDialog(MovieWithGenres movieWithGenres, MovieWithActors movieWithActors){
        this.movieWithGenres = movieWithGenres;
        this.movieWithActors = movieWithActors;
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        listener = (EditMovieDialog.EditMovieDialogListener) context;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        binding = EditMovieDialogBinding.inflate(LayoutInflater.from(requireContext()));
        binding.setMovieWithGenres(movieWithGenres);
        binding.setMovieWithActors(movieWithActors);
        final AlertDialog.Builder builder = new AlertDialog.Builder(requireActivity());
        final AlertDialog alertDialog = builder.setView(binding.getRoot())
                .setTitle(R.string.edit_movie)
                .setPositiveButton(R.string.done, (dialogInterface, i) -> {
                    final View layout = getLayoutInflater().inflate(R.layout.edit_movie_dialog, null);
                    EditText editTextActors = layout.findViewById(R.id.edit_actors);
                    EditText editTextGenres = layout.findViewById(R.id.edit_genres);
                    EditText editTextYear = layout.findViewById(R.id.edit_year);
                    if(movieWithGenres.movie.getYear() < 1900 ||
                            movieWithGenres.movie.getDirector().equals("") ||
                            movieWithGenres.movie.getDescription().equals("")){
                        Toast.makeText(requireActivity(), "All fields must be filled", Toast.LENGTH_SHORT).show();
                    } else {
                        listener.onDone(movieWithGenres, movieWithActors,
                                Arrays.asList(editTextGenres.getText().toString().split("\\s*,\\s*")),
                                Arrays.asList(editTextActors.getText().toString().split("\\s*,\\s*")));
                    }
                })
                .setNeutralButton(R.string.cancel,
                        (dialogInterface, i) -> {})
                .create();
        alertDialog.setCanceledOnTouchOutside(false);
        return alertDialog;
    }
}

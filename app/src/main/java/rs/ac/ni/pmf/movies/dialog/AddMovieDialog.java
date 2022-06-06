package rs.ac.ni.pmf.movies.dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;
import androidx.lifecycle.ViewModelProvider;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import rs.ac.ni.pmf.movies.R;
import rs.ac.ni.pmf.movies.fragment.MoviesListFragment;
import rs.ac.ni.pmf.movies.model.Actor;
import rs.ac.ni.pmf.movies.model.Genre;
import rs.ac.ni.pmf.movies.model.Movie;
import rs.ac.ni.pmf.movies.model.MoviesViewModel;

public class AddMovieDialog extends DialogFragment {

    public interface AddMovieDialogListener {
        void onAddMovie(Movie movie, List<String> genres, List<String> actors);
    }

    private AddMovieDialog.AddMovieDialogListener listener;

    public AddMovieDialog(){
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        listener = (AddMovieDialog.AddMovieDialogListener) context;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {

        final View layout = getLayoutInflater().inflate(R.layout.add_movie_dialog, null);
        final AlertDialog.Builder builder = new AlertDialog.Builder(requireActivity());
        final AlertDialog alertDialog = builder.setView(layout)
                .setTitle(R.string.add_movie)
                .setPositiveButton(R.string.add_movie, (dialogInterface, i) -> {

                    EditText editTextActors = layout.findViewById(R.id.edit_add_actors);
                    EditText editTextGenres = layout.findViewById(R.id.edit_add_genres);
                    EditText editTextYear = layout.findViewById(R.id.edit_add_year);
                    EditText editTextTitle = layout.findViewById(R.id.edit_add_name);
                    EditText editTextDirector = layout.findViewById(R.id.edit_add_director);
                    EditText editTextDescription = layout.findViewById(R.id.edit_add_description);
                    String actor = editTextGenres.getText().toString();
                    String genre = editTextActors.getText().toString();
                    String year = editTextYear.getText().toString();
                    String title = editTextTitle.getText().toString();
                    String director = editTextDirector.getText().toString();
                    String description = editTextDescription.getText().toString();

                    if(actor.chars().noneMatch(Character::isLetter) || genre.chars().noneMatch(Character::isLetter) ||
                           title.chars().noneMatch(Character::isLetter) || director.chars().noneMatch(Character::isLetter)
                            || year.equals("") || description.equals("")){
                        Toast.makeText(requireActivity(), "All fields must be filled correctly", Toast.LENGTH_SHORT).show();
                    } else {
                        List<String> actors = Arrays.asList(actor.split("\\s*,\\s*"));
                        actors.removeIf(String::isEmpty);
                        List<String> genres = Arrays.asList(genre.split("\\s*,\\s*"));
                        genres.removeIf(String::isEmpty);
                        listener.onAddMovie(new Movie(title, "no_image", director, Long.parseLong(year), description),
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

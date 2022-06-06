package rs.ac.ni.pmf.movies.dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;

import java.util.Arrays;
import java.util.List;

import rs.ac.ni.pmf.movies.R;
import rs.ac.ni.pmf.movies.databinding.EditMovieDialogBinding;
import rs.ac.ni.pmf.movies.fragment.MoviesListFragment;
import rs.ac.ni.pmf.movies.model.Movie;
import rs.ac.ni.pmf.movies.model.MovieWithActors;
import rs.ac.ni.pmf.movies.model.MovieWithGenres;

public class EditMovieDialog extends DialogFragment {

    public interface EditMovieDialogListener {
        void onDone(Movie movie, List<String> genres, List<String> actors);
    }

    private EditMovieDialog.EditMovieDialogListener listener;
    private MovieWithGenres movieWithGenres;
    private MovieWithActors movieWithActors;

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
        final View layout = getLayoutInflater().inflate(R.layout.edit_movie_dialog, null);
        TextView textViewTitle = layout.findViewById(R.id.text_name);
        EditText editTextActors = layout.findViewById(R.id.edit_actors);
        EditText editTextGenres = layout.findViewById(R.id.edit_genres);
        EditText editTextDirector = layout.findViewById(R.id.edit_director);
        EditText editTextYear = layout.findViewById(R.id.edit_year);
        EditText editTextDescription = layout.findViewById(R.id.edit_description);
        textViewTitle.setText(String.format(getResources().getString(R.string.title), movieWithGenres.movie.getTitle()));
        editTextActors.setText(movieWithActors.toString());
        editTextGenres.setText(movieWithGenres.toString());
        editTextDirector.setText(movieWithGenres.movie.getDirector());
        editTextYear.setText(String.valueOf(movieWithGenres.movie.getYear()));
        editTextDescription.setText(movieWithGenres.movie.getDescription());
        final AlertDialog.Builder builder = new AlertDialog.Builder(requireActivity());
        final AlertDialog alertDialog = builder.setView(layout)
                .setTitle(R.string.edit_movie)
                .setPositiveButton(R.string.done, (dialogInterface, i) -> {

                    String actor = editTextGenres.getText().toString();
                    String genre = editTextActors.getText().toString();
                    String director = editTextDirector.getText().toString();
                    String year = editTextYear.getText().toString();
                    String description = editTextDescription.getText().toString();

                    if(actor.chars().noneMatch(Character::isLetter) || genre.chars().noneMatch(Character::isLetter)
                            || director.chars().noneMatch(Character::isLetter) || year.equals("") || description.equals("") ){
                        Toast.makeText(requireActivity(), "All fields must be filled correctly", Toast.LENGTH_SHORT).show();
                    } else {
                        List<String> actors = Arrays.asList(actor.split("\\s*,\\s*"));
                        actors.removeIf(String::isEmpty);
                        List<String> genres = Arrays.asList(genre.split("\\s*,\\s*"));
                        genres.removeIf(String::isEmpty);
                        Movie movie = new Movie(movieWithGenres.movie.getTitle(), "no_image", director,
                                Long.parseLong(year), description);
                        movie.setMovie_id(movieWithGenres.movie.getMovie_id());
                        listener.onDone(movie, actors, genres);
                        MoviesListFragment.done = true;
                    }
                })
                .setNeutralButton(R.string.cancel,
                        (dialogInterface, i) -> {})
                .create();
        alertDialog.setCanceledOnTouchOutside(false);
        return alertDialog;
    }
}

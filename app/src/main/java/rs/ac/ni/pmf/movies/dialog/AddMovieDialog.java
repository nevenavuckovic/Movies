package rs.ac.ni.pmf.movies.dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;

import java.util.ArrayList;
import java.util.List;

import rs.ac.ni.pmf.movies.R;
import rs.ac.ni.pmf.movies.model.Actor;
import rs.ac.ni.pmf.movies.model.Genre;
import rs.ac.ni.pmf.movies.model.Movie;

public class AddMovieDialog extends DialogFragment {

    public interface AddMovieDialogListener {
        void onAddMovie(Movie movie, List<Genre> genres, List<Actor> actors);
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
                    Movie movie = null;
                    List<Genre> genres = new ArrayList<>();
                    List<Actor> actors = new ArrayList<>();
                    listener.onAddMovie(movie, genres, actors);
                })
                .setNeutralButton(R.string.cancel,
                        (dialogInterface, i) -> {})
                .create();
        alertDialog.setCanceledOnTouchOutside(false);
        return alertDialog;
    }
}

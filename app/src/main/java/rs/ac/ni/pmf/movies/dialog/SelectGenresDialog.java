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
import rs.ac.ni.pmf.movies.model.Genre;

public class SelectGenresDialog extends DialogFragment {

    public interface SelectGenresDialogListener {
        void onSearch(List<Genre> checkedGenres);
        void onCancel();
    }

    private SelectGenresDialogListener listener;
    private List<Genre> listOfGenres;
    private List<String> list;
    private boolean[] checkedGenres;

    public SelectGenresDialog(List<Genre> listOfGenres){
        this.listOfGenres = listOfGenres;
        this.list = new ArrayList<>();
        for (Genre genre: listOfGenres){
            this.list.add(genre.getGenre());
        }
        checkedGenres = new boolean[list.size()];
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        listener = (SelectGenresDialogListener) context;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        final View layout = getLayoutInflater().inflate(R.layout.select_genres_dialog, null);
        final AlertDialog.Builder builder = new AlertDialog.Builder(requireActivity());
        final AlertDialog alertDialog = builder.setView(layout)
                .setTitle(R.string.select_genres)
                .setMultiChoiceItems(list.toArray(new String[0]), checkedGenres,
                        (dialogInterface, i, b) -> checkedGenres[i] = b)
                .setPositiveButton(R.string.search, (dialogInterface, i) -> {
                        List<Genre> genres = new ArrayList<>();
                        for (int k = 0; k < checkedGenres.length;  k++){
                            if (checkedGenres[k]) {
                                genres.add(listOfGenres.get(k));
                            }
                        }
                            listener.onSearch(genres);
                    })
                .setNeutralButton(R.string.cancel,
                        (dialogInterface, i) -> listener.onCancel())
                .create();
        alertDialog.setCanceledOnTouchOutside(false);
        return alertDialog;
    }
}

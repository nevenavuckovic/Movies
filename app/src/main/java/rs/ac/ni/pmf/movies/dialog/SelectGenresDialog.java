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
    }

    private SelectGenresDialogListener listener;
    private ArrayList<Genre> listOfGenres;
    private ArrayList<String> list;
    private boolean[] checkedGenres;

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelableArrayList("GENRES", listOfGenres);
        outState.putStringArrayList("GENRES_NAMES", list);
        outState.putBooleanArray("CHECKED", checkedGenres);

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (savedInstanceState != null){
            listOfGenres = savedInstanceState.getParcelableArrayList("GENRES");
            list = savedInstanceState.getStringArrayList("GENRES_NAMES");
            checkedGenres = savedInstanceState.getBooleanArray("CHECKED");

        }
    }

    public SelectGenresDialog(){
    }

    public SelectGenresDialog(List<Genre> listOfGenres, List<Genre> checked){
        this.listOfGenres = new ArrayList<>(listOfGenres);
        this.list = new ArrayList<>();
        checkedGenres = new boolean[listOfGenres.size()];
        int i = 0;
        for (Genre genre: listOfGenres){
            this.list.add(genre.getGenre());
            for (Genre g: checked){
                if (genre.equals(g)) {
                    checkedGenres[i] = true;
                    break;
                }
            }
            i++;
        }
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
                        (dialogInterface, i) -> {})
                .create();
        alertDialog.setCanceledOnTouchOutside(false);
        return alertDialog;
    }
}

package rs.ac.ni.pmf.movies.dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;

import java.util.Arrays;
import java.util.List;

import rs.ac.ni.pmf.movies.R;

public class SortDialog extends DialogFragment {

    public interface SortDialogListener {
        void onSort(String checkedSort);
    }

    private SortDialog.SortDialogListener listener;
    private final List<String> sorts;
    private int checkedSort;

    public SortDialog(){
        sorts = Arrays.asList("-", "A-Z", "Z-A");
    }

    public SortDialog(String checked){
        sorts = Arrays.asList("-", "A-Z", "Z-A");
        checkedSort = sorts.indexOf(checked);
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt("CHECKED_SORT", checkedSort);

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (savedInstanceState != null){
            checkedSort = savedInstanceState.getInt("CHECKED_SORT");
        }
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        listener = (SortDialog.SortDialogListener) context;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        final AlertDialog.Builder builder = new AlertDialog.Builder(requireActivity());
        final AlertDialog alertDialog = builder
                .setTitle(R.string.sort)
                .setSingleChoiceItems(sorts.toArray(new String[0]), checkedSort,
                        (dialogInterface, i) -> checkedSort = i)
                .setPositiveButton(R.string.sort,
                        (dialogInterface, i) -> listener.onSort(sorts.get(checkedSort)))
                .setNeutralButton(R.string.cancel,
                        (dialogInterface, i) -> {})
                .create();
        alertDialog.setCanceledOnTouchOutside(false);
        return alertDialog;
    }
}

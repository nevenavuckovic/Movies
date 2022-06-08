package rs.ac.ni.pmf.movies.dialog;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import rs.ac.ni.pmf.movies.R;
import rs.ac.ni.pmf.movies.databinding.EditMovieDialogBinding;
import rs.ac.ni.pmf.movies.fragment.MoviesListFragment;
import rs.ac.ni.pmf.movies.model.Movie;

public class EditMovieDialog extends DialogFragment {

    public interface EditMovieDialogListener {
        void onDone(Movie movie, List<String> genres, List<String> actors, boolean resultOk);
        void onCancel();
    }

    private EditMovieDialog.EditMovieDialogListener listener;
    private ActivityResultLauncher<Intent> activityResultLauncher;
    private Movie movie;
    private List<String> genres;
    private List<String> actors;
    private EditMovieDialogBinding binding;
    private byte[] img;

    public EditMovieDialog(){
    }


    public EditMovieDialog(Movie movie, List<String> genres, List<String> actors){
        this.movie = movie;
        this.genres = genres;
        this.actors = actors;
        this.img = movie.getImage();
    }


    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        img = movie.getImage();
        outState.putByteArray("IMG", img);
        movie.setImage(null);
        outState.putParcelable("MOVIE", movie);
        outState.putStringArrayList("GENRES", new ArrayList<>(genres));
        outState.putStringArrayList("ACTORS", new ArrayList<>(actors));
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (savedInstanceState != null){
            movie = savedInstanceState.getParcelable("MOVIE");
            img = savedInstanceState.getByteArray("IMG");
            movie.setImage(img);
            genres = savedInstanceState.getStringArrayList("GENRES");
            actors = savedInstanceState.getStringArrayList("ACTORS");
        }
    }


    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        listener = (EditMovieDialog.EditMovieDialogListener) context;

        activityResultLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    if (result.getResultCode() == Activity.RESULT_OK && result.getData() != null) {
                        try {
                            final Uri imageUri = result.getData().getData();
                            final InputStream imageStream = context.getContentResolver().openInputStream(imageUri);
                            final String mimeType = context.getContentResolver().getType(imageUri);
                            final Bitmap selectedImage = BitmapFactory.decodeStream(imageStream);
                            ByteArrayOutputStream stream = new ByteArrayOutputStream();
                            if(mimeType.endsWith("png")) {
                                selectedImage.compress(Bitmap.CompressFormat.PNG, 100, stream);
                            } else if(mimeType.endsWith("jpg") || mimeType.endsWith("jpeg")){
                                selectedImage.compress(Bitmap.CompressFormat.JPEG, 100, stream);
                            } else {
                                Toast.makeText(requireActivity(), "Supported formats are PNG and JPEG", Toast.LENGTH_LONG).show();
                            }
                            byte[] byteArray = stream.toByteArray();
                            movie.setImage(byteArray);
                            binding.movieImage.setImageBitmap(Bitmap.createScaledBitmap(selectedImage, 200, 250, false));
                        } catch (FileNotFoundException e) {
                            e.printStackTrace();
                        }
                    }
                }
        );
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        this.setCancelable(false);
        binding = EditMovieDialogBinding.inflate(getLayoutInflater());
        binding.setMovie(movie);

        binding.editButton.setOnClickListener(view -> {
            Intent photoPickerIntent = new Intent(Intent.ACTION_PICK);
            photoPickerIntent.setType("image/*");
            activityResultLauncher.launch(photoPickerIntent);
        });

        if (movie.getImage() != null) {
            Bitmap bmp = BitmapFactory.decodeByteArray(movie.getImage(), 0,
                    movie.getImage().length);
            binding.movieImage.setImageBitmap(Bitmap.createScaledBitmap(bmp, 200, 250, false));
        }

        if (genres != null)
            binding.editGenres.setText(TextUtils.join(", ", genres));
        if (actors != null)
            binding.editActors.setText(TextUtils.join(", ", actors));


        final AlertDialog.Builder builder = new AlertDialog.Builder(requireActivity());
        final AlertDialog alertDialog = builder.setView(binding.getRoot())
                .setTitle(R.string.edit_movie)
                .setPositiveButton(R.string.done, (dialogInterface, i) -> {

                    actors = Arrays.asList(binding.editActors.getText().toString().split("\\s*,\\s*"));
                    genres = Arrays.asList(binding.editGenres.getText().toString().split("\\s*,\\s*"));

                    if(binding.editActors.getText().toString().chars().noneMatch(Character::isLetter) ||
                            binding.editGenres.getText().toString().chars().noneMatch(Character::isLetter) ||
                            movie.getDirector().chars().noneMatch(Character::isLetter) ||
                            binding.editYear.getText().toString().equals("") || movie.getDescription().equals("") ){
                        Toast.makeText(requireActivity(), "All fields must be filled correctly", Toast.LENGTH_LONG).show();
                        listener.onDone(movie, genres, actors, false);
                    } else {
                        actors.removeIf(String::isEmpty);
                        genres.removeIf(String::isEmpty);
                        movie.setYear(Long.parseLong(binding.editYear.getText().toString()));
                        listener.onDone(movie, genres, actors, true);
                        MoviesListFragment.menu = false;
                    }
                })
                .setNeutralButton(R.string.cancel,
                        (dialogInterface, i) -> {
                            listener.onCancel();
                            MoviesListFragment.menu = false;
                        })
                .create();
        alertDialog.setCanceledOnTouchOutside(false);
        return alertDialog;
    }


}

package rs.ac.ni.pmf.movies.dialog;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;
import androidx.lifecycle.ViewModelProvider;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
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
    private ActivityResultLauncher<Intent> activityResultLauncher;
    private Movie movie;

    public AddMovieDialog(){
        movie = new Movie("", null, "", 2000, "");
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        listener = (AddMovieDialog.AddMovieDialogListener) context;

        final View layout = getLayoutInflater().inflate(R.layout.add_movie_dialog, null);
        activityResultLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    if (result.getResultCode() == Activity.RESULT_OK && result.getData() != null) {
                        try {
                            final Uri imageUri = result.getData().getData();
                            final InputStream imageStream = requireContext().getContentResolver().openInputStream(imageUri);
                            final Bitmap selectedImage = BitmapFactory.decodeStream(imageStream);
                            ByteArrayOutputStream stream = new ByteArrayOutputStream();
                        //    if(imageUri.toString().endsWith(".png")) {
                         //       selectedImage.compress(Bitmap.CompressFormat.PNG, 100, stream);
                        //    } else if(imageUri.toString().endsWith(".jpg")){
                                selectedImage.compress(Bitmap.CompressFormat.JPEG, 100, stream);
                        //    } else {
                        //        Toast.makeText(requireActivity(), "Supported formats are PNG and JPEG", Toast.LENGTH_LONG).show();
                         //   }
                            byte[] byteArray = stream.toByteArray();
                            movie.setImage(byteArray);
                            ImageView imageView = layout.findViewById(R.id.movie_add_image); // not working
                            Bitmap bmp = BitmapFactory.decodeByteArray(byteArray, 0, byteArray.length);
                            imageView.setImageBitmap(Bitmap.createScaledBitmap(bmp, 200, 250, false));
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
        final View layout = getLayoutInflater().inflate(R.layout.add_movie_dialog, null);
        Button button = layout.findViewById(R.id.add_button);
        button.setOnClickListener(view -> {
            Intent photoPickerIntent = new Intent(Intent.ACTION_PICK);
            photoPickerIntent.setType("image/*");
            activityResultLauncher.launch(photoPickerIntent);
        });

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
                        Toast.makeText(requireActivity(), "All fields must be filled correctly",
                                Toast.LENGTH_SHORT).show();
                    } else {
                        List<String> actors = Arrays.asList(actor.split("\\s*,\\s*"));
                        actors.removeIf(String::isEmpty);
                        List<String> genres = Arrays.asList(genre.split("\\s*,\\s*"));
                        genres.removeIf(String::isEmpty);
                        movie.setTitle(title);
                        movie.setDirector(director);
                        movie.setYear(Long.parseLong(year));
                        movie.setDescription(description);
                        listener.onAddMovie(movie, actors, genres);
                    }
                })
                .setNeutralButton(R.string.cancel,
                        (dialogInterface, i) -> {})
                .create();
        alertDialog.setCanceledOnTouchOutside(false);
        return alertDialog;
    }
}

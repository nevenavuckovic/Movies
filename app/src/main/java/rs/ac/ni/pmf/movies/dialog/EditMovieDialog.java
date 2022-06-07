package rs.ac.ni.pmf.movies.dialog;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
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
        void onCancel();
    }

    private EditMovieDialog.EditMovieDialogListener listener;
    private MovieWithGenres movieWithGenres;
    private MovieWithActors movieWithActors;
    private ActivityResultLauncher<Intent> activityResultLauncher;
    private View layout;
    private byte[] bytes;

    public EditMovieDialog(){
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        bytes = movieWithGenres.movie.getImage();
        movieWithGenres.movie.setImage(null);
        movieWithActors.movie.setImage(null);
        outState.putByteArray("img", bytes);
        outState.putParcelable("MOVIE_WITH_GENRES", movieWithGenres);
        outState.putParcelable("MOVIE_WITH_ACTORS", movieWithActors);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (savedInstanceState != null){
            bytes = savedInstanceState.getByteArray("img");
            movieWithGenres = savedInstanceState.getParcelable("MOVIE_WITH_GENRES");
            movieWithActors = savedInstanceState.getParcelable("MOVIE_WITH_ACTORS");
            movieWithGenres.movie.setImage(bytes);
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
                            movieWithGenres.movie.setImage(byteArray);
                            ImageView imageView = layout.findViewById(R.id.movie_image);
                            imageView.setImageBitmap(Bitmap.createScaledBitmap(selectedImage, 200, 250, false));
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
        layout = getLayoutInflater().inflate(R.layout.edit_movie_dialog, null);
        Button button = layout.findViewById(R.id.edit_button);
        button.setOnClickListener(view -> {
            Intent photoPickerIntent = new Intent(Intent.ACTION_PICK);
            photoPickerIntent.setType("image/*");
            activityResultLauncher.launch(photoPickerIntent);
        });

        ImageView imageView = layout.findViewById(R.id.movie_image);
        if (movieWithGenres.movie.getImage() != null) {
            Bitmap bmp = BitmapFactory.decodeByteArray(movieWithGenres.movie.getImage(), 0,
                    movieWithGenres.movie.getImage().length);
            imageView.setImageBitmap(Bitmap.createScaledBitmap(bmp, 200, 250, false));
        }

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
                        Movie movie = new Movie(movieWithGenres.movie.getTitle(), movieWithGenres.movie.getImage(),
                                director, Long.parseLong(year), description);
                        movie.setMovie_id(movieWithGenres.movie.getMovie_id());
                        listener.onDone(movie, actors, genres);
                        MoviesListFragment.done = true;
                    }
                })
                .setNeutralButton(R.string.cancel,
                        (dialogInterface, i) -> listener.onCancel())
                .create();
        alertDialog.setCanceledOnTouchOutside(false);
        return alertDialog;
    }
}

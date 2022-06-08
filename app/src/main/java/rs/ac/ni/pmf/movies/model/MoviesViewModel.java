package rs.ac.ni.pmf.movies.model;

import android.app.Application;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import java.io.ByteArrayOutputStream;
import java.util.List;

import rs.ac.ni.pmf.movies.R;
import rs.ac.ni.pmf.movies.repository.MoviesRepository;

public class MoviesViewModel extends AndroidViewModel {
    private final MutableLiveData<MovieWithGenres> selectedMovieWithGenres = new MutableLiveData<>();
    private LiveData<MovieWithActors> selectedMovieWithActors = new MutableLiveData<>();
    private List<MovieWithActors> movieWithActors;

    private final MoviesRepository moviesRepository;


    public MoviesViewModel(@NonNull Application application) {
        super(application);
        moviesRepository = new MoviesRepository(application);


        if (moviesRepository.getMovieCount() == 0) {
            long movie1 = moviesRepository.addMovie(new Movie
                    ("The Lord of the Rings: The Return of the King", getImageRes(R.drawable.tlotr_the_return_of_the_king),
                            "Peter Jackson", 2003, "Gandalf and Aragorn lead the World of Men against " +
                            "Sauron's army to draw his gaze from Frodo and Sam as they approach Mount Doom with the One Ring."));
            long movie2 = moviesRepository.addMovie(new Movie
                    ("Inception", getImageRes(R.drawable.inception), "Christopher Nolan", 2010,
                            "A thief who steals corporate secrets through the use of dream-sharing technology" +
                                    " is given the inverse task of planting an idea into the mind of a C.E.O., but his " +
                                    "tragic past may doom the project and his team to disaster."));
            long movie3 = moviesRepository.addMovie(new Movie
                    ("Coco", getImageRes(R.drawable.coco), "Lee Unkrich", 2017,
                            "Aspiring musician Miguel, confronted with his family's ancestral ban on music, " +
                                    "enters the Land of the Dead to find his great-great-grandfather, a legendary singer."));
            long movie4 = moviesRepository.addMovie(new Movie
                    ("Iron Man", getImageRes(R.drawable.iron_man), "Jon Favreau", 2008,
                            "After being held captive in an Afghan cave, billionaire engineer " +
                                    "Tony Stark creates a unique weaponized suit of armor to fight evil."));
            long movie5 = moviesRepository.addMovie(new Movie
                    ("The Prestige", getImageRes(R.drawable.the_prestige), "Christopher Nolan", 2006,
                            "After a tragic accident, two stage magicians in 1890s London engage in a battle to " +
                                    "create the ultimate illusion while sacrificing everything they have to outwit each other."));

            long genre1 = moviesRepository.addGenre(new Genre("Action"));
            long genre2 = moviesRepository.addGenre(new Genre("Adventure"));
            long genre3 = moviesRepository.addGenre(new Genre("Sci-fi"));
            long genre4 = moviesRepository.addGenre(new Genre("Drama"));
            long genre5 = moviesRepository.addGenre(new Genre("Animation"));
            long genre6 = moviesRepository.addGenre(new Genre("Family"));
            long genre7 = moviesRepository.addGenre(new Genre("Mystery"));

            moviesRepository.addMovieGenre(new MovieGenreCrossRef(movie1, genre1));
            moviesRepository.addMovieGenre(new MovieGenreCrossRef(movie1, genre2));
            moviesRepository.addMovieGenre(new MovieGenreCrossRef(movie1, genre3));
            moviesRepository.addMovieGenre(new MovieGenreCrossRef(movie2, genre1));
            moviesRepository.addMovieGenre(new MovieGenreCrossRef(movie2, genre2));
            moviesRepository.addMovieGenre(new MovieGenreCrossRef(movie2, genre4));
            moviesRepository.addMovieGenre(new MovieGenreCrossRef(movie3, genre5));
            moviesRepository.addMovieGenre(new MovieGenreCrossRef(movie3, genre6));
            moviesRepository.addMovieGenre(new MovieGenreCrossRef(movie4, genre1));
            moviesRepository.addMovieGenre(new MovieGenreCrossRef(movie4, genre2));
            moviesRepository.addMovieGenre(new MovieGenreCrossRef(movie4, genre3));
            moviesRepository.addMovieGenre(new MovieGenreCrossRef(movie5, genre4));
            moviesRepository.addMovieGenre(new MovieGenreCrossRef(movie5, genre7));
            moviesRepository.addMovieGenre(new MovieGenreCrossRef(movie5, genre3));

            long actor1 = moviesRepository.addActor(new Actor("Elijah Wood"));
            long actor2 = moviesRepository.addActor(new Actor("Viggo Mortensen"));
            long actor3 = moviesRepository.addActor(new Actor("Ian McKellen"));
            long actor4 = moviesRepository.addActor(new Actor("Leonardo DiCaprio"));
            long actor5 = moviesRepository.addActor(new Actor("Ken Watanabe"));
            long actor6 = moviesRepository.addActor(new Actor("Anthony Gonzalez"));
            long actor7 = moviesRepository.addActor(new Actor("Robert Downey Jr"));
            long actor8 = moviesRepository.addActor(new Actor("Gwyneth Paltrow"));
            long actor9 = moviesRepository.addActor(new Actor("Hugh Jackman"));
            long actor10 = moviesRepository.addActor(new Actor("Christian Bale"));

            moviesRepository.addMovieActor(new MovieActorCrossRef(movie1, actor1));
            moviesRepository.addMovieActor(new MovieActorCrossRef(movie1, actor2));
            moviesRepository.addMovieActor(new MovieActorCrossRef(movie1, actor3));
            moviesRepository.addMovieActor(new MovieActorCrossRef(movie2, actor4));
            moviesRepository.addMovieActor(new MovieActorCrossRef(movie2, actor5));
            moviesRepository.addMovieActor(new MovieActorCrossRef(movie3, actor6));
            moviesRepository.addMovieActor(new MovieActorCrossRef(movie4, actor7));
            moviesRepository.addMovieActor(new MovieActorCrossRef(movie4, actor8));
            moviesRepository.addMovieActor(new MovieActorCrossRef(movie5, actor9));
            moviesRepository.addMovieActor(new MovieActorCrossRef(movie5, actor10));
        }

    }

    public byte[] getImageRes(int id){
        Bitmap bitmap = BitmapFactory.decodeResource(getApplication().getResources(), id);
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
        return stream.toByteArray();
    }


    public LiveData<List<MovieWithGenres>> getMovies() {
        return  moviesRepository.getMoviesWithGenres();
    }

    public MutableLiveData<MovieWithGenres> getSelectedMovieWithGenres() {
        return selectedMovieWithGenres;
    }

    public LiveData<MovieWithActors> getSelectedMovieWithActors() {
        if (selectedMovieWithGenres.getValue() != null) {
            selectedMovieWithActors = moviesRepository.getMoviesWithActors(selectedMovieWithGenres.getValue().movie.getMovie_id());
        }
        return selectedMovieWithActors;
    }


    public void setSelectedMovie(MovieWithGenres movieWithGenres) {
        selectedMovieWithGenres.setValue(movieWithGenres);
    }

    public void deleteMovie(long id) {
        moviesRepository.deleteMovie(id);
    }

    public LiveData<List<Genre>> getGenres() {
        return moviesRepository.getGenres();
    }

    public LiveData<MovieWithActors> getMovie(long id) {
        return moviesRepository.getMovieWithActors(id);
    }

    public void updateMovie(Movie movie, List<String> genres, List<String> actors){
        long movie_id = movie.getMovie_id();
        moviesRepository.updateMovie(movie);
        moviesRepository.deleteMovieGenre(movie_id);
        for (String s: genres){
            long genre_id = moviesRepository.getGenreId(s);
            if(genre_id == 0L){
                genre_id = moviesRepository.addGenre(new Genre(s));
            }
            moviesRepository.addMovieGenre(new MovieGenreCrossRef(movie_id, genre_id));
        }
        moviesRepository.deleteMovieActor(movie_id);
        for(String s: actors){
            long actor_id = moviesRepository.getActorId(s);
            if (actor_id == 0L) {
                actor_id = moviesRepository.addActor(new Actor(s));
            }
            moviesRepository.addMovieActor(new MovieActorCrossRef(movie_id, actor_id));
        }
    }


    public void addMovie(Movie movie, List<String> genres, List<String> actors) {
        long movie_id = moviesRepository.addMovie(movie);
        for (String s: genres){
            long genre_id = moviesRepository.getGenreId(s);
            if (genre_id == 0L) {
                genre_id = moviesRepository.addGenre(new Genre(s));
            }
            moviesRepository.addMovieGenre(new MovieGenreCrossRef(movie_id, genre_id));
        }
        for (String s: actors){
            long actor_id = moviesRepository.getActorId(s);
            if (actor_id == 0L) {
                actor_id = moviesRepository.addActor(new Actor(s));
            }
            moviesRepository.addMovieActor(new MovieActorCrossRef(movie_id, actor_id));
        }
    }

    public long getMovieId(String title) {
        return moviesRepository.getMovieId(title);
    }
}

package rs.ac.ni.pmf.movies.model;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import java.util.List;

import rs.ac.ni.pmf.movies.repository.MoviesRepository;

public class MoviesViewModel extends AndroidViewModel {
    private final MutableLiveData<MovieWithGenres> selectedMovieWithGenres = new MutableLiveData<>();
    private LiveData<MovieWithActors> selectedMovieWithActors = new MutableLiveData<>();

    private final MoviesRepository moviesRepository;


    public MoviesViewModel(@NonNull Application application) {
        super(application);
        moviesRepository = new MoviesRepository(application);


        if (moviesRepository.getMovieCount() == 0) {
            long movie1 = moviesRepository.addMovie(new Movie
                    ("Iron Man", "iron_man", "nekko", 2008, "nessto"));
            long movie2 = moviesRepository.addMovie(new Movie
                    ("The Avengers", "the_avengers", "nio", 2012, "nit"));

            long genre1 = moviesRepository.addGenre(new Genre("Action"));
            long genre2 = moviesRepository.addGenre(new Genre("Adventure"));
            long genre3 = moviesRepository.addGenre(new Genre("Sci-fi"));

            moviesRepository.addMovieGenre(new MovieGenreCrossRef(movie1, genre1));
            moviesRepository.addMovieGenre(new MovieGenreCrossRef(movie1, genre2));
            moviesRepository.addMovieGenre(new MovieGenreCrossRef(movie2, genre1));
            moviesRepository.addMovieGenre(new MovieGenreCrossRef(movie2, genre3));

            long actor1 = moviesRepository.addActor(new Actor("Chris"));
            long actor2 = moviesRepository.addActor(new Actor("Robert"));

            moviesRepository.addMovieActor(new MovieActorCrossRef(movie1, actor1));
            moviesRepository.addMovieActor(new MovieActorCrossRef(movie1, actor2));
            moviesRepository.addMovieActor(new MovieActorCrossRef(movie2, actor2));
        }

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

    public void updateMovie(MovieWithGenres movieWithGenres, MovieWithActors movieWithActors,
                            List<String> genres, List<String> actors){
        moviesRepository.updateMovie(movieWithGenres.movie);
        for(String s: genres){
            boolean contains = false;
            for(Genre genre: movieWithGenres.genres){
                if(genre.getGenre().equals(s)){
                    contains = true;
                    break;
                }
            }
            if(!contains){
                long genre_id = moviesRepository.getGenreId(s);
                if(genre_id == 0L){
                    genre_id = moviesRepository.addGenre(new Genre(s));
                }
                moviesRepository.addMovieGenre(new MovieGenreCrossRef(movieWithGenres.movie.getMovie_id(), genre_id));
            }
        }
        for(String s: actors){
            boolean contains = false;
            for(Actor actor: movieWithActors.actors){
                if(actor.getActor().equals(s)){
                    contains = true;
                    break;
                }
            }
            if(!contains){
                long actor_id = moviesRepository.getActorId(s);
                if (actor_id == 0L) {
                    actor_id = moviesRepository.addActor(new Actor(s));
                }
                moviesRepository.addMovieActor(new MovieActorCrossRef(movieWithGenres.movie.getMovie_id(), actor_id));
            }
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

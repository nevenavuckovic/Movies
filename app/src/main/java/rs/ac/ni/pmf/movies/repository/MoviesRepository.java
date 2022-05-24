package rs.ac.ni.pmf.movies.repository;

import java.util.ArrayList;
import java.util.List;

import rs.ac.ni.pmf.movies.model.Movie;

public class MoviesRepository {

    public static MoviesRepository INSTANCE = new MoviesRepository();

    private List<Movie> movies = new ArrayList<>();

    public MoviesRepository(){
        List<String> actors = new ArrayList<>();
        actors.add("Robert Downey Jr");
        actors.add("Gwyneth Paltrow");
        actors.add("Terrence Howard");
        List<String> genres = new ArrayList<>();
        genres.add("Action");
        genres.add("Adventure");
        genres.add("Sci-Fi");
        movies.add(new Movie(1, "Iron man", "Jon Favreau", actors, 2008, genres,
                "After being held captive in an Afghan cave, billionaire engineer Tony Stark creates a unique weaponized suit of armor to fight evil"));
        movies.add(new Movie(2, "The Avengers", "Jon Favreau", actors, 2008, genres,
                "After being held captive in an Afghan cave, billionaire engineer Tony Stark creates a unique weaponized suit of armor to fight evil"));
        movies.add(new Movie(3, "Gifted", "Jon Favreau", actors, 2008, genres,
                "After being held captive in an Afghan cave, billionaire engineer Tony Stark creates a unique weaponized suit of armor to fight evil"));
        movies.add(new Movie(4, "The Lord of the Rings: The Return of the King", "Jon Favreau", actors, 2008, genres,
                "After being held captive in an Afghan cave, billionaire engineer Tony Stark creates a unique weaponized suit of armor to fight evil"));
        movies.add(new Movie(5, "Coco", "Jon Favreau", actors, 2008, genres,
                "After being held captive in an Afghan cave, billionaire engineer Tony Stark creates a unique weaponized suit of armor to fight evil"));

    }

    public List<Movie> getMovies(){
        return movies;
    }
}

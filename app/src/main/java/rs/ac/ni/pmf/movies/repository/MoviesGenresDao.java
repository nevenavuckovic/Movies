package rs.ac.ni.pmf.movies.repository;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Transaction;

import rs.ac.ni.pmf.movies.model.MovieGenreCrossRef;

@Dao
public interface MoviesGenresDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertMovieGenreCrossRef(MovieGenreCrossRef movieGenreCrossRef);

    @Transaction
    @Query("DELETE FROM movies_genres WHERE movie_id = :movie_id")
    void deleteMovieGenre(long movie_id);
}

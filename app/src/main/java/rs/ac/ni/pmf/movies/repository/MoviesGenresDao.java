package rs.ac.ni.pmf.movies.repository;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;

import rs.ac.ni.pmf.movies.model.MovieGenreCrossRef;

@Dao
public interface MoviesGenresDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertMovieGenreCrossRef(MovieGenreCrossRef movieGenreCrossRef);
}

package rs.ac.ni.pmf.movies.repository;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Transaction;
import androidx.room.Update;

import java.util.List;

import rs.ac.ni.pmf.movies.model.Genre;
import rs.ac.ni.pmf.movies.model.GenreWithMovies;


@Dao
public interface GenresDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    long insertGenre(Genre genre);

    @Query("SELECT * FROM genres")
    LiveData<List<Genre>> getAllGenres();

    @Query("SELECT genre_id FROM genres WHERE genre LIKE :s")
    long getGenre(String s);
}

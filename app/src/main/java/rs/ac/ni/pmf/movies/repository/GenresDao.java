package rs.ac.ni.pmf.movies.repository;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

import rs.ac.ni.pmf.movies.model.Genre;

@Dao
public interface GenresDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    long insertGenre(Genre genre);

    @Query("SELECT * FROM genres")
    LiveData<List<Genre>> getAllGenres();

    @Query("SELECT genre_id FROM genres WHERE genre LIKE :s")
    long getGenre(String s);
}

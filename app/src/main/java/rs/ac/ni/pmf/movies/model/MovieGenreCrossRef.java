package rs.ac.ni.pmf.movies.model;

import androidx.room.ColumnInfo;
import androidx.room.Entity;

@Entity(tableName = "movies_genres", primaryKeys = {"movie_id", "genre_id"})
public class MovieGenreCrossRef {
    @ColumnInfo(name = "movie_id")
    public long movie_id;
    @ColumnInfo(name = "genre_id")
    public long genre_id;

    public MovieGenreCrossRef(long movie_id, long genre_id) {
        this.movie_id = movie_id;
        this.genre_id = genre_id;
    }

    public long getMovie_id() {
        return movie_id;
    }

    public void setMovie_id(long movie_id) {
        this.movie_id = movie_id;
    }

    public long getGenre_id() {
        return genre_id;
    }

    public void setGenre_id(long genre_id) {
        this.genre_id = genre_id;
    }
}

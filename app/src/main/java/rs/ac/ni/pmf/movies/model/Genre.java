package rs.ac.ni.pmf.movies.model;


import androidx.databinding.BaseObservable;
import androidx.databinding.Bindable;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Index;
import androidx.room.PrimaryKey;

@Entity(tableName = "genres", indices = @Index(value = "genre", unique = true))
public class Genre extends BaseObservable {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "genre_id")
    private long genre_id;
    @ColumnInfo(name = "genre")
    private String genre;

    public Genre(long genre_id, String genre) {
        this.genre_id = genre_id;
        this.genre = genre;
    }

    @Bindable
    public long getGenre_id() {
        return genre_id;
    }

    @Bindable
    public String getGenre() {
        return genre;
    }

    public void setGenre_id(long genre_id) {
        this.genre_id = genre_id;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    @Override
    public boolean equals(Object o){
        Genre g = (Genre) o;
        if (getGenre().equals(g.getGenre()) && getGenre_id() == g.getGenre_id()){
            return true;
        }
        return false;
    }
}
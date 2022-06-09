package rs.ac.ni.pmf.movies.model;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.databinding.BaseObservable;
import androidx.databinding.Bindable;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Index;
import androidx.room.PrimaryKey;

@Entity(tableName = "genres", indices = @Index(value = "genre", unique = true))
public class Genre extends BaseObservable implements Parcelable {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "genre_id")
    private long genre_id;
    @ColumnInfo(name = "genre")
    private String genre;

    public Genre(String genre) {
        this.genre = genre;
    }

    protected Genre(Parcel in) {
        genre_id = in.readLong();
        genre = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(genre_id);
        dest.writeString(genre);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<Genre> CREATOR = new Creator<Genre>() {
        @Override
        public Genre createFromParcel(Parcel in) {
            return new Genre(in);
        }

        @Override
        public Genre[] newArray(int size) {
            return new Genre[size];
        }
    };

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
        if  (o instanceof Genre) {
            Genre g = (Genre) o;
            return getGenre().equals(g.getGenre()) && getGenre_id() == g.getGenre_id();
        }
        return false;
    }
}
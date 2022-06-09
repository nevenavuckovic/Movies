package rs.ac.ni.pmf.movies.model;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.databinding.BaseObservable;
import androidx.databinding.Bindable;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Index;
import androidx.room.PrimaryKey;

@Entity(tableName = "actors", indices = @Index(value = "actor", unique = true))
public class Actor extends BaseObservable implements Parcelable {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "actor_id")
    private long actor_id;
    @ColumnInfo(name = "actor")
    private String actor;

    public Actor(String actor) {
        this.actor = actor;
    }

    protected Actor(Parcel in) {
        actor_id = in.readLong();
        actor = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(actor_id);
        dest.writeString(actor);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<Actor> CREATOR = new Creator<Actor>() {
        @Override
        public Actor createFromParcel(Parcel in) {
            return new Actor(in);
        }

        @Override
        public Actor[] newArray(int size) {
            return new Actor[size];
        }
    };

    @Bindable
    public long getActor_id() {
        return actor_id;
    }

    @Bindable
    public String getActor() {
        return actor;
    }

    public void setActor_id(long actor_id) {
        this.actor_id = actor_id;
    }

    public void setActor(String actor) {
        this.actor = actor;
    }
}

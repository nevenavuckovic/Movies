package rs.ac.ni.pmf.movies.model;

import androidx.databinding.BaseObservable;
import androidx.databinding.Bindable;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Index;
import androidx.room.PrimaryKey;

@Entity(tableName = "actors", indices = @Index(value = "actor", unique = true))
public class Actor extends BaseObservable {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "actor_id")
    private long actor_id;
    @ColumnInfo(name = "actor")
    private String actor;

    public Actor(long actor_id, String actor) {
        this.actor_id = actor_id;
        this.actor = actor;
    }

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

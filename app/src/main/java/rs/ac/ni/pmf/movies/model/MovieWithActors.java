package rs.ac.ni.pmf.movies.model;

import android.os.Parcel;
import android.os.Parcelable;
import android.text.TextUtils;

import androidx.annotation.NonNull;
import androidx.databinding.BaseObservable;
import androidx.databinding.Bindable;
import androidx.room.Embedded;
import androidx.room.Junction;
import androidx.room.Relation;

import java.util.ArrayList;
import java.util.List;

public class MovieWithActors extends BaseObservable implements Parcelable {
    @Embedded
    @Bindable
    public Movie movie;
    @Relation(
            entity = Actor.class,
            parentColumn = "movie_id",
            entityColumn = "actor_id",
            associateBy = @Junction(value = MovieActorCrossRef.class,
                    parentColumn = "movie_id",
                    entityColumn = "actor_id")
    )

    @Bindable
    public List<Actor> actors;

    public MovieWithActors(Movie movie, List<Actor> actors) {
        this.movie = movie;
        this.actors = actors;
    }

    protected MovieWithActors(Parcel in) {
        movie = in.readParcelable(Movie.class.getClassLoader());
        actors = in.createTypedArrayList(Actor.CREATOR);
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeParcelable(movie, flags);
        dest.writeTypedList(actors);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<MovieWithActors> CREATOR = new Creator<MovieWithActors>() {
        @Override
        public MovieWithActors createFromParcel(Parcel in) {
            return new MovieWithActors(in);
        }

        @Override
        public MovieWithActors[] newArray(int size) {
            return new MovieWithActors[size];
        }
    };

    @NonNull
    @Override
    public String toString() {
        List<String> actorNames = new ArrayList<>();
        for (Actor actor: actors){
            actorNames.add(actor.getActor());
        }
        return TextUtils.join(", ", actorNames);
    }
}

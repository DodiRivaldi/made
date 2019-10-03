package marchtech.app.movieconsume.api.model;

import android.os.Parcel;
import android.os.Parcelable;

import org.json.JSONObject;

public class Movie implements Parcelable {
    private String id;
    private String voteAverage;
    private String title;
    private String popularity;
    private String poster_path;
    private String overview;
    private String release_date;

    protected Movie(Parcel in) {
        id = in.readString();
        voteAverage = in.readString();
        title = in.readString();
        popularity = in.readString();
        poster_path = in.readString();
        overview = in.readString();
        release_date = in.readString();
    }

    public static final Creator<Movie> CREATOR = new Creator<Movie>() {
        @Override
        public Movie createFromParcel(Parcel in) {
            return new Movie(in);
        }

        @Override
        public Movie[] newArray(int size) {
            return new Movie[size];
        }
    };

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getVoteAverage() {
        return voteAverage;
    }

    public void setVoteAverage(String voteAverage) {
        this.voteAverage = voteAverage;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPopularity() {
        return popularity;
    }

    public void setPopularity(String popularity) {
        this.popularity = popularity;
    }

    public String getPoster_path() {
        return poster_path;
    }

    public void setPoster_path(String poster_path) {
        this.poster_path = poster_path;
    }

    public String getOverview() {
        return overview;
    }

    public void setOverview(String overview) {
        this.overview = overview;
    }

    public String getRelease_date() {
        return release_date;
    }

    public void setRelease_date(String release_date) {
        this.release_date = release_date;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(id);
        parcel.writeString(voteAverage);
        parcel.writeString(title);
        parcel.writeString(popularity);
        parcel.writeString(poster_path);
        parcel.writeString(overview);
        parcel.writeString(release_date);
    }

    public Movie(JSONObject object) {
        try {
            String id = object.getString("id");
            String voteAverage = object.getString("vote_average");
            String title = object.getString("title");
            String popularity = object.getString("popularity");
            String poster_path = object.getString("poster_path");
            String overview = object.getString("overview");
            String release_date = object.getString("release_date");

            this.id = id;
            this.voteAverage = voteAverage;
            this.title = title;
            this.popularity = popularity;
            this.poster_path = poster_path;
            this.overview = overview;
            this.release_date = release_date;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

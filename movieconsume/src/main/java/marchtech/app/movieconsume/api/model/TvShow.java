package marchtech.app.movieconsume.api.model;

import android.os.Parcel;
import android.os.Parcelable;

import org.json.JSONObject;

public class TvShow implements Parcelable {
    private String id;
    private String name;
    private String popularity;
    private String voteAverage;
    private String poster_path;
    private String overview;
    private String first_air_date;

    protected TvShow(Parcel in) {
        id = in.readString();
        name = in.readString();
        popularity = in.readString();
        voteAverage = in.readString();
        poster_path = in.readString();
        overview = in.readString();
        first_air_date = in.readString();
    }

    public static final Creator<TvShow> CREATOR = new Creator<TvShow>() {
        @Override
        public TvShow createFromParcel(Parcel in) {
            return new TvShow(in);
        }

        @Override
        public TvShow[] newArray(int size) {
            return new TvShow[size];
        }
    };

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPopularity() {
        return popularity;
    }

    public void setPopularity(String popularity) {
        this.popularity = popularity;
    }

    public String getVoteAverage() {
        return voteAverage;
    }

    public void setVoteAverage(String voteAverage) {
        this.voteAverage = voteAverage;
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

    public String getFirst_air_date() {
        return first_air_date;
    }

    public void setFirst_air_date(String first_air_date) {
        this.first_air_date = first_air_date;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(id);
        parcel.writeString(name);
        parcel.writeString(popularity);
        parcel.writeString(voteAverage);
        parcel.writeString(poster_path);
        parcel.writeString(overview);
        parcel.writeString(first_air_date);
    }

    public TvShow(JSONObject object) {
        try {
            String id = object.getString("id");
            String name = object.getString("name");
            String popularity = object.getString("popularity");
            String voteAverage = object.getString("vote_average");
            String poster_path = object.getString("poster_path");
            String overview = object.getString("overview");
            String first_air_date = object.getString("first_air_date");

            this.id = id;
            this.voteAverage = voteAverage;
            this.name = name;
            this.popularity = popularity;
            this.poster_path = poster_path;
            this.overview = overview;
            this.first_air_date = first_air_date;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

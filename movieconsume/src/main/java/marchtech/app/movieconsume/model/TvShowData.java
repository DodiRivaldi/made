package marchtech.app.movieconsume.model;

import com.google.gson.annotations.SerializedName;

public class TvShowData {
    @SerializedName("name")
    private String tvShowName;
    @SerializedName("poster_path")
    private String tvShowPoster;
    @SerializedName("overview")
    private String tvShowOverView;
    @SerializedName("popularity")
    private Double tvShowpopularity;
    @SerializedName("vote_count")
    private Integer tvShowVote;
    @SerializedName("vote_average")
    private Double tvShowRate;
    @SerializedName("id")
    private Integer tvShowId;
    @SerializedName("first_air_date")
    private String mFirstAirDate;

    public String getmFirstAirDate() {
        return mFirstAirDate;
    }

    public String getTvShowName() {
        return tvShowName;
    }

    public String getTvShowPoster() {
        return tvShowPoster;
    }

    public String getTvShowOverView() {
        return tvShowOverView;
    }

    public Double getTvShowpopularity() {
        return tvShowpopularity;
    }

    public Integer getTvShowVote() {
        return tvShowVote;
    }

    public Double getTvShowRate() {
        return tvShowRate;
    }

    public Integer getTvShowId() {
        return tvShowId;
    }

}

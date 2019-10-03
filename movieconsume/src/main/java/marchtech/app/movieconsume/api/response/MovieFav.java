package marchtech.app.movieconsume.api.response;

import com.google.gson.annotations.SerializedName;

public class MovieFav {
    @SerializedName("title")
    private String movieName;
    @SerializedName("poster_path")
    private String moviePoster;
    @SerializedName("overview")
    private String movieOverview;
    @SerializedName("release_date")
    private String movieDate;
    @SerializedName("popularity")
    private Double moviePopularity;
    @SerializedName("vote_count")
    private Integer movieVoteCount;
    @SerializedName("vote_average")
    private Double movieRate;
    @SerializedName("id")
    private Integer movieId;

    public String getMovieName() {
        return movieName;
    }

    public String getMoviePoster() {
        return moviePoster;
    }

    public String getMovieOverview() {
        return movieOverview;
    }

    public String getMovieDate() {
        return movieDate;
    }

    public Double getMoviePopularity() {
        return moviePopularity;
    }

    public Integer getMovieVoteCount() {
        return movieVoteCount;
    }

    public Double getMovieRate() {
        return movieRate;
    }

    public Integer getMovieId() {
        return movieId;
    }
}

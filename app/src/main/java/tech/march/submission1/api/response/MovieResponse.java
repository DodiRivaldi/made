package tech.march.submission1.api.response;

import com.google.gson.annotations.SerializedName;

import java.util.List;

import tech.march.submission1.api.model.MovieRelease;

public class MovieResponse {
    @SerializedName("page")
    private Long mPage;
    @SerializedName("results")
    private List<MovieRelease> mResults;
    @SerializedName("total_pages")
    private Long mTotalPages;
    @SerializedName("total_results")
    private Long mTotalResults;

    public List<MovieRelease> getMovies() {
        return mResults;
    }

}

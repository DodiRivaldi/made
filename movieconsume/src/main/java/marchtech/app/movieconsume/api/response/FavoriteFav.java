package marchtech.app.movieconsume.api.response;

import com.google.gson.annotations.SerializedName;

import java.util.List;

import marchtech.app.movieconsume.api.model.TvData;


public class FavoriteFav {
    @SerializedName("page")
    private Long mPage;
    @SerializedName("results")
    private List<TvData> mResults;
    @SerializedName("total_pages")
    private Long mTotalPages;
    @SerializedName("total_results")
    private Long mTotalResults;

    public List<TvData> getTvs() {
        return mResults;
    }
}

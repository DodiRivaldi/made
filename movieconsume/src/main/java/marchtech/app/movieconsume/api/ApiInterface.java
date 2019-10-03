package marchtech.app.movieconsume.api;

import marchtech.app.movieconsume.api.model.TvData;
import marchtech.app.movieconsume.api.response.MovieFav;
import marchtech.app.movieconsume.api.response.MovieResponse;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ApiInterface {

    @GET("3/movie/{movie_id}")
    Call<MovieFav> getMovie(
            @Path("movie_id") int id,
            @Query("api_key") String apiKey
    );



    @GET("discover/movie")
    Call<MovieResponse> getReleaseToday(
            @Query("api_key") String apiKey,
            @Query("primary_release_date.gte") String gteDate,
            @Query("primary_release_date.lte") String lteDate
    );


    @GET("3/tv/{tv_id}")
    Call<TvData> getTv(
            @Path("tv_id") int id,
            @Query("api_key") String apiKey
    );
}

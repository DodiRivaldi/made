package tech.march.submission1.api;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;
import tech.march.submission1.api.response.MovieResponse;

public interface ApiInterface {

    @GET("discover/movie")
    Call<MovieResponse> getReleaseToday(
            @Query("api_key") String apiKey,
            @Query("primary_release_date.gte") String gteDate,
            @Query("primary_release_date.lte") String lteDate
    );
}
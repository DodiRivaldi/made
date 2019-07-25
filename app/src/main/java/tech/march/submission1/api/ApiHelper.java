package tech.march.submission1.api;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiHelper {
    public static final String BASE_URL = "https://api.themoviedb.org/";
    public static final String APIKEY = "0b8306d7d4e46c13da26789e18038dad";
    public static final String BASE_IMAGE_URL = "https://image.tmdb.org/t/p/";
    public static Retrofit retrofit;

    public static Retrofit getRetrofitClient() {
        if (retrofit == null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit;
    }
}

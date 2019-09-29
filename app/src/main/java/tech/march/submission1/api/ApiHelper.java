package tech.march.submission1.api;


import tech.march.submission1.BuildConfig;

public class ApiHelper {
    public static final String BASE_URL = BuildConfig.BASE_URL;
    public static final String APIKEY = BuildConfig.API_KEY;
    public static final String BASE_IMAGE_URL = BuildConfig.BASE_URL_IMAGE;


    public static ApiInterface getApi() {
        return ApiClient.getClient(BASE_URL).create(ApiInterface.class);
    }
}

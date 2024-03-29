package tech.march.submission1.api;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Objects;

import cz.msebera.android.httpclient.Header;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import tech.march.submission1.api.model.Movie;
import tech.march.submission1.api.model.TvData;
import tech.march.submission1.api.model.TvShow;
import tech.march.submission1.api.response.MovieFav;

public class ApiRequest extends ViewModel {
    private static final ApiInterface apiInterface = new ApiHelper().getApi();
    private MutableLiveData<ArrayList<Movie>> movies = new MutableLiveData<>();
    private MutableLiveData<ArrayList<TvShow>> tvShows = new MutableLiveData<>();
    private final MutableLiveData<MovieFav> movie = new MutableLiveData<>();
    private final MutableLiveData<TvData> tv = new MutableLiveData<>();

    public void setMovies(final String extra) {
        AsyncHttpClient client = new AsyncHttpClient();
        final ArrayList<Movie> listItems = new ArrayList<>();

        String url = ApiHelper.BASE_URL + "3/discover/movie?api_key=" + ApiHelper.APIKEY + "&language=en-US";

        client.get(url, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                try {
                    String result = new String(responseBody);
                    JSONObject responseObject = new JSONObject(result);
                    JSONArray list = responseObject.getJSONArray("results");

                    for (int i = 0; i < list.length(); i++) {
                        JSONObject weather = list.getJSONObject(i);
                        Movie movieItems = new Movie(weather);

                        Log.d("SUKSES : ", movieItems.getTitle());
                        listItems.add(movieItems);
                    }
                    movies.postValue(listItems);
                } catch (Exception e) {
                    Log.d("Exception", e.getMessage());
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                Log.d("onFailure", error.getMessage());
            }
        });
    }

    public void setFavMovie(int id) {
        apiInterface.getMovie(id, ApiHelper.APIKEY).enqueue(new Callback<MovieFav>() {
            @Override
            public void onResponse(Call<MovieFav> call, Response<MovieFav> response) {
                if (response.isSuccessful()) {
                    movie.postValue(response.body());
                    Log.d("setMovie", "success");
                } else {

                }
            }

            @Override
            public void onFailure(Call<MovieFav> call, Throwable t) {
                Log.d("setMovie", "Failed: " + t.getMessage());
            }
        });
    }

    public void setTv(int id) {
        apiInterface.getTv(id, ApiHelper.APIKEY).enqueue(new Callback<TvData>() {
            @Override
            public void onResponse(Call<TvData> call, Response<TvData> response) {
                if (response.isSuccessful()) {
                    tv.postValue(response.body());
                    Log.d("setTv", "succsess");
                } else {

                }
            }

            @Override
            public void onFailure(Call<TvData> call, Throwable t) {
                Log.d("setTv", "Failed: " + t.getMessage());
            }
        });
    }

    public LiveData<ArrayList<Movie>> getMovies() {
        return movies;
    }

    public LiveData<MovieFav> getMovieFav() {
        return movie;
    }
    public LiveData<TvData> getTvFav() {
        return tv;
    }


    public void setTvShows(final String extra) {
        AsyncHttpClient client = new AsyncHttpClient();
        final ArrayList<TvShow> items = new ArrayList<>();

        String url = ApiHelper.BASE_URL + "3/discover/tv?api_key=" + ApiHelper.APIKEY + "&language=en-US";

        client.get(url, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                try {
                    String result = new String(responseBody);
                    JSONObject responseObject = new JSONObject(result);
                    JSONArray list = responseObject.getJSONArray("results");

                    for (int i = 0; i < list.length(); i++) {
                        JSONObject weather = list.getJSONObject(i);
                        TvShow tvShow = new TvShow(weather);
                        items.add(tvShow);
                    }
                    tvShows.postValue(items);
                } catch (Exception e) {
                    Log.d("Exception", e.getMessage());
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                Log.d("onFailure", error.getMessage());
            }
        });
    }

    public LiveData<ArrayList<TvShow>> getTvShows() {
        return tvShows;
    }

    public void setMoviesSearch(final String extra,String param) {
        AsyncHttpClient client = new AsyncHttpClient();
        final ArrayList<Movie> listItems = new ArrayList<>();

        String url = ApiHelper.BASE_URL + "3/search/movie?api_key=" + ApiHelper.APIKEY + "&language=en-US&query="+param;

        client.get(url, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                try {
                    String result = new String(responseBody);
                    JSONObject responseObject = new JSONObject(result);
                    JSONArray list = responseObject.getJSONArray("results");

                    for (int i = 0; i < list.length(); i++) {
                        JSONObject weather = list.getJSONObject(i);
                        Movie movieItems = new Movie(weather);

                        Log.d("SUKSES : ", movieItems.getTitle());
                        listItems.add(movieItems);
                    }
                    movies.postValue(listItems);
                } catch (Exception e) {
                    Log.d("Exception", e.getMessage());
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                Log.d("onFailure", error.getMessage());
            }
        });
    }
    public void setTvShowsSearch(final String extra,String param) {
        AsyncHttpClient client = new AsyncHttpClient();
        final ArrayList<TvShow> items = new ArrayList<>();

        String url = ApiHelper.BASE_URL + "3/search/tv?api_key=" + ApiHelper.APIKEY + "&language=en-US&query="+param;

        client.get(url, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                try {
                    String result = new String(responseBody);
                    JSONObject responseObject = new JSONObject(result);
                    JSONArray list = responseObject.getJSONArray("results");

                    for (int i = 0; i < list.length(); i++) {
                        JSONObject weather = list.getJSONObject(i);
                        TvShow tvShow = new TvShow(weather);
                        items.add(tvShow);
                    }
                    tvShows.postValue(items);
                } catch (Exception e) {
                    Log.d("Exception", e.getMessage());
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                Log.d("onFailure", error.getMessage());
            }
        });
    }

    public void setReleaseToday(final String extra,String today) {
        AsyncHttpClient client = new AsyncHttpClient();
        final ArrayList<Movie> listItems = new ArrayList<>();

        String url = ApiHelper.BASE_URL + "3/discover/movie?api_key=" + ApiHelper.APIKEY +
                "&primary_release_date.gte="+today+"&primary_release_date.lte="+today;

        client.get(url, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                try {
                    String result = new String(responseBody);
                    JSONObject responseObject = new JSONObject(result);
                    JSONArray list = responseObject.getJSONArray("results");

                    for (int i = 0; i < list.length(); i++) {
                        JSONObject weather = list.getJSONObject(i);
                        Movie movieItems = new Movie(weather);

                        Log.d("SUKSES : ", movieItems.getTitle());
                        listItems.add(movieItems);
                    }
                    movies.postValue(listItems);
                } catch (Exception e) {
                    Log.d("Exception", e.getMessage());
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                Log.d("onFailure", error.getMessage());
            }
        });
    }



}

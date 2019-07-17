package tech.march.submission1.activity.main;

import android.content.Context;
import android.content.res.TypedArray;

import java.util.ArrayList;

import tech.march.submission1.R;
import tech.march.submission1.model.Movie;

public class MainPresenter {
    MainView mainView;
    private String[] dataTitle,dataDesc,dataArtist,dataDate,dataRate;
    private TypedArray dataPoster;
    private Context context;
    private ArrayList<Movie> movieArrayList;



    public MainPresenter(Context context,MainView mainView) {
        this.mainView = mainView;
        this.context = context;
    }

   public void getData() {
        dataTitle = context.getResources().getStringArray(R.array.movie_name);
        dataDesc = context.getResources().getStringArray(R.array.movie_desc);
        dataArtist = context.getResources().getStringArray(R.array.movie_artist);
        dataRate = context.getResources().getStringArray(R.array.movies_rate);
        dataDate = context.getResources().getStringArray(R.array.movies_date);
        dataPoster = context.getResources().obtainTypedArray(R.array.movie_poster);

        movieArrayList = new ArrayList<>();

        for (int i = 0; i < dataTitle.length; i++) {
            Movie movie = new Movie();
            movie.setTitle(dataTitle[i]);
            movie.setDesc(dataDesc[i]);
            movie.setDate(dataDate[i]);
            movie.setRate(dataRate[i]);
            movie.setArtist(dataArtist[i]);
            movie.setPoster(dataPoster.getResourceId(i, -1));
            movieArrayList.add(movie);
        }

        mainView.onGetResult(movieArrayList);


    }
}

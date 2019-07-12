package tech.march.submission1.activity.main;

import java.util.ArrayList;

import tech.march.submission1.model.Movie;

public interface MainView {
    void showLoading();

    void hideLoading();

    void onPrepare();

    void onGetResult( ArrayList<Movie> movieArrayList);

    void onError(String message);
}

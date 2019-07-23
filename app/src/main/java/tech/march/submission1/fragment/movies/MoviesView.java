package tech.march.submission1.fragment.movies;

import java.util.ArrayList;

import tech.march.submission1.model.Movie;

public interface MoviesView {
    void onGetResult( ArrayList<Movie> movieArrayList);
}

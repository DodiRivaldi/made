package tech.march.submission1.fragment.movies;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.facebook.shimmer.ShimmerFrameLayout;

import butterknife.BindView;
import butterknife.ButterKnife;
import tech.march.submission1.R;
import tech.march.submission1.adapter.MovieAdapter;
import tech.march.submission1.api.ApiRequest;
import tech.march.submission1.api.model.Movie;

/**
 * A simple {@link Fragment} subclass.
 */
public class MoviesFragment extends Fragment {
    @BindView(R.id.rv_movies)
    RecyclerView rvMovies;
    @BindView(R.id.parentShimmerLayout)
    ShimmerFrameLayout shimmerFrameLayout;

    private MovieAdapter adapter;
    private ApiRequest request;

    public MoviesFragment() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_movies, container, false);
        ButterKnife.bind(this, view);
        rvMovies.setHasFixedSize(true);
        adapter = new MovieAdapter();
        request = ViewModelProviders.of(this).get(ApiRequest.class);
        shimmerFrameLayout.startShimmerAnimation();
        request.getMovies().observe(this, getMovie);
        request.setMovies("EXTRA_MOVIE");
        rvMovies.setLayoutManager(new LinearLayoutManager(this.getContext()));
        rvMovies.setAdapter(adapter);

        return view;
    }

    private Observer<ArrayList<Movie>> getMovie = new Observer<ArrayList<Movie>>() {
        @Override
        public void onChanged(ArrayList<Movie> movies) {
            if (movies != null) {
                shimmerFrameLayout.stopShimmerAnimation();
                adapter.setupData(movies);
                shimmerFrameLayout.setVisibility(View.GONE);
            }

            shimmerFrameLayout.setVisibility(View.GONE);
        }
    };

}

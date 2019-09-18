package tech.march.submission1.activity.search;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.MenuItemCompat;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.facebook.shimmer.ShimmerFrameLayout;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import tech.march.submission1.R;
import tech.march.submission1.adapter.MovieAdapter;
import tech.march.submission1.adapter.TvShowAdapter;
import tech.march.submission1.api.ApiRequest;
import tech.march.submission1.api.model.Movie;
import tech.march.submission1.api.model.TvShow;

public class SearchActivity extends AppCompatActivity {
    private String status;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.rv_search)
    RecyclerView rv;
    @BindView(R.id.parentShimmerLayout)
    ShimmerFrameLayout shimmerFrameLayout;

    private MovieAdapter adapterMovie;
    private TvShowAdapter adapterTv;
    private ApiRequest request;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        ButterKnife.bind(this);
        Bundle extra = getIntent().getExtras();
        setSupportActionBar(toolbar);
        status = extra.getString("status");

        rv.setHasFixedSize(true);
        adapterMovie = new MovieAdapter();
        adapterTv = new TvShowAdapter();
        request = ViewModelProviders.of(this).get(ApiRequest.class);
        shimmerFrameLayout.setVisibility(View.GONE);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.search_menu, menu);

        MenuItem mSearch = menu.findItem(R.id.app_bar_search);

        SearchView mSearchView = (SearchView) mSearch.getActionView();
        mSearchView.setQueryHint("Search");

        mSearchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                shimmerFrameLayout.startShimmerAnimation();
                shimmerFrameLayout.setVisibility(View.VISIBLE);

                if (status.equals("movie")) {
                    request.getMovies().observe(SearchActivity.this, getMovie);
                    request.setMoviesSearch("EXTRA_MOVIE",query);
                    rv.setLayoutManager(new LinearLayoutManager(SearchActivity.this));
                    rv.setAdapter(adapterMovie);
                }else if (status.equals("tv")){
                    request.getTvShows().observe(SearchActivity.this, getTvShow);
                    request.setTvShowsSearch("EXTRA_TV",query);
                    rv.setLayoutManager(new LinearLayoutManager(SearchActivity.this));
                    rv.setAdapter(adapterTv);
                }

                mSearchView.clearFocus();
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {

                return true;
            }
        });

        return super.onCreateOptionsMenu(menu);
    }

    private Observer<ArrayList<Movie>> getMovie = new Observer<ArrayList<Movie>>() {
        @Override
        public void onChanged(ArrayList<Movie> movies) {
            if (movies != null) {
                shimmerFrameLayout.stopShimmerAnimation();
                adapterMovie.setupData(movies);
                shimmerFrameLayout.setVisibility(View.GONE);
            }

            shimmerFrameLayout.setVisibility(View.GONE);
        }
    };

    private Observer<ArrayList<TvShow>> getTvShow = new Observer<ArrayList<TvShow>>() {
        @Override
        public void onChanged(ArrayList<TvShow> tvShows) {
            if (tvShows != null) {
                shimmerFrameLayout.stopShimmerAnimation();
                adapterTv.setupData(tvShows);
                shimmerFrameLayout.setVisibility(View.GONE);
            }

            shimmerFrameLayout.setVisibility(View.GONE);
        }
    };
}

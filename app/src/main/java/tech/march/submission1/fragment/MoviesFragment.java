package tech.march.submission1.fragment;


import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;
import tech.march.submission1.R;
import tech.march.submission1.activity.detail.DetailActivity;
import tech.march.submission1.activity.main.MainPresenter;
import tech.march.submission1.activity.main.MainView;
import tech.march.submission1.adapter.MovieAdapter;
import tech.march.submission1.model.Movie;

/**
 * A simple {@link Fragment} subclass.
 */
public class MoviesFragment extends Fragment implements MainView {
    @BindView(R.id.rv_movies)
    RecyclerView rvMovies;

    private MovieAdapter adapter;
    private MainPresenter presenter;

    public MoviesFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_movies, container, false);
        ButterKnife.bind(this, view);
        rvMovies.setHasFixedSize(true);
        presenter = new MainPresenter(getContext(), this);

        presenter.getData();
        return view;
    }

    @Override
    public void onGetResult(ArrayList<Movie> movieArrayList) {
        rvMovies.setLayoutManager(new LinearLayoutManager(getContext()));
        adapter = new MovieAdapter(movieArrayList, new MovieAdapter.OnItemClickListener() {
            @Override
            public void onClick(Movie item) {
                Intent intent = new Intent(getContext(), DetailActivity.class);
                intent.putExtra(DetailActivity.EXTRA_DATA, item);
                startActivity(intent);
            }
        });
        rvMovies.setAdapter(adapter);
    }
}

package tech.march.submission1.fragment.tvshow;


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
import tech.march.submission1.adapter.MovieAdapter;
import tech.march.submission1.adapter.TvShowAdapter;
import tech.march.submission1.fragment.movies.MoviesPresenter;
import tech.march.submission1.fragment.movies.MoviesView;
import tech.march.submission1.model.Movie;

/**
 * A simple {@link Fragment} subclass.
 */
public class TvShowFragment extends Fragment implements MoviesView {

    @BindView(R.id.rv_tv)
    RecyclerView rvTv;

    private TvShowAdapter adapter;
    private MoviesPresenter presenter;

    public TvShowFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_tv_show, container, false);
        ButterKnife.bind(this, view);
        rvTv.setHasFixedSize(true);
        presenter = new MoviesPresenter(getContext(), this);

        presenter.getData();
        return view;
    }

    @Override
    public void onGetResult(ArrayList<Movie> movieArrayList) {
        rvTv.setLayoutManager(new LinearLayoutManager(getContext()));
        adapter = new TvShowAdapter(movieArrayList, new TvShowAdapter.OnItemClickListener() {
            @Override
            public void onClick(Movie item) {
                Intent intent = new Intent(getContext(), DetailActivity.class);
                intent.putExtra(DetailActivity.EXTRA_DATA, item);
                startActivity(intent);
            }
        });
        rvTv.setAdapter(adapter);
    }
}

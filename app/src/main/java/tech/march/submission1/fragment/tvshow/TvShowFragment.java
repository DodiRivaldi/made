package tech.march.submission1.fragment.tvshow;


import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.facebook.shimmer.ShimmerFrameLayout;

import butterknife.BindView;
import butterknife.ButterKnife;
import tech.march.submission1.R;
import tech.march.submission1.adapter.TvShowAdapter;
import tech.march.submission1.api.ApiRequest;
import tech.march.submission1.api.model.TvShow;

/**
 * A simple {@link Fragment} subclass.
 */
public class TvShowFragment extends Fragment {

    @BindView(R.id.rv_tv)
    RecyclerView rvTv;
    @BindView(R.id.parentShimmerLayout)
    ShimmerFrameLayout shimmerFrameLayout;

    private TvShowAdapter adapter;
    private ApiRequest request;

    public TvShowFragment() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_tv_show, container, false);
        ButterKnife.bind(this, view);
        rvTv.setHasFixedSize(true);
        adapter = new TvShowAdapter();
        request = ViewModelProviders.of(this).get(ApiRequest.class);
        shimmerFrameLayout.startShimmerAnimation();
        request.getTvShows().observe(this, getTvShow);
        request.setTvShows("EXTRA_TV");
        rvTv.setLayoutManager(new LinearLayoutManager(this.getContext()));
        rvTv.setAdapter(adapter);
        return view;
    }

    private Observer<ArrayList<TvShow>> getTvShow = new Observer<ArrayList<TvShow>>() {
        @Override
        public void onChanged(ArrayList<TvShow> tvShows) {
            if (tvShows != null) {
                shimmerFrameLayout.stopShimmerAnimation();
                adapter.setupData(tvShows);
                shimmerFrameLayout.setVisibility(View.GONE);
            }

            shimmerFrameLayout.setVisibility(View.GONE);
        }
    };

}

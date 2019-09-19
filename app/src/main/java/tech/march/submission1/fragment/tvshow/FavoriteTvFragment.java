package tech.march.submission1.fragment.tvshow;


import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import tech.march.submission1.R;
import tech.march.submission1.adapter.FavoriteAdapter;
import tech.march.submission1.database.helper.RealmHelper;
import tech.march.submission1.database.model.Favorite;

/**
 * A simple {@link Fragment} subclass.
 */
public class FavoriteTvFragment extends Fragment {
    private ArrayList<Favorite> arrayList;
    @BindView(R.id.rv_favorite)
    RecyclerView rvFav;

    private FavoriteAdapter adapter;
    private RealmHelper helper;


    public FavoriteTvFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_favorite_tv, container, false);
        ButterKnife.bind(this, view);
        adapter = new FavoriteAdapter();
        helper = new RealmHelper(getContext());

        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        loadData();
        rvFav.setLayoutManager(new LinearLayoutManager(this.getContext()));
        rvFav.setAdapter(adapter);
    }

    private void loadData() {
        try {
            arrayList = helper.getAllData("tv");
            adapter.setupData(arrayList);

        } catch (Exception e) {
            e.printStackTrace();
        }


    }

    private Observer<ArrayList<Favorite>> getFav = new Observer<ArrayList<Favorite>>() {
        @Override
        public void onChanged(ArrayList<Favorite> tvShows) {
            if (tvShows != null) {
                //  shimmerFrameLayout.stopShimmerAnimation();
                adapter.setupData(arrayList);
                ///   shimmerFrameLayout.setVisibility(View.GONE);
            }

            //  shimmerFrameLayout.setVisibility(View.GONE);
        }
    };
}

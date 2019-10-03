package tech.march.submission1.fragment.tvshow;


import android.content.Context;
import android.content.Intent;
import android.database.ContentObserver;
import android.database.Cursor;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.HandlerThread;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;
import tech.march.submission1.R;
import tech.march.submission1.activity.detail.DetailActivity;
import tech.march.submission1.adapter.FavoriteAdapter;
import tech.march.submission1.db.FavoriteData;
import tech.march.submission1.db.FavoriteHelper;
import tech.march.submission1.db.LoadFavoriteCallback;

import static tech.march.submission1.db.DatabaseContract.FavoriteColumns.CONTENT_URI;
import static tech.march.submission1.db.MappingHelper.getTvFavoriteList;

/**
 * A simple {@link Fragment} subclass.
 */
public class FavoriteTvFragment  extends Fragment {
    private static final String EXTRA_STATE = "EXTRA_STATE";
    @BindView(R.id.rvListFav)
     RecyclerView rvMovie;
    private LoadFavoriteCallback callback;
    private FavoriteAdapter adapter;
    private FavoriteHelper helper;


    public FavoriteTvFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_favorite, container, false);
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        ButterKnife.bind(this, view);

        setupView();

        if (savedInstanceState == null) {
            new FavoriteTvFragment.LoadFavoriteAsync(getContext(), callback).execute();
        } else {
            ArrayList<FavoriteData> list = savedInstanceState.getParcelableArrayList(EXTRA_STATE);
            if (list != null) {
                adapter.setListFavoriteData(list);
            }
        }
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelableArrayList(EXTRA_STATE, adapter.getFavoriteData());
    }


    private void setupView() {
        callback = new LoadFavoriteCallback() {
            @Override
            public void preExecute() {

            }

            @Override
            public void postExecute(Cursor cursor) {
                ArrayList<FavoriteData> list = getTvFavoriteList(cursor);
                if (list.size() > 0) {
                    adapter.setListFavoriteData(list);
                    rvMovie.setVisibility(View.VISIBLE);

                } else {
                    adapter.setListFavoriteData(new ArrayList<>());
                    rvMovie.setVisibility(View.GONE);
                }
            }
        };

        HandlerThread handlerThread = new HandlerThread("DataObserver");
        handlerThread.start();
        Handler handler = new Handler(handlerThread.getLooper());
        FavoriteTvFragment.DataObserver myObserver = new FavoriteTvFragment.DataObserver(handler, getContext(), callback);
        Objects.requireNonNull(getActivity()).getContentResolver().registerContentObserver(CONTENT_URI, true, myObserver);

        rvMovie.setLayoutManager(new LinearLayoutManager(getActivity()));
        rvMovie.setHasFixedSize(true);

        helper = FavoriteHelper.getInstance(getContext());
        helper.open();

        adapter = new FavoriteAdapter(getActivity(), id -> {
            Intent intent = new Intent(getContext(), DetailActivity.class);
            intent.putExtra(DetailActivity.TVID, id);
            intent.putExtra("type", "favtv");
            startActivity(intent);
        });

        rvMovie.setAdapter(adapter);
    }


    private static class LoadFavoriteAsync extends AsyncTask<Void, Void, Cursor> {

        private final WeakReference<Context> weakContext;
        private final WeakReference<LoadFavoriteCallback> weakCallback;

        private LoadFavoriteAsync(Context context, LoadFavoriteCallback callback) {
            weakContext = new WeakReference<>(context);
            weakCallback = new WeakReference<>(callback);
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            weakCallback.get().preExecute();
        }

        @Override
        protected Cursor doInBackground(Void... voids) {
            Context context = weakContext.get();
            return context.getContentResolver().query(CONTENT_URI, null, null, null, null);
        }

        @Override
        protected void onPostExecute(Cursor cursor) {
            super.onPostExecute(cursor);
            weakCallback.get().postExecute(cursor);
        }
    }

    static class DataObserver extends ContentObserver {

        final Context context;
        final LoadFavoriteCallback callback;

        DataObserver(Handler handler, Context context, LoadFavoriteCallback callback) {
            super(handler);
            this.context = context;
            this.callback = callback;
        }

        @Override
        public void onChange(boolean selfChange) {
            super.onChange(selfChange);
            new FavoriteTvFragment.LoadFavoriteAsync(context, callback).execute();

        }
    }
}

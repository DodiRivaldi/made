
package marchtech.app.movieconsume;

import android.content.Context;
import android.database.ContentObserver;
import android.database.Cursor;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.HandlerThread;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.lang.ref.WeakReference;
import java.util.ArrayList;

import static marchtech.app.movieconsume.DatabaseContract.URI_MOVIE;
import static marchtech.app.movieconsume.Helper.mapCursorToArrayList;

public class MainActivity extends AppCompatActivity implements LoadMovieCallback {
    private FavoriteAdapter adapter;
    private RecyclerView mRecyclerView;
    private DataObserver mDataObserver;
    private ImageView error_img;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mRecyclerView = findViewById(R.id.rv_movies);
        error_img = findViewById(R.id.error_img);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.setHasFixedSize(true);
        adapter = new FavoriteAdapter(this);
        mRecyclerView.setAdapter(adapter);
        HandlerThread handlerThread = new HandlerThread("DataObserver");
        handlerThread.start();
        Handler handler = new Handler(handlerThread.getLooper());
        mDataObserver = new DataObserver(handler, this);
        getContentResolver().registerContentObserver(URI_MOVIE, true, mDataObserver);
        new getData(this, this).execute();
    }

    @Override
    public void onPostExecute(Cursor movies) {
        ArrayList<Favorite> listMovies = mapCursorToArrayList(movies);
        if (listMovies.size() > 0) {
            error_img.setVisibility(View.GONE);
            adapter.setListMovies(listMovies);
        } else {
            Toast.makeText(this, "There is no data", Toast.LENGTH_SHORT).show();
            error_img.setVisibility(View.VISIBLE);
            adapter.setListMovies(new ArrayList<Favorite>());
        }
    }

    static class DataObserver extends ContentObserver {
        final Context context;

        public DataObserver(Handler handler, Context context) {
            super(handler);
            this.context = context;
        }

        @Override
        public void onChange(boolean selfChange) {
            super.onChange(selfChange);
            new getData(context, (MainActivity) context).execute();
        }
    }

    private static class getData extends AsyncTask<Void, Void, Cursor> {
        private final WeakReference<Context> weakContext;
        private final WeakReference<LoadMovieCallback> weakCallback;

        private getData(Context context, LoadMovieCallback callback) {
            weakContext = new WeakReference<>(context);
            weakCallback = new WeakReference<>(callback);
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected Cursor doInBackground(Void... voids) {
            return weakContext.get().getContentResolver().query(URI_MOVIE, null, null, null, null);
        }

        @Override
        protected void onPostExecute(Cursor cursor) {
            super.onPostExecute(cursor);
            weakCallback.get().onPostExecute(cursor);
        }
    }
}

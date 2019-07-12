package tech.march.submission1.activity.main;

import android.app.ProgressDialog;
import android.content.res.TypedArray;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

import androidx.appcompat.app.AppCompatActivity;
import butterknife.BindView;
import butterknife.ButterKnife;
import tech.march.submission1.R;
import tech.march.submission1.adapter.MovieAdapter;
import tech.march.submission1.model.Movie;

public class MainActivity extends AppCompatActivity implements MainView {

    private String[] dataTitle;
    private String[] dataDesc;
    private TypedArray dataPoster;
    private MovieAdapter adapter;
    private ArrayList<Movie> movieArrayList;

    MainPresenter presenter;
    @BindView(R.id.lv_movie)
    ListView listView;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
    }

    private void init() {
        ButterKnife.bind(this);
        presenter = new MainPresenter(MainActivity.this, MainActivity.this);
        presenter.getData();
        progressDialog = new ProgressDialog(MainActivity.this);
    }

    private void prepare() {
        dataTitle = getResources().getStringArray(R.array.movie_name);
        dataDesc = getResources().getStringArray(R.array.movie_desc);
        dataPoster = getResources().obtainTypedArray(R.array.movie_poster);
    }

    private void addItem() {
        movieArrayList = new ArrayList<>();

        for (int i = 0; i < dataTitle.length; i++) {
            Movie movie = new Movie();
            movie.setTitle(dataTitle[i]);
            movie.setDesc(dataDesc[i]);
            movie.setPoster(dataPoster.getResourceId(i, -1));
            movieArrayList.add(movie);
        }
    }

    @Override
    public void showLoading() {
//        progressDialog.show();
    }

    @Override
    public void hideLoading() {
  //      progressDialog.dismiss();
    }

    @Override
    public void onPrepare() {

    }

    @Override
    public void onGetResult(ArrayList<Movie> movieArrayList) {
        adapter = new MovieAdapter(this);

        adapter.notifyDataSetChanged();
        adapter.setMovieArrayList(movieArrayList);
        listView.setAdapter(adapter);

    }

    @Override
    public void onError(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }
}

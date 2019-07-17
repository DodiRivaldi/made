package tech.march.submission1.activity.main;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;


import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import butterknife.BindView;
import butterknife.ButterKnife;
import tech.march.submission1.R;
import tech.march.submission1.activity.detail.DetailActivity;
import tech.march.submission1.adapter.MovieAdapter;
import tech.march.submission1.fragment.MoviesFragment;
import tech.march.submission1.fragment.TvShowFragment;
import tech.march.submission1.model.Movie;

public class MainActivity extends AppCompatActivity implements MainView {

    private MovieAdapter adapter;

    @BindView(R.id.lv_movie)
    ListView listView;
    private MainPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();

    }

    private void init() {
        ButterKnife.bind(this);
        presenter = new MainPresenter(this, this);
        presenter.getData();
    }


    @Override
    public void onGetResult(ArrayList<Movie> movieArrayList) {
        adapter = new MovieAdapter(this);
        adapter.setMovieArrayList(movieArrayList);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(MainActivity.this, DetailActivity.class);
                intent.putExtra(DetailActivity.EXTRA_DATA, movieArrayList.get(i));
                startActivity(intent);
            }
        });

    }
}


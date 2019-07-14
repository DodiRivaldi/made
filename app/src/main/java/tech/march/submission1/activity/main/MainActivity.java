package tech.march.submission1.activity.main;

import android.os.Bundle;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import tech.march.submission1.R;
import tech.march.submission1.fragment.MoviesFragment;
import tech.march.submission1.fragment.TvShowFragment;

public class MainActivity extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener {

  /*  private MovieAdapter adapter;

    @BindView(R.id.lv_movie)
    ListView listView;
    private MainPresenter presenter;*/

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //     init();

        loadFragment(new MoviesFragment());
        // inisialisasi BottomNavigaionView
        BottomNavigationView bottomNavigationView = findViewById(R.id.bn_main);
        // beri listener pada saat item/menu bottomnavigation terpilih
        bottomNavigationView.setOnNavigationItemSelectedListener(this);
    }
    private boolean loadFragment(Fragment fragment){
        if (fragment != null) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fl_container, fragment)
                    .commit();
            return true;
        }
        return false;
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        Fragment fragment = null;
        switch (menuItem.getItemId()){
            case R.id.movies_menu:
                fragment = new MoviesFragment();
                break;
            case R.id.tvshow_menu:
                fragment = new TvShowFragment();
                break;
        }
        return loadFragment(fragment);
    }
/*
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

    }*/
}


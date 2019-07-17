package tech.march.submission1.activity.main;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;


import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;
import butterknife.BindView;
import butterknife.ButterKnife;
import tech.march.submission1.R;
import tech.march.submission1.activity.detail.DetailActivity;
import tech.march.submission1.adapter.MovieAdapter;
import tech.march.submission1.adapter.ViewPagerAdapter;
import tech.march.submission1.fragment.MoviesFragment;
import tech.march.submission1.fragment.TvShowFragment;
import tech.march.submission1.model.Movie;

public class MainActivity extends AppCompatActivity implements MainView {

    private MainPresenter presenter;

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.viewpager)
    ViewPager viewPager;
    @BindView(R.id.tabs)
    TabLayout tabLayout;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();

    }

    private void init() {
        ButterKnife.bind(this);
        //  presenter = new MainPresenter(this, this);
        //presenter.getData();
        setSupportActionBar(toolbar);
        setupViewPager(viewPager);
        tabLayout.setupWithViewPager(viewPager);
    }

    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(new MoviesFragment(), getString(R.string.movies));
        adapter.addFragment(new TvShowFragment(), getString(R.string.tvshow));
        viewPager.setAdapter(adapter);
    }

    @Override
    public void onGetResult(ArrayList<Movie> movieArrayList) {


    }
}


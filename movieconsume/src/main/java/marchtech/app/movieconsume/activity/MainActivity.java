
package marchtech.app.movieconsume.activity;

import android.os.Bundle;
import android.view.MenuItem;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;
import butterknife.BindView;
import butterknife.ButterKnife;
import marchtech.app.movieconsume.R;
import marchtech.app.movieconsume.adapter.ViewPagerAdapter;
import marchtech.app.movieconsume.fragment.FavoriteMovieFragment;
import marchtech.app.movieconsume.fragment.FavoriteTvFragment;

public class MainActivity  extends AppCompatActivity {
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.viewpager)
    ViewPager viewPager;
    @BindView(R.id.tabs)
    TabLayout tabLayout;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favorite);
        init();
        setSupportActionBar(toolbar);
    }

    private void init() {
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);
        setupViewPager(viewPager);
        tabLayout.setupWithViewPager(viewPager);
    }

    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(new FavoriteMovieFragment(), getString(R.string.movies));
        adapter.addFragment(new FavoriteTvFragment(), getString(R.string.tvshow));
        viewPager.setAdapter(adapter);
    }

}

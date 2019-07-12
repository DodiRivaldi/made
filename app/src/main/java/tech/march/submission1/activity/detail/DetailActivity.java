package tech.march.submission1.activity.detail;

import android.os.Bundle;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import androidx.appcompat.app.AppCompatActivity;
import butterknife.BindView;
import butterknife.ButterKnife;
import tech.march.submission1.R;
import tech.march.submission1.model.Movie;

public class DetailActivity extends AppCompatActivity {

    @BindView(R.id.img_poster)
    ImageView imgPoster;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.tv_date)
    TextView tvDate;
    @BindView(R.id.tv_artist)
    TextView tvArtist;
    @BindView(R.id.tv_overview)
    TextView tvOverView;


    public static final String EXTRA_DATA = String.valueOf(R.string.extra_data);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        this.getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        init();
    }

    private void init() {
        ButterKnife.bind(this);
        Movie movie = getIntent().getParcelableExtra(EXTRA_DATA);
        getSupportActionBar().setTitle(movie.getTitle());
        tvTitle.setText(movie.getTitle());
        tvDate.setText(movie.getDate());
        tvArtist.setText(movie.getArtist());
        tvOverView.setText(movie.getDesc());
        Picasso.get().load(movie.getPoster()).into(imgPoster);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            onBackPressed();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

}

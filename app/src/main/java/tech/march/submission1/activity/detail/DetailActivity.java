package tech.march.submission1.activity.detail;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.os.Handler;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;

import butterknife.BindView;
import butterknife.ButterKnife;
import tech.march.submission1.R;
import tech.march.submission1.api.ApiHelper;
import tech.march.submission1.api.ApiRequest;
import tech.march.submission1.api.model.Movie;
import tech.march.submission1.api.model.TvShow;

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
    public static final String EXTRA_DATA_TV = String.valueOf(R.string.extra_data_tv);
    private ProgressDialog dialog;
    private ApiRequest request;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        request = ViewModelProviders.of(this).get(ApiRequest.class);

        init();

    }

    private void init() {
        ButterKnife.bind(this);
        dialog = new ProgressDialog(this);
        final Handler handler = new Handler();
        dialog.setMessage(getString(R.string.waiting));
        dialog.setCancelable(false);
        dialog.show();
        Bundle extras = getIntent().getExtras();
        String type = extras.getString(String.valueOf(R.string.type));

        if (type.equals(String.valueOf(R.string.tv))) {
            new Thread(new Runnable() {
                public void run() {
                    try {
                        Thread.sleep(3000);
                    } catch (Exception e) {
                    }

                    handler.post(new Runnable() {
                        public void run() {
                            TvShow item = getIntent().getParcelableExtra(EXTRA_DATA_TV);
                            tvTitle.setText(item.getName());
                            tvDate.setText(item.getFirst_air_date());
                            tvArtist.setText(item.getVoteAverage());
                            tvOverView.setText(item.getOverview());
                            Picasso.get().load(ApiHelper.BASE_IMAGE_URL+"original" + item.getPoster_path()).into(imgPoster);
                            dialog.dismiss();
                        }
                    });
                }
            }).start();
        } else if (type.equals(String.valueOf(R.string.movie))){
            new Thread(new Runnable() {
                public void run() {
                    try {
                        Thread.sleep(3000);
                    } catch (Exception e) {
                    }

                    handler.post(new Runnable() {
                        public void run() {
                            Movie movie = getIntent().getParcelableExtra(EXTRA_DATA);
                            tvTitle.setText(movie.getTitle());
                            tvDate.setText(movie.getRelease_date());
                            tvArtist.setText(movie.getVoteAverage());
                            tvOverView.setText(movie.getOverview());
                            Picasso.get().load(ApiHelper.BASE_IMAGE_URL+"original" + movie.getPoster_path()).into(imgPoster);
                            dialog.dismiss();
                        }
                    });
                }
            }).start();


        }
    }


}

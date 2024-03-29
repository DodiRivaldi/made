package tech.march.submission1.activity.detail;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.squareup.picasso.Picasso;

import butterknife.BindView;
import butterknife.ButterKnife;
import tech.march.submission1.R;
import tech.march.submission1.activity.favorite.FavoriteActivity;
import tech.march.submission1.activity.main.MainActivity;
import tech.march.submission1.api.ApiHelper;
import tech.march.submission1.api.ApiRequest;
import tech.march.submission1.api.model.Movie;
import tech.march.submission1.api.model.TvData;
import tech.march.submission1.api.model.TvShow;
import tech.march.submission1.api.response.MovieFav;
import tech.march.submission1.db.FavoriteData;
import tech.march.submission1.db.FavoriteHelper;

import static tech.march.submission1.db.DatabaseContract.FavoriteColumns.CATEGORY;
import static tech.march.submission1.db.DatabaseContract.FavoriteColumns.CONTENT_URI;
import static tech.march.submission1.db.DatabaseContract.FavoriteColumns.ID;
import static tech.march.submission1.db.DatabaseContract.FavoriteColumns.OVERVIEW;
import static tech.march.submission1.db.DatabaseContract.FavoriteColumns.POSTER;
import static tech.march.submission1.db.DatabaseContract.FavoriteColumns.RATING;
import static tech.march.submission1.db.DatabaseContract.FavoriteColumns.TITLE;
import static tech.march.submission1.widget.FavoriteWidget.sendRefreshBroadcast;

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
    @BindView(R.id.btn_fav)
    Button btnFav;

    //private ArrayList<Favorite> arrayList;
    //private RealmHelper helper;

    public static final String EXTRA_DATA = String.valueOf(R.string.extra_data);
    public static final String EXTRA_DATA_TV = String.valueOf(R.string.extra_data_tv);
    public static final String MID = "movie_id";
    public static final String TVID = "tv_id";

    private ApiRequest request;
    private int id, mId;
    private int MOVIE_ID;
    private int TV_ID;
    private String poster;

    private FavoriteHelper helper;
    private FavoriteData favoriteData = new FavoriteData();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        request = ViewModelProviders.of(this).get(ApiRequest.class);

        init();


    }


    private void init() {
        ButterKnife.bind(this);
        Bundle extras = getIntent().getExtras();
        String type = extras.getString("type");
        if (type.equals("tv")) {

            TvShow item = getIntent().getParcelableExtra(EXTRA_DATA_TV);
            tvTitle.setText(item.getName());
            tvDate.setText(item.getFirst_air_date());
            tvArtist.setText(item.getVoteAverage());
            tvOverView.setText(item.getOverview());
            Picasso.get().load(ApiHelper.BASE_IMAGE_URL + "original" +
                    item.getPoster_path()).into(imgPoster);


            btnFav.setOnClickListener((View v) -> {
                favoriteData.setId(Integer.parseInt(item.getId()));
                favoriteData.setTitle(item.getName());
                favoriteData.setPoster(item.getPoster_path());
                favoriteData.setOverview(item.getOverview());
                favoriteData.setRating(Double.parseDouble(item.getPopularity()));
                favoriteData.setCategory("tv");

                ContentValues values = new ContentValues();
                values.put(ID, item.getId());
                values.put(TITLE, item.getName());
                values.put(OVERVIEW, item.getOverview());
                values.put(POSTER, item.getPoster_path());
                values.put(RATING, item.getPopularity());
                values.put(CATEGORY, "tv");

                if (getContentResolver().insert(CONTENT_URI, values) != null) {
                    Toast.makeText(DetailActivity.this, item.getName() + " " + " has been a favorite", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(DetailActivity.this, MainActivity.class));
                    finish();
                } else {
                    Toast.makeText(DetailActivity.this, item.getName() + " " + " failed to be favorite", Toast.LENGTH_SHORT).show();
                }
            });

        } else if (type.equals("movie")) {
            Movie item = getIntent().getParcelableExtra(EXTRA_DATA);
            tvTitle.setText(item.getTitle());
            tvDate.setText(item.getRelease_date());
            tvArtist.setText(item.getVoteAverage());
            tvOverView.setText(item.getOverview());
            Picasso.get().load(ApiHelper.BASE_IMAGE_URL + "original" +
                    item.getPoster_path()).into(imgPoster);

            btnFav.setOnClickListener((View v) -> {
                favoriteData.setId(Integer.parseInt(item.getId()));
                favoriteData.setTitle(item.getTitle());
                favoriteData.setPoster(item.getPoster_path());
                favoriteData.setOverview(item.getOverview());
                favoriteData.setRating(Double.parseDouble(item.getPopularity()));
                favoriteData.setCategory("movie");

                ContentValues values = new ContentValues();
                values.put(ID, item.getId());
                values.put(TITLE, item.getTitle());
                values.put(OVERVIEW, item.getOverview());
                values.put(POSTER, item.getPoster_path());
                values.put(RATING, item.getPopularity());
                values.put(CATEGORY, "movie");

                if (getContentResolver().insert(CONTENT_URI, values) != null) {
                    Toast.makeText(DetailActivity.this, item.getTitle() + " " + " has been a favorite", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(DetailActivity.this, MainActivity.class));
                    finish();
                } else {
                    Toast.makeText(DetailActivity.this, item.getTitle() + " " + " failed to be favorite", Toast.LENGTH_SHORT).show();
                }
            });


        } else if (type.equals("favmovie")) {

            helper = new FavoriteHelper(getApplicationContext());
            helper.open();
            MOVIE_ID = getIntent().getIntExtra(MID, MOVIE_ID);

            request = ViewModelProviders.of(this).get(ApiRequest.class);
            request.getMovieFav().observe(this, getMovie);

            request.setFavMovie(MOVIE_ID);

            Toast.makeText(this, String.valueOf(MOVIE_ID), Toast.LENGTH_SHORT).show();
            btnFav.setText(R.string.deletefav);

            btnFav.setOnClickListener((View v) -> {
               // if (isFavorite()) {
                    Uri uri = Uri.parse(CONTENT_URI + "/" +mId );
                    getContentResolver().delete(uri, null, null);
                    Toast.makeText(DetailActivity.this, "Favorite Deleted", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(DetailActivity.this, FavoriteActivity.class));
                    finish();
                /*} else {
                    Toast.makeText(DetailActivity.this, "Cannot Deleted", Toast.LENGTH_SHORT).show();

                }*/
            });
            sendRefreshBroadcast(getApplicationContext());

        } else if (type.equals("favtv")) {

            helper = new FavoriteHelper(getApplicationContext());
            helper.open();
            TV_ID = getIntent().getIntExtra(TVID, TV_ID);

            request = ViewModelProviders.of(this).get(ApiRequest.class);
            request.getTvFav().observe(this, getTv);

            request.setTv(TV_ID);

            btnFav.setText(R.string.deletefav);

            btnFav.setOnClickListener((View v) -> {
              //  if (isFavorite()) {
                    Uri uri = Uri.parse(CONTENT_URI + "/" + mId);
                    getContentResolver().delete(uri, null, null);
                    Toast.makeText(DetailActivity.this, "Favorite Deleted", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(DetailActivity.this, FavoriteActivity.class));
                    finish();
              /*  } else {
                    Toast.makeText(DetailActivity.this, "Cannot delete", Toast.LENGTH_SHORT).show();

                }*/
            });
            sendRefreshBroadcast(getApplicationContext());

        }
    }


    private final Observer<MovieFav> getMovie = new Observer<MovieFav>() {
        @Override
        public void onChanged(MovieFav movie) {
            poster = movie.getMoviePoster();
            mId = movie.getMovieId();
            isFavorite();
            tvTitle.setText(movie.getMovieName());
            tvDate.setText(movie.getMovieDate());
            tvOverView.setText(movie.getMovieOverview());
            Picasso.get().load(ApiHelper.BASE_IMAGE_URL + "original" +
                    movie.getMoviePoster()).into(imgPoster);


        }
    };

    private final Observer<TvData> getTv = new Observer<TvData>() {
        @Override
        public void onChanged(TvData tvShowData) {


            mId = tvShowData.getTvShowId();
            poster = tvShowData.getTvShowPoster();
            isFavorite();

            tvTitle.setText(tvShowData.getTvShowName());
            tvDate.setText(tvShowData.getmFirstAirDate());
            tvOverView.setText(tvShowData.getTvShowOverView());
            Picasso.get().load(ApiHelper.BASE_IMAGE_URL + "original" +
                    tvShowData.getTvShowPoster()).into(imgPoster);
        }
    };


    private boolean isFavorite() {
        Uri uri = Uri.parse(CONTENT_URI + "");
        boolean favorite = false;
        Cursor cursor = getContentResolver().query(uri, null, null, null, null);
        int getmId;
        if (cursor.moveToFirst()) {
            do {
                getmId = cursor.getInt(1);
                if (getmId == mId) {
                    id = cursor.getInt(0);
                    favorite = true;
                }
            } while (cursor.moveToNext());
        }
        return favorite;
    }

}

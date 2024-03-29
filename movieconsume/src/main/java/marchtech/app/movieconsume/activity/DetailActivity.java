package marchtech.app.movieconsume.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

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

import com.bumptech.glide.Glide;
import com.squareup.picasso.Picasso;

import butterknife.BindView;
import butterknife.ButterKnife;
import marchtech.app.movieconsume.R;
import marchtech.app.movieconsume.api.ApiHelper;
import marchtech.app.movieconsume.api.ApiRequest;
import marchtech.app.movieconsume.api.model.Movie;
import marchtech.app.movieconsume.api.model.TvData;
import marchtech.app.movieconsume.api.model.TvShow;
import marchtech.app.movieconsume.api.response.MovieFav;
import marchtech.app.movieconsume.db.FavoriteHelper;
import marchtech.app.movieconsume.model.FavoriteData;

import static marchtech.app.movieconsume.db.DatabaseContract.FavoriteColumns.CATEGORY;
import static marchtech.app.movieconsume.db.DatabaseContract.FavoriteColumns.CONTENT_URI;
import static marchtech.app.movieconsume.db.DatabaseContract.FavoriteColumns.ID;
import static marchtech.app.movieconsume.db.DatabaseContract.FavoriteColumns.OVERVIEW;
import static marchtech.app.movieconsume.db.DatabaseContract.FavoriteColumns.POSTER;
import static marchtech.app.movieconsume.db.DatabaseContract.FavoriteColumns.RATING;
import static marchtech.app.movieconsume.db.DatabaseContract.FavoriteColumns.TITLE;

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
    public static final String EXTRA_DATA_FAV = String.valueOf(R.string.extra_data_fav);
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
                favoriteData.setId(mId);
                favoriteData.setTitle(item.getName());
                favoriteData.setPoster(item.getPoster_path());
                favoriteData.setOverview(item.getOverview());
                favoriteData.setRating(Double.parseDouble(item.getPopularity()));
                favoriteData.setCategory("tv");

                ContentValues values = new ContentValues();
                values.put(ID, mId);
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
            Movie movie = getIntent().getParcelableExtra(EXTRA_DATA);
            tvTitle.setText(movie.getTitle());
            tvDate.setText(movie.getRelease_date());
            tvArtist.setText(movie.getVoteAverage());
            tvOverView.setText(movie.getOverview());
            Picasso.get().load(ApiHelper.BASE_IMAGE_URL + "original" +
                    movie.getPoster_path()).into(imgPoster);

            btnFav.setOnClickListener((View v) -> {
                Movie m = getIntent().getParcelableExtra(EXTRA_DATA);

                favoriteData.setId(mId);
                favoriteData.setTitle(m.getTitle());
                favoriteData.setPoster(m.getPoster_path());
                favoriteData.setOverview(m.getOverview());
                favoriteData.setRating(Double.parseDouble(m.getPopularity()));
                favoriteData.setCategory("movie");

                ContentValues values = new ContentValues();
                values.put(ID, mId);
                values.put(TITLE, m.getTitle());
                values.put(OVERVIEW, m.getOverview());
                values.put(POSTER, m.getPoster_path());
                values.put(RATING, m.getPopularity());
                values.put(CATEGORY, "movie");

                if (getContentResolver().insert(CONTENT_URI, values) != null) {
                    Toast.makeText(DetailActivity.this, m.getTitle() + " " + " has been a favorite", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(DetailActivity.this, MainActivity.class));
                    finish();
                } else {
                    Toast.makeText(DetailActivity.this, m.getTitle() + " " + " failed to be favorite", Toast.LENGTH_SHORT).show();
                }
            });


        } else if (type.equals("favmovie")) {

            helper = new FavoriteHelper(getApplicationContext());
            helper.open();
            MOVIE_ID = getIntent().getIntExtra(MID, MOVIE_ID);

            request = ViewModelProviders.of(this).get(ApiRequest.class);
            request.getMovieFav().observe(this, getMovie);

            request.setFavMovie(MOVIE_ID);

            btnFav.setText(R.string.deletefav);

            btnFav.setOnClickListener((View v) -> {
           //     if (isFavorite()) {
                    Uri uri = Uri.parse(CONTENT_URI + "/" + mId);
                    getContentResolver().delete(uri, null, null);
                    Toast.makeText(DetailActivity.this, "Favorite Deleted", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(DetailActivity.this, MainActivity.class));
                    finish();
              /*  } else {
                    Toast.makeText(DetailActivity.this, "F Deleted", Toast.LENGTH_SHORT).show();

                }*/
            });
        } else if (type.equals("favtv")) {

            helper = new FavoriteHelper(getApplicationContext());
            helper.open();
            TV_ID = getIntent().getIntExtra(TVID, TV_ID);

            request = ViewModelProviders.of(this).get(ApiRequest.class);
            request.getTvFav().observe(this, getTv);

            Toast.makeText(this, String.valueOf(TV_ID), Toast.LENGTH_SHORT).show();
            request.setTv(TV_ID);

            btnFav.setText(R.string.deletefav);

            btnFav.setOnClickListener((View v) -> {
         //       if (isFavorite()) {
                    Uri uri = Uri.parse(CONTENT_URI + "/" + mId);
                    getContentResolver().delete(uri, null, null);
                    Toast.makeText(DetailActivity.this, "Favorite Deleted", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(DetailActivity.this, MainActivity.class));
                    finish();
          /*      } else {
                    Toast.makeText(DetailActivity.this, "Cannot delete", Toast.LENGTH_SHORT).show();

                }*/
            });
        }
    }


    private final Observer<MovieFav> getMovie = new Observer<MovieFav>() {
        @Override
        public void onChanged(MovieFav movie) {
            isFavorite();
            tvTitle.setText(movie.getMovieName());
            tvDate.setText(movie.getMovieDate());
            tvOverView.setText(movie.getMovieOverview());
            Picasso.get().load(ApiHelper.BASE_IMAGE_URL + "original" +
                    movie.getMoviePoster()).into(imgPoster);
            poster = movie.getMoviePoster();
            mId = movie.getMovieId();


        }
    };

    private final Observer<TvData> getTv = new Observer<TvData>() {
        @Override
        public void onChanged(TvData tvShowData) {


            mId = tvShowData.getTvShowId();
            poster = tvShowData.getTvShowPoster();
            isFavorite();


            Glide.with(DetailActivity.this)
                    .load(ApiHelper.BASE_IMAGE_URL + "original/" + tvShowData.getTvShowPoster())
                    .into(imgPoster);

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

package tech.march.submission1.activity.detail;

import android.app.ProgressDialog;
import android.content.ContentValues;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;

import com.squareup.picasso.Picasso;

import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import tech.march.submission1.R;
import tech.march.submission1.api.ApiHelper;
import tech.march.submission1.api.ApiRequest;
import tech.march.submission1.api.model.Movie;
import tech.march.submission1.api.model.TvShow;
import tech.march.submission1.db.FavoriteData;
import tech.march.submission1.db.FavoriteHelper;


import static tech.march.submission1.db.DatabaseContract.FavoriteColumns.CATEGORY;
import static tech.march.submission1.db.DatabaseContract.FavoriteColumns.CONTENT_URI;
import static tech.march.submission1.db.DatabaseContract.FavoriteColumns.ID;
import static tech.march.submission1.db.DatabaseContract.FavoriteColumns.OVERVIEW;
import static tech.march.submission1.db.DatabaseContract.FavoriteColumns.POSTER;
import static tech.march.submission1.db.DatabaseContract.FavoriteColumns.RATING;
import static tech.march.submission1.db.DatabaseContract.FavoriteColumns.TITLE;

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
    private ProgressDialog dialog;
    private ApiRequest request;

    private FavoriteHelper helper;
    private FavoriteData favoriteData = new FavoriteData();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        request = ViewModelProviders.of(this).get(ApiRequest.class);

        init();


    }

   /* private void setupData() {

        helper = new FavoriteHelper(getApplicationContext());
        helper.open();
        MOVIE_ID = getIntent().getIntExtra(MID, MOVIE_ID);
        String LANGUANGE = Locale.getDefault().toString();
        if (LANGUANGE.equals("in_ID")) {
            LANGUANGE = "id_ID";
        }
        movieViewModel.setMovie(MOVIE_ID, LANGUANGE);

    }*/

    private void init() {
        ButterKnife.bind(this);
        dialog = new ProgressDialog(this);
      //  helper = new RealmHelper(this);
        final Handler handler = new Handler();
        dialog.setMessage(getString(R.string.waiting));
        dialog.setCancelable(false);
        dialog.show();
        Bundle extras = getIntent().getExtras();
        String type = extras.getString("type");
        if (type.equals("tv")) {
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
                            Picasso.get().load(ApiHelper.BASE_IMAGE_URL + "original" +
                                    item.getPoster_path()).into(imgPoster);
                            dialog.dismiss();

        /*                    btnFav.setOnClickListener((View v) -> {
                                helper.addFavorite(item.getId(), item.getPoster_path(), item.getName(),
                                        item.getPopularity(), item.getFirst_air_date(), item.getOverview(), "tv");
                            });

                            arrayList = helper.checkData(item.getId());

                            if (arrayList.size() == 0) {
                                btnFav.setVisibility(View.VISIBLE);
                            } else {
                                btnFav.setVisibility(View.GONE);
                            }
*/

                        }
                    });
                }
            }).start();
        } else if (type.equals("movie")) {
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
                            Picasso.get().load(ApiHelper.BASE_IMAGE_URL + "original" + movie.getPoster_path()).into(imgPoster);
                            dialog.dismiss();

  /*                          arrayList = helper.checkData(movie.getId());

                            if (arrayList.size() == 0) {
                                btnFav.setVisibility(View.VISIBLE);
                            } else {
                                btnFav.setVisibility(View.GONE);
                            }
                            btnFav.setOnClickListener((View v) -> {
                                helper.addFavorite(movie.getId(), movie.getPoster_path(), movie.getTitle(),
                                        movie.getPopularity(), movie.getRelease_date(), movie.getOverview(), "movie");

                            });
*/

  btnFav.setOnClickListener((View v)->{
      Movie m = getIntent().getParcelableExtra(EXTRA_DATA);

      favoriteData.setId(Integer.parseInt(m.getId()));
      favoriteData.setTitle(m.getTitle());
      favoriteData.setPoster(m.getPoster_path());
      favoriteData.setOverview(m.getOverview());
      favoriteData.setRating(Double.parseDouble(m.getPopularity()));
      favoriteData.setCategory("movie");

      ContentValues values = new ContentValues();
      values.put(ID, m.getId());
      values.put(TITLE, m.getTitle());
      values.put(OVERVIEW, m.getOverview());
      values.put(POSTER, m.getPoster_path());
      values.put(RATING, m.getPopularity());
      values.put(CATEGORY, "movie");

      if (getContentResolver().insert(CONTENT_URI, values) != null) {
          Toast.makeText(DetailActivity.this, m.getTitle() + " " + " has been a favorite", Toast.LENGTH_SHORT).show();
          // item.setIcon(R.drawable.ic_favorite_pink_24dp);
      } else {
          Toast.makeText(DetailActivity.this, m.getTitle() + " " + " failed to be favorite" , Toast.LENGTH_SHORT).show();
      }
  });

                        }
                    });
                }
            }).start();


        } else if (type.equals("fav")) {
            Bundle ext = getIntent().getExtras();
            String ids = ext.getString("id");
            new Thread(new Runnable() {
                public void run() {
                    try {
                        Thread.sleep(3000);
                    } catch (Exception e) {
                    }

  /*                  handler.post(new Runnable() {
                        public void run() {
                            arrayList = helper.checkData(ids);
                            tvTitle.setText(arrayList.get(0).getTitle());
                            tvDate.setText(arrayList.get(0).getDate());
                            tvArtist.setText(arrayList.get(0).getArtist());
                            tvOverView.setText(arrayList.get(0).getOverview());
                            Picasso.get().load(ApiHelper.BASE_IMAGE_URL + "original" + arrayList.get(0).getImage()).into(imgPoster);
                            dialog.dismiss();
                            if (arrayList.size() == 0) {
                                btnFav.setVisibility(View.VISIBLE);
                            } else {
                                btnFav.setText(R.string.hapus);
                                btnFav.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {
                                        helper.deleteData(ids);
                                        Toast.makeText(DetailActivity.this, R.string.deletedata, Toast.LENGTH_SHORT).show();
                                        startActivity(new Intent(DetailActivity.this, FavoriteActivity.class));
                                        finish();
                                    }
                                });
                            }
                        }
                    });
    */            }
            }).start();


        }
    }


}

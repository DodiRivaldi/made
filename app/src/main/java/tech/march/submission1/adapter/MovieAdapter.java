package tech.march.submission1.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import tech.march.submission1.R;
import tech.march.submission1.model.Movie;

public class MovieAdapter extends BaseAdapter {
    private final Context context;
    private ArrayList<Movie> movieArrayList;

    public MovieAdapter(Context context) {
        this.context = context;
        movieArrayList = new ArrayList<>();
    }

    public void setMovieArrayList(ArrayList<Movie> movies) {
        this.movieArrayList = movies;
    }


    @Override
    public int getCount() {
        return movieArrayList.size();
    }

    @Override
    public Object getItem(int i) {
        return movieArrayList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        if (view == null) {
            view = LayoutInflater.from(context).inflate(R.layout.item_movie, viewGroup, false);
        }


        ViewHolder holder = new ViewHolder(view);
        Movie movie = (Movie) getItem(i);
        holder.bind(movie);
        return view;
    }

    public class ViewHolder {
        @BindView(R.id.tv_movie_title)
        TextView tvTitle;
        @BindView(R.id.tv_description)
        TextView tvDesc;
        @BindView(R.id.img_movie_poster)
        ImageView imgPoster;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }

        void bind(Movie movie) {
            tvTitle.setText(movie.getTitle());
            tvDesc.setText(movie.getDesc());
            Picasso.get().load(movie.getPoster()).into(imgPoster);
        }
    }
}

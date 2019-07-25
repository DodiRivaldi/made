package tech.march.submission1.adapter;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import butterknife.BindView;
import butterknife.ButterKnife;
import tech.march.submission1.R;
import tech.march.submission1.activity.detail.DetailActivity;
import tech.march.submission1.api.ApiHelper;
import tech.march.submission1.api.model.Movie;

public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.MovieViewHolder> {
    private ArrayList<Movie> movies = new ArrayList<>();

    public void setupData(ArrayList<Movie> items) {
        movies.clear();
        movies.addAll(items);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public MovieAdapter.MovieViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_movie, parent, false);
        return new MovieViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MovieViewHolder holder, int position) {
        holder.bind(movies.get(position));
    }

    @Override
    public int getItemCount() {
        return movies.size();
    }

    class MovieViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        @BindView(R.id.img_poster)
        ImageView imgPoster;
        @BindView(R.id.tv_title)
        TextView tvTitle;
        @BindView(R.id.tv_type)
        TextView tvType;
        @BindView(R.id.tv_time)
        TextView tvTime;
        @BindView(R.id.tv_rate)
        TextView tvRate;

        MovieViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            itemView.setOnClickListener(this);
        }

        void bind(Movie movie) {
            Picasso.get().load(ApiHelper.BASE_IMAGE_URL+"w780" + movie.getPoster_path()).into(imgPoster);
            tvTime.setText(movie.getRelease_date());
            tvTitle.setText(movie.getTitle());
            tvType.setText(movie.getVoteAverage());
        }

        @Override
        public void onClick(View v) {
            int position = getAdapterPosition();
            Movie movie = movies.get(position);
//
            movie.setTitle(movie.getTitle());
            movie.setOverview(movie.getOverview());
            movie.setPoster_path(movie.getPoster_path());
            movie.setRelease_date(movie.getRelease_date());
            movie.setVoteAverage(movie.getVoteAverage());

            Intent intent = new Intent(itemView.getContext(), DetailActivity.class);
            intent.putExtra(DetailActivity.EXTRA_DATA, movie);
            intent.putExtra(String.valueOf(R.string.type),String.valueOf(R.string.movie));
            itemView.getContext().startActivity(intent);
        }
    }


}

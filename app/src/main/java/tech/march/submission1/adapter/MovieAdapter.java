package tech.march.submission1.adapter;

import android.content.Context;
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
import tech.march.submission1.model.Movie;

public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.ListViewHolder> {
    private final OnItemClickListener listener;
    private ArrayList<Movie> movieArrayList;

    public MovieAdapter(ArrayList<Movie> movieArrayList, OnItemClickListener listener) {
        this.movieArrayList = movieArrayList;
        this.listener = listener;
    }

    @NonNull
    @Override
    public MovieAdapter.ListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_movie, parent, false);
        return new ListViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MovieAdapter.ListViewHolder holder, int position) {
        Movie movie = movieArrayList.get(position);
        holder.click(movie, listener);
        Picasso.get().load(movie.getPoster()).into(holder.imgPoster);
        holder.tvTime.setText(movie.getDate());
        holder.tvTitle.setText(movie.getTitle());
        holder.tvRate.setText(movie.getRate());
        holder.tvType.setText(movie.getArtist());

    }

    @Override
    public int getItemCount() {
        return movieArrayList.size();
    }

    public class ListViewHolder extends RecyclerView.ViewHolder {
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

        public ListViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        public void click(final Movie movie, final OnItemClickListener listener) {
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onClick(movie);
                }
            });
        }

    }

    public interface OnItemClickListener {
        void onClick(Movie item);
    }

}

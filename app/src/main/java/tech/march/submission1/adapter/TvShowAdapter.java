package tech.march.submission1.adapter;

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

public class TvShowAdapter extends RecyclerView.Adapter<TvShowAdapter.ListViewHolder> {
    private final TvShowAdapter.OnItemClickListener listener;
    private ArrayList<Movie> movieArrayList;

    public TvShowAdapter(ArrayList<Movie> movieArrayList, TvShowAdapter.OnItemClickListener listener) {
        this.movieArrayList = movieArrayList;
        this.listener = listener;
    }

    @NonNull
    @Override
    public TvShowAdapter.ListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_tv, parent, false);
        return new TvShowAdapter.ListViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TvShowAdapter.ListViewHolder holder, int position) {
        Movie movie = movieArrayList.get(position);
        holder.click(movie, listener);
        Picasso.get().load(movie.getPoster()).into(holder.imgPoster);
        holder.tvRate.setText(movie.getRate());
    }

    @Override
    public int getItemCount() {
        return movieArrayList.size();
    }

    public class ListViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.img_poster)
        ImageView imgPoster;
        @BindView(R.id.tv_rate)
        TextView tvRate;

        public ListViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        public void click(final Movie movie, final TvShowAdapter.OnItemClickListener listener) {
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

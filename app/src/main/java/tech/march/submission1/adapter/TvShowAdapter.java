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
import tech.march.submission1.api.model.TvShow;

public class TvShowAdapter extends RecyclerView.Adapter<TvShowAdapter.TvViewHolder> {
    private ArrayList<TvShow> tvShows = new ArrayList<>();

    public void setupData(ArrayList<TvShow> items) {
        tvShows.clear();
        tvShows.addAll(items);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public TvViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_movie, parent, false);
        return new TvViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TvViewHolder holder, int position) {
        holder.bind(tvShows.get(position));
    }

    @Override
    public int getItemCount() {
        return tvShows.size();
    }

    class TvViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
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

        TvViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            itemView.setOnClickListener(this);
        }

        void bind(TvShow tvShow) {
            Picasso.get().load(ApiHelper.BASE_IMAGE_URL+"w780" + tvShow.getPoster_path()).into(imgPoster);
            tvTime.setText(tvShow.getFirst_air_date());
            tvTitle.setText(tvShow.getName());
            tvType.setText(tvShow.getVoteAverage());
        }

        @Override
        public void onClick(View v) {
            int position = getAdapterPosition();
            TvShow tvShow = tvShows.get(position);

            tvShow.setName(tvShow.getName());
            tvShow.setOverview(tvShow.getOverview());
            tvShow.setPoster_path(tvShow.getPoster_path());
            tvShow.setFirst_air_date(tvShow.getFirst_air_date());
            tvShow.setVoteAverage(tvShow.getVoteAverage());

            Intent intent = new Intent(itemView.getContext(), DetailActivity.class);
            intent.putExtra(DetailActivity.EXTRA_DATA_TV, tvShow);
            intent.putExtra(String.valueOf(R.string.type),String.valueOf(R.string.tv));
            itemView.getContext().startActivity(intent);
        }
    }


}
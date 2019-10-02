package tech.march.submission1.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import tech.march.submission1.R;
import tech.march.submission1.db.FavoriteData;

import static tech.march.submission1.api.ApiHelper.BASE_IMAGE_URL;

public class FavMovieAdapter extends RecyclerView.Adapter<FavMovieAdapter.ViewHolder> {
    private final ArrayList<FavoriteData> favoriteData = new ArrayList<>();
    private final Activity activity;
    private final PostItemListener postItemListener;


    public FavMovieAdapter(Activity activity, PostItemListener postItemListener) {
        this.activity = activity;
        this.postItemListener = postItemListener;
    }

    public ArrayList<FavoriteData> getFavoriteData() {
        return favoriteData;
    }

    public void setListFavoriteData(ArrayList<FavoriteData> listFavoriteData) {
        if (listFavoriteData.size() > 0) {
            favoriteData.clear();
        }
        favoriteData.addAll(listFavoriteData);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View postView = inflater.inflate(R.layout.item_movie, parent, false);
        return new ViewHolder(postView, this.postItemListener);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        FavoriteData favoriteDatas = favoriteData.get(position);

        holder.tvTitle.setText(favoriteDatas.getTitle());

        Glide.with(activity)
                .load(BASE_IMAGE_URL +"original/"+ favoriteDatas.getPoster())
                .transform(new RoundedCorners(45))
                .into(holder.imgPoster);
    }

    @Override
    public int getItemCount() {
        return favoriteData.size();
    }


    public interface PostItemListener {
        void onPostClick(int mId);
    }

    private FavoriteData getItem(int adapterPosition) {
        return favoriteData.get(adapterPosition);
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
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
        PostItemListener postItemListener;
        public ViewHolder(@NonNull View itemView, PostItemListener postItemListener) {
            super(itemView);


            ButterKnife.bind(this,itemView);

            this.postItemListener = postItemListener;
            itemView.setOnClickListener(this);

        }

        @Override
        public void onClick(View view) {
            FavoriteData item = getItem(getAdapterPosition());
            this.postItemListener.onPostClick(item.getmId());
            notifyDataSetChanged();
        }
    }
}

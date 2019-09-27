package marchtech.app.movieconsume;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import tech.march.submission1.activity.detail.DetailActivity;
import tech.march.submission1.api.ApiHelper;
import tech.march.submission1.database.model.Favorite;

public class FavoriteAdapter extends RecyclerView.Adapter<FavoriteAdapter.ViewHolder> {
    private ArrayList<Favorite> arrayList = new ArrayList<>();

    public void setupData(ArrayList<Favorite> items) {
        arrayList.clear();
        arrayList.addAll(items);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_movie, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.bind(arrayList.get(position));
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
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
        String ids;

        ViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            itemView.setOnClickListener(this);
        }

        void bind(Favorite item) {
            Picasso.get().load(ApiHelper.BASE_IMAGE_URL + "w780" + item.getImage()).into(imgPoster);
            tvTime.setText(item.getDate());
            tvTitle.setText(item.getTitle());
            tvType.setText(item.getArtist());
            ids = item.getID();
        }

        @Override
        public void onClick(View v) {
            Intent intent = new Intent(itemView.getContext(), DetailActivity.class);
            //   intent.putExtra(DetailActivity.EXTRA_DATA_FAV, arrayList.get(getAdapterPosition()));
            intent.putExtra("type", "fav");
            intent.putExtra("id", ids);
            itemView.getContext().startActivity(intent);
        }
    }
}
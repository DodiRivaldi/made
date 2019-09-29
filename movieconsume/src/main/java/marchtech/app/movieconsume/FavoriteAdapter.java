package marchtech.app.movieconsume;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import java.util.List;

public class FavoriteAdapter extends RecyclerView.Adapter<FavoriteAdapter.ViewHolder> {
    private Context context;
    private List<Favorite> movies;

    public FavoriteAdapter(Context context) {
        this.context = context;

    }

    public void setListMovies(List<Favorite> movies) {
        this.movies = movies;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.item_movie, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        final int pos = position;
        String poster = "https://image.tmdb.org/t/p/w500" + movies.get(pos).getImage();
        Glide.with(context)
                .load(poster)
                .apply(new RequestOptions().override(300, 300))
                .into(holder.img);
        holder.txtTitle.setText(movies.get(pos).getTitle());
        holder.txtDateStart.setText(movies.get(pos).getDate());
        holder.imgButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //   Intent detailIntent = new Intent(context, DetailActivity.class);
                // detailIntent.putExtra(DetailActivity.EXTRA_DATA, movies.get(pos));
                // context.startActivity(detailIntent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return movies.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView img;
        TextView txtDateStart;
        TextView rating;
        TextView txtTitle;
        ImageButton imgButton;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imgButton = itemView.findViewById(R.id.button_id);
            rating = itemView.findViewById(R.id.rating_id);
            img = itemView.findViewById(R.id.img_id);
            txtDateStart = itemView.findViewById(R.id.date_id);
            txtTitle = itemView.findViewById(R.id.title_id);
        }
    }
}
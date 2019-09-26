package tech.march.submission1.widget;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Binder;
import android.os.Bundle;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.Target;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

import tech.march.submission1.R;
import tech.march.submission1.api.ApiHelper;
import tech.march.submission1.database.helper.RealmHelper;
import tech.march.submission1.database.model.Favorite;

import static android.provider.Settings.System.CONTENT_URI;

public class StackRemoteViewsFactory implements RemoteViewsService.RemoteViewsFactory {

    private final Context mContext;
    private RealmHelper helper;

    private List<Favorite> FavItems = new ArrayList<>();

    StackRemoteViewsFactory(Context context) {
        mContext = context;
    }

    @Override
    public void onCreate() {
        helper = new RealmHelper(mContext);
        FavItems.addAll(helper.getAllDataWidget());
    }

    @Override
    public void onDataSetChanged() {
        helper = new RealmHelper(mContext);
        FavItems.addAll(helper.getAllDataWidget());
    }

    @Override
    public void onDestroy() {

    }

    @Override
    public int getCount() {
        return FavItems.size();
    }

    @Override
    public RemoteViews getViewAt(int position) {
        Favorite item = FavItems.get(position);
        RemoteViews rv = new RemoteViews(mContext.getPackageName(), R.layout.item_widget);
        Bitmap bitmap = null;
        try {
            //noinspection deprecation
            bitmap = Glide.with(mContext)
                    .asBitmap()
                    .load(ApiHelper.BASE_IMAGE_URL + "w780" +   item.getImage())
                    .into(Target.SIZE_ORIGINAL, Target.SIZE_ORIGINAL)
                    .get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

        rv.setImageViewBitmap(R.id.img_widget, bitmap);

        Bundle extras = new Bundle();
        extras.putInt(FavoriteWidget.EXTRA_ITEM, position);
        Intent fillInIntent = new Intent();
        fillInIntent.putExtras(extras);

        rv.setOnClickFillInIntent(R.id.img_widget, fillInIntent);

        return rv;
    }

    @Override
    public RemoteViews getLoadingView() {
        return null;
    }

    @Override
    public int getViewTypeCount() {
        return 1;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

}
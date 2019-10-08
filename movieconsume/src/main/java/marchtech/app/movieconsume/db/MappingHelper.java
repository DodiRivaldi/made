package marchtech.app.movieconsume.db;

import android.database.Cursor;


import java.util.ArrayList;

import marchtech.app.movieconsume.model.FavoriteData;

import static android.provider.BaseColumns._ID;
import static marchtech.app.movieconsume.db.DatabaseContract.FavoriteColumns.CATEGORY;
import static marchtech.app.movieconsume.db.DatabaseContract.FavoriteColumns.ID;
import static marchtech.app.movieconsume.db.DatabaseContract.FavoriteColumns.OVERVIEW;
import static marchtech.app.movieconsume.db.DatabaseContract.FavoriteColumns.POSTER;
import static marchtech.app.movieconsume.db.DatabaseContract.FavoriteColumns.RATING;
import static marchtech.app.movieconsume.db.DatabaseContract.FavoriteColumns.TITLE;

public class MappingHelper {
    public static ArrayList<FavoriteData> getMovieFavoriteList(Cursor cursor) {
        ArrayList<FavoriteData> list = new ArrayList<>();

        while (cursor.moveToNext()) {
            int id = cursor.getInt(cursor.getColumnIndexOrThrow(ID));
            int mId = cursor.getInt(cursor.getColumnIndexOrThrow(ID));
            String title = cursor.getString(cursor.getColumnIndexOrThrow(TITLE));
            String overview = cursor.getString(cursor.getColumnIndexOrThrow(OVERVIEW));
            String poster = cursor.getString(cursor.getColumnIndexOrThrow(POSTER));
            double rating = cursor.getDouble(cursor.getColumnIndexOrThrow(RATING));
            String category = cursor.getString(cursor.getColumnIndexOrThrow(CATEGORY));

            if (category.equals("movie")) {
                list.add(new FavoriteData(id, mId ,rating, title, overview, poster, category));
            }
        }
        return list;
    }

    public static ArrayList<FavoriteData> getTvFavoriteList(Cursor cursor) {
        ArrayList<FavoriteData> list = new ArrayList<>();

        while (cursor.moveToNext()) {
            int id = cursor.getInt(cursor.getColumnIndexOrThrow(ID));
            int mId = cursor.getInt(cursor.getColumnIndexOrThrow(ID));
            String title = cursor.getString(cursor.getColumnIndexOrThrow(TITLE));
            String overview = cursor.getString(cursor.getColumnIndexOrThrow(OVERVIEW));
            String poster = cursor.getString(cursor.getColumnIndexOrThrow(POSTER));
            double rating = cursor.getDouble(cursor.getColumnIndexOrThrow(RATING));
            String category = cursor.getString(cursor.getColumnIndexOrThrow(CATEGORY));

            if (category.equals("tv")) {
                list.add(new FavoriteData(id, mId ,rating, title, overview, poster, category));
            }
        }
        return list;
    }
}

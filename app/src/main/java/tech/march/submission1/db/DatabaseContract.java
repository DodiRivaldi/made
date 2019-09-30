package tech.march.submission1.db;

import android.database.Cursor;
import android.net.Uri;
import android.provider.BaseColumns;

public class DatabaseContract {
    public static final String AUTHORITY = "tech.march.submission1";
    private static final String SCHEME = "content";

    private DatabaseContract() {

    }

    public static final class FavoriteColumns implements BaseColumns {
        public static final String TABLE_NAME = "favorite";
        public static final String ID = "mId";
        public static final String POSTER = "poster";
        public static final String TITLE = "title";
        public static final String RATING = "rating";
        public static final String OVERVIEW = "overview";
        public static final String CATEGORY = "category";

        public static final Uri CONTENT_URI = new Uri.Builder().scheme(SCHEME)
                .authority(AUTHORITY)
                .appendPath(TABLE_NAME)
                .build();
    }

    public static String getColumnString(Cursor cursor, String columnName) {
        return cursor.getString(cursor.getColumnIndex(columnName));
    }

    public static int getColumnInt(Cursor cursor, String columnName) {
        return cursor.getInt(cursor.getColumnIndex(columnName));
    }

    public static double getColumnDouble(Cursor cursor, String columnName) {
        return cursor.getDouble(cursor.getColumnIndex(columnName));
    }
}

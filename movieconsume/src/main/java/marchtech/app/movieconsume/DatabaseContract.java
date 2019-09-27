package marchtech.app.movieconsume;

import android.net.Uri;

public class DatabaseContract {
    private static final String TABLE_NAME = "mademovie.db";
    private static final String AUTHORITY = "tech.march.submission1";
    private static final String SCHEME = "content";
    public static final Uri URI_MOVIE = new Uri.Builder().scheme(SCHEME)
            .authority(AUTHORITY)
            .appendPath(TABLE_NAME)
            .build();
}

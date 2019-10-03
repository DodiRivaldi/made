package marchtech.app.movieconsume.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import static marchtech.app.movieconsume.db.DatabaseContract.FavoriteColumns.TABLE_NAME;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "dbfavorite";
    private static final int DATABASE_VERSION = 1;
    public static final String SQL_CREATE_TABLE = String.format("CREATE TABLE %s"
            + " (%s INTEGER PRIMARY KEY AUTOINCREMENT," +
            " %s TEXT NOT NULL," +
            " %s TEXT NOT NULL," +
            " %s TEXT NOT NULL," +
            " %s TEXT NOT NULL," +
            " %s TEXT NOT NULL," +
            "%s TEXT NOT NULL)",
            TABLE_NAME,
            DatabaseContract.FavoriteColumns._ID,
            DatabaseContract.FavoriteColumns.ID,
            DatabaseContract.FavoriteColumns.TITLE,
            DatabaseContract.FavoriteColumns.POSTER,
            DatabaseContract.FavoriteColumns.OVERVIEW,
            DatabaseContract.FavoriteColumns.RATING,
            DatabaseContract.FavoriteColumns.CATEGORY
            );

    DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(SQL_CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(sqLiteDatabase);
    }
}

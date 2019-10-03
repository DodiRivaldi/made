package marchtech.app.movieconsume.db;

import android.database.Cursor;

public interface LoadFavoriteCallback {
    void preExecute();
    void postExecute(Cursor cursor);
}

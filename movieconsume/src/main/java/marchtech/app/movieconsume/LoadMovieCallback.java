package marchtech.app.movieconsume;

import android.database.Cursor;

public interface LoadMovieCallback {
    void onPostExecute(Cursor movies);
}

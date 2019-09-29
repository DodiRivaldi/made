package marchtech.app.movieconsume;

import android.database.Cursor;

import java.util.ArrayList;

public class Helper {
    public static ArrayList<Favorite>mapCursorToArrayList (Cursor moviesCursor)
    {
        ArrayList<Favorite> movies = new ArrayList<>();

        if(moviesCursor != null)
        {
            while(moviesCursor.moveToNext())
            {
                String id = moviesCursor.getString(moviesCursor.getColumnIndexOrThrow("ID"));
                String image = moviesCursor.getString(moviesCursor.getColumnIndexOrThrow("image"));
                String title = moviesCursor.getString(moviesCursor.getColumnIndexOrThrow("title"));
                String artist = moviesCursor.getString(moviesCursor.getColumnIndexOrThrow("artist"));
                String date = moviesCursor.getString(moviesCursor.getColumnIndexOrThrow("date"));
                String overview = moviesCursor.getString(moviesCursor.getColumnIndexOrThrow("overview"));
                String type = moviesCursor.getString(moviesCursor.getColumnIndexOrThrow("type"));

                movies.add(new Favorite(id,image,title,artist,date,overview,type));
            }
        }
        return movies;
    }
}

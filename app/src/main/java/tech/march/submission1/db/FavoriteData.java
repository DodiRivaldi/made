package tech.march.submission1.db;

import android.database.Cursor;
import android.os.Parcel;
import android.os.Parcelable;

import static tech.march.submission1.db.DatabaseContract.FavoriteColumns.CATEGORY;
import static tech.march.submission1.db.DatabaseContract.FavoriteColumns.ID;
import static tech.march.submission1.db.DatabaseContract.FavoriteColumns.OVERVIEW;
import static tech.march.submission1.db.DatabaseContract.FavoriteColumns.POSTER;
import static tech.march.submission1.db.DatabaseContract.FavoriteColumns.RATING;
import static tech.march.submission1.db.DatabaseContract.FavoriteColumns.TITLE;
import static tech.march.submission1.db.DatabaseContract.getColumnDouble;
import static tech.march.submission1.db.DatabaseContract.getColumnInt;
import static tech.march.submission1.db.DatabaseContract.getColumnString;

public class FavoriteData implements Parcelable {
    private int id;
    private int mId;
    private String poster;
    private String category;

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getmId() {
        return mId;
    }

    public void setmId(int mId) {
        this.mId = mId;
    }

    public String getPoster() {
        return poster;
    }

    public void setPoster(String poster) {
        this.poster = poster;
    }

    public double getRating() {
        return rating;
    }

    public void setRating(double rating) {
        this.rating = rating;
    }

    public String getOverview() {
        return overview;
    }

    public void setOverview(String overview) {
        this.overview = overview;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    private double rating;
    private String overview;
    private String title;

    public FavoriteData() {
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public FavoriteData(int id, int mId, double rating, String title, String overview, String poster, String category) {
        this.id = id;
        this.mId = mId;
        this.rating = rating;
        this.poster = poster;
        this.overview = overview;
        this.title = title;
        this.category = category;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.id);
        dest.writeInt(this.mId);
        dest.writeString(this.poster);
        dest.writeString(this.category);
        dest.writeDouble(this.rating);
        dest.writeString(this.overview);
        dest.writeString(this.title);
    }

    protected FavoriteData(Parcel in) {
        this.id = in.readInt();
        this.mId = in.readInt();
        this.poster = in.readString();
        this.category = in.readString();
        this.rating = in.readDouble();
        this.overview = in.readString();
        this.title = in.readString();
    }

    public FavoriteData(Cursor cursor) {
       this.id = getColumnInt(cursor, ID);
        this.mId = getColumnInt(cursor, ID);
        this.title = getColumnString(cursor, TITLE);
        this.rating = getColumnDouble(cursor, RATING);
        this.overview = getColumnString(cursor, OVERVIEW);
        this.category = getColumnString(cursor, CATEGORY);
        this.poster = getColumnString(cursor, POSTER);
    }

    public static final Creator<FavoriteData> CREATOR = new Creator<FavoriteData>() {
        @Override
        public FavoriteData createFromParcel(Parcel source) {
            return new FavoriteData(source);
        }

        @Override
        public FavoriteData[] newArray(int size) {
            return new FavoriteData[size];
        }
    };
}

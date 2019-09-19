package tech.march.submission1.database.model;

import android.database.Cursor;
import android.os.Parcel;
import android.os.Parcelable;

public class Favorite implements Parcelable {
    private String ID;
    private String image;
    private String title;
    private String artist;
    private String date;
    private String overview;
    private String type;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Favorite(String ID, String image, String title, String artist, String date, String overview, String type) {
        this.ID = ID;
        this.image = image;
        this.title = title;
        this.artist = artist;
        this.date = date;
        this.overview = overview;
        this.type = type;
    }

    public Favorite (Cursor curso){
        this.ID =ID;
        this.image = image;
        this.title = title;
        this.artist = artist;
        this.date = date;
        this.overview = overview;
        this.type = type;

    }

    protected Favorite(Parcel in) {
        ID = in.readString();
        image = in.readString();
        title = in.readString();
        artist = in.readString();
        date = in.readString();
        overview = in.readString();
    }

    public static final Creator<Favorite> CREATOR = new Creator<Favorite>() {
        @Override
        public Favorite createFromParcel(Parcel in) {
            return new Favorite(in);
        }

        @Override
        public Favorite[] newArray(int size) {
            return new Favorite[size];
        }
    };

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getArtist() {
        return artist;
    }

    public void setArtist(String artist) {
        this.artist = artist;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getOverview() {
        return overview;
    }

    public void setOverview(String overview) {
        this.overview = overview;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(ID);
        parcel.writeString(image);
        parcel.writeString(title);
        parcel.writeString(artist);
        parcel.writeString(date);
        parcel.writeString(overview);
        parcel.writeString(type);
    }
}

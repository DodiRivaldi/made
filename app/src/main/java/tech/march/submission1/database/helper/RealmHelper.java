package tech.march.submission1.database.helper;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.ArrayList;

import io.realm.Realm;
import io.realm.RealmResults;
import io.realm.Sort;
import tech.march.submission1.R;
import tech.march.submission1.activity.main.MainActivity;
import tech.march.submission1.api.model.Movie;
import tech.march.submission1.database.model.Favorite;
import tech.march.submission1.database.object.FavoriteObject;

public class RealmHelper extends ViewModel {

    private static final String TAG = "RealmHelper";

    private Realm realm;
    private RealmResults<FavoriteObject> realmResult;
    private MutableLiveData<ArrayList<Favorite>> favorite = new MutableLiveData<>();

    public Context context;

    public RealmHelper(Context context) {
        realm = Realm.getDefaultInstance();
        this.context = context;
    }

    public void addFavorite(String ID, String image, String title, String artist, String date, String overview,String type) {

        FavoriteObject object = new FavoriteObject();

        object.setID(ID);
        object.setImage(image);
        object.setTitle(title);
        object.setArtist(artist);
        object.setDate(date);
        object.setOverview(overview);
        object.setType(type);

        if (realm.isInTransaction()) {
            realm.copyToRealm(object);
            realm.commitTransaction();
        } else {
            realm.beginTransaction();
            realm.copyToRealm(object);
            realm.commitTransaction();
        }

        Toast.makeText(context, R.string.saved, Toast.LENGTH_SHORT).show();
        context.startActivity(new Intent(context, MainActivity.class));
    }

    public ArrayList<Favorite> getAllData(String type) {
        ArrayList<Favorite> data = new ArrayList<>();

        realmResult = realm.where(FavoriteObject.class).equalTo("type", type).findAll();

        realmResult.sort("ID", Sort.ASCENDING);
        if (realmResult.size() > 0) {

            for (int i = 0; i < realmResult.size(); i++) {
                String ID, image, title, artist, date, overview, Stype;

                ID = realmResult.get(i).getID();
                image = realmResult.get(i).getImage();
                title = realmResult.get(i).getTitle();
                artist = realmResult.get(i).getArtist();
                date = realmResult.get(i).getArtist();
                overview = realmResult.get(i).getOverview();
                Stype = realmResult.get(i).getType();

                data.add(new Favorite(ID, image, title, artist, date, overview, Stype));
            }
            favorite.postValue(data);


        } else {
            //   showToast("Tidak ada data tersimpan");
        }

        return data;
    }

    public ArrayList<Favorite> checkData(String id) {
        ArrayList<Favorite> data = new ArrayList<>();

        realmResult = realm.where(FavoriteObject.class).equalTo("ID", id).findAll();
        realmResult.sort("ID", Sort.ASCENDING);
        if (realmResult.size() > 0) {

            for (int i = 0; i < realmResult.size(); i++) {
                String ID, image, title, artist, date, overview,type;

                ID = realmResult.get(i).getID();
                image = realmResult.get(i).getImage();
                title = realmResult.get(i).getTitle();
                artist = realmResult.get(i).getArtist();
                date = realmResult.get(i).getArtist();
                overview = realmResult.get(i).getOverview();
                type = realmResult.get(i).getType();

                data.add(new Favorite(ID, image, title, artist, date, overview,type));
            }
            favorite.postValue(data);


        } else {
            //   showToast("Tidak ada data tersimpan");
        }

        return data;
    }

    public void deleteData(String ID) {
        try {
            RealmResults<FavoriteObject> realmResults = realm.where(FavoriteObject.class).equalTo("ID", ID).findAll();
            if (realm.isInTransaction()) {
                realmResults.deleteFromRealm(0);
                realm.commitTransaction();
            } else {
                realm.beginTransaction();
                realmResults.deleteFromRealm(0);
                realm.commitTransaction();
            }

            //    showToast("Hapus data berhasil.");
        } finally {
            //      realm.close();
        }
    }

    public LiveData<ArrayList<Favorite>> getFavorite() {
        return favorite;
    }


}

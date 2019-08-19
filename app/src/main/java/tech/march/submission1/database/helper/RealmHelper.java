package tech.march.submission1.database.helper;

import android.content.Context;
import android.widget.Toast;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.ArrayList;

import io.realm.Realm;
import io.realm.RealmResults;
import io.realm.Sort;
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

    public void addFavorite(String ID, String image, String title, String artist, String date, String overview) {

        FavoriteObject object = new FavoriteObject();

        object.setID(ID);
        object.setImage(image);
        object.setTitle(title);
        object.setArtist(artist);
        object.setDate(date);
        object.setOverview(overview);

        if (realm.isInTransaction()) {
            realm.copyToRealm(object);
            realm.commitTransaction();
        } else {
            realm.beginTransaction();
            realm.copyToRealm(object);
            realm.commitTransaction();
        }

        Toast.makeText(context, "Sukses", Toast.LENGTH_SHORT).show();
    }

    public ArrayList<Favorite> getAllData() {
        ArrayList<Favorite> data = new ArrayList<>();

        realmResult = realm.where(FavoriteObject.class).findAll();
        realmResult.sort("ID", Sort.ASCENDING);
        if (realmResult.size() > 0) {

            for (int i = 0; i < realmResult.size(); i++) {
                String ID, image, title, artist, date, overview;

                ID = realmResult.get(i).getID();
                image = realmResult.get(i).getImage();
                title = realmResult.get(i).getTitle();
                artist = realmResult.get(i).getArtist();
                date = realmResult.get(i).getArtist();
                overview = realmResult.get(i).getOverview();

                data.add(new Favorite(ID, image, title, artist, date, overview));
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
                String ID, image, title, artist, date, overview;

                ID = realmResult.get(i).getID();
                image = realmResult.get(i).getImage();
                title = realmResult.get(i).getTitle();
                artist = realmResult.get(i).getArtist();
                date = realmResult.get(i).getArtist();
                overview = realmResult.get(i).getOverview();

                data.add(new Favorite(ID, image, title, artist, date, overview));
            }
            favorite.postValue(data);


        } else {
            //   showToast("Tidak ada data tersimpan");
        }

        return data;
    }

    public void deleteStrand(String ID) {
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

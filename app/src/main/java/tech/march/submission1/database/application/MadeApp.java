package tech.march.submission1.database.application;

import android.app.Application;

import io.realm.Realm;
import io.realm.RealmConfiguration;

public class MadeApp extends Application {
    @Override
    public void onCreate() {
        super.onCreate();

        Realm.init(this);
        RealmConfiguration configuration = new RealmConfiguration.Builder()
                .name("mademovie.db")
                .schemaVersion(0)
                .deleteRealmIfMigrationNeeded()
                .build();

        Realm.setDefaultConfiguration(configuration);

    }
}

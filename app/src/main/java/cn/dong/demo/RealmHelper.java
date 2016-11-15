package cn.dong.demo;

import android.content.Context;

import io.realm.Realm;
import io.realm.RealmConfiguration;

/**
 * @author dong on 16/2/25.
 */
public class RealmHelper {

    public static void init(Context context) {
        Realm.init(context);
        RealmConfiguration config = new RealmConfiguration.Builder()
                .deleteRealmIfMigrationNeeded()
                .build();
        Realm.setDefaultConfiguration(config);
    }
}

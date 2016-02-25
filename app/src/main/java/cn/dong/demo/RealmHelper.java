package cn.dong.demo;

import io.realm.Realm;
import io.realm.RealmConfiguration;

/**
 * @author dong on 16/2/25.
 */
public class RealmHelper {

    public static void init() {
        RealmConfiguration config = new RealmConfiguration.Builder(MyApp.getInstance())
                .name(Realm.DEFAULT_REALM_NAME)
                .deleteRealmIfMigrationNeeded()
                .build();
        Realm.setDefaultConfiguration(config);
    }
}

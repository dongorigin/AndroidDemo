package cn.dong.demo;

import android.content.Context;
import android.os.Looper;

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

    /**
     * 延时更新，以期望主线程数据自动更新到了最新
     *
     * @param runnable 在数据更新后期望做的操作，操作会运行在主线程
     */
    public static void delayUpdate(final Realm realm, final Runnable runnable) {
        if (Looper.myLooper() != Looper.getMainLooper()) {
            throw new IllegalStateException("only work on main thread");
        }
        AndroidUtilities.runOnUIThread(new Runnable() {
            @Override
            public void run() {
                if (!realm.isClosed()) {
                    runnable.run();
                }
            }
        }, 500);
    }
}

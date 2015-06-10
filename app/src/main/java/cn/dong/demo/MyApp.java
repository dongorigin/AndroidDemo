package cn.dong.demo;

import android.app.Application;
import android.os.StrictMode;

public class MyApp extends Application {

    private static MyApp instantce;

    public static MyApp getInstance() {
        return instantce;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        instantce = this;
        initStrictMode();
    }

    /**
     * 初始化StrictMode
     */
    private void initStrictMode() {
        if (BuildConfig.DEBUG) {
            StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder()
                    .detectAll()
                    .penaltyLog()
                            // .penaltyDeath()
                    .build());
            StrictMode.setVmPolicy(new StrictMode.VmPolicy.Builder()
                    .detectAll()
                    .penaltyLog()
                            // .penaltyDeath()
                    .build());
        }
    }

}

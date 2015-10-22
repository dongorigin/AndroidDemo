package cn.dong.demo;

import android.app.Application;
import android.os.StrictMode;
import android.util.Log;

import timber.log.Timber;

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
        initLogger();
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


    private void initLogger() {
        if (BuildConfig.DEBUG) {
            Timber.plant(new Timber.DebugTree());
        } else {
            Timber.plant(new LogReportingTree());
        }
    }

    private static class LogReportingTree extends Timber.Tree {
        @Override
        protected void log(int priority, String tag, String message, Throwable t) {
            if (priority == Log.VERBOSE || priority == Log.DEBUG || priority == Log.INFO) {
                return;
            }
            // 将部分日志报告服务器
        }
    }

}

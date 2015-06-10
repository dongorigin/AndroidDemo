package cn.dong.demo.service;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;

import cn.dong.demo.util.L;

public class MyService extends Service {
    private static final String TAG = MyService.class.getSimpleName();

    public MyService() {
    }

    @Override
    public void onCreate() {
        L.d(TAG, "onCreate");
        super.onCreate();
    }

    @Override
    public void onDestroy() {
        L.d(TAG, "onDestroy");
        super.onDestroy();
    }

    @Override
    public IBinder onBind(Intent intent) {
        L.d(TAG, "onBind");
        return new Binder();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        L.d(TAG, L.getCurrentMethodName());
        return super.onStartCommand(intent, flags, startId);
    }
}

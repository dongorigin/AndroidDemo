package cn.dong.demo.util;

import android.content.Context;
import android.media.AudioManager;
import android.support.v4.app.NotificationManagerCompat;

import cn.dong.demo.MyApp;
import timber.log.Timber;

/**
 * @author dong on 16/1/21.
 */
public enum NotificationHelper {
    INSTANCE; // 单例

    private static final long VIBRATE_INTERVAL = 4000; // 震动间隔

    private final NotificationManagerCompat mNotificationManager;
    private final AudioManager audioManager;

    private long lastVibrateTime = 0; // 上次震动的时间

    NotificationHelper() {
        mNotificationManager = NotificationManagerCompat.from(MyApp.getInstance());
        audioManager = (AudioManager) MyApp.getInstance().getSystemService(Context.AUDIO_SERVICE);
    }

    public void vibrateAndSound() {
        Timber.d("RingerMode %d", audioManager.getRingerMode());
        long now = System.currentTimeMillis();
        if (lastVibrateTime == 0 || now - lastVibrateTime >= VIBRATE_INTERVAL) {
            lastVibrateTime = now;
            VibratorManager.INSTANCE.vibrate();
            RingManager.INSTANCE.play();
        }
    }
}

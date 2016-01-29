package cn.dong.demo.util;

import android.media.MediaPlayer;
import android.media.RingtoneManager;
import android.net.Uri;

import cn.dong.demo.MyApp;

/**
 * @author dong on 16/1/21.
 */
public enum RingManager {
    INSTANCE; // 单例

    private MediaPlayer mediaPlayer;

    RingManager() {
        Uri notificationUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        mediaPlayer = MediaPlayer.create(MyApp.getInstance(), notificationUri);
    }

    public void play() {
        if (mediaPlayer != null) {
            mediaPlayer.start();
        }
    }
}

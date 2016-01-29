package cn.dong.demo.util;

import android.app.Service;
import android.os.Vibrator;

import cn.dong.demo.MyApp;

/**
 * 振动器管理
 *
 * @author dong on 16/1/4.
 */
public enum VibratorManager {
    INSTANCE; // 单例

    public static final int VIBRATION_TIME = 250;

    private final Vibrator vibrator;

    VibratorManager() {
        vibrator = (Vibrator) MyApp.getInstance().getSystemService(Service.VIBRATOR_SERVICE);
    }

    public void vibrate() {
        vibrator.vibrate(new long[]{0, VIBRATION_TIME, VIBRATION_TIME, VIBRATION_TIME}, -1);
    }
}

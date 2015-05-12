package cn.dong.demo.util;

import android.content.Context;
import android.util.Log;

import cn.dong.demo.MyApp;

/**
 * 日志工具类
 *
 * @author dong 2014-11-18
 */
public class L {
    private static final String TAG = "log";
    private static Context sContext = MyApp.getInstance();

    public static void v(String tag, int id) {
        log(Log.VERBOSE, tag, sContext.getResources().getString(id));
    }

    public static void v(String tag, String msg) {
        log(Log.VERBOSE, tag, msg);
    }

    public static void d(String msg) {
        d(TAG, msg);
    }

    public static void d(String tag, int id) {
        log(Log.DEBUG, tag, sContext.getResources().getString(id));
    }

    public static void d(String tag, String msg) {
        log(Log.DEBUG, tag, msg);
    }

    public static void d(String tag, String format, Object... args) {
        log(Log.DEBUG, tag, format, args);
    }

    public static void i(String tag, int id) {
        log(Log.INFO, tag, sContext.getResources().getString(id));
    }

    public static void i(String tag, String msg) {
        log(Log.INFO, tag, msg);
    }

    public static void i(String tag, String format, Object... args) {
        log(Log.INFO, tag, format, args);
    }

    public static void w(String tag, int id) {
        log(Log.WARN, tag, sContext.getResources().getString(id));
    }

    public static void w(String tag, String msg) {
        log(Log.WARN, tag, msg);
    }

    public static void e(String tag, int id) {
        log(Log.ERROR, tag, sContext.getResources().getString(id));
    }

    public static void e(String tag, String msg) {
        log(Log.ERROR, tag, msg);
    }

    private static void log(int priority, String tag, String format, Object... args) {
        log(priority, tag, String.format(format, args));
    }

    private static void log(int priority, String tag, String msg) {
        Log.println(priority, tag, msg);
    }
}

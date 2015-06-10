package cn.dong.demo.util;

import android.content.Context;
import android.text.TextUtils;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * 常用工具
 *
 * @author dong 2014-11-13
 */
public class CommonUtils {
    /**
     * 重复点击最小间隔时间
     */
    private static final long MIN_INTERVAL = 1000;
    /**
     * 上次成功点击的时间
     */
    private static long lastClickTime;

    /**
     * 是否为快速重复点击
     */
    public static boolean isFastClick() {
        long curTime = System.currentTimeMillis();
        long intervalTime = curTime - lastClickTime;
        if (intervalTime < MIN_INTERVAL) {
            return true;
        } else {
            lastClickTime = curTime;
            return false;
        }
    }

    /**
     * 隐藏软键盘
     *
     * @param context
     * @param view
     */
    public static void hideSoftInput(Context context, View view) {
        InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

    /**
     * MD5散列 大写字母
     *
     * @param input
     * @return
     */
    public static String getMd5Upper(String input) {
        return getMd5Lower(input).toUpperCase();
    }

    /**
     * MD5散列 小写字母
     *
     * @param intput 字符串原文
     * @return MD5散列后的字符串
     */
    public static String getMd5Lower(String intput) {
        if (TextUtils.isEmpty(intput)) {
            throw new IllegalArgumentException("input not be empty");
        }
        try {
            // Create MD5 Hash
            MessageDigest digest = MessageDigest.getInstance("MD5");
            digest.update(intput.getBytes());
            byte[] messageDigest = digest.digest();

            // Create Hex String
            StringBuilder hexString = new StringBuilder();
            for (byte b : messageDigest) {
                String h = Integer.toHexString(0xFF & b);
                while (h.length() < 2) {
                    h = "0" + h;
                }
                hexString.append(h);
            }
            return hexString.toString();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return "";
        }
    }
}

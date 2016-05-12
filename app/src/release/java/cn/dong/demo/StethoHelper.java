package cn.dong.demo;

import android.content.Context;

import okhttp3.OkHttpClient;

/**
 * @author dong on 16/5/12.
 */
public class StethoHelper {
    public static void init(Context context) {

    }

    public static OkHttpClient.Builder setupInterceptor(OkHttpClient.Builder builder) {
        return builder;
    }
}

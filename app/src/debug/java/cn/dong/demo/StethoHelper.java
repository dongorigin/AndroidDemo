package cn.dong.demo;

import android.content.Context;

import com.facebook.stetho.Stetho;
import com.facebook.stetho.okhttp3.StethoInterceptor;

import okhttp3.OkHttpClient;

/**
 * @author dong on 16/5/12.
 */
public class StethoHelper {
    public static void init(Context context) {
        Stetho.initializeWithDefaults(context);
    }

    public static OkHttpClient.Builder setupInterceptor(OkHttpClient.Builder builder) {
        return builder.addNetworkInterceptor(new StethoInterceptor());
    }
}

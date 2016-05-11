package cn.dong.demo;

import android.content.Context;

import com.facebook.stetho.okhttp3.StethoInterceptor;
import com.jakewharton.picasso.OkHttp3Downloader;
import com.squareup.picasso.Picasso;

import okhttp3.OkHttpClient;
import timber.log.Timber;

/**
 * @author dong on 16/5/11.
 */
public enum PicassoHelper {
    INSTANCE;

    private final OkHttpClient okHttpClient;

    PicassoHelper() {
        okHttpClient = new OkHttpClient.Builder()
                .addNetworkInterceptor(new StethoInterceptor())
                .build();
    }

    public OkHttpClient getOkHttpClient() {
        return okHttpClient;
    }

    public void init(Context context) {
        Picasso picasso = new Picasso.Builder(context)
                .downloader(new OkHttp3Downloader(okHttpClient))
                .build();
        try {
            Picasso.setSingletonInstance(picasso);
        } catch (IllegalStateException e) {
            Timber.d(e, "");
        }
    }
}

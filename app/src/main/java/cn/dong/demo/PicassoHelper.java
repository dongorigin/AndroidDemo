package cn.dong.demo;

import android.content.Context;

import com.jakewharton.picasso.OkHttp3Downloader;
import com.squareup.picasso.Picasso;

import timber.log.Timber;

/**
 * @author dong on 16/5/11.
 */
public final class PicassoHelper {
    public static void init(Context context) {
        Picasso picasso = new Picasso.Builder(context)
                .downloader(new OkHttp3Downloader(OkHttpHelper.INSTANCE.getOkHttpClient()))
                .build();
        try {
            Picasso.setSingletonInstance(picasso);
        } catch (IllegalStateException e) {
            Timber.d(e, "");
        }
    }
}

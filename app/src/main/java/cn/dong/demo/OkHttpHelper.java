package cn.dong.demo;

import okhttp3.OkHttpClient;

/**
 * @author dong on 16/5/12.
 */
public enum OkHttpHelper {
    INSTANCE;

    private final OkHttpClient okHttpClient;

    OkHttpHelper() {
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        okHttpClient = StethoHelper.setupInterceptor(builder).build();
    }

    public OkHttpClient getOkHttpClient() {
        return okHttpClient;
    }
}

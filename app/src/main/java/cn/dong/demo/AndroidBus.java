package cn.dong.demo;

import android.os.Handler;
import android.os.Looper;

import com.squareup.otto.Bus;

/**
 * @author dong on 15/9/10.
 */
public class AndroidBus extends Bus {
    private final Handler mainHandler = new Handler(Looper.getMainLooper());

    @Override
    public void post(final Object event) {
        if (Looper.myLooper() == Looper.getMainLooper()) {
            super.post(event);
        } else {
            mainHandler.post(new Runnable() {
                @Override
                public void run() {
                    AndroidBus.super.post(event);
                }
            });
        }
    }
}

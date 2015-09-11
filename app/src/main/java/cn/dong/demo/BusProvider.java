package cn.dong.demo;

import com.squareup.otto.Bus;

/**
 * @author dong on 15/9/10.
 */
public final class BusProvider {
    private static final Bus sBus = new AndroidBus();

    public static Bus getInstance() {
        return sBus;
    }

    private BusProvider() {
        // No instances.
    }
}

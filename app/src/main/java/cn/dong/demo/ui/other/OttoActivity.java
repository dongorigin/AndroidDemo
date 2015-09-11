package cn.dong.demo.ui.other;

import android.os.Bundle;

import com.squareup.otto.Subscribe;

import butterknife.OnClick;
import cn.dong.demo.BusProvider;
import cn.dong.demo.R;
import cn.dong.demo.TestEvent;
import cn.dong.demo.ui.common.BaseActivity;
import cn.dong.demo.util.L;

/**
 * @author dong on 15/9/10.
 */
public class OttoActivity extends BaseActivity {

    @Override
    protected int initPageLayoutID() {
        return R.layout.activity_otto;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        BusProvider.getInstance().register(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        BusProvider.getInstance().unregister(this);
    }

    @OnClick(R.id.post_main)
    void mainPost() {
        BusProvider.getInstance().post(new TestEvent());
    }

    @OnClick(R.id.post_background)
    void bgPost() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                BusProvider.getInstance().post(new TestEvent());
            }
        }).start();
    }

    @Subscribe
    public void onReceive(TestEvent event) {
        L.d(TAG, "onReceive ThreadId = %d", Thread.currentThread().getId());
    }

}

package cn.dong.demo.ui.library;

import android.os.Bundle;

import com.squareup.otto.Subscribe;

import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

import butterknife.OnClick;
import cn.dong.demo.BusProvider;
import cn.dong.demo.R;
import cn.dong.demo.TestEvent;
import cn.dong.demo.ui.common.BaseActivity;
import timber.log.Timber;

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
        BusProvider.getInstance().post(new TestEvent("main"));
    }

    @OnClick(R.id.post_background)
    void bgPost() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                Timer timer = new Timer();
                timer.schedule(new TimerTask() {
                    int count = 0;

                    @Override
                    public void run() {
                        if (count > 5) {
                            cancel();
                        }
                        BusProvider.getInstance().post(new TestEvent("bg" + count));
                        count++;
                    }
                }, 0, 10);
            }
        }).start();
    }

    @Subscribe
    public void onReceive(TestEvent event) {
        Timber.d("onReceive event name = %s: start", event.name);
        // 耗时任务，大约需要2000ms
        Random random = new Random();
        String sum = "";
        for (int i = 0; i < 20000; i++) {
            sum += random.nextInt();
        }
        Timber.d("onReceive event name = %s: end", event.name);
    }

}

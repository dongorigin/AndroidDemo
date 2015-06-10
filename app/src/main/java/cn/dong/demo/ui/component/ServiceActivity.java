package cn.dong.demo.ui.component;

import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;

import butterknife.OnClick;
import cn.dong.demo.R;
import cn.dong.demo.service.MyService;
import cn.dong.demo.ui.common.BaseActivity;

/**
 * author DONG 2015/5/28.
 */
public class ServiceActivity extends BaseActivity implements ServiceConnection {

    private Intent mServiceIntent;

    @Override
    protected void init() {
        super.init();
        mServiceIntent = new Intent(mContext, MyService.class);
    }

    @Override
    protected int initPageLayoutID() {
        return R.layout.activity_service;
    }

    @OnClick(R.id.btn_start_service)
    void start() {
        startService(mServiceIntent);
    }

    @OnClick(R.id.btn_stop_service)
    void stop() {
        stopService(mServiceIntent);
    }

    @OnClick(R.id.btn_bind_service)
    void bind() {
        bindService(mServiceIntent, this, BIND_AUTO_CREATE);
    }

    @OnClick(R.id.btn_unbind_service)
    void unbind() {
        unbindService(this);
    }

    @Override
    public void onServiceConnected(ComponentName name, IBinder service) {

    }

    @Override
    public void onServiceDisconnected(ComponentName name) {

    }
}

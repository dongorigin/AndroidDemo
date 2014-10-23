package cn.dong.demo.ui;

import android.os.Bundle;
import android.os.Message;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v4.widget.SwipeRefreshLayout.OnRefreshListener;
import cn.dong.demo.R;
import cn.dong.demo.base.BaseActivity;

public class SwipeRefreshLayoutActivity extends BaseActivity {
    private SwipeRefreshLayout swipeLayout;

    @Override
    protected int initPageLayoutID() {
        return R.layout.activity_swipe_refresh_layout;
    }

    @Override
    protected void initPageView() {
        swipeLayout = (SwipeRefreshLayout) findViewById(R.id.swipe_layout);
    }

    @Override
    protected void initPageViewListener() {
        swipeLayout.setOnRefreshListener(new OnRefreshListener() {

            @Override
            public void onRefresh() {
                mHandler.sendEmptyMessageDelayed(1, 5000);
            }
        });
    }

    @Override
    protected void process(Bundle savedInstanceState) {
        // TODO Auto-generated method stub

    }

    @Override
    public boolean handleMessage(Message msg) {
        switch (msg.what) {
            case 1:
                swipeLayout.setRefreshing(false);
                break;
            default:
                break;
        }
        return true;
    }

}

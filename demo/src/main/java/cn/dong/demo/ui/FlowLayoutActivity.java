package cn.dong.demo.ui;

import android.os.Bundle;

import butterknife.InjectView;
import cn.dong.demo.R;
import cn.dong.demo.base.BaseActivity;
import cn.dong.demo.view.FlowLayout;

/**
 * author DONG 2015/5/8.
 */
public class FlowLayoutActivity extends BaseActivity {
    @InjectView(R.id.flow_layout)
    FlowLayout mFlowLayout;

    @Override
    protected int initPageLayoutID() {
        return R.layout.activity_flowlayout;
    }

    @Override
    protected void initPageView() {

    }

    @Override
    protected void initPageViewListener() {

    }

    @Override
    protected void process(Bundle savedInstanceState) {

    }
}

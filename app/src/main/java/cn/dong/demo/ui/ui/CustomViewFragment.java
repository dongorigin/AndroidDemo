package cn.dong.demo.ui.ui;

import android.os.Bundle;

import butterknife.InjectView;
import cn.dong.demo.R;
import cn.dong.demo.model.event.TestEvent;
import cn.dong.demo.ui.common.BaseFragment;
import cn.dong.demo.view.CustomView;
import de.greenrobot.event.EventBus;

/**
 * @author dong on 15/4/20.
 */
public class CustomViewFragment extends BaseFragment {
    @InjectView(R.id.custom)
    CustomView customView;

    @Override
    protected int initPageLayoutId() {
        return R.layout.fragment_customview;
    }

    @Override
    public void onPageFirstVisible() {
        customView.setState(4);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EventBus.getDefault().register(this);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    public void onEvent(TestEvent event) {
        customView.setShowShapeName(!customView.isShowShapeName());
    }
}

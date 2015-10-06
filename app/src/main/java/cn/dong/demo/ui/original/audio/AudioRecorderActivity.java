package cn.dong.demo.ui.original.audio;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import butterknife.InjectView;
import cn.dong.demo.R;
import cn.dong.demo.ui.common.BaseActivity;

/**
 * @author dong on 15/10/6.
 */
public class AudioRecorderActivity extends BaseActivity {
    @InjectView(R.id.recycler)
    RecyclerView mRecyclerView;

    @Override
    protected int initPageLayoutID() {
        return R.layout.activity_audiorecording;
    }

    @Override
    protected void initPageView() {
        super.initPageView();
        mRecyclerView.setLayoutManager(new LinearLayoutManager(mContext));
    }
}

package cn.dong.demo.ui.original.audio;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import butterknife.InjectView;
import cn.dong.demo.R;
import cn.dong.demo.ui.common.BaseActivity;
import cn.dong.demo.view.AudioRecorderButton;
import timber.log.Timber;

/**
 * @author dong on 15/10/6.
 */
public class AudioRecorderActivity extends BaseActivity {
    @InjectView(R.id.recycler)
    RecyclerView mRecyclerView;
    @InjectView(R.id.recorder_btn)
    AudioRecorderButton mAudioRecorderButton;

    @Override
    protected void init() {
        super.init();

    }

    @Override
    protected int initPageLayoutID() {
        return R.layout.activity_audiorecording;
    }

    @Override
    protected void initPageView() {
        super.initPageView();
        mRecyclerView.setLayoutManager(new LinearLayoutManager(mContext));

        mAudioRecorderButton.setCallback(new AudioRecorderButton.RecordCallback() {
            @Override
            public void onRecordEnd(String audioPath) {
                Timber.d("record end: path=%s", audioPath);
            }
        });
    }
}

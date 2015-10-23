package cn.dong.demo.ui.original.audio;

import android.graphics.drawable.AnimationDrawable;
import android.media.MediaPlayer;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import butterknife.InjectView;
import cn.dong.demo.R;
import cn.dong.demo.ui.common.BaseActivity;
import cn.dong.demo.util.MediaPlayerManager;
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

    private List<Audio> mData;
    private AudioAdapter mAdapter;

    private static class Audio {
        int duration;
        String audioPath;
    }

    @Override
    protected void init() {
        super.init();
        mData = new ArrayList<>();
        mAdapter = new AudioAdapter();
    }

    @Override
    protected int initPageLayoutID() {
        return R.layout.activity_audiorecording;
    }

    @Override
    protected void initPageView() {
        super.initPageView();
        mRecyclerView.setLayoutManager(new LinearLayoutManager(mContext));
        mRecyclerView.setAdapter(mAdapter);

        mAudioRecorderButton.setCallback(new AudioRecorderButton.RecordCallback() {
            @Override
            public void onRecordEnd(String audioPath, int duration) {
                Timber.d("record end: duration=%d path=%s", duration, audioPath);
                Audio audio = new Audio();
                audio.duration = duration;
                audio.audioPath = audioPath;
                mData.add(audio);
                mAdapter.notifyDataSetChanged();
            }
        });
    }

    @Override
    protected void onPause() {
        super.onPause();
        MediaPlayerManager.release();
        mAudioRecorderButton.pause();
    }

    private static class Holder extends RecyclerView.ViewHolder {
        View contentView;
        View contentAnimView;
        TextView durationView;

        public Holder(View itemView) {
            super(itemView);
            contentView = itemView.findViewById(R.id.content_container);
            contentAnimView = itemView.findViewById(R.id.recorder_anim);
            durationView = (TextView) itemView.findViewById(R.id.duration);
        }
    }

    private class AudioAdapter extends RecyclerView.Adapter<Holder> {
        private int maxContentWidth;
        private int minContentWidth;

        public AudioAdapter() {
            minContentWidth = (int) (getResources().getDisplayMetrics().widthPixels * 0.15f);
            maxContentWidth = (int) (getResources().getDisplayMetrics().widthPixels * 0.6f);
        }

        @Override
        public int getItemCount() {
            return mData.size();
        }

        @Override
        public Holder onCreateViewHolder(ViewGroup parent, int viewType) {
            return new Holder(LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_audiorecoring_item, parent, false));
        }

        @Override
        public void onBindViewHolder(final Holder holder, int position) {
            final Audio audio = mData.get(position);
            holder.durationView.setText(String.format("%d″", audio.duration));

            holder.contentView.getLayoutParams().width = (int) (minContentWidth + maxContentWidth / 60f * audio.duration);

            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // 播放动画
                    holder.contentAnimView.setBackgroundResource(R.drawable.play_audio_anim);
                    AnimationDrawable animationDrawable = (AnimationDrawable) holder.contentAnimView.getBackground();
                    animationDrawable.start();
                    // 播放音频
                    MediaPlayerManager.playAudio(audio.audioPath, new MediaPlayer.OnCompletionListener() {
                        @Override
                        public void onCompletion(MediaPlayer mp) {
                            holder.contentAnimView.setBackgroundResource(R.drawable.adj);
                            MediaPlayerManager.release();
                        }
                    });
                }
            });
        }

    }
}

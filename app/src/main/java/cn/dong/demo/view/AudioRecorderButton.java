package cn.dong.demo.view;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.ImageView;
import android.widget.TextView;

import cn.dong.demo.R;
import cn.dong.demo.util.AudioRecorderManager;
import cn.dong.demo.util.PixelUtils;

/**
 * 语音录制按钮
 *
 * @author dong on 15/10/5.
 */
public class AudioRecorderButton extends TextView implements AudioRecorderManager.StateCallback {
    private static final int STATE_NORMAL = 1;
    private static final int STATE_RECORDING = 2;
    private static final int STATE_WANT_CANCEL = 3;

    private static final float MIN_DURATION = 1f; // 最短录制时间
    private static final float MAX_DURATION = 60f; // 最大录制时间
    private static final int DISTANCE_Y_CANCEL = PixelUtils.dp2px(50);

    private int mState;
    private AudioRecorderManager mAudioRecorderManager;
    private RecorderDialogFragment mRecorderDialogFragment;
    private Thread mRecordThread;
    private RecordCallback mCallback;

    private Runnable mRecordUpdateRunnable = new Runnable() {
        @Override
        public void run() {
            while (mAudioRecorderManager.isRecording()) {
                try {
                    Thread.sleep(100);
                    Activity activity = (Activity) getContext();
                    activity.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            mRecorderDialogFragment.updateVoiceLevel(mAudioRecorderManager.getVoiceLevel(8));
                            if (mAudioRecorderManager.getRecordDuration() > MAX_DURATION - 10) {
                                // 提示时间倒计时

                            }
                        }
                    });
                } catch (InterruptedException e) {

                }
            }
        }
    };

    public AudioRecorderButton(Context context) {
        this(context, null);
    }

    public AudioRecorderButton(Context context, AttributeSet attrs) {
        super(context, attrs);

        mAudioRecorderManager = AudioRecorderManager.getInstance();
        mAudioRecorderManager.setStateCallback(this);
        mRecorderDialogFragment = new RecorderDialogFragment();

        setTextColor(getResources().getColor(R.color.text_gray));
        changeState(STATE_NORMAL);
    }

    @Override
    public void onRecordingStart() {
        startVoiceUpdateThread();
        FragmentActivity activity = (FragmentActivity) getContext();
        mRecorderDialogFragment.show(activity.getSupportFragmentManager());
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                changeState(STATE_RECORDING);
                mAudioRecorderManager.startRecord();
                return true;
            case MotionEvent.ACTION_MOVE:
                if (mAudioRecorderManager.isRecording()) {
                    if (wantToCancel(event.getX(), event.getY())) {
                        changeState(STATE_WANT_CANCEL);
                        mRecorderDialogFragment.wantToCancel();
                    } else {
                        changeState(STATE_RECORDING);
                        mRecorderDialogFragment.recording();
                    }
                }
                break;
            case MotionEvent.ACTION_UP:
                mRecordThread.interrupt();
                switch (mState) {
                    case STATE_RECORDING:
                        if (!mAudioRecorderManager.isRecording() || mAudioRecorderManager.getRecordDuration() < MIN_DURATION) {
                            mAudioRecorderManager.cancel();
                            mRecorderDialogFragment.tooShort();
                        } else {
                            mAudioRecorderManager.release();
                            mRecorderDialogFragment.dismiss();
                            if (mCallback != null) {
                                mCallback.onRecordEnd(mAudioRecorderManager.getCurrentFile().getAbsolutePath(), mAudioRecorderManager.getRecordDuration());
                            }
                        }
                        break;
                    case STATE_WANT_CANCEL:
                        mAudioRecorderManager.cancel();
                        mRecorderDialogFragment.dismiss();
                        break;
                }
                reset();
                break;
        }
        return super.onTouchEvent(event);
    }

    private void startVoiceUpdateThread() {
        mRecordThread = new Thread(mRecordUpdateRunnable);
        mRecordThread.start();
    }

    /**
     * 重置
     */
    private void reset() {
        changeState(STATE_NORMAL);
    }

    public void pause() {
        reset();
        mRecorderDialogFragment.dismiss();
        mAudioRecorderManager.cancel(); // todo 当前录制内容是否保留并发送
    }

    /**
     * 是否要取消录音，手指是否移动到了范围外
     *
     * @param x
     * @param y
     * @return
     */
    private boolean wantToCancel(float x, float y) {
        if (x < 0 || x > getWidth() || y < -DISTANCE_Y_CANCEL || y > getHeight() + DISTANCE_Y_CANCEL) {
            return true;
        } else {
            return false;
        }
    }

    private void changeState(int state) {
        if (mState == state) {
            return;
        }
        mState = state;
        switch (state) {
            case STATE_NORMAL:
                setText(R.string.recorder_normal);
                setBackgroundResource(R.drawable.bg_recorder_normal);
                break;
            case STATE_RECORDING:
                setText(R.string.recorder_recording);
                setBackgroundResource(R.drawable.bg_recorder_recording);
                break;
            case STATE_WANT_CANCEL:
                setText(R.string.recorder_want_cancel);
                setBackgroundResource(R.drawable.bg_recorder_recording);
                break;
        }
    }

    public interface RecordCallback {
        void onRecordEnd(String audioPath, int duration);
    }

    public void setCallback(RecordCallback callback) {
        mCallback = callback;
    }

    public static class RecorderDialogFragment extends DialogFragment {
        public static final String TAG = "RecorderDialog";
        private ImageView mIconView;
        private TextView mTextView;

        @NonNull
        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            Dialog dialog = new Dialog(getActivity(), R.style.AudioDialog);
            dialog.setCancelable(false);
            dialog.setCanceledOnTouchOutside(false);
            dialog.setContentView(dialog.getLayoutInflater().inflate(R.layout.dialog_recorder, null));
            mIconView = (ImageView) dialog.findViewById(R.id.icon);
            mTextView = (TextView) dialog.findViewById(R.id.text);
            return dialog;
        }

        public void show(FragmentManager manager) {
            super.show(manager, TAG);
        }

        public boolean isShowing() {
            return getActivity() != null && getDialog() != null && getDialog().isShowing();
        }

        public void recording() {
            if (isShowing()) {
                mTextView.setText(R.string.recorder_dialog_recording);
                mTextView.setTextColor(getResources().getColor(R.color.white));
            }
        }

        public void wantToCancel() {
            if (isShowing()) {
                mTextView.setText(R.string.recorder_want_cancel);
                mTextView.setTextColor(getResources().getColor(R.color.text_red));
            }
        }

        public void tooShort() {
            if (isShowing()) {
                mIconView.setImageResource(R.drawable.ic_record_warn);
                mTextView.setText(R.string.recorder_dialog_too_short);
                mTextView.setTextColor(getResources().getColor(R.color.white));

                mIconView.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        // todo 可能将之后新的Dialog关闭
                        dismiss();
                    }
                }, 500);
            }
        }

        public void updateVoiceLevel(int level) {
            if (!isShowing() || level < 1 || level > 8) {
                return;
            }
            if (getActivity() == null) {
                return;
            }
            int resId = getActivity().getResources().getIdentifier("ic_mic_voice_00" + level, "drawable", getActivity().getPackageName());
            mIconView.setImageResource(resId);
        }
    }
}

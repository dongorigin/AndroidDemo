package cn.dong.demo.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.TextView;

import cn.dong.demo.R;
import cn.dong.demo.util.PixelUtil;

/**
 * 语音录制按钮
 *
 * @author dong on 15/10/5.
 */
public class AudioRecorderButton extends TextView {
    private static final int STATE_NORMAL = 1;
    private static final int STATE_RECORDING = 2;
    private static final int STATE_WANT_CANCEL = 3;

    private static final int DISTANCE_Y_CANCEL = PixelUtil.dp2px(50);

    private int mState;
    private boolean isRecording = false;

    public AudioRecorderButton(Context context) {
        this(context, null);
    }

    public AudioRecorderButton(Context context, AttributeSet attrs) {
        super(context, attrs);
        setTextColor(getResources().getColor(R.color.text_gray));
        changeState(STATE_NORMAL);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                isRecording = true; // // TODO: 15/10/6
                changeState(STATE_RECORDING);
                return true;
            case MotionEvent.ACTION_MOVE:
                if (!isRecording) {
                    return false;
                }
                if (wantToCancel(event.getX(), event.getY())) {
                    changeState(STATE_WANT_CANCEL);
                } else {
                    changeState(STATE_RECORDING);
                }
                break;
            case MotionEvent.ACTION_UP:
                switch (mState) {
                    case STATE_RECORDING:
                        // release
                        // callback
                        break;
                    case STATE_WANT_CANCEL:

                        break;
                }
                reset();
                break;
        }
        return super.onTouchEvent(event);
    }

    /**
     * 重置
     */
    private void reset() {
        isRecording = false;
        changeState(STATE_NORMAL);
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
                // todo Dialog.recoring()
                break;
            case STATE_WANT_CANCEL:
                setText(R.string.recorder_want_cancel);
                setBackgroundResource(R.drawable.bg_recorder_recording);
                // todo Dialog.cancel()
                break;
        }
    }

}

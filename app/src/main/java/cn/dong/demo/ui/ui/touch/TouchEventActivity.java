package cn.dong.demo.ui.ui.touch;

import android.os.Bundle;
import android.support.v4.view.GestureDetectorCompat;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;

import cn.dong.demo.R;
import cn.dong.demo.ui.common.BaseActivity;
import timber.log.Timber;

import static cn.dong.demo.ui.ui.touch.TouchEventHelper.displayAction;

/**
 * 触摸事件分发测试
 *
 * @author dong 2014-6-29
 */
public class TouchEventActivity extends BaseActivity {
    private TouchButton button;
    private TouchView view;

    @Override
    protected int initPageLayoutID() {
        return R.layout.touch_activity;
    }

    @Override
    protected void initPageView() {
        button = (TouchButton) findViewById(R.id.button);
        view = (TouchView) findViewById(R.id.view);
    }

    @Override
    protected void initPageViewListener() {
        final GestureDetectorCompat buttonGestureDetector = new GestureDetectorCompat(this, new GestureDetector.SimpleOnGestureListener() {
            @Override
            public boolean onSingleTapUp(MotionEvent e) {
                Timber.d("onSingleTapUp");
//                return super.onSingleTapUp(e);
                return true;
            }

            @Override
            public boolean onSingleTapConfirmed(MotionEvent e) {
                Timber.d("onSingleTapConfirmed");
//                return super.onSingleTapConfirmed(e);
                return true;
            }

            @Override
            public boolean onDoubleTap(MotionEvent e) {
                Timber.d("onDoubleTap");
                return true;
//                return super.onDoubleTap(e);
            }

            @Override
            public void onLongPress(MotionEvent e) {
                Timber.d("onLongPress");
                super.onLongPress(e);
            }
        });
        button.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                displayAction(event, "button", "onTouch");
                return buttonGestureDetector.onTouchEvent(event);
            }
        });
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i("button", "onClick");
            }
        });

        view.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                displayAction(event, "view", "OnTouch");
                return false;
            }
        });
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i("View", "onClick");
            }
        });
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        displayAction(event, TAG, "dispatchTouchEvent");
        boolean result = super.dispatchTouchEvent(event);
        Log.i(TAG, "dispatchTouchEvent return " + result);
        return result;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        displayAction(event, TAG, "onTouchEvent");
        return super.onTouchEvent(event);
    }

    @Override
    protected void process(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
    }

    @Override
    public void finish() {
        Log.i(TAG, "finish");
        super.finish();
    }

}

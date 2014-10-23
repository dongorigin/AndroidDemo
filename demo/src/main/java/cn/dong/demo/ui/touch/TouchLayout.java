package cn.dong.demo.ui.touch;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.widget.RelativeLayout;

public class TouchLayout extends RelativeLayout {
	private static final String TAG = "RelativeLayout";

	public TouchLayout(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	@Override
	public boolean dispatchTouchEvent(MotionEvent event) {
		TouchEventHelper.displayAction(event, TAG, "dispatchTouchEvent");
		boolean result = super.dispatchTouchEvent(event);
		Log.i(TAG, "dispatchTouchEvent return " + result);
		return result;
	}

	@Override
	public boolean onInterceptTouchEvent(MotionEvent ev) {
//		TouchEventHelper.displayAction(ev, TAG, "onInterceptTouchEvent");
		return super.onInterceptTouchEvent(ev);
	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		TouchEventHelper.displayAction(event, TAG, "onTouchEvent");
		boolean result = super.onTouchEvent(event);
		Log.i(TAG, "onTouchEvent return " + result);
		return result;
	}
}

package cn.dong.demo.ui.touch;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.widget.Button;

public class TouchButton extends Button {
	private static final String TAG = "button";

	public TouchButton(Context context, AttributeSet attrs) {
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
	public boolean onTouchEvent(MotionEvent event) {
		TouchEventHelper.displayAction(event, TAG, "onTouchEvent");
		boolean result = super.onTouchEvent(event);
		Log.i(TAG, "onTouchEvent return " + result);
		return result;
	}
}

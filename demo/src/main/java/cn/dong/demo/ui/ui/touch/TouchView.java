package cn.dong.demo.ui.ui.touch;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

public class TouchView extends View {
	private static final String TAG = "View";

	public TouchView(Context context, AttributeSet attrs) {
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

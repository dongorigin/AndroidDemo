package cn.dong.demo.ui.touch;

import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import cn.dong.demo.R;
import cn.dong.demo.base.BaseActivity;

/**
 * 触摸事件分发测试
 * 
 * @author dong 2014-6-29
 */
public class TouchEventActivity extends BaseActivity {
	private static final String TAG = "Activity";

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
		// button.setOnTouchListener(new OnTouchListener() {
		// @Override
		// public boolean onTouch(View v, MotionEvent event) {
		// displayAction(event, "button", "onTouch");
		// return false;
		// }
		// });
		// button.setOnClickListener(new OnClickListener() {
		// @Override
		// public void onClick(View v) {
		// Log.i("button", "onClick");
		// }
		// });
		// view.setOnTouchListener(new OnTouchListener() {
		// @Override
		// public boolean onTouch(View v, MotionEvent event) {
		// displayAction(event, "view", "OnTouch");
		// return false;
		// }
		// });
		// view.setOnClickListener(new OnClickListener() {
		// @Override
		// public void onClick(View v) {
		// Log.i("View", "onClick");
		// }
		// });
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

package cn.dong.demo.ui.touch;

import android.util.Log;
import android.view.MotionEvent;

public class TouchEventHelper {
	public static void displayAction(MotionEvent event, String tag, String msg) {
		String action = "";
		switch (event.getAction()) {
		case MotionEvent.ACTION_DOWN:
			action = "DOWN";
			break;
		case MotionEvent.ACTION_MOVE:
			action = "MOVE";
			break;
		case MotionEvent.ACTION_UP:
			action = "UP";
			break;
		}
		Log.i(tag, msg + "---" + action);
	}
}

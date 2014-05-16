package cn.dong.demo.util;

import android.content.Context;
import android.widget.Toast;

public class T {
	private static Toast toast;

	public static void shortT(Context context, String msg) {
		if (toast == null) {
			toast = Toast.makeText(context.getApplicationContext(), msg, Toast.LENGTH_SHORT);
			toast.show();
		} else {
			toast.setText(msg);
			toast.setDuration(Toast.LENGTH_SHORT);
			toast.show();
		}
	}

	public static void shortT(Context context, int msgResId) {
		if (toast == null) {
			toast = Toast.makeText(context.getApplicationContext(), msgResId, Toast.LENGTH_SHORT);
			toast.show();
		} else {
			toast.setText(msgResId);
			toast.setDuration(Toast.LENGTH_SHORT);
			toast.show();
		}
	}

	public static void longT(Context context, String msg) {
		if (toast == null) {
			toast = Toast.makeText(context.getApplicationContext(), msg, Toast.LENGTH_LONG);
			toast.show();
		} else {
			toast.setText(msg);
			toast.setDuration(Toast.LENGTH_LONG);
			toast.show();
		}
	}
}

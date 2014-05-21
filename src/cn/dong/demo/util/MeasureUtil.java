package cn.dong.demo.util;

import android.content.Context;
import android.content.res.Resources;
import android.util.TypedValue;

public class MeasureUtil {

	public static int px2Dip(Context context, float px) {
		Resources r;
		if (context == null) {
			r = Resources.getSystem();
		} else {
			r = context.getResources();
		}
		return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, px,
				r.getDisplayMetrics());
	}
}

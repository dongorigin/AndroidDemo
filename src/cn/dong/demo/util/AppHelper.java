package cn.dong.demo.util;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager.NameNotFoundException;

public class AppHelper {

	public static PackageInfo getPackageInfo(Context context) {
		try {
			return context.getPackageManager().getPackageInfo(context.getPackageName(), 0);
		} catch (NameNotFoundException e) {
			return null;
		}
	}

	public static int getVersionCode(Context context) {
		PackageInfo info = getPackageInfo(context);
		if (info != null) {
			return info.versionCode;
		} else {
			return 0;
		}
	}

	public static String getVersionName(Context context) {
		PackageInfo info = getPackageInfo(context);
		if (info != null) {
			return info.versionName;
		} else {
			return "";
		}
	}
}

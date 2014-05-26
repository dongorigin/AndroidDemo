package cn.dong.demo;

import android.app.Application;
import android.content.Context;
import android.util.Log;

import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;

public class DongApplication extends Application {
	private static DongApplication instantce;

	public String IMG_DIR;

	public static DongApplication getInstance() {
		return instantce;
	}

	@Override
	public void onCreate() {
		super.onCreate();
		instantce = this;

		initImageLoader(getApplicationContext());
		IMG_DIR = getExternalFilesDir(null).getPath();
		Log.d("appliciton", "IMG_DIR = " + IMG_DIR);
	}

	private static void initImageLoader(Context context) {
		ImageLoaderConfiguration.Builder builder = new ImageLoaderConfiguration.Builder(context);
		builder.threadPriority(Thread.NORM_PRIORITY - 2);
		builder.denyCacheImageMultipleSizesInMemory();
		builder.discCacheFileNameGenerator(new Md5FileNameGenerator());
		builder.tasksProcessingOrder(QueueProcessingType.LIFO);
		builder.writeDebugLogs();
		ImageLoaderConfiguration config = builder.build();
		ImageLoader.getInstance().init(config);
	}
}

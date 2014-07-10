package cn.dong.demo;

import android.app.Application;
import android.util.Log;

import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
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
		initImageLoader();
		IMG_DIR = getExternalFilesDir(null).getPath();
		Log.d("appliciton", "IMG_DIR = " + IMG_DIR);
	}

	private void initImageLoader() {
		DisplayImageOptions options = new DisplayImageOptions.Builder()
				.showImageOnLoading(R.drawable.empty_photo).imageScaleType(ImageScaleType.NONE)
				.cacheInMemory(true).cacheOnDisc(true).build();
		ImageLoaderConfiguration.Builder builder = new ImageLoaderConfiguration.Builder(this);
		builder.defaultDisplayImageOptions(options);
		builder.threadPriority(Thread.NORM_PRIORITY - 2);
		builder.denyCacheImageMultipleSizesInMemory();
		builder.discCacheFileNameGenerator(new Md5FileNameGenerator());
		builder.tasksProcessingOrder(QueueProcessingType.LIFO);
		builder.writeDebugLogs();
		ImageLoaderConfiguration config = builder.build();
		ImageLoader.getInstance().init(config);
	}
}

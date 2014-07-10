package cn.dong.demo.ui.image;

import android.app.Activity;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import cn.dong.demo.R;
import cn.dong.demo.waterfall.ScaleImageView;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.assist.ImageLoadingListener;
import com.nostra13.universalimageloader.core.imageaware.ImageViewAware;

public class ImageLoaderLargeActivity extends Activity {
	ImageLoader imageLoader;
	DisplayImageOptions options;
	ScaleImageView imageView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_imageloader_large);
		initImageLoader();
		imageView = (ScaleImageView) findViewById(R.id.image);
		// TODO 超大图片测试
		String url =
				"http://img4.duitang.com/uploads/item/201212/13/20121213143309_JSXxW.thumb.200_0.jpeg";
		ImageViewAware imageViewAware = new ImageViewAware(imageView);
		Log.d("ImageViewAware", "width=" + imageViewAware.getWidth() + ", height=" + imageViewAware.getHeight());
		imageLoader.displayImage(url, imageViewAware, options, new ImageLoadingListener() {
			@Override
			public void onLoadingStarted(String imageUri, View view) {

			}

			@Override
			public void onLoadingFailed(String imageUri, View view, FailReason failReason) {

			}

			@Override
			public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
				ScaleImageView image = (ScaleImageView) view;
				image.setImageHeight(loadedImage.getHeight());
				image.setImageWidth(loadedImage.getWidth());
			}

			@Override
			public void onLoadingCancelled(String imageUri, View view) {

			}
		});
	}

	private void initImageLoader() {
		options =
				new DisplayImageOptions.Builder()
						.showImageOnLoading(R.drawable.empty_photo)
						.cacheInMemory(true)
						.cacheOnDisc(true)
						.build();
		imageLoader = ImageLoader.getInstance();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.image, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case R.id.item_clear_memory_cache:
			imageLoader.clearMemoryCache();
			return true;
		case R.id.item_clear_disc_cache:
			imageLoader.clearDiscCache();
			return true;
		default:
			return false;
		}
	}
}

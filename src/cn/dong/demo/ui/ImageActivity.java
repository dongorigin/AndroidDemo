package cn.dong.demo.ui;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import cn.dong.demo.R;
import cn.dong.demo.base.BaseActivity;

/**
 * 将应用内资源图片存储至储存卡
 * 
 * @author dong 2014-5-23
 */
public class ImageActivity extends BaseActivity {
	private static final String TAG = "ImageActivity";

	private static final String IMAGE_NAME = "ic_launcher.png";

	private Button btn_save;

	@Override
	protected int initPageLayoutID() {
		return R.layout.activity_image;
	}

	@Override
	protected void initPageView() {
		btn_save = (Button) findViewById(R.id.save);
	}

	@Override
	protected void initPageViewListener() {
		btn_save.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				a();
			}
		});
	}

	@Override
	protected void process(Bundle savedInstanceState) {

	}

	private void a() {
		Log.d(TAG, getExternalFilesDir("").toString());
		Log.d(TAG, getExternalFilesDir(Environment.DIRECTORY_PICTURES).toString());

		Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.ic_launcher);
		File extStorageDirectory = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
		File file = new File(extStorageDirectory, IMAGE_NAME);
		try {
			OutputStream os = new FileOutputStream(file);
			bitmap.compress(Bitmap.CompressFormat.PNG, 100, os);
			os.flush();
			os.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void b() {

	}
}

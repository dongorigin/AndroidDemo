package cn.dong.demo.ui.image;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import cn.dong.demo.R;
import cn.dong.demo.util.CameraUtil;

public class ImageSelectorActivity extends Activity {

	private CameraUtil cameraUtil;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_image_selector);

		cameraUtil = new CameraUtil(this);

		Button button = (Button) findViewById(R.id.button);
		button.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				imageSelector();
			}
		});
	}

	private void imageSelector() {
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		builder.setItems(new String[] { "拍照", "本地图片" },
				new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						switch (which) {
						case 0:
							cameraUtil.startActionCamera();
							break;
						case 1:
							cameraUtil.startImagePick();
							break;
						}
					}
				});
		builder.show();
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		
	}
}

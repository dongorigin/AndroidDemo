package cn.dong.demo.ui;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import cn.dong.demo.R;
import cn.dong.demo.widget.SharePage;

public class ShareActivity extends Activity {
	SharePage sharePage;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_share);
		sharePage = new SharePage(this);
		sharePage.setText("分享的内容");

		Button button = (Button) findViewById(R.id.bn_share);
		button.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				sharePage.show();
			}
		});
	}
}

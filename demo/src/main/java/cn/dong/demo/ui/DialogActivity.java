package cn.dong.demo.ui;

import java.lang.ref.WeakReference;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import cn.dong.demo.R;

public class DialogActivity extends FragmentActivity {
	private static final String TAG = "test";
	private MyHandler handler;
	private TextView text;
	private Button button;
	private MyDialogFragment dialog;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_dialog);
		initData();
		initView();
	}

	private void initData() {
		handler = new MyHandler(this);
	}

	private void initView() {
		dialog = new MyDialogFragment();

		text = (TextView) findViewById(R.id.text);
		button = (Button) findViewById(R.id.button);
		button.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				dialog.show(getSupportFragmentManager(), "dialog");
			}
		});
	}

	private static class MyHandler extends Handler {
		private WeakReference<DialogActivity> mActivity;

		public MyHandler(DialogActivity activity) {
			mActivity = new WeakReference<DialogActivity>(activity);
		}

		@Override
		public void dispatchMessage(Message msg) {
			DialogActivity theActivity = mActivity.get();
			if (theActivity == null) {
				return;
			}
			switch (msg.what) {
			case 0:
				theActivity.text.setText("handler!");
				break;
			case 1:
				break;
			}
		}
	}

}

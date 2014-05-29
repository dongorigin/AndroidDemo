package cn.dong.demo.ui;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.Switch;
import cn.dong.demo.R;
import cn.dong.demo.base.BaseActivity;

public class SwipeActivity extends BaseActivity {
	private static final String TAG = "SwipeActivity";

	private Switch gestureSwitch;
	private Button finishButton;

	@Override
	protected int initPageLayoutID() {
		return R.layout.swipe_activity;
	}

	@Override
	protected void initPageView() {
		gestureSwitch = (Switch) findViewById(R.id.gesture_switch);
		finishButton = (Button) findViewById(R.id.btn_finish);
	}

	@Override
	protected void initPageViewListener() {
		gestureSwitch.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				mSwipeBackLayout.setEnableGesture(isChecked);
				setEnableBackAnimation(isChecked);
			}
		});
		finishButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				finish();
			}
		});
	}

	@Override
	protected void process(Bundle savedInstanceState) {
	}

	@Override
	public void finish() {
		Log.d(TAG, "finish");
		super.finish();
	}

}

package cn.dong.demo.ui;

import me.imid.swipebacklayout.lib.SwipeBackLayout;
import me.imid.swipebacklayout.lib.app.SwipeBackActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.Switch;
import cn.dong.demo.R;

public class SwipeActivity extends SwipeBackActivity {
	private static final String TAG = "SwipeActivity";

	private SwipeBackLayout mSwipeBackLayout;
	private Switch gestureSwitch;
	private Button finishButton;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(initPageLayoutID());
		initPageView();
		initPageViewListener();
		process(savedInstanceState);
	}

	protected int initPageLayoutID() {
		return R.layout.swipe_activity;
	}

	protected void initPageView() {
		mSwipeBackLayout = getSwipeBackLayout();
		gestureSwitch = (Switch) findViewById(R.id.gesture_switch);
		finishButton = (Button) findViewById(R.id.btn_finish);
	}

	protected void initPageViewListener() {
		gestureSwitch.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				mSwipeBackLayout.setEnableGesture(isChecked);
			}
		});
		finishButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				finish();
			}
		});
	}

	protected void process(Bundle savedInstanceState) {
	}

	@Override
	public void finish() {
		Log.d(TAG, "finish");
		super.finish();
	}

}

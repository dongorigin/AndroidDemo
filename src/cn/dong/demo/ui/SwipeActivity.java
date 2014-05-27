package cn.dong.demo.ui;

import me.imid.swipebacklayout.lib.SwipeBackLayout;
import android.os.Bundle;
import android.util.Log;
import cn.dong.demo.R;
import cn.dong.demo.base.BaseSwipeBackActivity;

public class SwipeActivity extends BaseSwipeBackActivity {
	private static final String TAG = "SwipeActivity";

	private SwipeBackLayout mSwipeBackLayout;

	@Override
	protected int initPageLayoutID() {
		return R.layout.swipe_activity;
	}

	@Override
	protected void initPageView() {
		mSwipeBackLayout = getSwipeBackLayout();
	}

	@Override
	protected void initPageViewListener() {
		// TODO Auto-generated method stub

	}

	@Override
	protected void process(Bundle savedInstanceState) {
		mSwipeBackLayout.setEnableGesture(true);
		mSwipeBackLayout.setEdgeTrackingEnabled(SwipeBackLayout.EDGE_LEFT);
		mSwipeBackLayout.setEdgeSize(100);
	}

	@Override
	public void finish() {
		Log.d(TAG, "finish");
		super.finish();
	}
}

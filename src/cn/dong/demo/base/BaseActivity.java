package cn.dong.demo.base;

import me.imid.swipebacklayout.lib.SwipeBackLayout;
import me.imid.swipebacklayout.lib.app.SwipeBackActivity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Handler.Callback;
import android.os.Message;
import android.support.v4.app.FragmentActivity;
import android.view.MenuItem;
import cn.dong.demo.DongApplication;

public abstract class BaseActivity extends SwipeBackActivity implements Callback {
	private boolean enableBackAnimation = true;

	protected FragmentActivity context;
	protected DongApplication application;
	protected Handler mHandler;
	protected SwipeBackLayout mSwipeBackLayout;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		init();
		setContentView(initPageLayoutID());
		initPageView();
		initPageViewListener();
		process(savedInstanceState);
	}

	private void init() {
		context = this;
		application = DongApplication.getInstance();
		mHandler = new Handler(this);
		mSwipeBackLayout = getSwipeBackLayout();
		getActionBar().setDisplayHomeAsUpEnabled(true);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case android.R.id.home:
			scrollToFinishActivity();
			return true;
		default:
			return super.onOptionsItemSelected(item);
		}
	}

	@Override
	public void onBackPressed() {
		if (!getSupportFragmentManager().popBackStackImmediate()) {
			if (enableBackAnimation) {
				// 滑动动画结束后会自动调用finish()，请勿手动调用finish()
				scrollToFinishActivity();
			} else {
				finish();
			}
		}
	}

	/**
	 * 设置back键关闭页面时是否开启滑动动画,finish时需要动画效果请调用scrollToFinishActivity
	 * 
	 * @param enable
	 */
	protected void setEnableBackAnimation(boolean enable) {
		enableBackAnimation = enable;
	}

	/**
	 * 返回主布局id
	 */
	protected abstract int initPageLayoutID();

	/**
	 * 初始化页面控件
	 */
	protected abstract void initPageView();

	/**
	 * 页面控件点击事件处理
	 */
	protected abstract void initPageViewListener();

	/**
	 * 逻辑处理
	 */
	protected abstract void process(Bundle savedInstanceState);

	@Override
	public boolean handleMessage(Message msg) {
		return false;
	}

}

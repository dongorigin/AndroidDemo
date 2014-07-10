package cn.dong.demo.ui.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentManager.OnBackStackChangedListener;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import cn.dong.demo.R;
import cn.dong.demo.util.FragmentUtil;

public class FragmentTestActivity extends FragmentActivity {
	private FragmentManager fm;
	private int fragmentNum;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_fragment_test);
		fm = getSupportFragmentManager();
		fm.addOnBackStackChangedListener(new OnBackStackChangedListener() {
			@Override
			public void onBackStackChanged() {
				Log.d("main", "onBackStackChanged");
				Fragment currentFragment = fm.findFragmentById(R.id.container);
				if (currentFragment != null) {
					String fragmentName = currentFragment.getClass().getName();
					Log.d("main", fragmentName);
				}
			}
		});
	}

	public void OnClick(View v) {
		Bundle args = new Bundle();
		args.putInt("Num", fragmentNum);
		fragmentNum++;

		switch (v.getId()) {
		case R.id.button1:
			FragmentUtil.startFragment(fm, TestFragment.class, args);
			break;
		case R.id.button2:
			break;
		case R.id.button3:
			FragmentUtil.replaceFragment(fm, TestFragment.class, args, true);
			break;
		case R.id.button4:
			FragmentUtil.replaceFragment(fm, TestFragment.class, args, false);
			break;
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		if (item.getItemId() == R.id.action_settings) {
			fm.popBackStack(0, FragmentManager.POP_BACK_STACK_INCLUSIVE);
			fragmentNum = 0;
		}
		return true;
	}
}

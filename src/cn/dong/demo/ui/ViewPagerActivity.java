package cn.dong.demo.ui;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import cn.dong.demo.R;
import cn.dong.demo.ui.fragment.TestFragment;

public class ViewPagerActivity extends FragmentActivity {
	private ViewPager mViewPager;
	private MyPagerAdapter mPagerAdapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_viewpager);
		mViewPager = (ViewPager) findViewById(R.id.pager);
		mViewPager.setOffscreenPageLimit(9);

		mPagerAdapter = new MyPagerAdapter(this);
		mViewPager.setAdapter(mPagerAdapter);

	}

	public static class MyPagerAdapter extends FragmentPagerAdapter {
		private final Context mContext;

		public MyPagerAdapter(FragmentActivity activity) {
			super(activity.getSupportFragmentManager());
			mContext = activity;
		}

		@Override
		public Fragment getItem(int position) {
			Log.d("PagerAdapter", "getItem " + position);
			Bundle args = new Bundle();
			args.putInt("Num", position);
			return Fragment.instantiate(mContext, TestFragment.class.getName(), args);
		}

		@Override
		public int getCount() {
			return 10;
		}

	}
}

package cn.dong.demo.ui.main;

import android.app.Activity;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.app.FragmentManager;
import android.support.v4.widget.DrawerLayout;
import android.view.MenuItem;
import android.view.View;
import cn.dong.demo.R;
import cn.dong.demo.base.BaseActivity;

public class MainActivity extends BaseActivity {
	private static final String TAG = "Main";

	private DrawerLayout mDrawerLayout;
	private ActionBarDrawerToggle mDrawerToggle;

	@Override
	protected int initPageLayoutID() {
		return R.layout.main_activity;
	}

	@Override
	protected void initPageView() {
		mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
		mDrawerToggle = new MainActionBarDrawerToggle(mContext, mDrawerLayout, R.drawable.ic_drawer,
				R.string.drawer_open, R.string.drawer_close);
	}

	@Override
	protected void onPostCreate(Bundle savedInstanceState) {
		super.onPostCreate(savedInstanceState);
		mDrawerToggle.syncState();
	}

	@Override
	public void onConfigurationChanged(Configuration newConfig) {
		super.onConfigurationChanged(newConfig);
		mDrawerToggle.onConfigurationChanged(newConfig);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		if (mDrawerToggle.onOptionsItemSelected(item)) {
			return true;
		} else {
			return super.onOptionsItemSelected(item);
		}
	}

	@Override
	protected void initPageViewListener() {
		mDrawerLayout.setDrawerListener(mDrawerToggle);
	}

	@Override
	protected void process(Bundle savedInstanceState) {
		FragmentManager fragmentManager = getSupportFragmentManager();
		fragmentManager.beginTransaction().replace(R.id.content_frame, new MainFragment()).commit();
	}

	class MainActionBarDrawerToggle extends ActionBarDrawerToggle {

		public MainActionBarDrawerToggle(Activity activity, DrawerLayout drawerLayout,
				int drawerImageRes, int openDrawerContentDescRes, int closeDrawerContentDescRes) {
			super(activity, drawerLayout, drawerImageRes, openDrawerContentDescRes,
					closeDrawerContentDescRes);
		}

		@Override
		public void onDrawerOpened(View drawerView) {
			getSupportActionBar().setTitle("导航");
			invalidateOptionsMenu();
		}

		@Override
		public void onDrawerClosed(View drawerView) {
			getSupportActionBar().setTitle("主页");
			invalidateOptionsMenu();
		}
	}

}

package cn.dong.demo.ui;

import android.os.Bundle;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;

import cn.dong.demo.R;
import cn.dong.demo.base.BaseActivity;

public class DrawerActivity extends BaseActivity {
	private DrawerLayout mDrawerLayout;

	@Override
	protected int initPageLayoutID() {
		return R.layout.activity_drawer;
	}

	@Override
	protected void initPageView() {
		mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
	}

	@Override
	protected void initPageViewListener() {
		// TODO Auto-generated method stub

	}

	@Override
	protected void process(Bundle savedInstanceState) {
		mDrawerLayout.setDrawerShadow(R.drawable.drawer_shadow, GravityCompat.START);
	}

}

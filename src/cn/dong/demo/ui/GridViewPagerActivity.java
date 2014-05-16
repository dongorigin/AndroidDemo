package cn.dong.demo.ui;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.TextView;
import cn.dong.demo.R;

public class GridViewPagerActivity extends Activity {
	private ViewPager viewPager;
	private MyPagerAdapter pagerAdapter;
	private List<GridView> grids;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_grid_view_pager);
		init();
		initView();
	}

	private void init() {
		grids = new ArrayList<GridView>();
		for (int i = 0; i < 3; i++) {
			GridView gv = new GridView(this);
			gv.setNumColumns(3);
			gv.setAdapter(new MyGridAdapter());
			grids.add(gv);
		}
	}

	private void initView() {
		viewPager = (ViewPager) findViewById(R.id.pager);
		pagerAdapter = new MyPagerAdapter();
		viewPager.setAdapter(pagerAdapter);
	}

	class MyGridAdapter extends BaseAdapter {

		@Override
		public int getCount() {
			return 6;
		}

		@Override
		public Object getItem(int position) {
			return null;
		}

		@Override
		public long getItemId(int position) {
			return position;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			TextView tv = new TextView(GridViewPagerActivity.this);
			tv.setText("测试");
			return tv;
		}

	}

	class MyPagerAdapter extends PagerAdapter {
		@Override
		public Object instantiateItem(ViewGroup container, int position) {
			container.addView(grids.get(position));
			return grids.get(position);
		}

		@Override
		public void destroyItem(ViewGroup container, int position, Object object) {
			container.removeView((View) object);
		}

		@Override
		public int getCount() {
			return grids.size();
		}

		@Override
		public boolean isViewFromObject(View view, Object object) {
			return view == object;
		}

	}

}

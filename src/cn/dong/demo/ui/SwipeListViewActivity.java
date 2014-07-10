package cn.dong.demo.ui;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.TextView;
import cn.dong.demo.R;
import cn.dong.demo.base.BaseActivity;
import cn.dong.demo.base.MyBaseAdapter;
import cn.dong.demo.util.MeasureUtil;
import cn.dong.demo.util.ViewHolder;
import cn.dong.demo.view.SwipeXListView;

import com.fortysevendeg.swipelistview.BaseSwipeListViewListener;
import com.fortysevendeg.swipelistview.SwipeListView;
import com.fortysevendeg.swipelistview.SwipeListViewListener;

public class SwipeListViewActivity extends BaseActivity {

	private SwipeXListView swipeListView;

	private List<String> list;
	private SwipeAdapter adapter;

	@Override
	protected int initPageLayoutID() {
		return R.layout.swipe_listview_activity;
	}

	@Override
	protected void initPageView() {
		swipeListView = (SwipeXListView) findViewById(R.id.list);
	}

	@Override
	protected void initPageViewListener() {
		swipeListView.setSwipeListViewListener(new BaseSwipeListViewListener(){
			@Override
			public void onClickFrontView(int position) {
				
			}
		
			@Override
			public void onClickBackView(int position) {
				
			}
		});
	}

	@Override
	protected void process(Bundle savedInstanceState) {
		list = new ArrayList<String>();
		adapter = new SwipeAdapter(context, list);
		swipeListView.setAdapter(adapter);

		DisplayMetrics dm = new DisplayMetrics();
		getWindowManager().getDefaultDisplay().getMetrics(dm);
		swipeListView.setOffsetLeft(dm.widthPixels - MeasureUtil.px2Dip(context, 90));

		for (int i = 0; i < 20; i++) {
			list.add(String.valueOf(i));
		}
		adapter.notifyDataSetChanged();
	}

	class SwipeAdapter extends MyBaseAdapter<String> {

		public SwipeAdapter(Context context, List<String> data) {
			super(context, data);
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			if (convertView == null) {
				convertView = mInflater.inflate(R.layout.swipe_listview_item, parent, false);
			}
			((SwipeListView) parent).recycle(convertView, position);
			TextView textView = ViewHolder.get(convertView, R.id.front);
			textView.setText(data.get(position));
			return convertView;
		}

	}
}

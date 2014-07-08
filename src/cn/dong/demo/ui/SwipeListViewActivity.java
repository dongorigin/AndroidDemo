package cn.dong.demo.ui;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import cn.dong.demo.R;
import cn.dong.demo.base.BaseActivity;
import cn.dong.demo.base.MyBaseAdapter;
import cn.dong.demo.util.MeasureUtil;
import cn.dong.demo.util.ViewHolder;

import com.fortysevendeg.swipelistview.SwipeListView;

public class SwipeListViewActivity extends BaseActivity {

	private SwipeListView swipeListView;

	private List<String> list;
	private SwipeAdapter adapter;

	@Override
	protected int initPageLayoutID() {
		return R.layout.swipe_listview_activity;
	}

	@Override
	protected void initPageView() {
		swipeListView = (SwipeListView) findViewById(R.id.list);
	}

	@Override
	protected void initPageViewListener() {
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

			TextView textView = ViewHolder.get(convertView, R.id.front);
			textView.setText(data.get(position));
			return convertView;
		}

	}
}

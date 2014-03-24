package cn.dong.demo.ui;

import java.util.ArrayList;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.TextView;
import cn.dong.demo.R;
import cn.dong.demo.widget.XListView;
import cn.dong.demo.widget.XListView.IXListViewListener;

public class XListViewActivity extends Activity implements OnItemClickListener {
	private static final String TAG = "XListView";
	private LayoutInflater mInflater;

	private ArrayList<Item> list;

	private MyAdapter adapter;
	private XListView listView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_xlist);
		initData();
		initList();
	}

	private void initData() {
		mInflater = LayoutInflater.from(this);

		list = new ArrayList<Item>();
		for (int i = 0; i < 20; i++) {
			Item item = new Item();
			item.isChecked = false;
			item.text = i + "";
			list.add(item);
		}
	}

	private void initList() {
		adapter = new MyAdapter();
		listView = (XListView) findViewById(R.id.listview);
		listView.setAdapter(adapter);
		listView.setOnItemClickListener(this);
		listView.setXListViewListener(new IXListViewListener() {
			@Override
			public void onRefresh() {

			}

			@Override
			public void onLoadMore() {

			}
		});
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
		Adapter listAdapter = parent.getAdapter();
		int count = listAdapter.getCount();
		if (position > 0 && position < count - 1) {
			Item item = (Item) listAdapter.getItem(position);
			item.isChecked = true;
			for (int i = 0; i < list.size(); i++) {
				if (i != position - 1) {
					list.get(i).isChecked = false;
				}
			}
			adapter.notifyDataSetChanged();
			Log.d(TAG, item.text);
		}
	}

	private class MyAdapter extends BaseAdapter {

		@Override
		public int getCount() {
			return list.size();
		}

		@Override
		public Object getItem(int position) {
			return list.get(position);
		}

		@Override
		public long getItemId(int position) {
			return position;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			if (convertView == null) {
				convertView = mInflater.inflate(R.layout.activity_xlist_item, parent,
						false);
			}
			Item item = list.get(position);
			TextView text = (TextView) convertView.findViewById(R.id.text);
			text.setText(item.text);
			if (item.isChecked) {
				convertView.setBackgroundColor(Color.parseColor("#99CCB4"));
			} else {
				convertView.setBackgroundColor(Color.parseColor("#FFFFFF"));
			}
			return convertView;
		}
	}

	class Item {
		public boolean isChecked;
		public String text;
	}

}

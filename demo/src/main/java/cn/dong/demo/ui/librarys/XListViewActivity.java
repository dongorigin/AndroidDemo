package cn.dong.demo.ui.librarys;

import android.graphics.Color;
import android.os.Bundle;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import cn.dong.demo.R;
import cn.dong.demo.ui.common.BaseActivity;
import cn.dong.demo.view.XListView;
import cn.dong.demo.view.XListView.IXListViewListener;

public class XListViewActivity extends BaseActivity {
	private LayoutInflater mInflater;

	private ArrayList<Item> list;

	private MyAdapter adapter;
	private XListView listView;

	@Override
	protected int initPageLayoutID() {
		return R.layout.activity_xlist;
	}

	@Override
	protected void initPageView() {
		listView = (XListView) findViewById(R.id.listview);
	}

	@Override
	protected void initPageViewListener() {
		listView.setOnItemClickListener(new OnItemClickListener() {
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
		});
		listView.setXListViewListener(new IXListViewListener() {
			@Override
			public void onRefresh() {
				mHandler.sendEmptyMessageDelayed(0, 2000);
			}

			@Override
			public void onLoadMore() {
				mHandler.sendEmptyMessageDelayed(1, 2000);
			}
		});
	}

	@Override
	protected void process(Bundle savedInstanceState) {
		mInflater = LayoutInflater.from(this);

		list = new ArrayList<Item>();
		for (int i = 0; i < 20; i++) {
			Item item = new Item();
			item.isChecked = false;
			item.text = i + "";
			list.add(item);
		}

		adapter = new MyAdapter();
		listView.setAdapter(adapter);
	}

	@Override
	public boolean handleMessage(Message msg) {
		switch (msg.what) {
		case 0:
			listView.stopRefresh();
			listView.setRefreshTime();
			break;
		case 1:
			listView.stopLoadMore();
			break;
		}
		return true;
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
				convertView = mInflater.inflate(R.layout.activity_xlist_item, parent, false);
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

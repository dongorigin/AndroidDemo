package cn.dong.demo.ui.list;

import java.util.ArrayList;
import java.util.List;

import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import cn.dong.demo.R;
import cn.dong.demo.base.BaseActivity;

public class ListViewActivity extends BaseActivity {
	private Button btn_refresh;
	private Button btn_visibility;
	private EditText et_input;
	private ListView listView;

	private List<Item> list;
	private MyAdapter adapter;

	@Override
	protected int initPageLayoutID() {
		return R.layout.activity_listview;
	}

	@Override
	protected void initPageView() {
		btn_refresh = (Button) findViewById(R.id.refresh);
		btn_visibility = (Button) findViewById(R.id.visibility);
		et_input = (EditText) findViewById(R.id.input);
		listView = (ListView) findViewById(R.id.list);
	}

	@Override
	protected void initPageViewListener() {
		btn_refresh.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				list.clear();
				for (int i = 0; i < 20; i++) {
					Item item = new Item();
					item.isChecked = false;
					item.text = i + "";
					list.add(item);
				}
				adapter.notifyDataSetChanged();

				listView.setSelection(0);
			}
		});
		btn_visibility.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				if (listView.getVisibility() == View.VISIBLE) {
					listView.setVisibility(View.GONE);
				} else {
					listView.setVisibility(View.VISIBLE);
				}
			}
		});
	}

	@Override
	protected void process(Bundle savedInstanceState) {

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
				convertView = LayoutInflater.from(mContext).inflate(R.layout.activity_xlist_item,
						parent, false);
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

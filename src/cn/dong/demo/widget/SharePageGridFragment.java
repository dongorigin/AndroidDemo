package cn.dong.demo.widget;

import java.util.ArrayList;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;
import cn.dong.demo.R;
import cn.dong.demo.widget.SharePage.ShareItem;

public class SharePageGridFragment extends Fragment {
	private LayoutInflater mInflater;
	private ArrayList<ShareItem> items;
	private PageGridAdapter adapter;

	private GridView gridView;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		gridView = (GridView) inflater.inflate(R.layout.share_page_grid_layout, container, false);
		return gridView;
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		mInflater = LayoutInflater.from(getActivity());
		items = new ArrayList<SharePage.ShareItem>();
		adapter = new PageGridAdapter();
		gridView.setAdapter(adapter);
		test();
	}

	private void test() {
		items.add(new ShareItem(R.drawable.logo_sinaweibo, R.string.sinaweibo));
		adapter.notifyDataSetChanged();
	}

	class PageGridAdapter extends BaseAdapter {
		@Override
		public int getCount() {
			return items.size();
		}

		@Override
		public ShareItem getItem(int position) {
			return items.get(position);
		}

		@Override
		public long getItemId(int position) {
			return position;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			ShareItem item = getItem(position);
			if (convertView == null) {
				convertView = mInflater.inflate(R.layout.share_page_grid_item, parent, false);
			}
			ImageView icon = (ImageView) convertView.findViewById(R.id.share_icon);
			TextView name = (TextView) convertView.findViewById(R.id.share_name);
			icon.setImageResource(item.icon);
			name.setText(item.name);
			return convertView;
		}

	}
}

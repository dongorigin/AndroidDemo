package cn.dong.demo.ui.main;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;
import cn.dong.demo.R;
import cn.dong.demo.base.BaseFragment;
import cn.dong.demo.ui.AnimeActivity;
import cn.dong.demo.ui.AutoCompleteActivity;
import cn.dong.demo.ui.ContentProviderActivity;
import cn.dong.demo.ui.DialogActivity;
import cn.dong.demo.ui.DrawerActivity;
import cn.dong.demo.ui.FragmentTestActivity;
import cn.dong.demo.ui.GridViewPagerActivity;
import cn.dong.demo.ui.ImageActivity;
import cn.dong.demo.ui.ImageLoaderActivity;
import cn.dong.demo.ui.ImageLoaderLargeActivity;
import cn.dong.demo.ui.ImageSelectorActivity;
import cn.dong.demo.ui.ListViewActivity;
import cn.dong.demo.ui.ShareActivity;
import cn.dong.demo.ui.TextSizeActivity;
import cn.dong.demo.ui.ViewPagerActivity;
import cn.dong.demo.ui.WaterfallActivity;
import cn.dong.demo.ui.WebViewActivity;
import cn.dong.demo.ui.XListViewActivity;

public class MainFragment extends BaseFragment {
	private static final String TAG = "MainFragment";
	private ListView listView;

	private DemoInfo[] demos = { new DemoInfo("XListView", "", XListViewActivity.class),
			new DemoInfo("ListView", "", ListViewActivity.class),
			new DemoInfo("Dialog", "", DialogActivity.class),
			new DemoInfo("AutoComplete", "", AutoCompleteActivity.class),
			new DemoInfo("FragmentTest", "", FragmentTestActivity.class),
			new DemoInfo("ViewPager", "", ViewPagerActivity.class),
			new DemoInfo("ImageLoader", "", ImageLoaderActivity.class),
			new DemoInfo("ImageSelector", "", ImageSelectorActivity.class),
			new DemoInfo("TextSize", "", TextSizeActivity.class),
			new DemoInfo("ContentProvider", "", ContentProviderActivity.class),
			new DemoInfo("Waterfall", "", WaterfallActivity.class),
			new DemoInfo("ImageLoaderLarge", "", ImageLoaderLargeActivity.class),
			new DemoInfo("Share", "", ShareActivity.class),
			new DemoInfo("WebView", "", WebViewActivity.class),
			new DemoInfo("GridViewPager", "", GridViewPagerActivity.class),
			new DemoInfo("Anime", "", AnimeActivity.class),
			new DemoInfo("Image", "", ImageActivity.class),
			new DemoInfo("Drawer", "", DrawerActivity.class) };

	static class DemoInfo {
		private String title;
		private String desc;
		private Class<? extends Activity> clazz;

		public DemoInfo(String title, String desc, Class<? extends Activity> clazz) {
			this.title = title;
			this.desc = desc;
			this.clazz = clazz;
		}
	}

	@Override
	protected int initPageLayoutID() {
		return R.layout.main_fragment;
	}

	@Override
	protected void initPageView(View rootView) {
		listView = (ListView) rootView.findViewById(android.R.id.list);
	}

	@Override
	protected void initPageViewListener() {
		listView.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				Intent intent = new Intent(context, demos[position].clazz);
				startActivity(intent);
			}
		});
	}

	@Override
	protected void process(Bundle savedInstanceState) {
		listView.setAdapter(new MainListAdapter());
	}

	class MainListAdapter extends BaseAdapter {

		@Override
		public int getCount() {
			return demos.length;
		}

		@Override
		public Object getItem(int position) {
			return demos[position];
		}

		@Override
		public long getItemId(int position) {
			return position;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			if (convertView == null) {
				convertView = LayoutInflater.from(context).inflate(R.layout.main_item, parent,
						false);
			}
			DemoInfo item = demos[position];
			TextView title = (TextView) convertView.findViewById(R.id.title);
			title.setText(item.title);
			return convertView;
		}

	}

}

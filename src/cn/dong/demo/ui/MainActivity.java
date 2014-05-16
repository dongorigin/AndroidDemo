package cn.dong.demo.ui;

import android.app.Activity;
import android.app.ActivityManager;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;
import cn.dong.demo.R;

public class MainActivity extends Activity {
	private static final String TAG = "Main";
	private ListView listView;

	private DemoInfo[] demos = { new DemoInfo("XListView", "", XListViewActivity.class),
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
			new DemoInfo("Anime", "", AnimeActivity.class) };

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		initData();
		initView();
	}

	private void initData() {
		ActivityManager activityManager = (ActivityManager) getSystemService(ACTIVITY_SERVICE);
		Log.d(TAG, "heap = " + activityManager.getMemoryClass());
		Log.d(TAG, "largeHeap = " + activityManager.getLargeMemoryClass());

	}

	private void initView() {
		listView = (ListView) findViewById(android.R.id.list);
		listView.setAdapter(new MainListAdapter());
		listView.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				Intent intent = new Intent(MainActivity.this, demos[position].clazz);
				startActivity(intent);
			}
		});
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
				convertView = getLayoutInflater().inflate(R.layout.activity_main_item, parent,
						false);
			}
			DemoInfo item = demos[position];
			TextView title = (TextView) convertView.findViewById(R.id.title);
			title.setText(item.title);
			return convertView;
		}

	}

	class DemoInfo {
		private String title;
		private String desc;
		private Class<? extends Activity> clazz;

		public DemoInfo(String title, String desc, Class<? extends Activity> clazz) {
			this.title = title;
			this.desc = desc;
			this.clazz = clazz;
		}
	}

}

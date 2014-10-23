package cn.dong.demo.ui.list;

import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.AsyncTask.Status;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import cn.dong.demo.R;
import cn.dong.demo.model.DuitangInfo;

import com.dodowaterfall.widget.Helper;
import com.dodowaterfall.widget.MultiColumnXListView;
import com.dodowaterfall.widget.ScaleImageView;
import com.dodowaterfall.widget.MultiColumnXListView.IXListViewListener;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.assist.ImageLoadingListener;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;

public class WaterfallActivity extends FragmentActivity implements IXListViewListener {
	private ImageLoader imageLoader;
	private DisplayImageOptions options;
	private MultiColumnXListView mAdapterView = null;
	private StaggeredAdapter mAdapter = null;
	private int currentPage = 0;
	ContentTask task = new ContentTask(this, 2);

	private class ContentTask extends AsyncTask<String, Integer, List<DuitangInfo>> {

		private Context mContext;
		private int mType = 1;

		public ContentTask(Context context, int type) {
			super();
			mContext = context;
			mType = type;
		}

		@Override
		protected List<DuitangInfo> doInBackground(String... params) {
			try {
				return parseNewsJSON(params[0]);
			} catch (IOException e) {
				e.printStackTrace();
			}
			return null;
		}

		@Override
		protected void onPostExecute(List<DuitangInfo> result) {
			if (mType == 1) {

				mAdapter.addItemTop(result);
				mAdapter.notifyDataSetChanged();
				mAdapterView.stopRefresh();

			} else if (mType == 2) {
				mAdapterView.stopLoadMore();
				mAdapter.addItemLast(result);
				mAdapter.notifyDataSetChanged();
			}

		}

		@Override
		protected void onPreExecute() {
		}

		public List<DuitangInfo> parseNewsJSON(String url) throws IOException {
			List<DuitangInfo> duitangs = new ArrayList<DuitangInfo>();
			String json = "";
			if (Helper.checkConnection(mContext)) {
				try {
					json = Helper.getStringFromUrl(url);

				} catch (IOException e) {
					Log.e("IOException is : ", e.toString());
					e.printStackTrace();
					return duitangs;
				}
			}
			Log.d("MainActiivty", "json:" + json);

			try {
				if (null != json) {
					JSONObject newsObject = new JSONObject(json);
					JSONObject jsonObject = newsObject.getJSONObject("data");
					JSONArray blogsJson = jsonObject.getJSONArray("blogs");

					for (int i = 0; i < blogsJson.length(); i++) {
						JSONObject newsInfoLeftObject = blogsJson.getJSONObject(i);
						DuitangInfo newsInfo1 = new DuitangInfo();
						newsInfo1.setAlbid(newsInfoLeftObject.isNull("albid") ? ""
								: newsInfoLeftObject.getString("albid"));
						newsInfo1.setIsrc(newsInfoLeftObject.isNull("isrc") ? ""
								: newsInfoLeftObject.getString("isrc"));
						newsInfo1.setMsg(newsInfoLeftObject.isNull("msg") ? "" : newsInfoLeftObject
								.getString("msg"));
						int height = newsInfoLeftObject.getInt("iht");
						// if (height > 2048) {
						// continue;
						// }
						// TODO 假设服务器未提供图片大小信息
						// newsInfo1.setHeight(height);
						duitangs.add(newsInfo1);
					}
				}
			} catch (JSONException e) {
				e.printStackTrace();
			}
			return duitangs;
		}
	}

	/**
	 * 添加内容
	 * 
	 * @param pageindex
	 * @param type
	 *            1为下拉刷新 2为加载更多
	 */
	private void AddItemToContainer(int pageindex, int type) {
		if (task.getStatus() != Status.RUNNING) {
			String url = "http://www.duitang.com/album/1733789/masn/p/" + pageindex + "/24/";
			Log.d("MainActivity", "current url:" + url);
			ContentTask task = new ContentTask(this, type);
			task.execute(url);
		}
	}

	public class StaggeredAdapter extends BaseAdapter {
		private Context mContext;
		private LinkedList<DuitangInfo> mInfos;
		private MultiColumnXListView mListView;

		public StaggeredAdapter(Context context, MultiColumnXListView xListView) {
			mContext = context;
			mInfos = new LinkedList<DuitangInfo>();
			mListView = xListView;
		}

		private void testItem() {
			DuitangInfo newsInfo1 = new DuitangInfo();
			String url =
					"http://img4.duitang.com/uploads/item/201212/13/20121213143309_JSXxW.thumb.200_0.jpeg";
			newsInfo1.setIsrc(url);
			mInfos.add(newsInfo1);
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			ViewHolder holder;
			DuitangInfo duitangInfo = mInfos.get(position);

			if (convertView == null) {
				LayoutInflater layoutInflator = LayoutInflater.from(parent.getContext());
				convertView = layoutInflator.inflate(R.layout.activity_waterfall_item, null);
				holder = new ViewHolder();
				holder.imageView = (ScaleImageView) convertView.findViewById(R.id.image);
				holder.text = (TextView) convertView.findViewById(R.id.text);
				convertView.setTag(holder);
			}

			holder = (ViewHolder) convertView.getTag();
			holder.text.setText(position + "");
			holder.imageView.setTag(position);
			Log.d("bitmap started", position
					+ " height="
					+ duitangInfo.getHeight()
					+ ", width="
					+ duitangInfo.getWidth());
			// 如果height存在，调整item的布局大小
			if (duitangInfo.getHeight() > 0) {
				holder.imageView.setImageHeight(duitangInfo.getHeight());
				holder.imageView.setImageWidth(duitangInfo.getWidth());
			}
			imageLoader.displayImage(duitangInfo.getIsrc(), holder.imageView, options,
					new ImageLoadingListener() {
						@Override
						public void onLoadingStarted(String imageUri, View view) {

						}

						@Override
						public void onLoadingFailed(String imageUri, View view,
								FailReason failReason) {

						}

						@Override
						public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
							ScaleImageView imageView = (ScaleImageView) view;
							// 更新图片的大小数据，以便再次加载时使用
							int position = (Integer) imageView.getTag();
							Log.d("bitmap complete", position
									+ " height="
									+ loadedImage.getHeight()
									+ ", width="
									+ loadedImage.getWidth());
							mInfos.get(position).setHeight(loadedImage.getHeight());
							imageView.setImageWidth(loadedImage.getWidth());
							imageView.setImageHeight(loadedImage.getHeight());
						}

						@Override
						public void onLoadingCancelled(String imageUri, View view) {

						}
					});
			return convertView;
		}

		class ViewHolder {
			ScaleImageView imageView;
			TextView text;
		}

		@Override
		public int getCount() {
			return mInfos.size();
		}

		@Override
		public Object getItem(int arg0) {
			return mInfos.get(arg0);
		}

		@Override
		public long getItemId(int arg0) {
			return 0;
		}

		public void addItemLast(List<DuitangInfo> datas) {
			mInfos.addAll(datas);
		}

		public void addItemTop(List<DuitangInfo> datas) {
			for (DuitangInfo info : datas) {
				mInfos.addFirst(info);
			}
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_waterfall);
		mAdapterView = (MultiColumnXListView) findViewById(R.id.list);
		mAdapterView.setPullLoadEnable(true);
		mAdapterView.setXListViewListener(this);

		mAdapter = new StaggeredAdapter(this, mAdapterView);

		initImageLoader();
		
//		Fragment fragment = new Fragment();
//		fragment.getLayoutInflater(savedInstanceState);
	}

	private void initImageLoader() {
		imageLoader = ImageLoader.getInstance();
		options =
				new DisplayImageOptions.Builder()
						.showImageOnLoading(R.drawable.empty_photo)
						.imageScaleType(ImageScaleType.NONE)
						.cacheInMemory(true)
						.cacheOnDisc(true)
						.build();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		return super.onCreateOptionsMenu(menu);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		return true;
	}

	@Override
	protected void onResume() {
		super.onResume();
		mAdapterView.setAdapter(mAdapter);
		// AddItemToContainer(currentPage, 2);
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();

	}

	@Override
	public void onRefresh() {
		AddItemToContainer(++currentPage, 1);

	}

	@Override
	public void onLoadMore() {
		AddItemToContainer(++currentPage, 2);

	}
}// end of class

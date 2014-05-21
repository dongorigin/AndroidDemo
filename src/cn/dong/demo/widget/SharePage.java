package cn.dong.demo.widget;

import java.util.ArrayList;
import java.util.HashMap;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.Handler.Callback;
import android.os.Message;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import cn.dong.demo.R;
import cn.dong.demo.util.MeasureUtil;
import cn.dong.demo.util.T;
import cn.sharesdk.framework.Platform;
import cn.sharesdk.framework.Platform.ShareParams;
import cn.sharesdk.framework.PlatformActionListener;
import cn.sharesdk.framework.ShareSDK;
import cn.sharesdk.framework.utils.UIHandler;
import cn.sharesdk.system.email.Email;
import cn.sharesdk.system.text.ShortMessage;
import cn.sharesdk.tencent.qq.QQ;
import cn.sharesdk.wechat.friends.Wechat;
import cn.sharesdk.wechat.moments.WechatMoments;

import com.nostra13.universalimageloader.utils.L;

/**
 * 自定义分享页
 * 
 * @author dong 2014-5-20
 */
public class SharePage implements Callback, PlatformActionListener, OnItemClickListener {
	private static final String TAG = "Share";

	private static final int COMPLETE = 1;
	private static final int ORROR = 2;
	private static final int CANCEL = 3;
	private static final int MSG_NOTIFY_CANCEL = 4;

	private Handler mHandler;
	private LayoutInflater mInflater;
	private Activity context;
	private Dialog dialog;
	private PlatformActionListener callback;

	private String shareContent = "嗨，我正在使用天下互联客户端，赶快来试试吧！！";
	private String shareTitle = "好友推荐";

	private static ArrayList<ShareItem> items; // 分享的平台列表
	static {
		items = new ArrayList<SharePage.ShareItem>();
		items.add(new ShareItem(R.drawable.logo_qq, R.string.qq));
		items.add(new ShareItem(R.drawable.logo_wechat, R.string.wechat));
		items.add(new ShareItem(R.drawable.logo_wechatmoments, R.string.wechatmoments));
		items.add(new ShareItem(R.drawable.logo_email, R.string.email));
		items.add(new ShareItem(R.drawable.logo_shortmessage, R.string.shortmessage));
		items.add(new ShareItem(R.drawable.logo_more, R.string.more));
	}

	public static class ShareItem {
		public int icon;
		public int name;

		public ShareItem(int icon, int name) {
			this.icon = icon;
			this.name = name;
		}
	}

	public SharePage(Activity context) {
		this.context = context;
		init();
		initPageView();
	}

	public void show() {
		if (dialog != null) {
			dialog.show();
		}
	}

	public void dismiss() {
		if (dialog != null) {
			dialog.dismiss();
		}
	}

	/**
	 * text 分享文本，支持全平台
	 */
	public void setContent(String text) {
		shareContent = text;
	}

	/**
	 * text 分享标题，微信、邮件使用
	 */
	public void setTitle(String text) {
		shareTitle = text;
	}

	/**
	 * 设置自定义的外部回调
	 */
	public void setCallback(PlatformActionListener callback) {
		this.callback = callback;
	}

	private void init() {
		ShareSDK.initSDK(context);
		mHandler = new Handler(this);
		mInflater = LayoutInflater.from(context);
		callback = this;
	}

	private void initPageView() {
		GridView gridView = new GridView(context);
		gridView.setAdapter(new PageGridAdapter());
		gridView.setOnItemClickListener(this);
		gridView.setNumColumns(3);
		gridView.setGravity(Gravity.CENTER);
		gridView.setStretchMode(GridView.STRETCH_COLUMN_WIDTH);
		gridView.setSelector(R.color.transparent); // TODO 暂时取消点击效果
		int padding = MeasureUtil.px2Dip(context, 12);
		gridView.setPadding(padding, padding, padding, padding);

		AlertDialog.Builder builder = new AlertDialog.Builder(context);
		builder.setTitle("分享");
		builder.setView(gridView);
		dialog = builder.create();
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

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
		ShareItem item = items.get(position);
		if (item != null) {
			share(item.name);
		}
	}

	private void share(int id) {
		if (id == R.string.more) { // 点击更多时，调动系统原生分享
			nativeShare();
			dialog.dismiss();
			return;
		}

		Platform platform = null;
		HashMap<String, Object> data = new HashMap<String, Object>();
		data.put("text", shareContent);
		switch (id) {
		// case R.string.sinaweibo:
		// platform = ShareSDK.getPlatform(context, SinaWeibo.NAME);
		// break;
		// case R.string.tencentweibo:
		// platform = ShareSDK.getPlatform(context, TencentWeibo.NAME);
		// break;
		case R.string.qq:
			data.put("title", shareTitle);
			data.put("text", shareContent);
			platform = ShareSDK.getPlatform(context, QQ.NAME);
			if (!platform.isValid()) {
				Log.d(TAG, "qq isValid");
				T.shortT(context, R.string.qq_client_inavailable);
				return;
			}
			break;
		case R.string.wechat:
			data.put("shareType", Platform.SHARE_TEXT);
			data.put("title", shareTitle);
			platform = ShareSDK.getPlatform(context, Wechat.NAME);
			if (!platform.isValid()) {
				Log.d(TAG, "wechat isValid");
				T.shortT(context, R.string.wechat_client_inavailable);
				return;
			}
			break;
		case R.string.wechatmoments:
			data.put("shareType", Platform.SHARE_TEXT);
			data.put("title", shareTitle);
			platform = ShareSDK.getPlatform(context, WechatMoments.NAME);
			if (!platform.isValid()) {
				Log.d(TAG, "wechat isValid");
				T.shortT(context, R.string.wechat_client_inavailable);
				return;
			}
			break;
		case R.string.shortmessage:
			data.put("address", "");
			platform = ShareSDK.getPlatform(context, ShortMessage.NAME);
			break;
		case R.string.email:
			data.put("title", shareTitle);
			platform = ShareSDK.getPlatform(context, Email.NAME);
			break;
		default:
			L.w(TAG, "分享对象不存在");
			return;
		}
		ShareParams sp = new ShareParams(data);
		platform.setPlatformActionListener(callback);
		platform.share(sp);
		showNotification(2000, context.getResources().getString(R.string.sharing));
		dismiss();
	}

	/**
	 * 系统原生分享方式
	 */
	private void nativeShare() {
		Intent intent = new Intent(Intent.ACTION_SEND);
		intent.setType("text/plain");
		intent.putExtra(Intent.EXTRA_SUBJECT, shareTitle);
		intent.putExtra(Intent.EXTRA_TEXT, shareContent);
		intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		context.startActivity(Intent.createChooser(intent, ""));
	}

	@Override
	public void onCancel(Platform platform, int action) {
		mHandler.sendEmptyMessage(CANCEL);
	}

	@Override
	public void onComplete(Platform platform, int action, HashMap<String, Object> res) {
		mHandler.sendEmptyMessage(COMPLETE);
	}

	@Override
	public void onError(Platform platform, int action, Throwable t) {
		Log.w(TAG, t.toString());
		mHandler.sendEmptyMessage(ORROR);
	}

	@Override
	public boolean handleMessage(Message msg) {
		switch (msg.what) {
		case COMPLETE:
			Toast.makeText(context, R.string.share_complete, Toast.LENGTH_SHORT).show();
			break;
		case ORROR:
			Toast.makeText(context, R.string.share_error, Toast.LENGTH_SHORT).show();
			break;
		case CANCEL:

			break;
		case MSG_NOTIFY_CANCEL:
			NotificationManager nm = (NotificationManager) msg.obj;
			if (nm != null) {
				nm.cancel(msg.arg1);
			}
			break;
		}
		dismiss();
		return true;
	}

	// 在状态栏提示分享操作
	private void showNotification(long cancelTime, String text) {
		try {
			Context app = context.getApplicationContext();
			NotificationManager nm = (NotificationManager) app
					.getSystemService(Context.NOTIFICATION_SERVICE);
			final int id = Integer.MAX_VALUE / 13 + 1;
			nm.cancel(id);

			long when = System.currentTimeMillis();
			Notification notification = new Notification(R.drawable.ic_launcher, text, when);
			PendingIntent pi = PendingIntent.getActivity(app, 0, new Intent(), 0);
			notification.setLatestEventInfo(app, context.getResources()
					.getString(R.string.app_name), text, pi);
			notification.flags = Notification.FLAG_AUTO_CANCEL;
			nm.notify(id, notification);

			if (cancelTime > 0) {
				Message msg = new Message();
				msg.what = MSG_NOTIFY_CANCEL;
				msg.obj = nm;
				msg.arg1 = id;
				UIHandler.sendMessageDelayed(msg, cancelTime, this);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}

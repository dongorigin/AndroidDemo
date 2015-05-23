package cn.dong.demo.ui;

import java.util.ArrayList;
import java.util.List;

import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Message;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import cn.dong.demo.R;
import cn.dong.demo.ui.common.BaseActivity;
import cn.dong.demo.ui.common.MyBaseAdapter;
import cn.dong.demo.util.T;
import cn.dong.demo.util.ViewHolder;

/**
 * PackageManager使用测试
 * 
 * @author dong 2014-7-20
 */
public class PackageManagerActivity extends BaseActivity {
    private ListView listView;

    private PackageManager pm;
    private List<AppInfo> data;
    private PackageInfoAdapter adapter;

    /**
     * 应用信息
     */
    private static class AppInfo {
        public Drawable appIcon;
        public String appName;
        public String appPackage;
    }

    @Override
    protected void init() {
        super.init();
        pm = getPackageManager();
        data = new ArrayList<AppInfo>();
        adapter = new PackageInfoAdapter(mContext, data);
    }

    @Override
    protected int initPageLayoutID() {
        return R.layout.packagemanager_activity;
    }

    @Override
    protected void initPageView() {
        listView = (ListView) findViewById(R.id.list);
    }

    @Override
    protected void initPageViewListener() {
        listView.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String packageName = adapter.getItem(position).appPackage;
                Intent launchIntent = pm.getLaunchIntentForPackage(packageName);
                if (launchIntent != null) {
                    startActivity(launchIntent);
                }
            }
        });
        listView.setOnItemLongClickListener(new OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                String packageName = adapter.getItem(position).appPackage;
                ClipboardManager cmb = (ClipboardManager) getSystemService(CLIPBOARD_SERVICE);
                cmb.setText(packageName);
                T.shortT(mContext, "包名已复制到剪贴板");
                return true;
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.package_manager_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.show_system:
                getData(true);
                return true;
            case R.id.hide_system:
                getData(false);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    protected void process(Bundle savedInstanceState) {
        listView.setAdapter(adapter);
        getData(false);
    }

    /**
     * 获取并格式化应用数据
     * 
     * @param containSystem 是否包含系统应用
     */
    private void getData(final boolean containSystem) {
        showLoadingDialog("加载中", false);
        new Thread() {
            @Override
            public void run() {
                data.clear();
                List<PackageInfo> packageList =
                        pm.getInstalledPackages(PackageManager.GET_ACTIVITIES);
                for (PackageInfo packageInfo : packageList) {
                    if (containSystem
                            || (packageInfo.applicationInfo.flags & ApplicationInfo.FLAG_SYSTEM) == 0) {
                        AppInfo info = new AppInfo();
                        info.appIcon = packageInfo.applicationInfo.loadIcon(pm);
                        info.appName = packageInfo.applicationInfo.loadLabel(pm).toString();
                        info.appPackage = packageInfo.packageName;
                        data.add(info);
                    }
                }
                mHandler.sendEmptyMessage(1);
            }
        }.start();
    }

    @Override
    protected void performHandleMessage(Message msg) {
        dismissLoadingDialog();
        switch (msg.what) {
            case 1:
                adapter.notifyDataSetChanged();
                break;
            default:
                break;
        }
    }

    private static class PackageInfoAdapter extends MyBaseAdapter<AppInfo> {

        public PackageInfoAdapter(Context context, List<AppInfo> data) {
            super(context, data);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            AppInfo item = getItem(position);
            if (convertView == null) {
                convertView = mInflater.inflate(R.layout.packagemanager_item, parent, false);
            }
            ImageView appIcon = ViewHolder.get(convertView, R.id.app_icon);
            TextView appName = ViewHolder.get(convertView, R.id.app_name);
            TextView appPackage = ViewHolder.get(convertView, R.id.app_package);

            appIcon.setImageDrawable(item.appIcon);
            appName.setText(item.appName);
            appPackage.setText(item.appPackage);
            return convertView;
        }

    }

}

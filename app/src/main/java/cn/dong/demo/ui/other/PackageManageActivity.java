package cn.dong.demo.ui.other;

import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Message;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import butterknife.InjectView;
import cn.dong.demo.R;
import cn.dong.demo.ui.common.BaseActivity;

/**
 * PackageManager使用测试
 *
 * @author dong 2014-7-20
 */
public class PackageManageActivity extends BaseActivity {
    @InjectView(R.id.recycler)
    RecyclerView mRecyclerView;

    private PackageManager mPackageManager;
    private PackageInfoAdapter mAdapter;

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
        mPackageManager = getPackageManager();
        mAdapter = new PackageInfoAdapter();
    }

    @Override
    protected int initPageLayoutID() {
        return R.layout.activity_packagemanager;
    }

    @Override
    protected void initPageView() {
        mRecyclerView.setLayoutManager(new LinearLayoutManager(mContext));
    }

    @Override
    protected void initPageViewListener() {
//        listView.setOnItemClickListener(new OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                String packageName = mAdapter.getItem(position).appPackage;
//                Intent launchIntent = mPackageManager.getLaunchIntentForPackage(packageName);
//                if (launchIntent != null) {
//                    startActivity(launchIntent);
//                }
//            }
//        });
//        listView.setOnItemLongClickListener(new OnItemLongClickListener() {
//            @Override
//            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
//                String packageName = mAdapter.getItem(position).appPackage;
//                ClipboardManager cmb = (ClipboardManager) getSystemService(CLIPBOARD_SERVICE);
//                cmb.setText(packageName);
//                T.shortT(mContext, "包名已复制到剪贴板");
//                return true;
//            }
//        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_packagemanager, menu);
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
        mRecyclerView.setAdapter(mAdapter);
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
                mAdapter.mData.clear();
                List<PackageInfo> packageList = mPackageManager.getInstalledPackages(PackageManager.GET_ACTIVITIES);
                for (PackageInfo packageInfo : packageList) {
                    if (containSystem
                            || (packageInfo.applicationInfo.flags & ApplicationInfo.FLAG_SYSTEM) == 0) {
                        AppInfo info = new AppInfo();
                        info.appIcon = packageInfo.applicationInfo.loadIcon(mPackageManager);
                        info.appName = packageInfo.applicationInfo.loadLabel(mPackageManager).toString();
                        info.appPackage = packageInfo.packageName;
                        mAdapter.mData.add(info);
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
                mAdapter.notifyDataSetChanged();
                break;
            default:
                break;
        }
    }

    private static class PackageInfoAdapter extends RecyclerView.Adapter<PackageInfoAdapter.ItemViewHolder> {
        private List<AppInfo> mData = new ArrayList<>();

        static class ItemViewHolder extends RecyclerView.ViewHolder {
            public final ImageView mIconView;
            public final TextView mNameView;
            public final TextView mPackagenameView;

            public ItemViewHolder(View itemView) {
                super(itemView);
                mIconView = (ImageView) itemView.findViewById(R.id.app_icon);
                mNameView = (TextView) itemView.findViewById(R.id.app_name);
                mPackagenameView = (TextView) itemView.findViewById(R.id.app_package);
            }
        }

        @Override
        public int getItemCount() {
            return mData.size();
        }

        @Override
        public ItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_packagemanager_item, parent, false);
            return new ItemViewHolder(itemView);
        }

        @Override
        public void onBindViewHolder(ItemViewHolder holder, int position) {
            AppInfo item = mData.get(position);
            holder.mIconView.setImageDrawable(item.appIcon);
            holder.mNameView.setText(item.appName);
            holder.mPackagenameView.setText(item.appPackage);
        }

    }

}

package cn.dong.demo.ui.main;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import butterknife.InjectView;
import cn.dong.demo.R;
import cn.dong.demo.config.Extra;
import cn.dong.demo.ui.other.Md5Activity;
import cn.dong.demo.ui.animation.MarkAnimationActivity;
import cn.dong.demo.ui.common.BaseActivity;
import cn.dong.demo.ui.other.ScreenshotActivity;
import cn.dong.demo.ui.other.ImageSelectorActivity;
import cn.dong.demo.ui.creation.FlexibleHeaderRecyclerViewActivity;
import cn.dong.demo.ui.creation.FadeoutHeaderRecyclerViewActivity;
import cn.dong.demo.ui.userinterface.FragmentTabHostActivity;
import cn.dong.demo.ui.textandinput.TextViewActivity;
import cn.dong.demo.ui.userinterface.ViewPagerActivity;
import cn.dong.demo.util.ViewHolder;

public class NewMainActivity extends BaseActivity {

    @InjectView(R.id.list)
    ListView listView;

    List<MainItem> mData;
    BaseAdapter mAdapter;

    static class MainItem {
        public String title;
        public Class cls;

        public MainItem(String title, Class cls) {
            this.title = title;
            this.cls = cls;
        }
    }

    @Override
    protected int initPageLayoutID() {
        return R.layout.activity_newmain;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mData = new ArrayList<>();
        mAdapter = new MainAdapter();

        listView.setAdapter(mAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                MainItem item = mData.get(position);
                Intent intent = new Intent(mContext, item.cls);
                intent.putExtra(Extra.TITLE, item.title);
                startActivity(intent);
            }
        });

        initMainList();
    }

    private void initMainList() {
        mData.add(new MainItem("FlexibleHeader", FlexibleHeaderRecyclerViewActivity.class));
        mData.add(new MainItem("ViewPager", ViewPagerActivity.class));
        mData.add(new MainItem("Text", TextViewActivity.class));
        mData.add(new MainItem("CustomView", FragmentTabHostActivity.class));
        mData.add(new MainItem("Input", Md5Activity.class));
        mData.add(new MainItem("Scroll", FadeoutHeaderRecyclerViewActivity.class));
        mData.add(new MainItem("Screenshot", ScreenshotActivity.class));
        mData.add(new MainItem("ImageSelected", ImageSelectorActivity.class));
        mData.add(new MainItem("MarkUpDemo", MarkAnimationActivity.class));

        mAdapter.notifyDataSetChanged();
    }

    private class MainAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return mData.size();
        }

        @Override
        public MainItem getItem(int position) {
            return mData.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            MainItem item = getItem(position);
            if (convertView == null) {
                convertView = getLayoutInflater().inflate(R.layout.main_item, parent, false);
            }

            TextView textView = ViewHolder.get(convertView, R.id.title);
            textView.setText(item.title);
            return convertView;
        }
    }

}

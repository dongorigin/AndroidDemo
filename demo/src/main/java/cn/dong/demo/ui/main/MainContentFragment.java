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

import java.util.List;

import cn.dong.demo.R;
import cn.dong.demo.ui.common.BaseActivity;
import cn.dong.demo.ui.common.BaseFragment;

public class MainContentFragment extends BaseFragment {
    private ListView listView;

    private MainActivity mMainActivity;
    private List<MainActivity.Item> items;
    private BaseAdapter mAdapter;

    public void updateContentList(List<MainActivity.Item> items) {
        this.items = items;
        mAdapter.notifyDataSetChanged();
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        mMainActivity = (MainActivity) activity;
        items = mMainActivity.getItems();
    }

    @Override
    protected int initPageLayoutId() {
        return R.layout.fragment_main_content;
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
                Intent intent = new Intent(mContext, items.get(position).cls);
                intent.putExtra(BaseActivity.EXTRA_TITLE, items.get(position).title);
                startActivity(intent);
            }
        });
    }

    @Override
    protected void process(Bundle savedInstanceState) {
        mAdapter = new MainListAdapter();
        listView.setAdapter(mAdapter);
    }

    class MainListAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return items != null ? items.size() : 0;
        }

        @Override
        public MainActivity.Item getItem(int position) {
            return items.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            if (convertView == null) {
                convertView =
                        LayoutInflater.from(mContext).inflate(R.layout.main_item, parent, false);
            }
            MainActivity.Item item = getItem(position);
            TextView title = (TextView) convertView.findViewById(R.id.title);
            title.setText(item.title);
            return convertView;
        }

    }

}

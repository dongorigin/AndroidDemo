package cn.dong.demo.ui.main;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import cn.dong.demo.R;
import cn.dong.demo.ui.common.BaseFragment;
import cn.dong.demo.ui.common.MyBaseAdapter;

/**
 * @author dong on 15/5/24.
 */
public class MainDrawerFragment extends BaseFragment {
    @InjectView(R.id.list)
    ListView listView;

    private MainActivity mMainActivity;
    private DrawerAdapter mAdapter;

    public interface OnDrawerItemSelectedListener {
        void onDrawerItemSelected(int position);
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        mMainActivity = (MainActivity) activity;
    }

    @Override
    protected int initPageLayoutId() {
        return R.layout.fragment_main_drawer;
    }

    @Override
    protected void initPageView(View rootView) {
        mAdapter = new DrawerAdapter(mContext, mMainActivity.getDrawerItems());
        listView.setAdapter(mAdapter);
    }

    @Override
    protected void initPageViewListener() {
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                mMainActivity.onDrawerItemSelected(position);
            }
        });
    }

    private static class DrawerAdapter extends MyBaseAdapter<MainActivity.DrawerItem> {

        public DrawerAdapter(Context context, List<MainActivity.DrawerItem> data) {
            super(context, data);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            if (convertView == null) {
                convertView = LayoutInflater.from(mContext).inflate(R.layout.fragment_main_drawer_item, parent, false);
            }
            convertView.setSelected(getItem(position).isSelected);

            TextView textView = ButterKnife.findById(convertView, R.id.title);
            textView.setText(getItem(position).title);
            return convertView;
        }
    }

    public void updateList() {
        mAdapter.notifyDataSetChanged();
    }

}

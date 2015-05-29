package cn.dong.demo.ui.main;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import cn.dong.demo.R;
import cn.dong.demo.ui.common.BaseActivity;
import cn.dong.demo.ui.common.BaseFragment;

public class MainContentFragment extends BaseFragment {
    @InjectView(R.id.recycler)
    RecyclerView mRecyclerView;

    private MainActivity mMainActivity;
    private List<MainActivity.Item> items;
    private RecyclerView.Adapter mAdapter;

    public void updateContentList(List<MainActivity.Item> items) {
        this.items = items;
        mAdapter.notifyDataSetChanged();
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        mMainActivity = (MainActivity) activity;
        items = mMainActivity.getCurrentItems();
        mAdapter = new MainListAdapter();
    }

    @Override
    protected int initPageLayoutId() {
        return R.layout.fragment_main_content;
    }

    @Override
    protected void initPageView(View rootView) {
        mRecyclerView.setAdapter(mAdapter);
    }

    class MainListAdapter extends RecyclerView.Adapter<ItemViewHolder> {

        public MainActivity.Item getItem(int position) {
            return items.get(position);
        }

        @Override
        public int getItemCount() {
            return items != null ? items.size() : 0;
        }

        @Override
        public ItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_main_content_item, parent, false);
            return new ItemViewHolder(itemView);
        }

        @Override
        public void onBindViewHolder(ItemViewHolder holder, int position) {
            final MainActivity.Item item = getItem(position);
            holder.titleView.setText(item.title);
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(mContext, item.cls);
                    intent.putExtra(BaseActivity.EXTRA_TITLE, item.title);
                    startActivity(intent);
                }
            });
        }
    }

    static class ItemViewHolder extends RecyclerView.ViewHolder {
        TextView titleView;

        public ItemViewHolder(View itemView) {
            super(itemView);
            titleView = ButterKnife.findById(itemView, R.id.title);
        }
    }

}

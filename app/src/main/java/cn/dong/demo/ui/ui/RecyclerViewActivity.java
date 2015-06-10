package cn.dong.demo.ui.ui;

import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.TextHttpResponseHandler;
import com.squareup.picasso.Picasso;

import org.apache.http.Header;

import java.util.ArrayList;
import java.util.List;

import butterknife.InjectView;
import cn.dong.demo.R;
import cn.dong.demo.model.DuitangBlog;
import cn.dong.demo.model.DuitangResponseInfo;
import cn.dong.demo.ui.common.BaseActivity;

/**
 * 新增控件RecyclerView试用，被设计来替代ListView<br/>
 * 相比ListView，RecyclerView的item布局管理与动画被抽离，更加灵活，可方便的扩展。
 * Adapter也被重新设计，面向ViewHolder ，逻辑更清晰。
 *
 * @author dong 2014-7-13
 */
public class RecyclerViewActivity extends BaseActivity {
    @InjectView(R.id.recycler)
    RecyclerView mRecyclerView;

    private RecyclerViewAdapter mAdapter;

    @Override
    protected int initPageLayoutID() {
        return R.layout.activity_recyclerview;
    }

    @Override
    protected void initPageView() {
        // 创建布局管理器
//        LinearLayoutManager layoutManager = new LinearLayoutManager(mContext);
        StaggeredGridLayoutManager layoutManager = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
        // 设置布局管理器
        mRecyclerView.setLayoutManager(layoutManager);
        // 设置适配器
        mAdapter = new RecyclerViewAdapter();
        mRecyclerView.setAdapter(mAdapter);
        // 请求网络数据
        fetchData();
    }

    /**
     * 网络
     */
    private void fetchData() {
        String url = "http://www.duitang.com/album/1733789/masn/p/0/24/";
        AsyncHttpClient client = new AsyncHttpClient();
        client.get(url, new TextHttpResponseHandler() {
            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {

            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, String responseString) {
                parseData(responseString);
            }
        });

    }

    private void parseData(String json) {
        DuitangResponseInfo info = new Gson().fromJson(json, DuitangResponseInfo.class);
        mAdapter.addData(info.data.duitangBlogs);
    }

    private static class RecyclerViewAdapter extends RecyclerView.Adapter<ItemViewHolder> {
        private List<DuitangBlog> mData = new ArrayList<>();

        public RecyclerViewAdapter() {

        }

        public void addData(List<DuitangBlog> data) {
            mData.addAll(data);
            notifyItemRangeInserted(mData.size() - data.size(), mData.size());
        }

        @Override
        public int getItemCount() {
            return mData.size();
        }

        @Override
        public ItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            LayoutInflater inflater = LayoutInflater.from(parent.getContext());
            View itemView = inflater.inflate(R.layout.activity_recyclerview_item, parent, false);
            return new ItemViewHolder(itemView);
        }

        @Override
        public void onBindViewHolder(ItemViewHolder holder, int position) {
            DuitangBlog duitangBlog = mData.get(position);
            holder.textView.setText(duitangBlog.msg);
            Picasso.with(holder.itemView.getContext()).load(duitangBlog.isrc).into(holder.imageView);
        }

    }

    static class ItemViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;
        TextView textView;

        public ItemViewHolder(View itemView) {
            super(itemView);
            imageView = (ImageView) itemView.findViewById(R.id.image);
            textView = (TextView) itemView.findViewById(R.id.text);
        }
    }

}

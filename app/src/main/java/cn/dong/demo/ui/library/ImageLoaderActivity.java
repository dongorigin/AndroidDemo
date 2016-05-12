package cn.dong.demo.ui.library;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import cn.dong.demo.OkHttpHelper;
import cn.dong.demo.R;
import cn.dong.demo.model.DuitangBlog;
import cn.dong.demo.model.DuitangResponseInfo;
import cn.dong.demo.ui.common.BaseActivity;
import okhttp3.Call;
import okhttp3.Request;
import okhttp3.Response;
import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

public class ImageLoaderActivity extends BaseActivity {
    private ListView listView;
    private ImageAdapter mAdapter;

    @Override
    protected int initPageLayoutID() {
        return R.layout.imageloader_activity;
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

            }
        });
    }

    @Override
    protected void process(Bundle savedInstanceState) {
        mAdapter = new ImageAdapter();
        listView.setAdapter(mAdapter);
        fetchData();
    }

    /**
     * 网络
     */
    private void fetchData() {
        String url = "http://www.duitang.com/album/1733789/masn/p/0/24/";
        Request request = new Request.Builder()
                .url(url)
                .build();
        final Call call = OkHttpHelper.INSTANCE.getOkHttpClient().newCall(request);
        Observable
                .create(new Observable.OnSubscribe<String>() {
                    @Override
                    public void call(Subscriber<? super String> subscriber) {
                        try {
                            Response response = call.execute();
                            subscriber.onNext(response.body().string());
                            subscriber.onCompleted();
                        } catch (IOException e) {
                            subscriber.onError(e);
                        }
                    }
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<String>() {
                    @Override
                    public void call(String s) {
                        parseData(s);
                    }
                });
    }

    private void parseData(String json) {
        DuitangResponseInfo info = new Gson().fromJson(json, DuitangResponseInfo.class);
        mAdapter.addData(info.data.duitangBlogs);
    }

    private static class ImageAdapter extends BaseAdapter {
        private List<DuitangBlog> mData = new ArrayList<>();

        public void addData(List<DuitangBlog> data) {
            mData.clear();
            mData.addAll(data);
            notifyDataSetChanged();
        }

        @Override
        public int getCount() {
            return mData.size();
        }

        @Override
        public DuitangBlog getItem(int position) {
            return mData.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            if (convertView == null) {
                convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_imageloader_item, parent, false);
            }
            TextView text = (TextView) convertView.findViewById(R.id.text);
            text.setText("item " + position);
            ImageView image = (ImageView) convertView.findViewById(R.id.image);

            DuitangBlog blog = getItem(position);
            Picasso.with(image.getContext())
                    .load(blog.isrc)
                    .into(image);
            return convertView;
        }
    }

}

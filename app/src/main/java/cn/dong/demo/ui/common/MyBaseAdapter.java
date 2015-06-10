package cn.dong.demo.ui.common;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

/**
 * Adapter 基类
 * 
 * @param <E> 数据实体类
 * @author dong 2014-7-25
 */
public abstract class MyBaseAdapter<E> extends BaseAdapter {
    protected Context mContext;
    protected List<E> mData;
    protected LayoutInflater mInflater;

    public MyBaseAdapter(Context context, List<E> data) {
        this.mContext = context;
        this.mData = data;
        mInflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        if (mData != null) {
            return mData.size();
        } else {
            return 0;
        }
    }

    @Override
    public E getItem(int position) {
        if (mData != null && mData.size() > 0) {
            return mData.get(position);
        } else {
            return null;
        }
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public List<E> getData() {
        return mData;
    }

    public void setData(List<E> data) {
        this.mData = data;
    }

    @Override
    public abstract View getView(int position, View convertView, ViewGroup parent);
}

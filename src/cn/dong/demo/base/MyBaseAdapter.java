package cn.dong.demo.base;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

public abstract class MyBaseAdapter<E> extends BaseAdapter {
	protected Context context;
	protected List<E> data;
	protected LayoutInflater mInflater;

	public MyBaseAdapter(Context context, List<E> data) {
		this.context = context;
		this.data = data;
		mInflater = LayoutInflater.from(context);
	}

	@Override
	public int getCount() {
		if (data != null) {
			return data.size();
		} else {
			return 0;
		}
	}

	@Override
	public E getItem(int position) {
		if (data != null && data.size() > 0) {
			return data.get(position);
		} else {
			return null;
		}
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	public List<E> getData() {
		return data;
	}

	public void setData(List<E> data) {
		this.data = data;
	}

	@Override
	public abstract View getView(int position, View convertView, ViewGroup parent);
}

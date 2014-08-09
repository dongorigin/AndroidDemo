package cn.dong.demo.ui.list;

import java.util.ArrayList;
import java.util.List;

import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import cn.dong.demo.R;
import cn.dong.demo.base.BaseActivity;

/**
 * 新增控件RecyclerView试用，被设计来替代ListView<br/>
 * 相比ListView，RecyclerView的item布局管理与动画被抽离，更加灵活，可方便的扩展。
 * Adapter也被重新设计，面向ViewHolder ，逻辑更清晰。
 * 
 * @author dong 2014-7-13
 */
public class RecyclerViewActivity extends BaseActivity {
	private List<String> data;
	private RecyclerViewAdapter adapter;

	private RecyclerView horizontalRecyclerView;
	private RecyclerView verticalRecyclerView;

	@Override
	protected int initPageLayoutID() {
		return R.layout.recyclerview_activity;
	}
	
	@Override
	protected void init() {
		super.init();
		data = new ArrayList<String>();
		adapter = new RecyclerViewAdapter(data);
	}

	@Override
	protected void initPageView() {
		horizontalRecyclerView = (RecyclerView) findViewById(R.id.list_horizontal);
		verticalRecyclerView = (RecyclerView) findViewById(R.id.list_vertical);
	}

	@Override
	protected void initPageViewListener() {
	}

	@Override
	protected void process(Bundle savedInstanceState) {
		// 创建线性布局管理器
		LinearLayoutManager layoutManager = new LinearLayoutManager(mContext);
		layoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
		// 设置布局管理器
		horizontalRecyclerView.setLayoutManager(layoutManager);
		// 设置动画
		horizontalRecyclerView.setItemAnimator(new DefaultItemAnimator());
		// 设置适配器
		horizontalRecyclerView.setAdapter(adapter);
		
		// 创建线性布局管理器
		LinearLayoutManager layoutManager2 = new LinearLayoutManager(mContext);
		layoutManager2.setOrientation(LinearLayoutManager.VERTICAL);
		// 设置布局管理器
		verticalRecyclerView.setLayoutManager(layoutManager2);
		// 设置适配器
		verticalRecyclerView.setAdapter(adapter);
		
		for (int i = 0; i < 20; i++) {
			data.add(String.valueOf(i));
		}
		adapter.notifyDataSetChanged();
	}

	private static class RecyclerViewAdapter extends RecyclerView.Adapter<ViewHolder> {
		private List<String> data;

		public RecyclerViewAdapter(List<String> data) {
			this.data = data;
		}

		@Override
		public int getItemCount() {
			if (data != null) {
				return data.size();
			} else {
				return 0;
			}
		}

		@Override
		public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
			LayoutInflater inflater = LayoutInflater.from(parent.getContext());
			View itemView = inflater.inflate(R.layout.activity_xlist_item, parent, false);
			ViewHolder holder = new ViewHolder(itemView);
			holder.titleView = (TextView) itemView.findViewById(R.id.text);
			return holder;
		}

		@Override
		public void onBindViewHolder(ViewHolder holder, int position) {
			String item = data.get(position);
			holder.titleView.setText(item);
		}

	}

	private static class ViewHolder extends RecyclerView.ViewHolder {
		public TextView titleView;

		public ViewHolder(View itemView) {
			super(itemView);
		}
	}

}

package cn.dong.demo.ui.original;

import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import cn.dong.demo.R;
import cn.dong.demo.util.L;

/**
 * 数字Adapter，便于测试
 *
 * @author dong on 15/3/4.
 */
public class NumberedAdapter extends RecyclerView.Adapter<NumberedAdapter.NumberViewHolder> {
    private static final int TYPE_ITEM_LEFT = 0;
    private static final int TYPE_ITEM_RIGHT = 1;
    private static final int TYPE_HEADER = 2;

    private List<String> data;
    private View mHeaderView;

    public NumberedAdapter(int count, View headerView) {
        mHeaderView = headerView;
        data = new ArrayList<>();
        for (int i = 0; i < count; i++) {
            data.add(String.valueOf(i));
        }
    }

    public void addHeader(int count) {
        for (int i = 0; i < count; i++) {
            data.add(0, String.format("new %d", i));
        }
    }

    public void add(int count) {
        for (int i = 0; i < count; i++) {
            data.add(String.format("more %d", i));
        }
    }

    @Override
    public int getItemViewType(int position) {
        if (position == 0) {
            return TYPE_HEADER;
        } else {
            return ((position - 1) % 2 == 0) ? TYPE_ITEM_LEFT : TYPE_ITEM_RIGHT;
        }
    }

    @Override
    public NumberViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == TYPE_HEADER) {
            return new HeaderViewHolder(mHeaderView);
        } else {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.numbered_item, parent, false);
            TextViewHolder holder = new TextViewHolder(view);
            switch (viewType) {
                default:
                case TYPE_ITEM_LEFT:
                    holder.textView.setGravity(Gravity.START);
                    return holder;
                case TYPE_ITEM_RIGHT:
                    holder.textView.setGravity(Gravity.END);
                    return holder;
            }
        }
    }

    @Override
    public void onBindViewHolder(NumberViewHolder holder, int position) {
        switch (holder.getItemViewType()) {
            case TYPE_ITEM_LEFT:
            case TYPE_ITEM_RIGHT:
                TextViewHolder textViewHolder = (TextViewHolder) holder;
                textViewHolder.textView.setText(data.get(position - 1));
                break;
            case TYPE_HEADER:
            default:
                break;
        }
    }

    @Override
    public int getItemCount() {
        return data != null ? data.size() + 1 : 0;
    }

    @Override
    public void onViewRecycled(NumberViewHolder holder) {
        L.d("adapter", "onViewRecycled");
        super.onViewRecycled(holder);
    }

    static class NumberViewHolder extends RecyclerView.ViewHolder {

        public NumberViewHolder(View itemView) {
            super(itemView);
        }
    }

    static class TextViewHolder extends NumberViewHolder {
        @InjectView(R.id.text)
        TextView textView;

        public TextViewHolder(View itemView) {
            super(itemView);
            ButterKnife.inject(this, itemView);
        }
    }

    static class HeaderViewHolder extends NumberViewHolder {

        public HeaderViewHolder(View itemView) {
            super(itemView);
        }
    }
}

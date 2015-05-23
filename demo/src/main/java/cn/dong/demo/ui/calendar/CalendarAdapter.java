package cn.dong.demo.ui.calendar;

import java.util.List;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import cn.dong.demo.R;
import cn.dong.demo.ui.common.MyBaseAdapter;
import cn.dong.demo.model.CalendarInfo;

public class CalendarAdapter extends MyBaseAdapter<CalendarInfo> {
    private int space;

    public CalendarAdapter(Context context, List<CalendarInfo> data, int space) {
        super(context, data);
        this.space = space;
    }

    @Override
    public int getCount() {
        if (mData != null) {
            return mData.size() + space;
        } else {
            return 0;
        }
    }

    @Override
    public CalendarInfo getItem(int position) {
        return super.getItem(position - space);
    }

    @Override
    public boolean areAllItemsEnabled() {
        return false;
    }

    @Override
    public boolean isEnabled(int position) {
        return position >= space;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.calendar_item, parent, false);
        }
        if (isEnabled(position)) {
            CalendarInfo item = getItem(position);
            ((TextView) convertView).setText(String.valueOf(item.getDate()));
        } else {
            ((TextView) convertView).setText("");
        }
        return convertView;
    }

}

package cn.dong.demo.ui.calendar;

import java.util.List;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import cn.dong.demo.R;
import cn.dong.demo.base.MyBaseAdapter;
import cn.dong.demo.model.CalendarInfo;

public class CalendarAdapter extends MyBaseAdapter<CalendarInfo> {

    public CalendarAdapter(Context context, List<CalendarInfo> data) {
        super(context, data);
    }

    @Override
    public boolean areAllItemsEnabled() {
        return false;
    }

    @Override
    public boolean isEnabled(int position) {
        return getItem(position).getDate() > 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        CalendarInfo item = getItem(position);
        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.calendar_item, parent, false);
        }
        if (item.getDate() > 0) {
            ((TextView) convertView).setText(String.valueOf(item.getDate()));
        }
        return convertView;
    }

}

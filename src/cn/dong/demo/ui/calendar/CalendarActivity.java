package cn.dong.demo.ui.calendar;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;
import cn.dong.demo.R;
import cn.dong.demo.base.BaseActivity;
import cn.dong.demo.model.CalendarInfo;

/**
 * 日历
 * 
 * @author dong 2014-8-19
 */
public class CalendarActivity extends BaseActivity {
    private GridView mCalendarGrid;

    private Calendar mCalendar;
    private List<CalendarInfo> mData;
    private CalendarAdapter mAdapter;

    @Override
    protected void init() {
        super.init();
        mCalendar = Calendar.getInstance();
        mData = new ArrayList<CalendarInfo>();
        mAdapter = new CalendarAdapter(mContext, mData);
    }

    @Override
    protected int initPageLayoutID() {
        return R.layout.activity_calendar;
    }

    @Override
    protected void initPageView() {
        mCalendarGrid = (GridView) findViewById(R.id.calendar);
    }

    @Override
    protected void initPageViewListener() {
        mCalendarGrid.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // TODO Auto-generated method stub

            }
        });
    }

    @Override
    protected void process(Bundle savedInstanceState) {
        mCalendarGrid.setAdapter(mAdapter);

        mCalendar.set(Calendar.DATE, 1);
        int space = mCalendar.get(Calendar.DAY_OF_WEEK) - 1;
        for (int i = 0; i < space; i++) {
            mData.add(new CalendarInfo(0, 0, 0));
        }
        int daysOfMonth = mCalendar.getActualMaximum(Calendar.DAY_OF_MONTH);
        for (int i = 1; i <= daysOfMonth; i++) {
            mData.add(new CalendarInfo(mCalendar.get(Calendar.YEAR), mCalendar.get(Calendar.MONTH),
                    i));
        }

        mAdapter.notifyDataSetChanged();
    }

}

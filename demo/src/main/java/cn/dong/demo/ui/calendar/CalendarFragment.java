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
import cn.dong.demo.ui.common.BaseFragment;
import cn.dong.demo.model.CalendarInfo;

/**
 * 日历Fragment，根据传入的Calendar绘制日历
 * 
 * @author dong 2014-10-19
 */
public class CalendarFragment extends BaseFragment {
    public static final String EXTRA_CALENDAR = "calendar";

    private GridView mCalendarGrid;

    private Calendar mCalendar;
    private List<CalendarInfo> mData;
    private CalendarAdapter mAdapter;

    @Override
    protected void init() {
        super.init();
        mData = new ArrayList<CalendarInfo>();
        mCalendar = (Calendar) getArguments().getSerializable(EXTRA_CALENDAR);
        int space = mCalendar.get(Calendar.DAY_OF_WEEK) - 1;
        int daysOfMonth = mCalendar.getActualMaximum(Calendar.DAY_OF_MONTH);
        for (int i = 1; i <= daysOfMonth; i++) {
            mData.add(new CalendarInfo(mCalendar.get(Calendar.YEAR), mCalendar.get(Calendar.MONTH),
                    i));
        }

        mAdapter = new CalendarAdapter(mContext, mData, space);
    }

    @Override
    protected int initPageLayoutID() {
        return R.layout.fragment_calendar;
    }

    @Override
    protected void initPageView(View rootView) {
        mCalendarGrid = (GridView) rootView.findViewById(R.id.calendar);
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
    }
}

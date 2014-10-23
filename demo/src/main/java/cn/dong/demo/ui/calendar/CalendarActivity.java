package cn.dong.demo.ui.calendar;

import java.util.Calendar;
import java.util.GregorianCalendar;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.util.Log;
import android.view.Menu;
import android.view.View;

import cn.dong.demo.R;
import cn.dong.demo.base.BaseActivity;

/**
 * 日历
 * 
 * @author dong 2014-8-19
 */
public class CalendarActivity extends BaseActivity {
    private static final String TAG = "CalendarActivity";
    private static final int SIZE = 1000; // Integer.MAX_VALUE;

    private ViewPager mViewPager;

    private PagerAdapter mAdapter;
    private Calendar mCalendar;
    private int curPosition;
    private int curYear;
    private int curMonth;

    @Override
    protected boolean onPrepareOptionsPanel(View view, Menu menu) {
        return super.onPrepareOptionsPanel(view, menu);
    }

    @Override
    public boolean onCreatePanelMenu(int featureId, Menu menu) {
        return super.onCreatePanelMenu(featureId, menu);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void init() {
        super.init();
        mAdapter = new CalendarPagerAdapter(getSupportFragmentManager());
        mCalendar = new GregorianCalendar();
    }

    @Override
    protected int initPageLayoutID() {
        return R.layout.activity_calendar;
    }

    @Override
    protected void initPageView() {
        mViewPager = (ViewPager) findViewById(R.id.pager);
    }

    @Override
    protected void initPageViewListener() {
        mViewPager.setOnPageChangeListener(new OnPageChangeListener() {
            @Override
            public void onPageSelected(int position) {
                Log.i(TAG, "onPageSelected " + position);
                mCalendar.add(Calendar.MONTH, position - curPosition);
                curYear = mCalendar.get(Calendar.YEAR);
                curMonth = mCalendar.get(Calendar.MONTH);
                curPosition = position;

                getActionBar().setTitle(String.format("%s年%s月", curYear, curMonth + 1));
            }

            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {}

            @Override
            public void onPageScrollStateChanged(int state) {}
        });
    }

    @Override
    protected void process(Bundle savedInstanceState) {
        curPosition = SIZE / 2;
        mViewPager.setOffscreenPageLimit(2);
        mViewPager.setAdapter(mAdapter);
        // 必须在setAdapter之后调用才有效，因为Adapter为空时无意义
        mViewPager.setCurrentItem(curPosition);
    }

    private class CalendarPagerAdapter extends FragmentPagerAdapter {

        public CalendarPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            Log.i(TAG, "PagerAdapter getItem " + position);
            Calendar calendar = new GregorianCalendar(curYear, curMonth, 1);
            calendar.add(Calendar.MONTH, position - curPosition);

            CalendarFragment fragment = new CalendarFragment();
            Bundle args = new Bundle();
            args.putSerializable(CalendarFragment.EXTRA_CALENDAR, calendar);
            fragment.setArguments(args);
            return fragment;
        }

        @Override
        public int getCount() {
            return SIZE;
        }

    }
}

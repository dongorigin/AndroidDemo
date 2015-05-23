package cn.dong.demo.ui;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import cn.dong.demo.R;
import cn.dong.demo.ui.common.BaseActivity;
import cn.dong.demo.ui.fragment.TestFragment;

public class ViewPagerActivity extends BaseActivity {
    private ViewPager mViewPager;
    private MyPagerAdapter mPagerAdapter;

    @Override
    protected int initPageLayoutID() {
        return R.layout.activity_viewpager;
    }

    @Override
    protected void initPageView() {
        mViewPager = (ViewPager) findViewById(R.id.pager);
    }

    @Override
    protected void initPageViewListener() {

    }

    @Override
    protected void process(Bundle savedInstanceState) {
        mPagerAdapter = new MyPagerAdapter(this);
        mViewPager.setAdapter(mPagerAdapter);
        mViewPager.setOffscreenPageLimit(9);
    }

    public static class MyPagerAdapter extends FragmentPagerAdapter {
        private final Context mContext;

        public MyPagerAdapter(FragmentActivity activity) {
            super(activity.getSupportFragmentManager());
            mContext = activity;
        }

        @Override
        public Fragment getItem(int position) {
            Log.d("PagerAdapter", "getItem " + position);
            Bundle args = new Bundle();
            args.putInt("Num", position);
            return Fragment.instantiate(mContext, TestFragment.class.getName(), args);
        }

        @Override
        public int getCount() {
            return 10;
        }

    }
}

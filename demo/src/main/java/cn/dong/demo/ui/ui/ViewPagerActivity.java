package cn.dong.demo.ui.ui;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;

import butterknife.InjectView;
import cn.dong.demo.R;
import cn.dong.demo.config.Extra;
import cn.dong.demo.ui.common.BaseActivity;

/**
 * @author dong on 15/3/27.
 */
public class ViewPagerActivity extends BaseActivity {
    @InjectView(R.id.pager)
    ViewPager viewPager;

    @Override
    protected int initPageLayoutID() {
        return R.layout.activity_viewpager;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        viewPager.setAdapter(new MyPagerAdapter(getSupportFragmentManager()));
    }

    private static class MyPagerAdapter extends FragmentStatePagerAdapter {

        public MyPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public int getCount() {
            return 5;
        }

        @Override
        public Fragment getItem(int position) {
            Log.d("PagerAdapter", "getItem " + position);
            Fragment fragment = new ColorFragment();
            Bundle args = new Bundle();
            args.putInt(Extra.POSITION, position);
            fragment.setArguments(args);
            return fragment;
        }

    }
}

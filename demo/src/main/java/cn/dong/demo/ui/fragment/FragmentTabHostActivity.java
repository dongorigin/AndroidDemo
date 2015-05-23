package cn.dong.demo.ui.fragment;

import android.os.Bundle;
import android.support.v4.app.FragmentTabHost;
import cn.dong.demo.R;
import cn.dong.demo.ui.common.BaseActivity;

/**
 * FragmentTabHost实验室
 * <p>
 * 和预想一样，子Fragment只会调用一次onCreate()，切换时销毁View，但保留全部成员变量
 * 
 * @author dong 2014-9-18
 */
public class FragmentTabHostActivity extends BaseActivity {

    FragmentTabHost mTabHost;

    @Override
    protected int initPageLayoutID() {
        return R.layout.activity_fragment_tabhost;
    }

    @Override
    protected void initPageView() {
        mTabHost = (FragmentTabHost) findViewById(android.R.id.tabhost);
    }

    @Override
    protected void initPageViewListener() {}

    @Override
    protected void process(Bundle savedInstanceState) {
        mTabHost.setup(mContext, getSupportFragmentManager(), R.id.realtabcontent);
        mTabHost.addTab(mTabHost.newTabSpec("simple1").setIndicator("Simple1"), TestFragment.class,
                getArgs(1));
        mTabHost.addTab(mTabHost.newTabSpec("simple2").setIndicator("Simple2"), TestFragment.class,
                getArgs(2));
        mTabHost.addTab(mTabHost.newTabSpec("simple3").setIndicator("Simple3"), TestFragment.class,
                getArgs(3));
    }

    private Bundle getArgs(int i) {
        Bundle args = new Bundle();
        args.putInt("Num", i);
        return args;
    }

}

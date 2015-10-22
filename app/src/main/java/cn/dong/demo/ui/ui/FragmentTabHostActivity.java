package cn.dong.demo.ui.ui;

import android.os.Bundle;
import android.support.v4.app.FragmentTabHost;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TabHost;

import butterknife.InjectView;
import cn.dong.demo.R;
import cn.dong.demo.config.Extra;
import cn.dong.demo.ui.common.BaseActivity;
import cn.dong.demo.util.L;

/**
 * @author dong on 15/4/17.
 */
public class FragmentTabHostActivity extends BaseActivity {
    @InjectView(android.R.id.tabhost)
    FragmentTabHost mTabHost;

    @Override
    protected int initPageLayoutID() {
        return R.layout.activity_tabhost;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mTabHost.setup(mContext, getSupportFragmentManager(), R.id.realtabcontent);
        mTabHost.addTab(getTabSpec("tab1"), ColorFragment.class, getArgs(0));
        mTabHost.addTab(getTabSpec("tab2"), ColorFragment.class, getArgs(1));
        mTabHost.addTab(getTabSpec("tab3"), CustomViewFragment.class, null);
    }

    private TabHost.TabSpec getTabSpec(String tab) {
        return mTabHost.newTabSpec(tab).setIndicator(tab);
    }

    private Bundle getArgs(int position) {
        Bundle args = new Bundle();
        args.putInt(Extra.POSITION, position);
        return args;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_customview, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_settings:
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        L.d(TAG, "onSaveInstanceState");
        super.onSaveInstanceState(outState);

    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        L.d(TAG, "onRestoreInstanceState");
        super.onRestoreInstanceState(savedInstanceState);
    }
}

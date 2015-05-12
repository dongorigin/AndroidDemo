package cn.dong.demo.ui.main;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.widget.DrawerLayout;
import android.view.Gravity;
import android.view.View;

import cn.dong.demo.R;
import cn.dong.demo.base.BaseActivity;

public class MainActivity extends BaseActivity {
    private static final String TAG = "Main";

    private DrawerLayout mDrawerLayout;

    @Override
    protected int initPageLayoutID() {
        return R.layout.main_activity;
    }

    @Override
    protected void initPageView() {
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        mDrawerLayout.setStatusBarBackgroundColor(getResources().getColor(R.color.theme_primary_dark));
        mDrawerLayout.setDrawerShadow(R.drawable.drawer_shadow, Gravity.START);

        if (getActionBarToolbar() != null) {
            getActionBarToolbar().setNavigationIcon(R.drawable.ic_drawer);
            getActionBarToolbar().setNavigationOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mDrawerLayout.openDrawer(Gravity.START);
                }
            });
        }
    }


    @Override
    protected void initPageViewListener() {
        mDrawerLayout.setDrawerListener(new DrawerLayout.DrawerListener() {
            @Override
            public void onDrawerSlide(View view, float v) {

            }

            @Override
            public void onDrawerOpened(View view) {

            }

            @Override
            public void onDrawerClosed(View view) {

            }

            @Override
            public void onDrawerStateChanged(int i) {

            }
        });
    }

    @Override
    protected void process(Bundle savedInstanceState) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.content_frame, new MainFragment()).commit();
    }


}

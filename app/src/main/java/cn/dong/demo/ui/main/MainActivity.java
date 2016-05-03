package cn.dong.demo.ui.main;

import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.view.MenuItem;

import java.util.HashMap;

import butterknife.InjectView;
import cn.dong.demo.R;
import cn.dong.demo.ui.animation.AnimationActivity;
import cn.dong.demo.ui.animation.MarkAnimationActivity;
import cn.dong.demo.ui.common.BaseActivity;
import cn.dong.demo.ui.component.FragmentManageActivity;
import cn.dong.demo.ui.component.IntentsActivity;
import cn.dong.demo.ui.component.NotificationActivity;
import cn.dong.demo.ui.component.PowerManagerActivity;
import cn.dong.demo.ui.component.SendDataOneActivity;
import cn.dong.demo.ui.component.SendDataTwoActivity;
import cn.dong.demo.ui.component.ServiceActivity;
import cn.dong.demo.ui.library.ImageLoaderActivity;
import cn.dong.demo.ui.library.OttoActivity;
import cn.dong.demo.ui.library.RealmActivity;
import cn.dong.demo.ui.library.RxJavaActivity;
import cn.dong.demo.ui.library.XListViewActivity;
import cn.dong.demo.ui.original.FadeoutHeaderRecyclerViewActivity;
import cn.dong.demo.ui.original.FlexibleHeaderRecyclerViewActivity;
import cn.dong.demo.ui.original.FlowLayoutActivity;
import cn.dong.demo.ui.original.audio.AudioRecorderActivity;
import cn.dong.demo.ui.original.calendar.CalendarActivity;
import cn.dong.demo.ui.other.ImageSelectorActivity;
import cn.dong.demo.ui.other.Md5Activity;
import cn.dong.demo.ui.other.PackageManageActivity;
import cn.dong.demo.ui.other.ScreenshotActivity;
import cn.dong.demo.ui.other.WebViewActivity;
import cn.dong.demo.ui.sensor.GeocoderActivity;
import cn.dong.demo.ui.storage.BitmapSaveLocalActivity;
import cn.dong.demo.ui.storage.ContentProviderActivity;
import cn.dong.demo.ui.text.AutoCompleteActivity;
import cn.dong.demo.ui.text.EditTextActivity;
import cn.dong.demo.ui.text.TextAdvanceActivity;
import cn.dong.demo.ui.text.TextSizeActivity;
import cn.dong.demo.ui.text.TextViewSpanActivity;
import cn.dong.demo.ui.ui.DialogActivity;
import cn.dong.demo.ui.ui.DrawableStateActivity;
import cn.dong.demo.ui.ui.FragmentTabHostActivity;
import cn.dong.demo.ui.ui.PopupWindowActivity;
import cn.dong.demo.ui.ui.RecyclerViewActivity;
import cn.dong.demo.ui.ui.SwipeRefreshLayoutActivity;
import cn.dong.demo.ui.ui.ViewPagerActivity;
import cn.dong.demo.ui.ui.touch.TouchEventActivity;

public class MainActivity extends BaseActivity {

    private static HashMap<Integer, Class[]> sNavigationMap;

    static {
        Class[] originalActivities = {
                CalendarActivity.class,
                FlexibleHeaderRecyclerViewActivity.class,
                FadeoutHeaderRecyclerViewActivity.class,
                FlowLayoutActivity.class,
                AudioRecorderActivity.class
        };
        Class[] libraryActivities = {
                ImageLoaderActivity.class,
                XListViewActivity.class,
                OttoActivity.class,
                RxJavaActivity.class,
                RealmActivity.class
        };
        Class[] componentActivities = {
                ServiceActivity.class,
                FragmentManageActivity.class,
                NotificationActivity.class,
                SendDataOneActivity.class,
                SendDataTwoActivity.class,
                IntentsActivity.class,
                PowerManagerActivity.class
        };
        Class[] uiActivities = {
                TouchEventActivity.class,
                RecyclerViewActivity.class,
                ViewPagerActivity.class,
                SwipeRefreshLayoutActivity.class,
                FragmentTabHostActivity.class,
                DialogActivity.class,
                PopupWindowActivity.class,
                DrawableStateActivity.class
        };
        Class[] textActivities = {
                TextSizeActivity.class,
                EditTextActivity.class,
                AutoCompleteActivity.class,
                TextViewSpanActivity.class,
                TextAdvanceActivity.class
        };
        Class[] animationActivities = {
                AnimationActivity.class,
                MarkAnimationActivity.class
        };
        Class[] sensorActivities = {
                GeocoderActivity.class
        };
        Class[] storageActivities = {
                ContentProviderActivity.class,
                BitmapSaveLocalActivity.class
        };
        Class[] otherActivities = {
                WebViewActivity.class,
                PackageManageActivity.class,
                ScreenshotActivity.class,
                ImageSelectorActivity.class,
                Md5Activity.class
        };
        sNavigationMap = new HashMap<>();
        sNavigationMap.put(R.id.navigation_original, originalActivities);
        sNavigationMap.put(R.id.navigation_library, libraryActivities);
        sNavigationMap.put(R.id.navigation_component, componentActivities);
        sNavigationMap.put(R.id.navigation_ui, uiActivities);
        sNavigationMap.put(R.id.navigation_text, textActivities);
        sNavigationMap.put(R.id.navigation_animation, animationActivities);
        sNavigationMap.put(R.id.navigation_sensor, sensorActivities);
        sNavigationMap.put(R.id.navigation_storage, storageActivities);
        sNavigationMap.put(R.id.navigation_other, otherActivities);
    }

    @InjectView(R.id.drawer_layout)
    DrawerLayout mDrawerLayout;
    @InjectView(R.id.navigation)
    NavigationView mNavigationView;

    private ActionBarDrawerToggle mDrawerToggle;
    private int currentNavigationId;

    @Override
    protected int initPageLayoutID() {
        return R.layout.activity_main;
    }

    @Override
    protected void initPageView() {
        mDrawerLayout.setDrawerShadow(R.drawable.drawer_shadow, GravityCompat.START);
        mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout, getActionBarToolbar(), R.string.drawer_open, R.string.drawer_close);
        mDrawerLayout.setDrawerListener(mDrawerToggle);
        mDrawerToggle.syncState();
    }

    @Override
    protected void initPageViewListener() {
        mNavigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem menuItem) {
                if (sNavigationMap.containsKey(menuItem.getItemId())) {
                    menuItem.setChecked(true);
                    setTitle(menuItem.getTitle());
                    currentNavigationId = menuItem.getItemId();
                    MainContentFragment contentFragment = (MainContentFragment) getSupportFragmentManager().findFragmentById(R.id.content_layout);
                    if (contentFragment != null) {
                        contentFragment.updateContentList(sNavigationMap.get(currentNavigationId));
                    }
                    mDrawerLayout.closeDrawers();
                    return true;
                } else if (menuItem.getItemId() == R.id.navigation_settings) {
                    // todo
                    return true;
                } else {
                    return false;
                }
            }
        });
    }

    @Override
    protected void process(Bundle savedInstanceState) {
        currentNavigationId = R.id.navigation_original;
        MainContentFragment fragment = new MainContentFragment();
        fragment.updateContentList(sNavigationMap.get(currentNavigationId));
        getSupportFragmentManager().beginTransaction().replace(R.id.content_layout, fragment).commit();
    }

}

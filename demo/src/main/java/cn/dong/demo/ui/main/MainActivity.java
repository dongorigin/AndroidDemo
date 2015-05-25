package cn.dong.demo.ui.main;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.view.Gravity;

import java.util.Arrays;
import java.util.List;

import cn.dong.demo.R;
import cn.dong.demo.ui.animation.AnimationActivity;
import cn.dong.demo.ui.animation.MarkAnimationActivity;
import cn.dong.demo.ui.common.BaseActivity;
import cn.dong.demo.ui.components.FragmentManageActivity;
import cn.dong.demo.ui.components.IntentsActivity;
import cn.dong.demo.ui.creation.FadeoutHeaderRecyclerViewActivity;
import cn.dong.demo.ui.creation.FlexibleHeaderRecyclerViewActivity;
import cn.dong.demo.ui.creation.FlowLayoutActivity;
import cn.dong.demo.ui.creation.calendar.CalendarActivity;
import cn.dong.demo.ui.datastorage.BitmapSaveLocalActivity;
import cn.dong.demo.ui.datastorage.ContentProviderActivity;
import cn.dong.demo.ui.librarys.ImageLoaderActivity;
import cn.dong.demo.ui.librarys.XListViewActivity;
import cn.dong.demo.ui.locationandsensors.GeocoderActivity;
import cn.dong.demo.ui.other.ImageSelectorActivity;
import cn.dong.demo.ui.other.Md5Activity;
import cn.dong.demo.ui.other.PackageManagerActivity;
import cn.dong.demo.ui.other.ScreenshotActivity;
import cn.dong.demo.ui.other.WebViewActivity;
import cn.dong.demo.ui.textandinput.AutoCompleteActivity;
import cn.dong.demo.ui.textandinput.TextSizeActivity;
import cn.dong.demo.ui.textandinput.TextViewActivity;
import cn.dong.demo.ui.userinterface.DialogActivity;
import cn.dong.demo.ui.userinterface.DrawableStateActivity;
import cn.dong.demo.ui.userinterface.FragmentTabHostActivity;
import cn.dong.demo.ui.userinterface.PopupWindowActivity;
import cn.dong.demo.ui.userinterface.RecyclerViewActivity;
import cn.dong.demo.ui.userinterface.SwipeRefreshLayoutActivity;
import cn.dong.demo.ui.userinterface.ViewPagerActivity;
import cn.dong.demo.ui.userinterface.touch.TouchEventActivity;
import cn.dong.demo.util.L;

public class MainActivity extends BaseActivity implements MainDrawerFragment.OnDrawerItemSelectedListener {
    private DrawerLayout mDrawerLayout;
    private ActionBarDrawerToggle mDrawerToggle;

    private DrawerItem selectedDrawerItem;

    private List<Item> creationList = Arrays.asList(
            new Item("Calendar", CalendarActivity.class),
            new Item("FlexibleHeader", FlexibleHeaderRecyclerViewActivity.class),
            new Item("FadeoutHeader", FadeoutHeaderRecyclerViewActivity.class),
            new Item("FlowLayout", FlowLayoutActivity.class)
    );

    private List<Item> libraryList = Arrays.asList(
            new Item("ImageLoader", ImageLoaderActivity.class),
            new Item("XListView", XListViewActivity.class)
    );

    private List<Item> componentList = Arrays.asList(
            new Item("FragmentManage", FragmentManageActivity.class),
            new Item("Intents", IntentsActivity.class)
    );

    private List<Item> uiList = Arrays.asList(
            new Item("TouchEvent", TouchEventActivity.class),
            new Item("RecyclerView", RecyclerViewActivity.class),
            new Item("ViewPager", ViewPagerActivity.class),
            new Item("SwipeRefreshLayout", SwipeRefreshLayoutActivity.class),
            new Item("FragmentTabHost", FragmentTabHostActivity.class),
            new Item("Dialog", DialogActivity.class),
            new Item("PopupWindow", PopupWindowActivity.class),
            new Item("DrawableState", DrawableStateActivity.class)
    );

    private List<Item> textList = Arrays.asList(
            new Item("TextSize", TextSizeActivity.class),
            new Item("Span", TextViewActivity.class),
            new Item("AutoComplete", AutoCompleteActivity.class)
    );

    private List<Item> animationList = Arrays.asList(
            new Item("Animation", AnimationActivity.class),
            new Item("MarkAnim", MarkAnimationActivity.class)
    );

    private List<Item> locationList = Arrays.asList(
            new Item("Geocoder", GeocoderActivity.class)
    );

    private List<Item> storageList = Arrays.asList(
            new Item("ContentProvider", ContentProviderActivity.class),
            new Item("SaveBitmap", BitmapSaveLocalActivity.class)
    );
    private List<Item> otherList = Arrays.asList(
            new Item("WebView", WebViewActivity.class),
            new Item("PackageManage", PackageManagerActivity.class),
            new Item("Screenshot", ScreenshotActivity.class),
            new Item("ImageSelector", ImageSelectorActivity.class),
            new Item("Md5", Md5Activity.class)
    );

    private List<DrawerItem> drawerItems = Arrays.asList(
            new DrawerItem("Creation", creationList),
            new DrawerItem("Library", libraryList),
            new DrawerItem("Components", componentList),
            new DrawerItem("User Interface", uiList),
            new DrawerItem("Text and Input", textList),
            new DrawerItem("Animation", animationList),
            new DrawerItem("Location and Sensors", locationList),
            new DrawerItem("Data Storage", storageList),
            new DrawerItem("Other", otherList)
    );

    static class DrawerItem {
        boolean isSelected;
        String title;
        List<Item> items;

        DrawerItem(String title, List<Item> items) {
            this.title = title;
            this.items = items;
        }
    }

    static class Item {
        String title;
        Class<? extends Activity> cls;

        public Item(String title, Class<? extends Activity> cls) {
            this.title = title;
            this.cls = cls;
        }
    }

    List<DrawerItem> getDrawerItems() {
        return drawerItems;
    }

    List<Item> getItems() {
        return selectedDrawerItem.items;
    }

    @Override
    protected int initPageLayoutID() {
        return R.layout.main_activity;
    }

    @Override
    protected void initPageView() {
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        mDrawerLayout.setStatusBarBackgroundColor(getResources().getColor(R.color.theme_primary_dark));
        mDrawerLayout.setDrawerShadow(R.drawable.drawer_shadow, Gravity.START);
    }

    @Override
    protected void initPageViewListener() {
        mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout, getActionBarToolbar(), R.string.drawer_open, R.string.drawer_close);
        mDrawerLayout.setDrawerListener(mDrawerToggle);
        mDrawerToggle.syncState();
    }

    @Override
    protected void process(Bundle savedInstanceState) {
        selectedDrawerItem = drawerItems.get(0);
        getSupportFragmentManager().beginTransaction().replace(R.id.content_frame, new MainContentFragment()).commit();
    }

    @Override
    public void onDrawerItemSelected(int position) {
        L.d(TAG, "onDrawerItemSelected " + position);
        DrawerItem drawerItem = drawerItems.get(position);
        if (selectedDrawerItem != drawerItem) {
            selectedDrawerItem = drawerItem;

            setTitle(selectedDrawerItem.title);

            for (DrawerItem item : drawerItems) {
                item.isSelected = (item == selectedDrawerItem);
            }

            // update
            MainDrawerFragment drawerFragment = (MainDrawerFragment) getSupportFragmentManager().findFragmentById(R.id.drawer);
            if (drawerFragment != null) {
                drawerFragment.updateList();
            }
            MainContentFragment contentFragment = (MainContentFragment) getSupportFragmentManager().findFragmentById(R.id.content_frame);
            if (contentFragment != null) {
                contentFragment.updateContentList(selectedDrawerItem.items);
            }
        }
        mDrawerLayout.closeDrawers();
    }
}

package cn.dong.demo.ui.main;

import android.app.Activity;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.view.MenuItem;

import java.util.Arrays;
import java.util.List;

import butterknife.InjectView;
import cn.dong.demo.R;
import cn.dong.demo.ui.animation.AnimationActivity;
import cn.dong.demo.ui.animation.MarkAnimationActivity;
import cn.dong.demo.ui.common.BaseActivity;
import cn.dong.demo.ui.component.FragmentManageActivity;
import cn.dong.demo.ui.component.IntentsActivity;
import cn.dong.demo.ui.component.ServiceActivity;
import cn.dong.demo.ui.original.FadeoutHeaderRecyclerViewActivity;
import cn.dong.demo.ui.original.FlexibleHeaderRecyclerViewActivity;
import cn.dong.demo.ui.original.FlowLayoutActivity;
import cn.dong.demo.ui.original.calendar.CalendarActivity;
import cn.dong.demo.ui.library.ImageLoaderActivity;
import cn.dong.demo.ui.library.XListViewActivity;
import cn.dong.demo.ui.other.ImageSelectorActivity;
import cn.dong.demo.ui.other.Md5Activity;
import cn.dong.demo.ui.other.PackageManagerActivity;
import cn.dong.demo.ui.other.ScreenshotActivity;
import cn.dong.demo.ui.other.WebViewActivity;
import cn.dong.demo.ui.sensor.GeocoderActivity;
import cn.dong.demo.ui.storage.BitmapSaveLocalActivity;
import cn.dong.demo.ui.storage.ContentProviderActivity;
import cn.dong.demo.ui.text.AutoCompleteActivity;
import cn.dong.demo.ui.text.EditTextActivity;
import cn.dong.demo.ui.text.TextSizeActivity;
import cn.dong.demo.ui.text.TextViewActivity;
import cn.dong.demo.ui.ui.DialogActivity;
import cn.dong.demo.ui.ui.DrawableStateActivity;
import cn.dong.demo.ui.ui.FragmentTabHostActivity;
import cn.dong.demo.ui.ui.PopupWindowActivity;
import cn.dong.demo.ui.ui.RecyclerViewActivity;
import cn.dong.demo.ui.ui.SwipeRefreshLayoutActivity;
import cn.dong.demo.ui.ui.ViewPagerActivity;
import cn.dong.demo.ui.ui.touch.TouchEventActivity;

public class MainActivity extends BaseActivity {
    @InjectView(R.id.drawer_layout)
    DrawerLayout mDrawerLayout;
    @InjectView(R.id.navigation)
    NavigationView mNavigationView;

    private ActionBarDrawerToggle mDrawerToggle;

    private List<Item> currentItems;

    private List<Item> originalItems = Arrays.asList(
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
            new Item("Service", ServiceActivity.class),
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
            new Item("EditText", EditTextActivity.class),
            new Item("AutoComplete", AutoCompleteActivity.class),
            new Item("Span", TextViewActivity.class)
    );

    private List<Item> animationList = Arrays.asList(
            new Item("Animation", AnimationActivity.class),
            new Item("MarkAnim", MarkAnimationActivity.class)
    );

    private List<Item> sensorItems = Arrays.asList(
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

    static class Item {
        String title;
        Class<? extends Activity> cls;

        public Item(String title, Class<? extends Activity> cls) {
            this.title = title;
            this.cls = cls;
        }
    }

    List<Item> getCurrentItems() {
        return currentItems;
    }

    @Override
    protected int initPageLayoutID() {
        return R.layout.main_activity;
    }

    @Override
    protected void initPageView() {
        mDrawerLayout.setStatusBarBackgroundColor(getResources().getColor(R.color.primaryDark));
        mDrawerLayout.setDrawerShadow(R.drawable.drawer_shadow, GravityCompat.START);
    }

    @Override
    protected void initPageViewListener() {
        mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout, getActionBarToolbar(), R.string.drawer_open, R.string.drawer_close);
        mDrawerLayout.setDrawerListener(mDrawerToggle);
        mDrawerToggle.syncState();

        mNavigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem menuItem) {
                setTitle(menuItem.getTitle());
                switch (menuItem.getItemId()) {
                    case R.id.navigation_original:
                        currentItems = originalItems;
                        break;
                    case R.id.navigation_library:
                        currentItems = libraryList;
                        break;
                    case R.id.navigation_component:
                        currentItems = componentList;
                        break;
                    case R.id.navigation_ui:
                        currentItems = uiList;
                        break;
                    case R.id.navigation_text:
                        currentItems = textList;
                        break;
                    case R.id.navigation_sensor:
                        currentItems = sensorItems;
                        break;
                    case R.id.navigation_storage:
                        currentItems = storageList;
                        break;
                    case R.id.navigation_animation:
                        currentItems = animationList;
                        break;
                    case R.id.navigation_other:
                        currentItems = otherList;
                        break;
                    case R.id.navigation_settings:
                        // todo
                        return true;
                    default:
                        return false;
                }
                MainContentFragment contentFragment = (MainContentFragment) getSupportFragmentManager().findFragmentById(R.id.content_frame);
                if (contentFragment != null) {
                    contentFragment.updateContentList(currentItems);
                }

                mDrawerLayout.closeDrawers();
                return true;
            }
        });

    }

    @Override
    protected void process(Bundle savedInstanceState) {
        currentItems = originalItems;
        getSupportFragmentManager().beginTransaction().replace(R.id.content_frame, new MainContentFragment()).commit();
    }

}

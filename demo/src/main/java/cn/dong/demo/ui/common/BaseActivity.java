package cn.dong.demo.ui.common;

import android.animation.ArgbEvaluator;
import android.animation.ObjectAnimator;
import android.animation.TypeEvaluator;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Handler.Callback;
import android.os.Message;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.DecelerateInterpolator;
import android.widget.AbsListView;
import android.widget.ListView;

import com.nostra13.universalimageloader.core.ImageLoader;

import butterknife.ButterKnife;
import cn.dong.demo.MyApp;
import cn.dong.demo.R;

/**
 * Activity 基类
 *
 * @author dong 2014-7-19
 */
public abstract class BaseActivity extends AppCompatActivity implements Callback {
    public static final String TAG = BaseActivity.class.getSimpleName();

    public static final String EXTRA_TITLE = "actionbar_title";

    // Durations for certain animations we use:
    private static final int HEADER_HIDE_ANIM_DURATION = 300;

    private static final TypeEvaluator ARGB_EVALUATOR = new ArgbEvaluator();

    private Toolbar mActionBarToolbar;
    protected boolean isDestroy;
    protected FragmentActivity mContext;
    protected MyApp mApplication;
    protected Handler mHandler;
    protected ImageLoader mImageLoader;

    // 统一的加载对话框
    protected ProgressDialog mLoadingDialog;

    // 控制ActionBar自动隐藏
    private ObjectAnimator mStatusBarColorAnimator;
    private boolean mActionBarAutoHideEnabled = false;
    private int mActionBarAutoHideSensivity = 0;
    private int mActionBarAutoHideMinY = 0;
    private int mActionBarAutoHideSignal = 0;
    private boolean mActionBarShown = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(initPageLayoutID());
        ButterKnife.inject(this);
        initActionBar();
        init();
        initPageView();
        initPageViewListener();
        process(savedInstanceState);
    }

    protected void init() {
        mContext = this;
        mApplication = MyApp.getInstance();
        mHandler = new Handler(this);
        mImageLoader = ImageLoader.getInstance();
    }

    /**
     * 返回主布局id
     */
    protected abstract int initPageLayoutID();

    /**
     * 初始化页面控件
     */
    protected void initPageView() {
    }

    /**
     * 页面控件点击事件处理
     */
    protected void initPageViewListener() {
    }

    /**
     * 逻辑处理
     */
    protected void process(Bundle savedInstanceState) {
    }

    protected void initActionBar() {
        if (getActionBarToolbar() == null) {
            return;
        }

        mActionBarToolbar.setNavigationIcon(R.drawable.abc_ic_ab_back_mtrl_am_alpha);
        mActionBarToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        String title = getIntent().getStringExtra(EXTRA_TITLE);
        if (mActionBarToolbar != null && !TextUtils.isEmpty(title) && getSupportActionBar() != null) {
            getSupportActionBar().setTitle(title);
        }

    }

    protected Toolbar getActionBarToolbar() {
        if (mActionBarToolbar == null) {
            mActionBarToolbar = (Toolbar) findViewById(R.id.toolbar_actionbar);
            if (mActionBarToolbar != null) {
                setSupportActionBar(mActionBarToolbar);
            }
        }
        return mActionBarToolbar;
    }

    public void enableActionBarAutoHide(final ListView listView) {
        initActionBarAutoHide();
        listView.setOnScrollListener(new AbsListView.OnScrollListener() {
            final static int ITEMS_THRESHOLD = 3;
            int lastFvi = 0;

            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {
            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount,
                                 int totalItemCount) {
                onMainContentScrolled(firstVisibleItem <= ITEMS_THRESHOLD ? 0 : Integer.MAX_VALUE,
                        lastFvi - firstVisibleItem > 0 ? Integer.MIN_VALUE :
                                lastFvi == firstVisibleItem ? 0 : Integer.MAX_VALUE
                );
                lastFvi = firstVisibleItem;
            }
        });
    }

    private void initActionBarAutoHide() {
        mActionBarAutoHideEnabled = true;
        mActionBarAutoHideMinY =
                getResources().getDimensionPixelSize(R.dimen.action_bar_auto_hide_min_y);
        mActionBarAutoHideSensivity =
                getResources().getDimensionPixelSize(R.dimen.action_bar_auto_hide_sensivity);
    }

    private void onMainContentScrolled(int currentY, int deltaY) {
        if (deltaY > mActionBarAutoHideSensivity) {
            deltaY = mActionBarAutoHideSensivity;
        } else if (deltaY < -mActionBarAutoHideSensivity) {
            deltaY = -mActionBarAutoHideSensivity;
        }

        if (Math.signum(deltaY) * Math.signum(mActionBarAutoHideSignal) < 0) {
            // deltaY is a motion opposite to the accumulated signal, so reset signal
            mActionBarAutoHideSignal = deltaY;
        } else {
            // add to accumulated signal
            mActionBarAutoHideSignal += deltaY;
        }

        boolean shouldShow = currentY < mActionBarAutoHideMinY ||
                (mActionBarAutoHideSignal <= -mActionBarAutoHideSensivity);
        autoShowOrHideActionBar(shouldShow);
    }

    protected void autoShowOrHideActionBar(boolean show) {
        if (show == mActionBarShown) {
            return;
        }

        mActionBarShown = show;
        onActionBarAutoShowOrHide(show);
    }

    protected void onActionBarAutoShowOrHide(boolean shown) {
        Log.d(TAG, "onActionBarAutoShowOrHide " + shown);
        if (mStatusBarColorAnimator != null) {
            mStatusBarColorAnimator.cancel();
        }

        if (shown) {
            mActionBarToolbar.animate().translationY(0).alpha(1)
                    .setDuration(HEADER_HIDE_ANIM_DURATION)
                    .setInterpolator(new DecelerateInterpolator());
        } else {
            mActionBarToolbar.animate().translationY(-mActionBarToolbar.getBottom()).alpha(0)
                    .setDuration(HEADER_HIDE_ANIM_DURATION)
                    .setInterpolator(new DecelerateInterpolator());
        }
    }

    @Override
    protected void onDestroy() {
        isDestroy = true;
        dismissLoadingDialog();
        super.onDestroy();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public boolean handleMessage(Message msg) {
        if (!isDestroy) {
            performHandleMessage(msg);
        }
        return true;
    }

    /**
     * 接收处理mHandler的消息
     */
    protected void performHandleMessage(Message msg) {

    }

    /**
     * 显示加载对话框
     *
     * @param msg          消息
     * @param isCancelable 是否可被用户关闭
     */
    public void showLoadingDialog(String msg, boolean isCancelable) {
        if (mLoadingDialog != null && mLoadingDialog.isShowing()) {
            return;
        } else {
            mLoadingDialog = new ProgressDialog(mContext);
            mLoadingDialog.setMessage(msg);
            mLoadingDialog.setIndeterminate(true);
            mLoadingDialog.setCancelable(isCancelable);
            mLoadingDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            mLoadingDialog.show();
        }
    }

    /**
     * 关闭加载对话框
     */
    public void dismissLoadingDialog() {
        if (mLoadingDialog != null && mLoadingDialog.isShowing()) {
            mLoadingDialog.dismiss();
            mLoadingDialog = null;
        }
    }

    /**
     * 启动Activity
     */
    public void launchActivity(Class<? extends Activity> cls) {
        startActivity(new Intent(mContext, cls));
    }

}

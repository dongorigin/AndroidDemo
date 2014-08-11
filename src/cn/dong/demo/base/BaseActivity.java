package cn.dong.demo.base;

import com.nostra13.universalimageloader.core.ImageLoader;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Handler.Callback;
import android.os.Message;
import android.support.v4.app.FragmentActivity;
import android.text.TextUtils;
import android.view.MenuItem;
import cn.dong.demo.DongApplication;

/**
 * Activity 基类
 * 
 * @author dong 2014-7-19
 */
public abstract class BaseActivity extends FragmentActivity implements Callback {
    public static final String EXTRA_TITLE = "actionbar_title";

    protected boolean isDestroy;
    protected FragmentActivity mContext;
    protected DongApplication mApplication;
    protected Handler mHandler;
    protected ImageLoader mImageLoader;
    /** 统一的加载对话框 */
    protected ProgressDialog mLoadingDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(initPageLayoutID());
        init();
        initPageView();
        initPageViewListener();
        process(savedInstanceState);
    }

    protected void init() {
        mContext = this;
        mApplication = DongApplication.getInstance();
        mHandler = new Handler(this);
        mImageLoader = ImageLoader.getInstance();

        initActionBar();
    }

    protected void initActionBar() {
        getActionBar().setDisplayHomeAsUpEnabled(true);
        String title = getIntent().getStringExtra(EXTRA_TITLE);
        if (!TextUtils.isEmpty(title)) {
            getActionBar().setTitle(title);
        }
    }

    /**
     * 返回主布局id
     */
    protected abstract int initPageLayoutID();

    /**
     * 初始化页面控件
     */
    protected abstract void initPageView();

    /**
     * 页面控件点击事件处理
     */
    protected abstract void initPageViewListener();

    /**
     * 逻辑处理
     */
    protected abstract void process(Bundle savedInstanceState);

    @Override
    protected void onDestroy() {
        isDestroy = true;
        dismissLoadingDialog();
        super.onDestroy();
        // System.gc();
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
     * @param msg 消息
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

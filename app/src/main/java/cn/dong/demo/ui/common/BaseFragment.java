package cn.dong.demo.ui.common;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Handler.Callback;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import butterknife.ButterKnife;
import cn.dong.demo.MyApp;
import cn.dong.demo.util.L;

/**
 * Fragment 基类
 *
 * @author dong 2014-7-20
 */
public abstract class BaseFragment extends Fragment implements Callback {
    protected final String TAG = this.getClass().getSimpleName();

    private boolean isActivityCreated = false; // 页面控件是否已初始化
    private boolean isFirstVisible = false; // 是否第一次可见

    protected BaseActivity mContext;
    protected MyApp mApplication;
    protected Handler mHandler;

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isActivityCreated) {
            if (isVisibleToUser) {
                if (!isFirstVisible) {
                    isFirstVisible = true;
                    onPageFirstVisible();
                }
                onPageStart();
            } else {
                onPageEnd();
            }
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        init();
    }

    protected void init() {
        mContext = (BaseActivity) getActivity();
        mApplication = MyApp.getInstance();
        mHandler = new Handler(this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(initPageLayoutId(), container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.inject(this, view);
        initPageView(view);
        initPageViewListener();
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        isActivityCreated = true;
        if (getUserVisibleHint()) {
            if (!isFirstVisible) {
                isFirstVisible = true;
                onPageFirstVisible();
            }
        }
        process(savedInstanceState);
    }

    /**
     * 生成主文件布局
     */
    protected abstract int initPageLayoutId();

    /**
     * 初始化页面控件
     */
    protected void initPageView(View rootView) {
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

    /**
     * 当页面首次可见时调用。调用时页面控件已经完成初始化
     * 用于ViewPager下的页面懒加载，在一个生命周期内只会调用一次
     */
    public void onPageFirstVisible() {
        L.v(TAG, "onPageFirstVisible");
    }

    public void onPageStart() {
        L.v(TAG, "onPageStart");
    }

    public void onPageEnd() {
        L.v(TAG, "onPageEnd");
    }

    @Override
    public boolean handleMessage(Message msg) {
        if (getActivity() != null) {
            performHandleMessage(msg);
        }
        return true;
    }

    protected void performHandleMessage(Message msg) {

    }

    /**
     * 启动Activity
     */
    protected void launchActivity(Class<? extends Activity> cls) {
        Intent intent = new Intent();
        intent.setClass(mContext, cls);
        startActivity(intent);
    }

}

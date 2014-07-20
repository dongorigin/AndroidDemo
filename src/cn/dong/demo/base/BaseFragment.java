package cn.dong.demo.base;

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
import cn.dong.demo.DongApplication;

/**
 * Fragment超类
 * 
 * @author dong 2014-7-20
 */
public abstract class BaseFragment extends Fragment implements Callback {

    protected BaseActivity context;
    protected DongApplication application;
    protected Handler mHandler;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = (BaseActivity) getActivity();
        application = DongApplication.getInstance();
        mHandler = new Handler(this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(initPageLayoutID(), container, false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initPageView(getView());
        initPageViewListener();
        process(savedInstanceState);
    }

    /**
     * 生成主文件布局
     */
    protected abstract int initPageLayoutID();

    /**
     * 初始化页面控件
     */
    protected abstract void initPageView(View rootView);

    /**
     * 页面控件点击事件处理
     */
    protected abstract void initPageViewListener();

    /**
     * 逻辑处理
     */
    protected abstract void process(Bundle savedInstanceState);

    @Override
    public boolean handleMessage(Message msg) {
        return false;
    }

    /**
     * 启动Activity
     */
    protected void launchActivity(Class<? extends Activity> cls) {
        Intent intent = new Intent();
        intent.setClass(context, cls);
        startActivity(intent);
    }

}

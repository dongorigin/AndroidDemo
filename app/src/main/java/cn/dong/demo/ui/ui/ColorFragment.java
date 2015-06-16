package cn.dong.demo.ui.ui;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import butterknife.ButterKnife;
import butterknife.InjectView;
import cn.dong.demo.R;
import cn.dong.demo.config.Extra;
import cn.dong.demo.util.L;

/**
 * 支持页面切换统计与懒加载的Fragment
 * 已通过ViewPager下的测试
 * todo 还需要FragmentTabHost下的测试
 *
 * @author dong on 15/4/1.
 */
public class ColorFragment extends Fragment {
    private static final String TAG = ColorFragment.class.getSimpleName();
    private static final String[] colors = {"#99CCB4", "#AABDB4", "#B3AD9B", "#FC9D9D", "#27C4C4"};

    @InjectView(R.id.text)
    TextView textView;

    private int mPosition;
    private boolean isActivityCreated = false;
    private boolean isFirstVisible = false;

    public String getCurrentMethodName() {
        return Thread.currentThread().getStackTrace()[3].getMethodName();
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        mPosition = getArguments().getInt(Extra.POSITION);
        L.d(TAG, "%d setUserVisibleHint %s start", mPosition, isVisibleToUser);
        super.setUserVisibleHint(isVisibleToUser);
        L.d(TAG, "%d setUserVisibleHint %s end", mPosition, isVisibleToUser);

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
    public void onAttach(Activity activity) {
        mPosition = getArguments().getInt(Extra.POSITION);
        L.d(TAG, "%d %s", mPosition, getCurrentMethodName());
        super.onAttach(activity);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        L.d(TAG, "%d %s", mPosition, getCurrentMethodName());
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        L.d(TAG, "%d %s", mPosition, getCurrentMethodName());
        return inflater.inflate(R.layout.fragment_color, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        L.d(TAG, "%d %s", mPosition, getCurrentMethodName());
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.inject(this, view);
        view.setBackgroundColor(Color.parseColor(colors[mPosition % 5]));
        textView.setText(String.valueOf(mPosition));
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        L.d(TAG, "%d %s", mPosition, getCurrentMethodName());
        super.onActivityCreated(savedInstanceState);
        isActivityCreated = true;
        if (getUserVisibleHint()) {
            if (!isFirstVisible) {
                isFirstVisible = true;
                onPageFirstVisible();
            }
        }
    }

    @Override
    public void onStart() {
        L.d(TAG, "%d %s", mPosition, getCurrentMethodName());
        super.onStart();
    }

    @Override
    public void onResume() {
        L.d(TAG, "%d %s", mPosition, getCurrentMethodName());
        super.onResume();
        if (getUserVisibleHint()) {
            onPageStart();
        }
    }

    @Override
    public void onPause() {
        L.d(TAG, "%d %s", mPosition, getCurrentMethodName());
        super.onPause();
        if (getUserVisibleHint()) {
            onPageEnd();
        }
    }

    @Override
    public void onStop() {
        L.d(TAG, "%d %s", mPosition, getCurrentMethodName());
        super.onStop();
    }

    @Override
    public void onDestroyView() {
        L.d(TAG, "%d %s", mPosition, getCurrentMethodName());
        super.onDestroyView();
    }

    @Override
    public void onDestroy() {
        L.d(TAG, "%d %s", mPosition, getCurrentMethodName());
        super.onDestroy();
    }

    @Override
    public void onDetach() {
        L.d(TAG, "%d %s", mPosition, getCurrentMethodName());
        super.onDetach();
    }

    public void onPageStart() {
        L.i(TAG, "%d %s", mPosition, getCurrentMethodName());
    }

    public void onPageEnd() {
        L.i(TAG, "%d %s", mPosition, getCurrentMethodName());
    }

    /**
     * 当页面首次可见时调用。调用时页面控件已经完成初始化
     * 用于ViewPager下的页面懒加载，在一个生命周期内只会调用一次
     */
    public void onPageFirstVisible() {
        L.i(TAG, "%d %s", mPosition, getCurrentMethodName());

    }
}

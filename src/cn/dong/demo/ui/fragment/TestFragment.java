package cn.dong.demo.ui.fragment;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import cn.dong.demo.R;
import cn.dong.demo.util.FragmentUtil;
import cn.dong.demo.util.T;

public class TestFragment extends Fragment {
    private static final String[] colors = {"#99CCB4", "#AABDB4", "#B3AD9B", "#FC9D9D", "#27C4C4"};
    private String tag = "Fragment";
    private int num;

    @Override
    public void onAttach(Activity activity) {
        if (getArguments() != null) {
            num = getArguments().getInt("Num");
        }
        tag = "Fragment " + num;
        Log.d(tag, "onAttach");
        super.onAttach(activity);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        Log.d(tag, "onCreate");
        super.onCreate(savedInstanceState);
        Log.d(tag, getFragmentManager().toString());
        Log.d(tag, getChildFragmentManager().toString());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Log.d(tag, "onCreateView");
        View view = inflater.inflate(R.layout.fragment_test, null);
        view.setBackgroundColor(Color.parseColor(colors[num % 5]));
        TextView text = (TextView) view.findViewById(R.id.fragmentNum);
        text.setText(num + "");
        Button button = (Button) view.findViewById(R.id.button);
        button.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                T.shortT(getActivity(), tag);
            }
        });

        Button button2 = (Button) view.findViewById(R.id.replaceButton);
        button2.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO 子页面中切换，注意使用ChildFragmentManager
                FragmentUtil.replaceFragment(getChildFragmentManager(), ChildFragment.class,
                        new Bundle(), R.id.childContainer, false);
            }
        });
        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        Log.d(tag, "onActivityCreated");
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public void onStart() {
        Log.d(tag, "onStart");
        super.onStart();
    }

    @Override
    public void onResume() {
        Log.d(tag, "onResume");
        super.onResume();
    }

    @Override
    public void onPause() {
        Log.d(tag, "onPause");
        super.onPause();
    }

    @Override
    public void onStop() {
        Log.d(tag, "onStop");
        super.onStop();
    }

    @Override
    public void onDestroyView() {
        Log.d(tag, "onDestroyView");
        super.onDestroyView();
    }

    @Override
    public void onDestroy() {
        Log.d(tag, "onDestroy");
        super.onDestroy();
    }

    @Override
    public void onDetach() {
        Log.d(tag, "onDetach");
        super.onDetach();
    }
}

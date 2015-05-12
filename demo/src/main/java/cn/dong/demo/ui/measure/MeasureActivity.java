package cn.dong.demo.ui.measure;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;

import cn.dong.demo.R;

/**
 * author DONG 2014/11/10.
 */
public class MeasureActivity extends FragmentActivity {
    private static final String TAG = "MeasureActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "onCreate");
        setContentView(R.layout.activity_measure);
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.d(TAG, "onStart");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d(TAG, "onResume");
    }


}

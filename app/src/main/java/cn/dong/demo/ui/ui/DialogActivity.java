package cn.dong.demo.ui.ui;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.DialogFragment;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

import java.lang.ref.WeakReference;

import cn.dong.demo.R;
import cn.dong.demo.ui.common.BaseActivity;

public class DialogActivity extends BaseActivity {
    private MyHandler handler;
    private TextView text;
    private Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initData();
        initView();
    }

    @Override
    protected int initPageLayoutID() {
        return R.layout.activity_dialog;
    }

    private void initData() {
        handler = new MyHandler(this);
    }

    private void initView() {
        text = (TextView) findViewById(R.id.text);
        button = (Button) findViewById(R.id.button);

        button.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogFragment fragment = new MyDialogFragment();
                fragment.show(getSupportFragmentManager(), "dialog");
            }
        });
    }

    private static class MyHandler extends Handler {
        private WeakReference<DialogActivity> mActivity;

        public MyHandler(DialogActivity activity) {
            mActivity = new WeakReference<DialogActivity>(activity);
        }

        @Override
        public void dispatchMessage(Message msg) {
            DialogActivity theActivity = mActivity.get();
            if (theActivity == null) {
                return;
            }
            switch (msg.what) {
                case 0:
                    theActivity.text.setText("handler!");
                    break;
                case 1:
                    break;
            }
        }
    }

}

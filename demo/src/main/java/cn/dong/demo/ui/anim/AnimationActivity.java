package cn.dong.demo.ui.anim;

import android.animation.ObjectAnimator;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import cn.dong.demo.R;
import cn.dong.demo.base.BaseActivity;

/**
 * author DONG 2014/10/29.
 */
public class AnimationActivity extends BaseActivity {
    private TextView textView;
    private Button button;

    @Override
    protected int initPageLayoutID() {
        return R.layout.activity_animation;
    }

    @Override
    protected void initPageView() {
        textView = (TextView) findViewById(R.id.text);
        button = (Button) findViewById(R.id.button);
    }

    @Override
    protected void initPageViewListener() {
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ObjectAnimator oa = ObjectAnimator.ofFloat(textView, "translationX", 200);
                oa.setDuration(3000);
                oa.start();
            }
        });
    }

    @Override
    protected void process(Bundle savedInstanceState) {

    }
}

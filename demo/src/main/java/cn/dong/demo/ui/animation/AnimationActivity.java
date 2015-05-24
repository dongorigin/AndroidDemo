package cn.dong.demo.ui.animation;

import android.animation.Animator;
import android.animation.ValueAnimator;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.DecelerateInterpolator;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import butterknife.OnClick;
import cn.dong.demo.R;
import cn.dong.demo.ui.common.BaseActivity;
import cn.dong.demo.util.L;

public class AnimationActivity extends BaseActivity {
    private ImageView imageView;
    private Button startButton;
    private Button cancelButton;
    private TextView text1View;
    private TextView text2View;
    private TextView text3View;

    private Animation zoom;
    private List<View> views;
    private List<Animator> animators;
    private Timer timer;
    private int position;
    private Handler handler;

    @Override
    protected int initPageLayoutID() {
        return R.layout.activity_anime;
    }

    @Override
    protected void initPageView() {
        text1View = (TextView) findViewById(R.id.text1);
        text2View = (TextView) findViewById(R.id.text2);
        text3View = (TextView) findViewById(R.id.text3);
        imageView = (ImageView) findViewById(R.id.image);
        startButton = (Button) findViewById(R.id.btn_start);
        cancelButton = (Button) findViewById(R.id.btn_cancel);
    }

    @Override
    protected void initPageViewListener() {
        imageView.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                imageView.startAnimation(zoom);
            }
        });

        startButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                // 停止之前的定时器
                if (timer != null) {
                    timer.cancel();
                }
                for (Animator animator : animators) {
                    animator.cancel();
                }
                animators.clear();
                // 重置
                position = 0;
                for (View view : views) {
                    view.setAlpha(0);
                }
                // 开启新的定时
                timer = new Timer();
                timer.schedule(new AnimationTask(), 1000, 1000);
            }
        });

        cancelButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                timer.cancel();
            }
        });
    }

    @Override
    protected void process(Bundle savedInstanceState) {
        zoom = AnimationUtils.loadAnimation(this, R.anim.zoom);

        views = new ArrayList<>();
        views.add(text1View);
        views.add(text2View);
        views.add(text3View);
        animators = new ArrayList<>();

        handler = new Handler(Looper.getMainLooper());
    }

    @OnClick(R.id.btn_move)
    void clickMove() {
        moveAnim();
    }

    private void moveAnim() {
        imageView.animate().translationX(200).translationY(200).setDuration(3000).start();
    }

    private class AnimationTask extends TimerTask {

        @Override
        public void run() {
            L.d(TAG, "AnimationTask run %d", position);
            if (position < views.size()) {
                final View view = views.get(position);
                final ValueAnimator animation = ValueAnimator.ofFloat(0, 1);
                animators.add(animation);
                animation.setDuration(2000);
                animation.setInterpolator(new DecelerateInterpolator());
                animation.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                    @Override
                    public void onAnimationUpdate(ValueAnimator animation) {
//                        L.d(TAG, "onAnimationUpdate %s", animation.getAnimatedValue());
                        view.setAlpha((Float) animation.getAnimatedValue());
                    }
                });

//                handler.post(new Runnable() {
//                    @Override
//                    public void run() {
//                        animation.start();
//                    }
//                });
                view.post(new Runnable() {
                    @Override
                    public void run() {
                        animation.start();
                    }
                });

                position++;
            } else {
                timer.cancel();
            }
        }
    }
}

package cn.dong.demo.view;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.util.AttributeSet;
import android.view.animation.AccelerateInterpolator;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import cn.dong.demo.R;

/**
 * @author dong on 15/3/10.
 */
public class MarkView extends RelativeLayout {
    private static final long TIME = 400;

    private ImageView imageView;

    public MarkView(Context context, AttributeSet attrs) {
        super(context, attrs);

        setClipChildren(false);

        imageView = new ImageView(context);
        imageView.setImageResource(R.drawable.ic_demo_mark_default);
        addView(imageView);
    }

    public void like(float scale) {
        final ImageView upView = new ImageView(getContext());
        upView.setImageResource(R.drawable.ic_mark_up);
        upView.measure(MeasureSpec.UNSPECIFIED, MeasureSpec.UNSPECIFIED);
        upView.setTranslationX(getWidth() - upView.getMeasuredWidth());

        // 初始化动画
        ObjectAnimator scaleXAnim = ObjectAnimator.ofFloat(upView, "scaleX", 1f, scale);
        scaleXAnim.setDuration(TIME);
        ObjectAnimator scaleYAnim = ObjectAnimator.ofFloat(upView, "scaleY", 1f, scale);
        scaleYAnim.setDuration(TIME);

        ObjectAnimator fadeoutAnim = ObjectAnimator.ofFloat(upView, "alpha", 1, 0f);
        fadeoutAnim.setDuration(TIME);
        fadeoutAnim.setInterpolator(new AccelerateInterpolator());

        ObjectAnimator locationAnim = ObjectAnimator.ofFloat(upView, "translationY", 0, -200);
        locationAnim.setDuration(TIME);

        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.play(scaleXAnim).with(scaleYAnim).with(fadeoutAnim).with(locationAnim);
        animatorSet.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {
                addView(upView);
            }

            @Override
            public void onAnimationEnd(Animator animation) {
                removeView(upView);
            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });

        animatorSet.start();
    }

}

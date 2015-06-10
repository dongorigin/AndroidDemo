package cn.dong.demo.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.TouchDelegate;
import android.view.View;
import android.widget.TextView;

import cn.dong.demo.R;
import cn.dong.demo.util.L;

/**
 * 扩大触摸范围的TextView，用于标签拖动优化
 * <p/>
 * Created by dong on 14/12/23.
 */
public class LargeTouchAreaText extends TextView {
    private static final String TAG = LargeTouchAreaText.class.getSimpleName();

    private static final int DEFAULT_PADDING = 0;

    private Context context;
    private int padding;
    private int mPreviousLeft = -1;
    private int mPreviousRight = -1;
    private int mPreviousBottom = -1;
    private int mPreviousTop = -1;

    public LargeTouchAreaText(Context context) {
        this(context, null);
    }

    public LargeTouchAreaText(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
        parseAttrs(attrs);
    }

    private void init(Context context) {
        this.context = context;

    }

    private void parseAttrs(AttributeSet attrs) {
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.LargeTouchAreaText);

        padding = a.getDimensionPixelOffset(R.styleable.LargeTouchAreaText_padding, DEFAULT_PADDING);

        a.recycle();
    }

    @Override
    public void setX(float x) {
        super.setX(x);
//        updateTouchDelegate();
    }

    @Override
    public void setY(float y) {
        super.setY(y);
//        updateTouchDelegate();
    }

    private void updateTouchDelegate() {
        Rect touchRect = new Rect();
        getHitRect(touchRect);
        touchRect.left -= padding;
        touchRect.top -= padding;
        touchRect.right += padding;
        touchRect.bottom += padding;
        View parent = (View) getParent();
        parent.setTouchDelegate(new TouchDelegate(touchRect, this));
    }

    @Override
    public void layout(int l, int t, int r, int b) {
        super.layout(l, t, r, b);
        L.d(TAG, "left %d top %d right %d bottom %d", l, t, r, b);
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        L.d(TAG, "left %d top %d right %d bottom %d", left, top, right, bottom);
        if (left != mPreviousLeft || top != mPreviousTop || right != mPreviousRight || bottom != mPreviousBottom) {
            mPreviousLeft = left;
            mPreviousTop = top;
            mPreviousRight = right;
            mPreviousBottom = bottom;

            Rect touchRect = new Rect(left - padding, top - padding, right + padding, bottom + padding);
            View parent = (View) getParent();
            parent.setTouchDelegate(new TouchDelegate(touchRect, parent));
        }
    }

}

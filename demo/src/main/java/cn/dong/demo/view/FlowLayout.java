package cn.dong.demo.view;

import android.content.Context;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

/**
 * 流式布局
 * <p/>
 * author DONG 2015/5/7.
 */
public class FlowLayout extends ViewGroup {

    private List<Integer> mLineCount = new ArrayList<>();
    private List<Integer> mLineHeight = new ArrayList<>();

    private final Rect mTmpChildRect = new Rect();

    public FlowLayout(Context context) {
        super(context);
    }

    public FlowLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public FlowLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public boolean shouldDelayChildPressedState() {
        return false;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        final int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        final int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        final int heightSize = MeasureSpec.getSize(heightMeasureSpec);
        final int heightMode = MeasureSpec.getMode(heightMeasureSpec);

        int maxWidth = 0;
        int maxHeight = 0;
        int lineWidth = 0;
        int lineHeight = 0;

        mLineCount.clear();
        mLineHeight.clear();
        int lineCount = 0;

        final int count = getChildCount();
        for (int i = 0; i < count; i++) {
            final View child = getChildAt(i);
            if (child.getVisibility() == GONE) {
                continue;
            }
            //  Measure the child
            measureChildWithMargins(child, widthMeasureSpec, 0, heightMeasureSpec, 0);

            if (lineWidth + child.getMeasuredWidth() <= widthSize) {
                // 未换行
                lineCount++;

                lineWidth += child.getMeasuredWidth();
                lineHeight = Math.max(lineHeight, child.getMeasuredHeight());
            } else {
                // 换行
                maxWidth = Math.max(maxWidth, lineWidth);
                maxHeight += lineHeight;

                lineWidth = child.getMeasuredWidth();
                lineHeight = child.getMeasuredHeight();

                mLineCount.add(lineCount);
                mLineHeight.add(lineHeight);
                lineCount = 1;
            }

            if (i == count - 1) {
                // end
                maxWidth = Math.max(maxWidth, lineWidth);
                maxHeight += lineHeight;

                mLineCount.add(lineCount);
                mLineHeight.add(lineHeight);
            }
        }

        setMeasuredDimension(widthMode == MeasureSpec.EXACTLY ? widthSize : maxWidth,
                heightMode == MeasureSpec.EXACTLY ? heightSize : maxHeight);
    }

    @Override
    protected void onLayout(boolean changed, final int left, final int top, int right, int bottom) {
        int preTop = top;
        int lineStart = 0;
        for (int i = 0; i < mLineCount.size(); i++) {
            // per line
            int lineCount = mLineCount.get(i);
            int lineHeight = mLineHeight.get(i);
            int preLeft = left;
            for (int j = 0; j < lineCount; j++) {
                final View child = getChildAt(lineStart + j);
                // per child
                if (child.getVisibility() == GONE) {
                    continue;
                }

                mTmpChildRect.left = preLeft;
                mTmpChildRect.top = preTop;
                mTmpChildRect.right = preLeft + child.getMeasuredWidth();
                mTmpChildRect.bottom = preTop + child.getMeasuredHeight();
                // layout
                child.layout(mTmpChildRect.left, mTmpChildRect.top, mTmpChildRect.right, mTmpChildRect.bottom);
                preLeft += child.getMeasuredWidth();
            }
            preTop += lineHeight;
            lineStart += lineCount;
        }
    }

    @Override
    public LayoutParams generateLayoutParams(AttributeSet attrs) {
        return new MarginLayoutParams(getContext(), attrs);
    }

}

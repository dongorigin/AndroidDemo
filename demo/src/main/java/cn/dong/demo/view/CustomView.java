package cn.dong.demo.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Point;
import android.os.Parcel;
import android.os.Parcelable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import cn.dong.demo.R;

/**
 * @author dong on 15/4/16.
 */
public class CustomView extends View {
    private static final String TAG = CustomView.class.getSimpleName();

    private int mCustomState = 0;
    private int mShapeColor;
    private boolean mShowShapeName;

    private Paint mShapePaint;
    private int mShapeWidth = 150;
    private int mShapeHeight = 150;
    private int mTextPadding = 16;

    private String[] shapeValues = {"square", "circle", "triangle"};
    private int index = 0;

    public CustomView(Context context) {
        super(context);
    }

    public CustomView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CustomView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setupAttributes(attrs);
        setupPaint();
    }

    private void setupAttributes(AttributeSet attrs) {
        TypedArray a = getContext().getTheme().obtainStyledAttributes(attrs, R.styleable.CustomView, 0, 0);

        try {
            mShowShapeName = a.getBoolean(R.styleable.CustomView_showShapeName, true);
            mShapeColor = a.getColor(R.styleable.CustomView_shapeColor, getResources().getColor(R.color.theme_primary));
        } finally {
            a.recycle();
        }
    }

    private void setupPaint() {
        mShapePaint = new Paint();
        mShapePaint.setStyle(Paint.Style.FILL);
        mShapePaint.setColor(mShapeColor);
        mShapePaint.setTextSize(40);
        mShapePaint.setTextAlign(Paint.Align.CENTER);
    }

    public void setShapeColor(int color) {
        mShapeColor = color;
        invalidate();
    }

    public void setShowShapeName(boolean show) {
        mShowShapeName = show;
        invalidate(); // 通知重绘
        requestLayout(); // 重新布局
    }

    public void setState(int state) {
        mCustomState = state;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int minw = mShapeWidth + getPaddingLeft() + getPaddingRight();
        int w = resolveSizeAndState(minw, widthMeasureSpec, 1);

        int minh = mShapeHeight + getPaddingTop() + getPaddingBottom();
        if (mShowShapeName) {
            minh += mTextPadding * 2;
        }
        int h = resolveSizeAndState(minh, heightMeasureSpec, 1);

        setMeasuredDimension(w, h);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        switch (index) {
            case 0:
                canvas.drawRect(0, 0, mShapeWidth, mShapeHeight, mShapePaint);
                break;
            case 1:
                canvas.drawCircle(mShapeWidth / 2, mShapeHeight / 2, mShapeWidth / 2, mShapePaint);
                break;
            case 2:
                canvas.drawPath(getTrianglePath(), mShapePaint);
                break;
        }

        if (mShowShapeName) {
            canvas.drawText(shapeValues[index], mShapeWidth / 2, mShapeHeight + mTextPadding * 2, mShapePaint);
        }
    }

    private Path getTrianglePath() {
        Point p1 = new Point(mShapeWidth / 2, 0);
        Point p2 = new Point(mShapeWidth, mShapeHeight);
        Point p3 = new Point(0, mShapeHeight);
        Path path = new Path();
        path.moveTo(p1.x, p1.y);
        path.lineTo(p2.x, p2.y);
        path.lineTo(p3.x, p3.y);
        return path;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            index = (index + 1) % shapeValues.length;
            postInvalidate();
            return true;
        }
        return super.onTouchEvent(event);
    }

    @Override
    protected Parcelable onSaveInstanceState() {
        Parcelable superState = super.onSaveInstanceState();
        SavedState ss = new SavedState(superState);
        ss.customState = mCustomState;
        return ss;
    }

    @Override
    protected void onRestoreInstanceState(Parcelable state) {
        if (!(state instanceof SavedState)) {
            super.onRestoreInstanceState(state);
            return;
        }

        SavedState ss = (SavedState) state;
        super.onRestoreInstanceState(ss.getSuperState());

        mCustomState = ss.customState;
    }

    static class SavedState extends BaseSavedState {
        int customState;

        public SavedState(Parcelable superState) {
            super(superState);
        }

        public SavedState(Parcel source) {
            super(source);
            customState = source.readInt();
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            super.writeToParcel(dest, flags);
            dest.writeInt(customState);
        }

        public static final Creator<SavedState> CREATOR = new Creator<SavedState>() {
            @Override
            public SavedState createFromParcel(Parcel source) {
                return new SavedState(source);
            }

            @Override
            public SavedState[] newArray(int size) {
                return new SavedState[size];
            }
        };

    }

}

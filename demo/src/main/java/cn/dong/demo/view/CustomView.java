package cn.dong.demo.view;

import android.content.Context;
import android.os.Parcel;
import android.os.Parcelable;
import android.util.AttributeSet;
import android.view.View;

import cn.dong.demo.util.L;

/**
 * @author dong on 15/4/16.
 */
public class CustomView extends View {
    private static final String TAG = CustomView.class.getSimpleName();

    private int shapeColor;
    private boolean displayShapeName;
    private int mCustomState = 0;

    public CustomView(Context context) {
        super(context);
    }

    public CustomView(Context context, AttributeSet attrs) {
        super(context, attrs);
        L.d(TAG, "onCreate");

    }

    public CustomView(Context context, AttributeSet attrs, int defStyleAttr, int mCustomState) {
        super(context, attrs, defStyleAttr);
        this.mCustomState = mCustomState;
    }

    public void setState(int state) {
        mCustomState = state;
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

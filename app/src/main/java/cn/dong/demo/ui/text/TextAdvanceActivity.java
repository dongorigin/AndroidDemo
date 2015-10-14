package cn.dong.demo.ui.text;

import android.view.KeyEvent;
import android.widget.EditText;
import android.widget.TextView;

import butterknife.InjectView;
import cn.dong.demo.R;
import cn.dong.demo.ui.common.BaseActivity;
import cn.dong.demo.util.CommonUtils;
import cn.dong.demo.util.PixelUtils;

/**
 * @author dong on 15/7/14.
 */
public class TextAdvanceActivity extends BaseActivity {
    @InjectView(R.id.tv_1)
    TextView mOneTextView;
    @InjectView(R.id.tv_2)
    TextView mTwoTextView;
    @InjectView(R.id.size_edit)
    EditText mSizeEdit;
    @InjectView(R.id.line_edit)
    EditText mLineEdit;
    @InjectView(R.id.padding_edit)
    EditText mPaddingEdit;

    @Override
    protected int initPageLayoutID() {
        return R.layout.activity_text_advance;
    }

    @Override
    protected void initPageView() {
        mPaddingEdit.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                int size = Integer.parseInt(mSizeEdit.getText() != null ? mSizeEdit.getText().toString() : "20");
                float mult = Float.parseFloat(mLineEdit.getText() != null ? mLineEdit.getText().toString() : "1.3");
                int padding = Integer.parseInt(mPaddingEdit.getText() != null ? mPaddingEdit.getText().toString() : "25");
                int dp25 = PixelUtils.dp2px(25);
                padding = PixelUtils.dp2px(padding);

                mOneTextView.setTextSize(size);
                mOneTextView.setLineSpacing(mOneTextView.getLineSpacingExtra(), mult);
                mOneTextView.setPadding(padding, dp25, padding, dp25);

                mTwoTextView.setTextSize(size);
                mTwoTextView.setLineSpacing(mTwoTextView.getLineSpacingExtra(), mult);
                mTwoTextView.setPadding(padding, dp25, padding, dp25);

                CommonUtils.hideSoftInput(mContext, mPaddingEdit);
                return true;
            }
        });
    }
}

package cn.dong.demo.ui.text;

import android.support.v7.widget.SwitchCompat;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.TextView;

import butterknife.InjectView;
import cn.dong.demo.R;
import cn.dong.demo.ui.common.BaseActivity;
import cn.dong.demo.util.L;

/**
 * 文字输入测试
 *
 * @author dong on 15/5/29.
 */
public class EditTextActivity extends BaseActivity {
    @InjectView(R.id.edit)
    EditText mEditView;
    @InjectView(R.id.edit_switch)
    SwitchCompat editSwitch;

    @Override
    protected int initPageLayoutID() {
        return R.layout.activity_edittext;
    }

    @Override
    protected void initPageViewListener() {
        mEditView.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                L.d(TAG, "onEditorAction %d", actionId);
                return false;
            }
        });

        mEditView.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        editSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    // 恢复编辑
                    setEditable(true);
                } else {
                    // 禁止编辑
                    setEditable(false);
                }
            }
        });
    }

    private void setEditable(boolean editable) {
        mEditView.setEnabled(editable);
        mEditView.setFocusableInTouchMode(editable);
        mEditView.setFocusable(editable);
        mEditView.setClickable(editable);
        mEditView.setLongClickable(editable);
    }
}

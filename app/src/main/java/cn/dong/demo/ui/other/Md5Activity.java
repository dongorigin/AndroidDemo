package cn.dong.demo.ui.other;

import android.os.Bundle;
import android.support.v7.widget.SwitchCompat;
import android.text.Editable;
import android.text.InputFilter;
import android.text.Spanned;
import android.text.TextWatcher;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.TextView;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import butterknife.InjectView;
import butterknife.OnClick;
import cn.dong.demo.R;
import cn.dong.demo.ui.common.BaseActivity;
import cn.dong.demo.util.CommonUtils;
import cn.dong.demo.util.L;

/**
 * @author dong on 15/3/11.
 */
public class Md5Activity extends BaseActivity {

    @InjectView(R.id.input)
    EditText inputView;
    @InjectView(R.id.output)
    EditText outputView;
    @InjectView(R.id.input_display)
    TextView displayView;
    @InjectView(R.id.enable_switch)
    SwitchCompat enableSwitch;

    @Override
    protected int initPageLayoutID() {
        return R.layout.activity_input;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        InputFilter filter = new InputFilter() {
            Pattern emoji = Pattern.compile("[\ud83c\udc00-\ud83c\udfff]|[\ud83d\udc00-\ud83d\udfff]|[\u2600-\u27ff]", Pattern.UNICODE_CASE | Pattern.CASE_INSENSITIVE);

            @Override
            public CharSequence filter(CharSequence source, int start, int end, Spanned dest, int dstart, int dend) {
                L.d(TAG, "filter source=%s start=%d end=%d dest=%s dstart=%d dend=%d", source, start, end, dest, dstart, dend);

                Matcher emojiMatcher = emoji.matcher(source);
                if (emojiMatcher.find()) {
                    return "";
                }
                return source;
            }
        };
        inputView.setFilters(new InputFilter[]{filter});
        inputView.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                L.d(TAG, "beforeTextChanged %s", s);
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                L.d(TAG, "onTextChanged %s", s);
            }

            @Override
            public void afterTextChanged(Editable s) {
                L.d(TAG, "afterTextChanged %s", s);
                displayView.setText(s);
            }
        });

        enableSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                findViewById(R.id.container).setEnabled(isChecked);
                displayView.setEnabled(isChecked);
            }
        });
    }

    @OnClick(R.id.md5_btn)
    void md5() {
        String in = inputView.getText().toString();
        String md5 = CommonUtils.getMd5Lower(in);
        outputView.setText(md5);
    }
}

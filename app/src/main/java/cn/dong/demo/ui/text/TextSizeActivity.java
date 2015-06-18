package cn.dong.demo.ui.text;

import android.content.res.Resources;
import android.os.Bundle;
import android.support.v7.widget.SwitchCompat;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.TextView;

import butterknife.InjectView;
import cn.dong.demo.R;
import cn.dong.demo.ui.common.BaseActivity;
import me.grantland.widget.AutofitHelper;
import me.grantland.widget.AutofitLayout;

public class TextSizeActivity extends BaseActivity {
    @InjectView(R.id.autofit_layout)
    AutofitLayout mAutofitLayout;
    @InjectView(R.id.content_text)
    EditText mContentText;
    @InjectView(R.id.content_hint)
    TextView mContentHint;
    @InjectView(R.id.fit_switch)
    SwitchCompat mFitSwitch;
    @InjectView(R.id.limit_text)
    TextView mLimitText;
    @InjectView(R.id.limit_seekbar)
    SeekBar mLimitSeekbar;
    @InjectView(R.id.large_size_text)
    TextView mLargeSizeText;
    @InjectView(R.id.large_size_seekBar)
    SeekBar mLargeSizeSeekBar;
    @InjectView(R.id.small_size_text)
    TextView mSmallSizeText;
    @InjectView(R.id.small_size_seekBar)
    SeekBar mSmallSizeSeekBar;
    @InjectView(R.id.random_button)
    Button mRandomButton;

    private int mLimit;
    private int mLargeSize;
    private int mSmallSize;
    private int mTextSize;

    private static final String[] contents = {
            "星爵被迅猛龙围攻的时候，老觉得他要开始跳舞了",
            "所以女主的高跟鞋到底是什么牌子，能穿着爬山涉水开车打仗还能跑过霸王龙，急，在线等",
            "这种电影里总是有一个自以为是的傻逼大富翁，一个绿茶圣母婊小秘书最后化身女汉子，若干个熊孩子，一个唯恐天下不乱的军方人员最后一定死的的很讽刺，以及一个美国肌肉男，故事的核心一定是爱与家庭……剧情中规中矩，暴虐霸王龙最后的死法真是令人心疼……",
            "恐龙情结，无以言表。22年后侏罗纪公园大门重开，配乐一响热血沸腾。一如既往的刺激，犹如重温系列经典，特效继续叹为观止，汇集前三集全部明星恐龙。人类贪欲践踏自然，恐龙反哺感应人性，点亮全片。迅猛龙串起前后高潮，第一部霸王龙重新登场，沧龙扑食致敬《大白鲨》。星爵帅爆了，新男神诞生。"
    };
    private int mPosition;

    @Override
    protected int initPageLayoutID() {
        return R.layout.activity_text_size;
    }

    @Override
    protected void initPageViewListener() {
        mContentText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                int inputCount = s.toString().length();
                mContentHint.setHint("当前字数: " + inputCount);
                updateContentSize();
            }
        });

        mFitSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                AutofitHelper helper = mAutofitLayout.getAutofitHelper(mContentText);
                if (isChecked) {
                    helper.setEnabled(true);
                    helper.setMaxLines(3);
                } else {
                    helper.setEnabled(false);
                    helper.setMaxLines(-1);
                    updateContentSize();
                }
            }
        });

        mLimitSeekbar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                mLimit = 30 + progress;
                mLimitText.setText("临界: " + mLimit);
                updateContentSize();
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        mLargeSizeSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                mLargeSize = 20 + progress;
                mLargeSizeText.setText("大字: " + mLargeSize);
                updateContentSize();
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        mSmallSizeSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                mSmallSize = 10 + progress;
                mSmallSizeText.setText("小字: " + mSmallSize);
                updateContentSize();
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        mRandomButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPosition = mPosition % contents.length;
                mContentText.setText(contents[mPosition]);
                mPosition++;
            }
        });

    }

    private void updateContentSize() {
        int contentCount = mContentText.getText().length();
        int textSize;
        if (contentCount > mLimit) {
            textSize = mSmallSize;
        } else {
            textSize = mLargeSize;
        }
        if (mTextSize != textSize) {
            mTextSize = textSize;
            mContentText.setTextSize(TypedValue.COMPLEX_UNIT_DIP, mTextSize);
        }
    }

    @Override
    protected void process(Bundle savedInstanceState) {
        printDensity();
        mLimitSeekbar.setProgress(10);
        mLargeSizeSeekBar.setProgress(4);
        mSmallSizeSeekBar.setProgress(6);
    }

    private void printDensity() {
        Resources resources = getResources();
        final DisplayMetrics metrics = resources.getDisplayMetrics();
        TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 14, metrics);
        Log.i("density", metrics.density + "");
        Log.i("scaledDensity", metrics.scaledDensity + "");
    }

}

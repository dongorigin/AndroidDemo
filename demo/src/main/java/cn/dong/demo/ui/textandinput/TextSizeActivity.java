package cn.dong.demo.ui.textandinput;

import android.content.res.Resources;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.TypedValue;
import android.widget.SeekBar;
import android.widget.TextView;

import butterknife.InjectView;
import cn.dong.demo.R;
import cn.dong.demo.ui.common.BaseActivity;

public class TextSizeActivity extends BaseActivity {
    @InjectView(R.id.dp_text)
    TextView dpTextView;
    @InjectView(R.id.sp_text)
    TextView spTextView;
    @InjectView(R.id.size_text)
    TextView sizeTextView;
    @InjectView(R.id.size_seekBar)
    SeekBar sizeSeekBar;

    @Override
    protected int initPageLayoutID() {
        return R.layout.activity_text_size;
    }

    @Override
    protected void initPageViewListener() {
        sizeSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                int textSize = progress + 10;
                sizeTextView.setText(String.valueOf(textSize));
                dpTextView.setTextSize(TypedValue.COMPLEX_UNIT_DIP, textSize);
                spTextView.setTextSize(TypedValue.COMPLEX_UNIT_SP, textSize);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
    }

    @Override
    protected void process(Bundle savedInstanceState) {
        Resources resources = getResources();
        final DisplayMetrics metrics = resources.getDisplayMetrics();
        TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 14, metrics);
        Log.i("density", metrics.density + "");
        Log.i("scaledDensity", metrics.scaledDensity + "");

        sizeSeekBar.setProgress(10);
    }
}

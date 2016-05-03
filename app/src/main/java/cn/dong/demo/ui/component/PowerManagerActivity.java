package cn.dong.demo.ui.component;

import android.support.v7.widget.SwitchCompat;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.TextView;

import butterknife.InjectView;
import cn.dong.demo.R;
import cn.dong.demo.ui.common.BaseActivity;

/**
 * @author dong on 16/5/3.
 */
public class PowerManagerActivity extends BaseActivity {
    @InjectView(R.id.text_keep) TextView keepText;
    @InjectView(R.id.switch_keep) SwitchCompat keepSwitch;

    @Override
    protected int initPageLayoutID() {
        return R.layout.activity_powermanager;
    }

    @Override
    protected void initPageView() {
        super.initPageView();
        keepSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                keepText.setVisibility(isChecked ? View.VISIBLE : View.GONE);
            }
        });
    }
}

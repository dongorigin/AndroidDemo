package cn.dong.demo.ui;

import org.apache.http.protocol.HTTP;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import cn.dong.demo.R;
import cn.dong.demo.ui.common.BaseActivity;

/**
 * 常用Intents示例
 * 
 * @author dong 2014-8-13
 */
public class IntentsActivity extends BaseActivity {
    Button shareButton;
    Button smsButton;

    @Override
    protected int initPageLayoutID() {
        return R.layout.activity_intents;
    }

    @Override
    protected void initPageView() {
        shareButton = (Button) findViewById(R.id.btn_share);
        smsButton = (Button) findViewById(R.id.btn_sms);
    }

    @Override
    protected void initPageViewListener() {
        shareButton.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                share();
            }
        });

        smsButton.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                sms();
            }
        });

    }

    @Override
    protected void process(Bundle savedInstanceState) {

    }

    private void share() {
        Intent smsIntent = new Intent(Intent.ACTION_SEND);
        smsIntent.setType(HTTP.PLAIN_TEXT_TYPE);
        if (smsIntent.resolveActivity(getPackageManager()) != null) {
            startActivity(smsIntent);
        }
    }

    private void sms() {
        Intent smsIntent = new Intent(Intent.ACTION_SENDTO);
        smsIntent.setData(Uri.parse("smsto:"));
        smsIntent.putExtra("sms_body", "sms_body");
        if (smsIntent.resolveActivity(getPackageManager()) != null) {
            startActivity(smsIntent);
        }
    }

}

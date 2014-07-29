package cn.dong.demo.ui.video;

import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import cn.dong.demo.Constants;
import cn.dong.demo.R;
import cn.dong.demo.base.BaseActivity;
import cn.dong.demo.util.T;

import com.baidu.cyberplayer.utils.VersionManager;
import com.baidu.cyberplayer.utils.VersionManager.CPU_TYPE;
import com.baidu.cyberplayer.utils.VersionManager.RequestCpuTypeAndFeatureCallback;
import com.yixia.vparser.VParser;
import com.yixia.vparser.model.Video;

/**
 * VParser 在线视频地址解析库
 * 
 * @author dong 2014-7-28
 */
public class VParserActivity extends BaseActivity {
    private Button versionButton;
    private Button parseButton;
    private Button playerButton;
    private TextView versionText;
    private EditText urlText;
    private TextView titleText;
    private TextView uriText;

    private VParser mVParser;
    private VersionManager mVersionManager;
    private CPU_TYPE mType;
    private String mTitle;
    private String mVideoUri;

    @Override
    protected void init() {
        super.init();
        mVParser = new VParser(context);
        mVersionManager = VersionManager.getInstance();
    }

    @Override
    protected int initPageLayoutID() {
        return R.layout.vparser_activity;
    }

    @Override
    protected void initPageView() {
        versionButton = (Button) findViewById(R.id.btn_version);
        parseButton = (Button) findViewById(R.id.btn_parse);
        playerButton = (Button) findViewById(R.id.btn_player);
        versionText = (TextView) findViewById(R.id.tv_version);
        urlText = (EditText) findViewById(R.id.et_url);
        titleText = (TextView) findViewById(R.id.tv_title);
        uriText = (TextView) findViewById(R.id.tv_uri);
    }

    @Override
    protected void initPageViewListener() {
        versionButton.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                mVersionManager.getCurrentSystemCpuTypeAndFeature(0, Constants.API_KEY,
                        Constants.SECRET_KEY16, new RequestCpuTypeAndFeatureCallback() {
                            @Override
                            public void onComplete(CPU_TYPE type, int result) {
                                Log.i("", "type = " + type);
                                mType = type;
                                mHandler.sendEmptyMessage(2);
                            }
                        });
            }
        });

        parseButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                final String url = urlText.getText().toString();
                if (TextUtils.isEmpty(url)) {
                    return;
                }
                new Thread() {
                    public void run() {
                        Video result = mVParser.parse(url);
                        if (result != null) {
                            mTitle = result.title;
                            mVideoUri = result.videoUri;
                            mHandler.sendEmptyMessage(1);
                        } else {
                            mHandler.sendEmptyMessage(3);
                        }
                    };
                }.start();
            }
        });

        playerButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!TextUtils.isEmpty(mVideoUri)) {
                    Intent intent = new Intent(context, VideoPlayerActivity.class);
                    intent.putExtra(VideoPlayerActivity.EXTRA_VIDEO_URL, mVideoUri);
                    startActivity(intent);
                }
            }
        });
    }

    @Override
    protected void process(Bundle savedInstanceState) {

    }

    @Override
    protected void performHandleMessage(Message msg) {
        switch (msg.what) {
            case 1:
                titleText.setText(mTitle);
                uriText.setText(mVideoUri);
                playerButton.setVisibility(View.VISIBLE);
                break;
            case 2:
                versionText.setText(mType.toString());
                break;
            case 3:
                T.shortT(context, "解析错误");
                break;
            default:
                break;
        }
    }
}

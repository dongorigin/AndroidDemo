package cn.dong.demo.ui.image;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import cn.dong.demo.R;
import cn.dong.demo.base.BaseActivity;
import cn.dong.demo.util.CameraUtil;

/**
 * 图片选择器
 * 
 * @author dong 2014-7-20
 */
public class ImageSelectorActivity extends BaseActivity {
    private Button button;
    private CameraUtil cameraUtil;

    @Override
    protected int initPageLayoutID() {
        return R.layout.image_selector_activity;
    }

    @Override
    protected void init() {
        super.init();
        cameraUtil = new CameraUtil(this);
    }

    @Override
    protected void initPageView() {
        button = (Button) findViewById(R.id.button);
    }

    @Override
    protected void initPageViewListener() {
        button.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                imageSelector();
            }
        });
    }

    @Override
    protected void process(Bundle savedInstanceState) {
        // TODO Auto-generated method stub

    }

    private void imageSelector() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setItems(new String[] {"拍照", "本地图片"}, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                switch (which) {
                    case 0:
                        cameraUtil.startActionCamera();
                        break;
                    case 1:
                        cameraUtil.startImagePick();
                        break;
                }
            }
        });
        builder.show();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

    }
}

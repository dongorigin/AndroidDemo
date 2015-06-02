package cn.dong.demo.ui.storage;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import cn.dong.demo.R;
import cn.dong.demo.ui.common.BaseActivity;
import cn.dong.demo.util.L;

/**
 * 将应用内资源图片存储至储存卡
 *
 * @author dong 2014-5-23
 */
public class BitmapSaveLocalActivity extends BaseActivity {
    private static final String IMAGE_NAME = "ic_launcher.png";

    private Button btn_save;

    @Override
    protected int initPageLayoutID() {
        return R.layout.activity_image;
    }

    @Override
    protected void initPageView() {
        btn_save = (Button) findViewById(R.id.save);
    }

    @Override
    protected void initPageViewListener() {
        btn_save.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                saveImage();
            }
        });
    }

    @Override
    protected void process(Bundle savedInstanceState) {

    }

    private void saveImage() {
//        L.d(TAG, getExternalFilesDir("").toString());
//        L.d(TAG, getExternalFilesDir(Environment.DIRECTORY_PICTURES).toString());
//        L.d(TAG, Environment.getExternalStorageDirectory().toString());
//        L.d(TAG, Environment.getExternalStoragePublicDirectory("test").toString());

        // 保存图片
        File appDir = new File(Environment.getExternalStorageDirectory(), "DongDemo");
        if (!appDir.exists()) {
            if (!appDir.mkdir()) {
                return;
            }
        }
        String fileName = System.currentTimeMillis() + ".jpg";
        File file = new File(appDir, fileName);
        L.d(TAG, "Path = " + file.getAbsolutePath());
        L.d(TAG, "Parent = " + file.getParent());

        try {
            Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.mipmap.ic_launcher);
            FileOutputStream fos = new FileOutputStream(file);
            bitmap.compress(Bitmap.CompressFormat.JPEG, 70, fos);
            fos.flush();
            fos.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        // 将文件插入系统图库
        // 一直提示失败
//        try {
//            MediaStore.Images.Media.insertImage(mContext.getContentResolver(), file.getAbsolutePath(), fileName, null);
//        } catch (FileNotFoundException e) {
//            e.printStackTrace();
//        }

        // 通知系统图库更新
        mContext.sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, Uri.fromFile(file)));
    }

}

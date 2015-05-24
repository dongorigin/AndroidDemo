package cn.dong.demo.ui.other;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.os.Bundle;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import butterknife.InjectView;
import cn.dong.demo.R;
import cn.dong.demo.ui.common.BaseActivity;
import cn.dong.demo.util.BlurUtils;
import cn.dong.demo.util.L;
import cn.dong.demo.util.ViewHolder;

/**
 * @author dong on 15/2/13.
 */
public class ScreenshotActivity extends BaseActivity {

    @InjectView(R.id.button1)
    Button btn1;
    @InjectView(R.id.image)
    ImageView imageView;
    @InjectView(R.id.list)
    ListView listView;


    @Override
    protected int initPageLayoutID() {
        return R.layout.activity_screenshot;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        L.d(TAG, "ScaledMaximumDrawingCacheSize %d", ViewConfiguration.get(this).getScaledMaximumDrawingCacheSize());

        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bitmap screenshot = screenshot(listView);
                if (screenshot != null) {
//                    Bitmap blurBitmap = BlurUtils.blurBitmap(screenshot, 30);
                    Bitmap blurBitmap = BlurUtils.apply(ScreenshotActivity.this, screenshot, 20);
                    imageView.setImageBitmap(blurBitmap);
                }
            }
        });

        listView.setAdapter(new MainAdapter());
    }

    private Bitmap screenshot(View view) {
        if (view.getWidth() == 0 || view.getHeight() == 0) {
            return null;
        }

        Bitmap bitmap = Bitmap.createBitmap(view.getWidth(), view.getHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        view.draw(canvas);
        return bitmap;
    }

    private void saveScreenshot(Bitmap bitmap) {
        File dir = getExternalFilesDir("screenshot");
        File file = new File(dir, System.currentTimeMillis() + ".jpg");
        try {
            FileOutputStream fos = new FileOutputStream(file);
            bitmap.compress(Bitmap.CompressFormat.JPEG, 70, fos);
            fos.flush();
            fos.close();
            bitmap.recycle();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private class MainAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return 20;
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            if (convertView == null) {
                convertView = getLayoutInflater().inflate(R.layout.activity_screenshot_item, parent, false);
            }
            TextView textView = ViewHolder.get(convertView, R.id.text);

            textView.setText(String.valueOf(position));

            ObjectAnimator scaleXAnim = ObjectAnimator.ofFloat(textView, "scaleX", 1f, 1.5f);
            scaleXAnim.setDuration(3000);
            scaleXAnim.setRepeatCount(100);
            scaleXAnim.setRepeatMode(ValueAnimator.REVERSE);
            ObjectAnimator scaleYAnim = ObjectAnimator.ofFloat(textView, "scaleY", 1f, 1.5f);
            scaleYAnim.setDuration(3000);
            scaleYAnim.setRepeatCount(100);
            scaleYAnim.setRepeatMode(ValueAnimator.REVERSE);

            AnimatorSet scaleSet = new AnimatorSet();
            scaleSet.play(scaleXAnim);
            scaleSet.play(scaleYAnim).with(scaleXAnim);
            scaleSet.start();

            return convertView;
        }
    }
}

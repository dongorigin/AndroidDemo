package cn.dong.demo.ui.other;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import butterknife.InjectView;
import butterknife.OnClick;
import cn.dong.demo.R;
import cn.dong.demo.ui.common.BaseActivity;

/**
 * Created by dong on 14/12/24.
 */
public class ImageSelectorActivity extends BaseActivity {
    @InjectView(R.id.uri)
    TextView uriView;
    @InjectView(R.id.path)
    TextView pathView;
    @InjectView(R.id.image)
    ImageView imageview;

    @Override
    protected int initPageLayoutID() {
        return R.layout.activity_selected_image;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @OnClick(R.id.button)
    void selectImage() {
        Intent imageIntent = new Intent(Intent.ACTION_GET_CONTENT);
        imageIntent.setType("image/*");
        startActivityForResult(imageIntent, 0);
    }

    @OnClick(R.id.button2)
    void selectImage2() {
        Intent imageIntent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
        imageIntent.setType("image/*");
        startActivityForResult(imageIntent, 0);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && data != null) {

        }
    }

}

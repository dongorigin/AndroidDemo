package cn.dong.demo.ui;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import cn.dong.demo.R;
import cn.dong.demo.base.BaseActivity;

public class AnimeActivity extends BaseActivity {
	private Animation zoom;
	private ImageView imageView;

	@Override
	protected int initPageLayoutID() {
		return R.layout.activity_anime;
	}

	@Override
	protected void initPageView() {
		imageView = (ImageView) findViewById(R.id.image);
	}

	@Override
	protected void initPageViewListener() {
		imageView.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				imageView.startAnimation(zoom);
			}
		});
	}

	@Override
	protected void process(Bundle savedInstanceState) {
		zoom = AnimationUtils.loadAnimation(this, R.anim.zoom);
	}
}

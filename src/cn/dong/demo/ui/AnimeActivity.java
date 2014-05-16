package cn.dong.demo.ui;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import cn.dong.demo.R;

public class AnimeActivity extends Activity implements OnClickListener {
	private Animation zoom;
	private ImageView imageView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_anime);

		zoom = AnimationUtils.loadAnimation(this, R.anim.zoom);
		imageView = (ImageView) findViewById(R.id.image);
		imageView.setOnClickListener(this);

	}

	@Override
	public void onClick(View v) {
		imageView.startAnimation(zoom);
	}
}

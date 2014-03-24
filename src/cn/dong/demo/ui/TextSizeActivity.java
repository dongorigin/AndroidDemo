package cn.dong.demo.ui;

import android.app.Activity;
import android.content.res.Resources;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import cn.dong.demo.R;

public class TextSizeActivity extends Activity {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_text_size);

		TextView text = (TextView) findViewById(R.id.text);
		text.setTextSize(14);

		Resources resources = getResources();
		final DisplayMetrics metrics = resources.getDisplayMetrics();
		TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 14, metrics);

		Button button = (Button) findViewById(R.id.button);
		button.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Log.i("density", metrics.density + "");
				Log.i("scaledDensity", metrics.scaledDensity + "");
			}
		});
		
	}
}

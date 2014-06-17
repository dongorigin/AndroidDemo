package cn.dong.demo.ui;

import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager.LayoutParams;
import android.widget.Button;
import android.widget.PopupWindow;
import cn.dong.demo.R;
import cn.dong.demo.base.BaseActivity;

public class PopupWindowActivity extends BaseActivity {

	private Button popupButton;
	private PopupWindow popupWindow;

	@Override
	protected int initPageLayoutID() {
		return R.layout.popup_window_activity;
	}

	@Override
	protected void initPageView() {
		popupButton = (Button) findViewById(R.id.btn_popup);

		View popupView = getLayoutInflater().inflate(R.layout.popup_window_layout, null);
		popupWindow = new PopupWindow(popupView, LayoutParams.MATCH_PARENT,
				LayoutParams.WRAP_CONTENT);
		popupWindow.setTouchable(true);
		popupWindow.setOutsideTouchable(true);
		popupWindow.setBackgroundDrawable(new BitmapDrawable());
	}

	@Override
	protected void initPageViewListener() {
		popupButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				popupWindow.showAsDropDown(v);
			}
		});
	}

	@Override
	protected void process(Bundle savedInstanceState) {
		// TODO Auto-generated method stub

	}

}

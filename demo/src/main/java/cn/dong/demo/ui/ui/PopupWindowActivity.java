package cn.dong.demo.ui.ui;

import android.app.Activity;
import android.app.Dialog;
import android.content.DialogInterface;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.view.WindowManager.LayoutParams;
import android.widget.Button;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import cn.dong.demo.R;
import cn.dong.demo.ui.common.BaseActivity;

public class PopupWindowActivity extends BaseActivity {

	private Button popupButton;
	private PopupWindow popupWindow;
	private View popupView;

	@Override
	protected int initPageLayoutID() {
		return R.layout.popup_window_activity;
	}

	@Override
	protected void initPageView() {
		popupButton = (Button) findViewById(R.id.btn_popup);

		popupView = getLayoutInflater().inflate(R.layout.popup_window_layout, null);
		popupWindow = new PopupWindow(popupView, LayoutParams.MATCH_PARENT,
				LayoutParams.WRAP_CONTENT);
		popupWindow.setTouchable(true);
		popupWindow.setOutsideTouchable(true);
		popupWindow.setBackgroundDrawable(new BitmapDrawable());
		popupWindow.setAnimationStyle(R.style.PopupWindowAnimation);
	}

	@Override
	protected void initPageViewListener() {
		popupButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// popupWindow.showAsDropDown(v);
				popUpMenu(popupView, PopupWindowActivity.this, null);
			}
		});
	}

	@Override
	protected void process(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
	}

	public static Dialog menuDialog;

	public static void popUpMenu(View view, Activity activityContext,
			DialogInterface.OnDismissListener listener) {

		menuDialog = new Dialog(activityContext, R.style.transparentFrameWindowStyle);
		menuDialog.setContentView(view, new RelativeLayout.LayoutParams(activityContext
				.getWindowManager().getDefaultDisplay().getWidth(),
				RelativeLayout.LayoutParams.WRAP_CONTENT));
		Window window = menuDialog.getWindow();
		// 设置显示动画
//		window.setWindowAnimations(R.style.main_menu_animstyle);
		WindowManager.LayoutParams wl = window.getAttributes();
		wl.x = 0;
//		wl.y = (int) (activityContext.getWindowManager().getDefaultDisplay().getHeight() * 0.5);
		wl.y = 0;
		wl.width = 300;
		wl.height = 300;
//		window.setAttributes(wl); 
		// 设置显示位置
		menuDialog.onWindowAttributesChanged(wl);
		// 设置点击外围解散
		menuDialog.setCanceledOnTouchOutside(true);
		menuDialog.show();
		menuDialog.setOnDismissListener(listener);
//		Display display = activityContext.getWindowManager().getDefaultDisplay();
//		WindowManager.LayoutParams lp = menuDialog.getWindow().getAttributes();
//		lp.width = (int) (display.getWidth()); // 设置宽度
//		menuDialog.getWindow().setAttributes(lp);
	}

}

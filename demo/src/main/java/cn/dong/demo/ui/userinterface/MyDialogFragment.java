package cn.dong.demo.ui.userinterface;

import android.app.Dialog;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import cn.dong.demo.R;

public class MyDialogFragment extends DialogFragment {
	private static final String TAG = "DialogFragment";
	private TextView title_tv;
	private Button cancel_bn;
	private Button confirm_bn;

	private String title;
	private OnClickListener listener;

	public MyDialogFragment() {
		super();
	}

	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState) {
		Log.d(TAG, "onCreateDialog");
		Dialog dialog = new Dialog(getActivity(), R.style.DialogHolo);
		dialog.requestWindowFeature(STYLE_NO_TITLE);
		return dialog;
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		Log.d(TAG, "onCreateView");
		View view = inflater.inflate(R.layout.dialog, container);
		title_tv = (TextView) view.findViewById(R.id.title);
		title_tv.setText(title);
		cancel_bn = (Button) view.findViewById(R.id.cancel);
		cancel_bn.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				dismiss();
			}
		});
		confirm_bn = (Button) view.findViewById(R.id.confirm);
		confirm_bn.setOnClickListener(listener);
		return view;
	}

}

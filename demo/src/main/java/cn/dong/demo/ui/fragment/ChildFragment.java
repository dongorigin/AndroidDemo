package cn.dong.demo.ui.fragment;

import cn.dong.demo.R;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class ChildFragment extends Fragment {
	private static final String TAG = "ChildFragment";

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Log.d(TAG, getFragmentManager().toString());
		Log.d(TAG, getChildFragmentManager().toString());
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		return inflater.inflate(R.layout.fragment_child, container, false);
	}
}

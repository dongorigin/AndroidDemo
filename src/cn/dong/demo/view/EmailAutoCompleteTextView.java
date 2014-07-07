package cn.dong.demo.view;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.TextView;

/**
 * @Description 邮箱地址自动补全的EditText
 * @author dong
 * @date 2014-2-12 下午6:04:35
 */
public class EmailAutoCompleteTextView extends AutoCompleteTextView {
	private static final String TAG = "EmailAutoCompleteTextView";

	private String[] emailSuffixes = new String[] { "@qq.com", "@163.com", "@126.com",
			"@sina.com", "@sohu.com", "@gmail.com", "@hotmail.com", "@yahoo.com",
			"@sina.cn", "@vip.163.com", "@vip.126.com" };

	public EmailAutoCompleteTextView(Context context) {
		this(context, null);
	}

	public EmailAutoCompleteTextView(Context context, AttributeSet attrs) {
		this(context, attrs, android.R.attr.autoCompleteTextViewStyle);
	}

	public EmailAutoCompleteTextView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		init(context);
	}

	public void setAdapterString(String[] emailSuffixes) {
		if (emailSuffixes != null && emailSuffixes.length > 0) {
			this.emailSuffixes = emailSuffixes;
		}
	}

	private void init(final Context context) {
		Log.i(TAG, "init");
		// emailSuffixes可通过setAdapterString方法更改
		this.setAdapter(new EmailAutoCompleteAdapter(context,
				android.R.layout.simple_list_item_1, emailSuffixes));
		// 输入1个字符即开启自动完成
		this.setThreshold(1);

		this.setOnFocusChangeListener(new OnFocusChangeListener() {

			@Override
			public void onFocusChange(View v, boolean hasFocus) {
				String currentText = EmailAutoCompleteTextView.this.getText().toString();
				if (hasFocus) {
					// 获得焦点时,重启自动完成
					if (!currentText.equals("")) {
						performFiltering(currentText, 0);
					}
				} else {
					// 丢失焦点时,可判断邮件格式是否正确
				}
			}
		});
	}

	/**
	 * 检查邮件地址格式是否正确
	 * 
	 * @param email
	 *            邮件地址字符串
	 * @return
	 *         格式是否正确
	 */
	public boolean checkEmailFormat(String email) {
		// TODO 正则表达式待优化..
		if (email != null && email.matches("^[a-zA-Z0-9_]+@[a-zA-Z0-9]+\\.[a-zA-Z0-9]+$")) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * 检查当前输入的邮件地址格式是否正确
	 * 
	 * @return
	 *         格式是否正确
	 */
	public boolean checkEmailFormat() {
		return checkEmailFormat(getText().toString());
	}

	@Override
	protected void replaceText(CharSequence text) {
		// 默认为,使用Adapter中的文本来替换已输入文本
		// 更改为,将已输入部分与后缀合并
		String currentText = this.getText().toString();
		int index = currentText.indexOf("@");
		if (index != -1) {
			currentText = currentText.substring(0, index);
		}
		super.replaceText(currentText + text);
	}

	@Override
	protected void performFiltering(CharSequence text, int keyCode) {
		String currentText = text.toString();
		int index = currentText.indexOf("@");
		if (index == -1) {
			super.performFiltering("@", keyCode);
		} else {
			super.performFiltering(currentText.substring(index), keyCode);
		}
	}

	private class EmailAutoCompleteAdapter extends ArrayAdapter<String> {

		public EmailAutoCompleteAdapter(Context context, int resource, String[] objects) {
			super(context, resource, objects);
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			if (convertView == null) {
				convertView = LayoutInflater.from(getContext()).inflate(
						android.R.layout.simple_list_item_1, parent, false);
			}
			TextView tv = (TextView) convertView.findViewById(android.R.id.text1);
			String currentText = EmailAutoCompleteTextView.this.getText().toString();
			int index = currentText.indexOf("@");
			if (index != -1) {
				currentText = currentText.substring(0, index);
			}
			tv.setText(currentText + getItem(position));
			return convertView;
		}
	}

}

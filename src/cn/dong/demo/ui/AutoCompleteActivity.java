package cn.dong.demo.ui;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import cn.dong.demo.R;
import cn.dong.demo.widget.EmailAutoCompleteTextView;

public class AutoCompleteActivity extends Activity {
	private EmailAutoCompleteTextView emailAutoText;
	private AutoCompleteTextView autoText;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_auto_complete);

		emailAutoText = (EmailAutoCompleteTextView) findViewById(R.id.email_auto_text);
		autoText = (AutoCompleteTextView) findViewById(R.id.autoText);

		String[] countries = getResources().getStringArray(R.array.countries_array);
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
				android.R.layout.simple_list_item_1, countries);
		autoText.setAdapter(adapter);

	}
}

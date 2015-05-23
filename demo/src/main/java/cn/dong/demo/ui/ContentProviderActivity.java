package cn.dong.demo.ui;

import java.util.ArrayList;

import android.content.ContentResolver;
import android.content.ContentUris;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.widget.TextView;
import cn.dong.demo.R;
import cn.dong.demo.ui.common.BaseActivity;

public class ContentProviderActivity extends BaseActivity {
    private ArrayList<String> names = new ArrayList<String>();

    private TextView text;

    @Override
    protected int initPageLayoutID() {
        return R.layout.activity_content_provider;
    }

    @Override
    protected void initPageView() {
        text = (TextView) findViewById(R.id.text);
    }

    @Override
    protected void initPageViewListener() {

    }

    @Override
    protected void process(Bundle savedInstanceState) {
        // 系统Provider测试
        names = getNames();
        StringBuilder sb = new StringBuilder();
        for (String name : names) {
            sb.append(name).append(", ");
        }
        text.setText(sb.toString());

        // 自定义Provider测试
        Uri uri = Uri.parse("content://cn.dong.demo.provider/");
        ContentUris.withAppendedId(uri, 1);
        System.out.println(uri.toString());
    }

    private ArrayList<String> getNames() {
        ArrayList<String> names = new ArrayList<String>();
        ContentResolver resolver = getContentResolver();
        Uri uri = ContactsContract.Contacts.CONTENT_URI;
        Cursor cursor = resolver.query(uri, null, null, null, null);
        while (cursor.moveToNext()) {
            int nameIndex = cursor.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME);
            String name = cursor.getString(nameIndex);
            names.add(name);
        }
        return names;
    }
}

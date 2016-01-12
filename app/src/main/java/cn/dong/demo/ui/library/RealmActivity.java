package cn.dong.demo.ui.library;

import android.os.Bundle;

import cn.dong.demo.R;
import cn.dong.demo.ui.common.BaseActivity;
import io.realm.Realm;

/**
 * @author dong on 16/1/7.
 */
public class RealmActivity extends BaseActivity {
    private Realm realm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        realm = Realm.getInstance(mContext);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        realm.close();
    }

    @Override
    protected int initPageLayoutID() {
        return R.layout.activity_realm;
    }

    @Override
    protected void process(Bundle savedInstanceState) {
        super.process(savedInstanceState);



    }
}

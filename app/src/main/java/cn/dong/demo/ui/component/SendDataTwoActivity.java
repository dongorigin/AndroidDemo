package cn.dong.demo.ui.component;

import android.content.Intent;

import java.util.ArrayList;

import cn.dong.demo.R;
import cn.dong.demo.model.TestInfo;
import cn.dong.demo.ui.common.BaseActivity;

/**
 * @author dong on 15/6/11.
 */
public class SendDataTwoActivity extends BaseActivity {
    public static final String EXTRA_INT = "int";
    public static final String EXTRA_STRING = "string";
    public static final String EXTRA_OBJECT = "object";
    public static final String EXTRA_OBJECT_LIST = "object_list";

    private int mInt;
    private String mString;
    private TestInfo mData;
    private ArrayList<TestInfo> mDataList = new ArrayList<>();

    @Override
    protected void init() {
        super.init();
        Intent intent = getIntent();
        mInt = intent.getIntExtra(EXTRA_INT, 0);
        mString = intent.getStringExtra(EXTRA_STRING);
        mData = intent.getParcelableExtra(EXTRA_OBJECT);
        mDataList = intent.getParcelableArrayListExtra(EXTRA_OBJECT_LIST);

        mData.one = 20;
        mData.two = 200;
        mData.three = "test";


    }

    private void changeData() {
        mData.one = 20;
        mData.two = 200;
        mData.three = "test";
    }

    @Override
    protected int initPageLayoutID() {
        return R.layout.activity_senddata_two;
    }

}

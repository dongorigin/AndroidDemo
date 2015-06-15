package cn.dong.demo.ui.component;

import android.content.Intent;
import android.view.View;

import java.util.ArrayList;

import cn.dong.demo.R;
import cn.dong.demo.model.TestInfo;
import cn.dong.demo.ui.common.BaseActivity;

/**
 * Activity传值测试
 *
 * @author dong on 15/6/11.
 */
public class SendDataOneActivity extends BaseActivity {
    private TestInfo mData;
    private ArrayList<TestInfo> mDataList = new ArrayList<>();

    @Override
    protected void init() {
        super.init();
        mData = new TestInfo(10, 100, "test0");
        mDataList.add(new TestInfo(11, 111, "test1"));
        mDataList.add(new TestInfo(12, 222, "test2"));
        mDataList.add(new TestInfo(13, 333, "test3"));
        mDataList.add(new TestInfo(14, 444, "test4"));
    }

    @Override
    protected int initPageLayoutID() {
        return R.layout.activity_senddata_one;
    }

    public void jump(View v) {
        Intent intent = new Intent(mContext, SendDataTwoActivity.class);
        intent.putExtra(SendDataTwoActivity.EXTRA_INT, 4);
        intent.putExtra(SendDataTwoActivity.EXTRA_STRING, "test");
        intent.putExtra(SendDataTwoActivity.EXTRA_OBJECT, mData);
        intent.putParcelableArrayListExtra(SendDataTwoActivity.EXTRA_OBJECT_LIST, mDataList);
        startActivityForResult(intent, 1);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

    }
}

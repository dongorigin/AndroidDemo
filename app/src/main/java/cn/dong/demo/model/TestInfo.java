package cn.dong.demo.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Activity传值测试，实现Parcelable接口
 *
 * @author dong on 15/6/11.
 */
public class TestInfo implements Parcelable {
    public int one;
    public int two;
    public String three;

    public TestInfo(int one, int two, String three) {
        this.one = one;
        this.two = two;
        this.three = three;
    }

    public TestInfo(Parcel source) {
        one = source.readInt();
        two = source.readInt();
        three = source.readString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(one);
        dest.writeInt(two);
        dest.writeString(three);
    }

    public static final Creator<TestInfo> CREATOR = new Creator<TestInfo>() {
        @Override
        public TestInfo createFromParcel(Parcel source) {
            return new TestInfo(source);
        }

        @Override
        public TestInfo[] newArray(int size) {
            return new TestInfo[size];
        }
    };

}

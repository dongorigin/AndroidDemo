package cn.dong.demo.util.inputfilter;

import android.text.InputFilter;
import android.text.Spanned;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 输入长度限制，半角字符算一个，全角字符算两个
 *
 * @author dong on 15/4/24.
 */
public class EmsLenghtFilter implements InputFilter {
    private static final Pattern chinesePattern = Pattern.compile("[\\u4e00-\\u9fa5]"); // unicode编码，判断是否为汉字

    private int mMax;

    public EmsLenghtFilter(int max) {
        this.mMax = max;
    }

    @Override
    public CharSequence filter(CharSequence source, int start, int end, Spanned dest, int dstart, int dend) {
        int destCount = dest.toString().length() + getChineseCount(dest.toString());
        int sourceCount = source.toString().length() + getChineseCount(source.toString());
        if (destCount + sourceCount > mMax) {
            return "";
        } else {
            return source;
        }
    }

    private int getChineseCount(String str) {
        int count = 0;
        Matcher m = chinesePattern.matcher(str);
        while (m.find()) {
            for (int i = 0; i <= m.groupCount(); i++) {
                count = count + 1;
            }
        }
        return count;
    }
}

package cn.dong.demo.util.inputfilter;

import android.text.InputFilter;
import android.text.Spanned;

/**
 * 文字长度过滤器，2半角字符=1全角字符=1字长
 *
 * @author dong on 16/3/16.
 */
public class InputLengthFilter implements InputFilter {
    private int max;

    public InputLengthFilter(int max) {
        this.max = max;
    }

    @Override
    public CharSequence filter(CharSequence source, int start, int end, Spanned dest, int dstart, int dend) {
        int inputLength = (int) Math.ceil(getInputLength(dest.toString()));
        int keep = max - inputLength;
        if (keep <= 0) {
            return "";
        } else {
            if (keep >= getInputLength(source.toString())) {
                return null; // keep original
            } else {
                return source.subSequence(start, keep / 2);
            }
        }
    }

    public static double getInputLength(String str) {
        String tmp = str.replaceAll("[^\\x00-\\xff]", "**");
        return tmp.length() / 2.0;
    }
}

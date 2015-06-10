package cn.dong.demo.util.inputfilter;

import android.text.InputFilter;
import android.text.Spanned;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import cn.dong.demo.util.L;

/**
 * @author dong on 15/5/29.
 */
public class LinebreakInputFilter implements InputFilter {
    private static final String TAG = LinebreakInputFilter.class.getSimpleName();
    private static final String PATTERN = "\\n";

    @Override
    public CharSequence filter(CharSequence source, int start, int end, Spanned dest, int dstart, int dend) {
        L.d(TAG, "filter source=%s start=%d end=%d dest=%s dstart=%d dend=%d", source, start, end, dest, dstart, dend);
        Pattern pattern = Pattern.compile(PATTERN);
        Matcher matcher = pattern.matcher(source);
        if (matcher.find()) {
            return "";
        } else {
            return source;
        }
    }
}

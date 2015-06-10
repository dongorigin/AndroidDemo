package cn.dong.demo.util.inputfilter;

import android.text.InputFilter;
import android.text.Spanned;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import cn.dong.demo.util.L;

/**
 * Emoji输入过滤
 * 在全面兼容Emoji前暂时禁止Emji的使用
 *
 * @author dong on 15/3/11.
 */
public class EmojiInputFilter implements InputFilter {
    private static final String TAG = EmojiInputFilter.class.getSimpleName();

    private Pattern emoji = Pattern.compile("[\ud83c\udc00-\ud83c\udfff]|[\ud83d\udc00-\ud83d\udfff]|[\u2600-\u27ff]", Pattern.UNICODE_CASE | Pattern.CASE_INSENSITIVE);

    @Override
    public CharSequence filter(CharSequence source, int start, int end, Spanned dest, int dstart, int dend) {
        L.d(TAG, "filter source=%s start=%d end=%d dest=%s dstart=%d dend=%d", source, start, end, dest, dstart, dend);
        Matcher emojiMatcher = emoji.matcher(source);
        if (emojiMatcher.find()) {
            return "";
        } else {
            return source;
        }
    }
}

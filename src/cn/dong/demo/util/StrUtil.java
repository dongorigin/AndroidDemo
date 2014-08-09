package cn.dong.demo.util;


/**
 * 字符串辅助类
 * 
 * @author dong 2014-8-10
 */
public class StrUtil {
    public static boolean isEmpty(String str) {
        if (str == null || str.trim().length() == 0) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * 返回以某个符号分隔的字符串的第几项 从0开始
     * 
     * @param str 原字符串
     * @param num 要取得的字符在字符串中的序号，以0开始
     * @param character 分隔符
     * @return String 返回类型
     */
    public static String getSingleSplitStr(String str, int num, String character) {
        if (isEmpty(str) || !str.contains(character)) {
            return str;
        }
        String[] strs = str.split(character);
        if (strs.length < num + 1) {
            return "";
        }
        try {
            return strs[num];
        } catch (Exception e) {
            return "";
        }
    }

}

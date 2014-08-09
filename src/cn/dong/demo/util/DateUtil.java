package cn.dong.demo.util;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Date工具类
 * 
 * @author dong 2014-8-10
 */
public class DateUtil {

    /**
     * 将一个java.util.Date对象转换成特定格式的字符串
     * 
     * @param date 日期对象
     * @param format 指定格式
     */
    public static String formatDate(Date date, String format) {
        String result = "";
        if (date == null) {
            return result;
        }
        SimpleDateFormat formatter = new SimpleDateFormat(format);
        try {
            result = formatter.format(date.getTime());
            return result;
        } catch (Exception e) {
            e.printStackTrace();
            return result;
        }
    }

}

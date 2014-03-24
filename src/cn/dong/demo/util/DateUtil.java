package cn.dong.demo.util;

import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

/**
 * 日期处理类
 * 
 */
public class DateUtil {
	public static String dateFormat(Date date) {
		String dateStr = "";
		if (date == null) {
			return "";
		}
		SimpleDateFormat sd = new SimpleDateFormat("HH:mm");
		dateStr = sd.format(date);
		return dateStr;
	}
	
	//格式化展会显示时间格式为 10.13 - 10.15
	public static String formatTradeData(String openTime,String closeTime) {
		String open = dateFormat(new Date(openTime),"MM.dd");
		String close = dateFormat(new Date(closeTime),"MM.dd");
		if(StrUtil.isNotEmpty(open)) {
			return open+"-"+close;
		}else if(StrUtil.isNotEmpty(close)) {
			return close;
		}
		return "";
	}

	public static String dateFormat(Date date, String formatStr) {
		if (date == null) {
			return "";
		}
		if (!StrUtil.isNotEmpty(formatStr)) {
			formatStr = "MM月dd日    HH:mm";
		}
		SimpleDateFormat sd = new SimpleDateFormat(formatStr);
		String dateStr = sd.format(date);
		return dateStr;
	}

	public static String dateFormat(long dateTime) {
		String dateStr = "";

		if (dateTime == 0) {
			return "";
		}
		Date date = new Date(dateTime);
		dateStr = dateFormat(date);
		return dateStr;
	}

	/*
	 * 取date和当前时间的差值
	 */
	public static String distanceDate(Date date) {
		String dateStr = "";
		if (date == null) {
			return "";
		}
		// Log.v("time", distanceDay(date)+"");
		if (distanceDay(date) == 0) {
			// 说明是当天的
			dateStr = dateFormat(date);
			return dateStr;
		} else if (distanceDay(date) == 1) {
			// 昨天
			String temStr = dateFormat(date);
			dateStr = "昨天" + temStr;
			return dateStr;

		} else {
			dateStr = dateFormat(date, "MM月dd日");
			return dateStr;
		}
	}

	/*
	 * 取date和当前时间的差值
	 */
	public static String distanceDate(long dateTime) {
		Date date = new Date(dateTime);
		return distanceDate(date);
	}

	public static int distanceDay(Date date) {
		if (date == null) {
			return -1;
		}
		Date nowDate = new Date();
		int nowDay = nowDate.getDay();
		int oldDay = date.getDay();
		int distance = nowDay - oldDay;
		return distance;
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
	}

	// 时间处理函数
	public static String formatTime(long second) {
		String result = "";
		if (second >= 0 && second < 60) {
			result = second + "秒";
		} else if (second >= 60 && second < 3600) {
			result = second / 60 + "分" + second % 60 + "秒";
		} else if (second >= 3600 && second < 3660) {
			result = second / 3600 + "小时" + second % 3600 + "秒";
		} else if (second >= 3660) {
			result = second / 3600 + "小时" + second % 3600 / 60 + "分" + second
					% 3600 % 60 + "秒";
		}
		return result;
	}

	public static String formatTime(String second) {
		if (StrUtil.isEmpty(second))
			return "";
		String result = "";
		try {
			result = formatTime(Long.parseLong(second));
		} catch (NumberFormatException e) {
			return "";
		}
		return result;
	}

	/**
	 * 将一个java.util.Date对象转换成特定格式的字符串
	 * 
	 * @param date
	 *            日期对象 format 格式
	 * @return
	 */
	public static String formatDate(Date date, String format) {
		String result = "";
		if (date == null) {
			return result;
		}
		SimpleDateFormat formatter = new SimpleDateFormat(format);
		try {
			result = formatter.format(date.getTime());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	/**
	 * 将一个字符串的日期描述转换为java.util.Date对象
	 * 
	 * @param strDate
	 *            字符串的日期描述
	 * @param format
	 *            字符串的日期格式，比如:“yyyy-MM-dd HH:mm”
	 * @return 字符串转换的日期对象java.util.Date
	 */
	public static Date getDate(String strDate) {
		if (strDate == null || strDate.trim().equals("")) {
			return null;
		}
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date date;
		try {
			date = formatter.parse(strDate);
			return date;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 将一个字符串的日期描述转换为java.util.Date对象
	 * 
	 * @param strDate
	 *            字符串的日期描述
	 * @param format
	 *            字符串的日期格式，比如:“yyyy-MM-dd HH:mm”
	 * @return 字符串转换的日期对象java.util.Date
	 */
	public static Date getDate(String strDate, String format) {
		if (strDate == null || strDate.trim().equals("")) {
			return null;
		}
		SimpleDateFormat formatter = new SimpleDateFormat(format);
		formatter.setTimeZone(timeZone);
		Date date;
		try {
			date = formatter.parse(strDate);
		} catch (Exception e) {
			date = null;
		}
		return date;
	}

	private static final TimeZone timeZone = TimeZone.getTimeZone("GMT+08:00");

	/**
	 * 取两日期之间的天数间隔
	 * 
	 * @param strDate1
	 *            格式:yyyymmdd
	 * @param strDate2
	 *            格式:yyyymmdd
	 * @return
	 */
	public static int getDistance(String strDate1, String strDate2) {
		int distance = 0;
		Date date1 = getDate(strDate1, "yyyyMMdd");
		Date date2 = getDate(strDate2, "yyyyMMdd");
		if (date1 == null || date2 == null) {
			return distance;
		}
		distance = (int) ((date2.getTime() - date1.getTime()) / 1000 / 60 / 60 / 24);
		return distance;
	}

	/**
	 * 将传入的日期(已经排序)转换为符合消息显示的字符串形式（按一定时间段对时间列表进行分割，返回Map<间隔位置,间隔位置的提示内容>
	 * 一小时以内显示“NN分钟前” 一天以内的显示“NN小时前” 3天内显示“1天前2天前3天前” 一周以外显示“2009-03-10”
	 * 
	 * @param sourceDateList
	 * @return
	 */
	public static String parseDate(Date sourceDate) {
		String resultStr = "";
		if (sourceDate == null) {
			return resultStr;
		}
		Date curDate = new Date();
		long curTime = System.currentTimeMillis();
		SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMdd");
		SimpleDateFormat formatter2 = new SimpleDateFormat("yyyy-MM-dd");
		long sourceTime = sourceDate.getTime();
		long subTime = Math.abs(curTime - sourceTime);// 毫秒单位的间隔
		int subDate = Math.abs(getDistance(formatter.format(curDate),
				formatter.format(sourceDate)));// 天单位的间隔
		if (subDate > 0) {
			// 间隔超过了一天，比如第一个时间23:50,第二个是次日0:10，已经不是同一天的了，不必考虑毫秒数
			if (subDate > 3) {
				resultStr = formatter2.format(sourceDate);
			} else {
				resultStr = subDate + "天前";
			}
		} else if (subDate == 0) {
			// 间隔在同一天之内
			if (subTime < 60 * 60 * 1000) {
				// 一小时以内
				String tempValue = ((subTime / (60 * 1000) > 0) ? ("" + subTime
						/ (60 * 1000)) : "1")
						+ "分钟之前";
				resultStr = tempValue;
			} else if (subTime >= 60 * 60 * 1000
					&& subTime < 24 * 60 * 60 * 1000) {
				// 一天以内
				resultStr = (subTime / (60 * 60 * 1000) > 0 ? "" + subTime
						/ (60 * 60 * 1000) : "1")
						+ "小时之前";
			}
		}

		return resultStr;
	}

	/**
	 * 返回两个时间间隔
	 * 
	 * @param sourceDate
	 * @return
	 */
	public static int parseDateDistance(Date sourceDate) {
		int distance = -1;
		if (sourceDate == null) {
			return distance;
		}
		Date curDate = new Date();
		SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMdd");
		int subDate = Math.abs(getDistance(formatter.format(curDate),
				formatter.format(sourceDate)));// 天单位的间隔
		return subDate;
	}

	public static int parseDateDistance(long time) {
		Date date = new Date(time);
		return parseDateDistance(date);
	}

	public static String parseDate(String dateStr) {
		if (dateStr == null || dateStr.equals(""))
			return "";
		Date date = getDate(dateStr);
		return parseDate(date);

	}

	public static String parseDate(long dateTime) {
		if (dateTime <= 0)
			return "";
		Date date = new Date(dateTime);
		return parseDate(date);

	}

	/** 解析签到记录时间 */
	public static String parseDate1(long dateTime) {
		if (dateTime <= 0)
			return "";
		Date date = new Date(dateTime);
		return parseDate1(date);

	}

	/** 解析为个人签到记录类型的时间字符串 （今天、昨天、几月几日） */
	public static String parseDate1(Date sourceDate) {
		String resultStr = "";
		if (sourceDate == null) {
			return resultStr;
		}
		SimpleDateFormat formatter = new SimpleDateFormat("M月dd日");
		int disDay = distanceDay(sourceDate);
		if (disDay == 0) {
			resultStr = "今天";
		} else if (disDay == 1) {
			resultStr = "昨天";
		} else if (disDay > 1) {
			resultStr = formatter.format(sourceDate);
		}
		return resultStr;
	}

	/**
	 * 取服务器当前时间
	 * 
	 * @param format
	 *            "yyyy-MM-dd" yyyy-MM-dd HH:mm:ss SSS
	 * @return
	 */
	public static String getNowDate(String format) {
		SimpleDateFormat d = new SimpleDateFormat(format);
		String dd = d.format(new Date());
		return dd;
	}

	/**
	 * 得到现在时间
	 */
	public static String getNowDate() {
		SimpleDateFormat d = new SimpleDateFormat("M月dd日");
		String dd = d.format(new Date());
		return dd;
	}

	public static String parseDateForDay(long time) {
		return parseDateForDay(new Date(time));
	}

	public static String parseDateForDay(Date date) {
		String resultStr = "";
		if (date == null) {
			return resultStr;
		}
		resultStr = DateUtil.formatDate(date, "M月dd日");
		String today = DateUtil.getNowDate();
		if (today.equals(resultStr)) {
			return "今天";
		}
		SimpleDateFormat formatter = new SimpleDateFormat("M月dd日");
		Date tempDate = new Date();
		long tempLong = tempDate.getTime();
		tempLong = tempLong - (24 * 60 * 60 * 1000);
		tempDate.setTime(tempLong);
		String yesterday = formatter.format(tempDate);
		if (yesterday.equals(resultStr)) {
			return "昨天";
		}
		return resultStr;
	}

	// 处理May 26,2013格式的日期
	public static String parseDateFromStr(String dateString) {
		dateString = dateString.replace(",", "");
		dateString = dateString.replace("Jan", "01");
		dateString = dateString.replace("Feb", "02");
		dateString = dateString.replace("Mar", "03");
		dateString = dateString.replace("Apr", "04");
		dateString = dateString.replace("May", "05");
		dateString = dateString.replace("Jun", "06");
		dateString = dateString.replace("Jul", "07");
		dateString = dateString.replace("Aug", "08");
		dateString = dateString.replace("Sep", "09");
		dateString = dateString.replace("Oct", "10");
		dateString = dateString.replace("Nov", "11");
		dateString = dateString.replace("Dec", "12");
		String[] strs = dateString.split(" ");
		StringBuffer sb = new StringBuffer();
		sb.append(strs[2]).append("/").append(strs[0]).append("/")
				.append(strs[1]);
		return sb.toString();
	}

	// 处理May 26,2013格式的日期
	public static String parseDateFromStr(String dateString, String split) {
		dateString = dateString.replace(",", "");
		dateString = dateString.replace("Jan", "01");
		dateString = dateString.replace("Feb", "02");
		dateString = dateString.replace("Mar", "03");
		dateString = dateString.replace("Apr", "04");
		dateString = dateString.replace("May", "05");
		dateString = dateString.replace("Jun", "06");
		dateString = dateString.replace("Jul", "07");
		dateString = dateString.replace("Aug", "08");
		dateString = dateString.replace("Sep", "09");
		dateString = dateString.replace("Oct", "10");
		dateString = dateString.replace("Nov", "11");
		dateString = dateString.replace("Dec", "12");
		String[] strs = dateString.split(" ");
		StringBuffer sb = new StringBuffer();
		sb.append(strs[2]).append(split).append(strs[0]).append(split)
				.append(strs[1]);
		return sb.toString();
	}

	public static String getFomatDate(Date date) {
		SimpleDateFormat fomat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		return fomat.format(date);
	}
	
	/**
	 * 
	 *  根据出生日期得到年龄
	  * getAgeFromBirthday(这里用一句话描述这个方法的作用)
	  * @Title: getAgeFromBirthday
	  * @Description: TODO
	  * @param @param data
	  * @param @return    设定文件
	  * @return String    返回类型
	  * @throws
	 */
	public static String getAgeFromBirthday(String data) throws ParseException {
		SimpleDateFormat myDataFormat = new SimpleDateFormat("yyyy-MM-dd");
		Date date = new Date();
		Date beDate = myDataFormat.parse(data);
		Date edDate = myDataFormat.parse(getTimeStr(date, "yyyy-MM-dd"));	
		if(edDate.getTime() < beDate.getTime()) {
			return "未知";
		}
		long day = (edDate.getTime()-beDate.getTime())/(24*60*60*1000)+1 ;
		String year = new DecimalFormat("#.00").format(day/365f);	
		if(year.startsWith(".")) {
			year = "0"+year;
		}
		String age[] = year.split("\\.");
		if(year.contains(".")) {
			return age[0];
		}else{
			return "";
		}
		
	}
	
	public static String getTimeStr(Date date ,String format) {
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat(format);
		return simpleDateFormat.format(date);
	}
}

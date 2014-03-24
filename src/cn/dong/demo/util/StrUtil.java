package cn.dong.demo.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import android.graphics.Color;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;

public class StrUtil {
	public static boolean isNumber(String str) {
		if (str == null) {
			return false;
		}
		String regex = "^((0|[1-9]|[1-9][0-9]+))$";
		boolean bol = str.matches(regex);
		if (bol == true) {
			return true;
		} else {
			return false;
		}
	}
	
	/*
	 * 是否以http开头
	 */
	public static boolean isHasHttp(String str) {
		if(str == null) {
			return false;
		}
		if(str.startsWith("http://")) {
			return true;
		}else{
			return false;
		}	
	}
	
	
	public static boolean isNotHasHttp(String str) {
		if(str == null) {
			return true;
		}
		if(str.startsWith("http://")) {
			return false;
		}else{
			return true;
		}	
	}
	
	/**
	 * 
	  * formatUrlHasHttp(这里用一句话描述这个方法的作用)
	  * @Title: formatUrlHasHttp
	  * @Description: TODO
	  * @param @param str
	  * @param @param defaultPath 默认地址 http开头
	  * @param @return    设定文件
	  * @return String    返回类型
	  * @throws
	 */
	public static String formatUrlHasHttp(String str , String defaultPath) {
		if(str == null) {
			return "";
		}
		if(isNotHasHttp(str)) {
			return defaultPath + str;
		}else{
			return str;
		}
		
	}
	
//	public static String formatUrlHasHttp(String str) {
//		return formatUrlHasHttp(str, Constants.IMGIP);
//	}
	
	/**
	 *  删除最后一个","
	  * cutLastlyComma(这里用一句话描述这个方法的作用)
	  * @Title: cutLastlyComma
	  * @Description: TODO
	  * @param @param s
	  * @param @return    设定文件
	  * @return String    返回类型
	  * @throws
	 */
	public static String cutLastlyComma(String s) {
		String str = s;
		if (s.indexOf(",") != -1) {
			int size = s.length();
			String comma = s.substring(size - 1, size);
			if (comma.equals(",")) {
				str = s.substring(0, size - 1);
			}
		}
		return str;
	}
	
	/**
	 *  删除第一个","
	  * cutLastlyComma(这里用一句话描述这个方法的作用)
	  * @Title: cutLastlyComma
	  * @Description: TODO
	  * @param @param s
	  * @param @return    设定文件
	  * @return String    返回类型
	  * @throws
	 */
	public static String cutFirstLyComma(String s) {
		if(isEmpty(s)){
			return "";
		}
		if(s.startsWith(",") && !s.trim().equals(",") && s.length() > 0){
			return s.substring(1);
		}
		return s;
	}
	
	/**
	 *  删除最后一个符号
	  * cutLastlyComma(这里用一句话描述这个方法的作用)
	  * @Title: cutLastlyComma
	  * @Description: TODO
	  * @param @param s
	  * @param @return    设定文件
	  * @return String    返回类型
	  * @throws
	 */
	public static String cutLastlyCharacter(String s , String character) {
		String str = s;
		
		if(character == null || character.equals("")) {
			return str;
		}
		
		if (s.indexOf(character) != -1) {
			int size = s.length();
			String comma = s.substring(size - 1, size);
			if (comma.equals(character)) {
				str = s.substring(0, size - 1);
			}
		}
		
		return str;
	}
	
	/**
	 *  返回以...结尾的字符串
	  * subString(这里用一句话描述这个方法的作用)
	  * @Title: subString
	  * @Description: TODO
	  * @param @param str 原字符串
	  * @param @param num 汉字数量(如果是num = 5 ,则是截取5个汉字 或者10个字符)
	  * @param @return    设定文件
	  * @return String    返回类型
	  * @throws
	 */
	public static String subString(String str, int num) {	
		String result = subStringByByte(str , num*2);
		if(isEmpty(result)) {
			result = result + "…";
		}
		return result;
	}

	
	/**
	 *  按字节截取字符串
	  * subStringByByte(这里用一句话描述这个方法的作用)
	  * @Title: subStringByByte
	  * @Description: TODO
	  * @param @param str
	  * @param @param len
	  * @param @return    设定文件
	  * @return String    返回类型
	  * @throws
	 */
	public static String subStringByByte(String str, int len) {
		String result = null;
		str = str.trim();
		if (str != null) {
			byte[] a = str.getBytes();
			if (a.length <= len) {
				result = str;
			} else if (len > 0) {
				result = new String(a, 0, len);
				int length = result.length();
				if (str.charAt(length - 1) != result.charAt(length - 1)) {
					if (length < 2) {
						result = null;
					} else {
						result = result.substring(0, length - 1);
					}
				}
				result = result+"…";
			}
		}
		return result;
	}
	
	/**
	 *  返回以,分隔的字符串的第几项 从0开始
	  * @Description: TODO
	  * @param @param str 原字符串
	  * @param @param num 要取得的字符在字符串中的序号，以0开始
	  * @param @param character 分隔符
	  * @return String    返回类型
	  * @throws
	 */
	public static String SplitCommaStr(String str , int num) {
		return getSingleSplitStr(str , num , ",");
	} 
	
	
	
	/**
	 *  返回以某个符号分隔的字符串的第几项 从0开始
	  * @Description: TODO
	  * @param @param str 原字符串
	  * @param @param num 要取得的字符在字符串中的序号，以0开始
	  * @param @param character 分隔符
	  * @return String    返回类型
	  * @throws
	 */
	public static String getSingleSplitStr(String str , int num , String character) {
		if(isEmpty(str) || !str.contains(character)) {
			return str;
		}
		String[] strs = str.split(character);
		if(strs.length < num+1) {
			return "";
		}		
		try{
			return strs[num];
		}catch(Exception e) {
			return "";
		}
	}
	
	
	public static void main(String[] args) {
		String str = "";
		boolean flag = isNumber(str);
		System.out.println(flag);
	}

	public static boolean isNotEmpty(String str) {
		if (str == null || str.trim().equals("")) {
			return false;
		} else {
			return true;
		}
	}

	public static boolean isEmpty(String str) {
		if (str == null || str.trim().equals("")) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * �ϸ��ֻ��ʽ��֤
	 * 
	 * @param str
	 * @return
	 */
	public static boolean isMoblie(String str) {
		if (StrUtil.isEmpty(str))
			return false;
		String regex = "^((\\+{0,1}86){0,1})1[0-9]{10}$";
		boolean bol = str.matches(regex);
		if (bol == true) {
			return true;
		} else {
			return false;
		}
	}

	public static String processMobile(String mobile) {
		String result = "";
		if (!isNotEmpty(mobile)) {
			return result;
		}
		String tempM = mobile.trim();
		tempM.replaceAll("-", "").replaceAll("_", "");
		if (tempM.startsWith("+86")) {
			result = tempM.substring(3);
		} else if (tempM.startsWith("86")) {
			result = tempM.substring(2);
		} else if (tempM.startsWith("+086")) {
			result = tempM.substring(4);
		} else {
			result = mobile;
		}
		return result;

	}

	public static String lineHight(String str) {
		SpannableString s = new SpannableString(str);

		Pattern p = Pattern.compile("abc");

		Matcher m = p.matcher(s);

		while (m.find()) {
			int start = m.start();
			int end = m.end();
			s.setSpan(new ForegroundColorSpan(Color.RED), start, end,
					Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
		}
		return s.toString();
	}

	/**
<<<<<<< StrUtil.java
=======
	 * �滻����
>>>>>>> 1.2
	 * 
	 * @param args
	 */
	public static String replaceLink(String str) {
		if (str == null || str.equals("")) {
			return "";
		}
		String repalceRex = "http://([\\w-]+\\.)+[\\w-]+(/[\\w- ./?%&=]*)?";
		return str.replaceAll(repalceRex, "<a target=_blank href=$0>$0</a>");
	}

	/**
<<<<<<< StrUtil.java
=======
	 * ��ʽ������
>>>>>>> 1.2
	 * 
	 * @param args
	 */
	public static String formatLink(String str) {
		if (str == null || str.equals("")) {
			return "";
		}
		String repalceRex = "(http://|www\\.)([\\w-]+.)+[\\w-]+(/[\\w- .%&=]*)?";

		return str.replaceAll(repalceRex, " $0 ").replaceAll("<br>", "\r\n")
				.replaceAll("<br/>", "\r\n").replaceAll("<br />", "\r\n");
	}

	/**
	 * ת��&quot;&prime;Ϊ" '
	 * 
	 * @param str
	 * @return
	 */
	public static String parseQuotOrPrime(String str) {
		return str.replaceAll("&quot;", "\"").replaceAll("&prime;", "'");
	}

	/**
	 * �Ƿ���HTTP�쳣
	 * 
	 * @param result
	 * @return
	 */
	public static boolean isHttpException(String result) {
		if (isEmpty(result) || "-1".equals(result) || "-2".equals(result)
				|| "-6".equals(result) || "-7".equals(result)) {
			return true;
		}
		return false;
	}

	public static String replaceLineFeed(String content) {
		if (isEmpty(content))
			return "";
		return content.replaceAll("\r\n", "\n");
	}
	public static String getRealMoblie(String str) {
		if (str == null || str.length() < 11) {
			return "";
		}
		StringBuffer strTmp = new StringBuffer();
		if (str.length() == 11) {
			if (isMoblie(str)) {
				return str;
			}
			return "";
		} else {
			for (int i = 0; i < str.length(); i++) {
				if (str.charAt(i) <= '9' && str.charAt(i) >= '0') {
					strTmp.append(str.charAt(i));
				}
			}
		}
		String resultStr = strTmp.toString();
		if (resultStr.startsWith("86")) {
			resultStr = resultStr.substring(2);
		}
		if (resultStr.startsWith("086")) {
			resultStr = resultStr.substring(3);
		}
		if (isMoblie(resultStr)) {
			return resultStr;
		}
		return "";
	}

	public static String FilterHtml(String str) {
		str = str.replaceAll("<(?!br|img)[^>]+>", "").trim();
		return str;
	}

	// ת��
	public static String UnicodeToGBK2(String s) {
		String[] k = s.split(";");
		String rs = "";
		for (int i = 0; i < k.length; i++) {
			String newstr = k[i];
			int strIndex = newstr.indexOf("&#");
			if (strIndex > -1) {
				String kstr = "";
				if (strIndex > 0) {
					kstr = newstr.substring(0, strIndex);
					rs += kstr;
					newstr = newstr.substring(strIndex);
				}
				int m = Integer.parseInt(newstr.replace("&#", ""));
				char c = (char) m;
				rs += c;
			} else {
				rs += k;
			}
		}
		return rs;
	}
	public static String formatTraffic(long trafficSize){
		if(trafficSize>=1024 && trafficSize<1024*1024){
			return (int)trafficSize/1024 + "KB";
		}else if(trafficSize>=1024*1024 && trafficSize<1024*1024*1024){
			return (int)trafficSize/1024/1024 +"MB";
		}else{
			return (int)trafficSize +"B";
		}
	}
	public static String formatNameLen(String name){
		if(isEmpty(name)){
			return "";
		}else if(name.length()<=3){
			return name ;
		}else{
			return name.substring(0,2)+"...";
		}
	}
	public static String parseDistance(double distance){
		String result = "";
			if(distance<1000){
				result =  Math.round(distance/100)+1 +"00米以内";
			}else{
				result = Math.round(distance/1000) +"公里";
			}
		return result ;
	}
	/**
<<<<<<< StrUtil.java
=======
	 * ȥ������
>>>>>>> 1.2
	 * @param str
	 * @return
	 */
	public static String replaceWrap(String str){
		if(isEmpty(str))return str;
		return str.replaceAll("\r\n", "").replaceAll("\r", "").replaceAll("\n", "");
	}
	/**
	 * @return
	 */
	public static String formatPic(String pic ,String domin){
		if(isEmpty(pic))return "";
		if(pic.startsWith("http://")){
			return pic;
		}else {
			return domin + pic.replace("/opt", "");
		}
	}
	public static String formatFileSize(int size){
		String fileSize = "";
		if(size>1000 && size<1000*1000){
			fileSize=size/1000+"K";
		}else if(size>1000*1000){
			fileSize= size/1000000+"M";
		}else if(size <= 0){
			size = 1;
			fileSize = size + "Byte";
		}else{
			fileSize = size + "Byte";
		}
		return fileSize ;
	}
	public static String formatFileSize(long size){
		String fileSize = "";
		if(size>1000 && size<1000*1000){
			fileSize=(Math.round( size /1000)*100)/100+"K";
		}else if(size>1000*1000){
			fileSize=(Math.round( size /1000000)*100)/100+"M";
		}else if(size <= 0){
			size = 1;
			fileSize = size + "Byte";
		}else{
			fileSize = size + "Byte";
		}
		return fileSize ;
	}
	/**
	 * @param optString
	 * @return
	 */
	public static String formatGender(String genderStr) {
		try {
			return formatGender(Integer.parseInt(genderStr));
		} catch (NumberFormatException e) {
			return  "保密" ;
		}
	}
	public static String formatGender(int gender) {
		if(gender  == 1){
			return "男";
		}else if(gender == 2){
			return "女";
		}else{
			return "保密";
		}
	}
}

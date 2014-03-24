package cn.dong.demo.util;


public class FileUtil {

	/**
	 * 获取文件后缀
	 * 
	 * @param filePath
	 * @return
	 */
	public static String getSuffix(String filePath) {
		if (StrUtil.isEmpty(filePath)) {
			return "";
		} else if (filePath.lastIndexOf(".") <= -1) {
			return "";
		} else {
			return filePath.substring(filePath.lastIndexOf("."));
		}
	}

}

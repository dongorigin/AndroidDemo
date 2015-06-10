package cn.dong.demo.config;

import android.os.Environment;

/**
 * 常量
 *
 * @author dong 2014-7-28
 */
public class Constants {
    public static class Path {
        public static final String BASE_DIR = Environment.getExternalStorageDirectory().getPath();
    }

    public static class Extra {
        public static final String IMAGES = "cn.dong.demo.IMAGES";
        public static final String IMAGE_POSITION = "cn.dong.demo.IMAGE_POSITION";
    }

}

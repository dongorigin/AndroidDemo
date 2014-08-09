package cn.dong.demo.util;

import android.content.Context;
import android.widget.Toast;

/**
 * 简易Toast<br>
 * 保持只有一个实例，防止多次调用时依次显示问题
 * 
 * @author dong 2014-8-9
 */
public class T {
    private static Toast sToast;

    public static void shortT(Context context, int msgResId) {
        shortT(context, context.getResources().getString(msgResId));
    }

    public static void shortT(Context context, String msg) {
        if (sToast == null) {
            sToast = Toast.makeText(context.getApplicationContext(), msg, Toast.LENGTH_SHORT);
            sToast.show();
        } else {
            sToast.setText(msg);
            sToast.setDuration(Toast.LENGTH_SHORT);
            sToast.show();
        }
    }

    public static void longT(Context context, int msgResId) {
        longT(context, context.getResources().getString(msgResId));
    }

    public static void longT(Context context, String msg) {
        if (sToast == null) {
            sToast = Toast.makeText(context.getApplicationContext(), msg, Toast.LENGTH_LONG);
            sToast.show();
        } else {
            sToast.setText(msg);
            sToast.setDuration(Toast.LENGTH_LONG);
            sToast.show();
        }
    }
}

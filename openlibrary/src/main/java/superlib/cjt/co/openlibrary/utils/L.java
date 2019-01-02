package superlib.cjt.co.openlibrary.utils;


import android.text.TextUtils;

import com.orhanobut.logger.Logger;


public class L {

    private L() {
        /* cannot be instantiated */
        throw new UnsupportedOperationException("cannot be instantiated");
    }

    public static boolean isDebug = true;
    private static final String TAG = "RYIT:";

    public static void i(String msg) {
        if (isDebug) {
            if (!TextUtils.isEmpty(msg)) {
                Logger.i(msg);
            } else {
                Logger.i("");
            }
        }
    }

    public static void d(String msg) {
        if (isDebug) {
            if (!TextUtils.isEmpty(msg))
                Logger.d(msg);
            else
                Logger.d("");
        }
    }

    public static void e(String msg) {
        if (isDebug) {
            Logger.e(msg);// 打印剩余日志
        } else
            Logger.e("");
    }

    public static void json(String msg) {
        if (isDebug) {

            Logger.json(msg);

        } else
            Logger.json("");
    }

    public static void v(String msg) {
        if (isDebug) {
            if (!TextUtils.isEmpty(msg)) {
                Logger.v(msg);
            } else {
                Logger.v("");
            }
        }
    }

    public static void i(String tag, String msg) {
        if (isDebug)
            Logger.i(msg);
    }

    public static void d(String tag, String msg) {
        if (isDebug)
            Logger.d(msg);
    }

    public static void e(String tag, String msg) {
        if (isDebug)
            Logger.e(msg);
    }

    public static void v(String tag, String msg) {
        if (isDebug)
            Logger.v(msg);
    }
}
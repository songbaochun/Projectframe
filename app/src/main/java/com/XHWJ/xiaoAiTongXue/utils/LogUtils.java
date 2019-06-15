package com.XHWJ.xiaoAiTongXue.utils;

import android.util.Log;

public class LogUtils {
    //todo 上线需要改为false
    private static boolean isDebug = true;//if false the log will be close

    /**
     * print I log
     *
     * @param tag
     * @param msg
     */
    public static void i(String tag, String msg) {
        if (isDebug) {
            Log.i(tag, msg);
        }
    }

    /**
     * print I log
     *
     * @param object
     * @param msg
     */
    public static void i(Object object, String msg) {
        if (isDebug) {
            Log.i(object.getClass().getSimpleName(), msg);
        }
    }

    /**
     * print E log
     *
     * @param tag
     * @param msg
     */
    public static void e(String tag, String msg) {
        if (isDebug) {
            Log.e(tag, msg);
        }
    }

    /**
     * print E log
     *
     * @param object
     * @param msg
     */
    public static void e(Object object, String msg) {
        if (isDebug) {
            Log.e(object.getClass().getSimpleName(), msg);
        }
    }
}

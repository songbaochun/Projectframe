package com.XHWJ.xiaoAiTongXue.utils;

import android.content.Context;
import android.view.Gravity;
import android.widget.Toast;

import com.XHWJ.xiaoAiTongXue.MyApplication;

/**
 * Created by 宋宝春 on 2018/11/6.
 */
public class ToastUtils {

    private ToastUtils() {
        /* cannot be instantiated */
        throw new UnsupportedOperationException("cannot be instantiated");
    }

    public static boolean isShow = true;

    /**
     * 短时间显示Toast
     *
     * @param context
     * @param message
     */
    public static void showShort(Context context, CharSequence message) {
        if (isShow)
            if (context == null) {
                context = MyApplication.getContext();
            }
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
    }
    public static void showShort(CharSequence message) {
        if (isShow)
        Toast.makeText(MyApplication.getContext(), message, Toast.LENGTH_SHORT).show();
    }

    /**
     * 短时间显示Toast
     *
     * @param context
     * @param message
     */
    public static void showShort(Context context, int message) {
        if (isShow)
            if (context == null) {
                context = MyApplication.getContext();
            }
        Toast.makeText(context, message + "", Toast.LENGTH_SHORT).show();
    }

    /**
     * 长时间显示Toast
     *
     * @param context
     * @param message
     */
    public static void showLong(Context context, CharSequence message) {
        if (isShow)
            if (context == null) {
                context = MyApplication.getContext();
            }
        Toast.makeText(context, message, Toast.LENGTH_LONG).show();
    }

    /**
     * 长时间显示Toast
     *
     * @param context
     * @param message
     */
    public static void showLong(Context context, int message) {
        if (isShow)
            if (context == null) {
                context = MyApplication.getContext();
            }
        Toast.makeText(context, message, Toast.LENGTH_LONG).show();
    }

    /**
     * 自定义显示Toast时间
     *
     * @param context
     * @param message
     * @param duration
     */
    public static void show(Context context, CharSequence message, int duration) {
        if (isShow)
            if (context == null) {
                context = MyApplication.getContext();
            }
        Toast.makeText(context, message, duration).show();
    }

    /**
     * 自定义显示Toast时间
     *
     * @param context
     * @param message
     * @param duration
     */
    public static void show(Context context, int message, int duration) {
        if (isShow)
            if (context == null) {
                context = MyApplication.getContext();
            }
        Toast.makeText(MyApplication.getContext(), message, duration).show();
    }

    /**
     * 提示信息
     *
     * @param context
     * @param msg
     */
    public static void showInfo(Context context, String msg) {
        if (context == null) {
            context = MyApplication.getContext();
        }
        Toast toast = Toast.makeText(context, msg, Toast.LENGTH_SHORT);
        toast.setGravity(Gravity.CENTER, 0, 0);
        toast.show();
    }

    /**
     * 提示信息
     *
     * @param context
     */
    public static void showInfo(Context context, int resId) {
        if (context == null) {
            context = MyApplication.getContext();
        }
        Toast toast = Toast.makeText(context, MyApplication.getContext().getResources().getString(resId), Toast.LENGTH_SHORT);
        toast.setGravity(Gravity.CENTER, 0, 0);
        toast.show();
    }
}

package com.XHWJ.xiaoAiTongXue.utils;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Map;

import android.annotation.SuppressLint;

/**
 * 异常处理工具类
 */
public class ExceptionUtil {

    //标签
    public static final String TAG = "Exception";

    //异常信息
    private static String result = null;

    //用来存储设备信息
    private static Map<String, String> infos = new HashMap<String, String>();

    //用于格式化日期,作为日志文件名的一部分
    @SuppressLint("SimpleDateFormat")
    private static DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss");

    public static void handle(Exception e) {
        StringWriter stringWriter = new StringWriter();
        PrintWriter printWriter = new PrintWriter(stringWriter);
        //异常信息转为字符串
        e.printStackTrace(printWriter);
        result = stringWriter.toString();
        LogUtils.i(TAG, result);
    }
}

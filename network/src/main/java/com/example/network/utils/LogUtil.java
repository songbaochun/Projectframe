package com.example.network.utils;

import com.orhanobut.logger.LogLevel;
import com.orhanobut.logger.Logger;


/**
 * @author 张志强
 * @file LogUtil.java
 * @brief 日志工具
 * @date 2017-12-16
 * Copyright (c) 2017
 * All rights reserved.
 */
public class LogUtil {

    private LogUtil() {
    }

    public static void init(boolean isDebug) {
        Logger
                .init(LogUtil.class.getSimpleName())
                .logLevel(isDebug ? LogLevel.FULL : LogLevel.NONE)//Log級別
                .methodCount(1)                 // default 2
                .hideThreadInfo()               // default shown
                .methodOffset(1);               // default 0
    }

    /**
     * @param msg
     */
    public static void i(String msg) {
        Logger.i(msg);
    }

    /**
     * @param tag
     * @param msg
     */
    public static void i(String tag, String msg) {
        Logger
                .t(tag)
                .i(msg);
    }

    /**
     * @param tag
     * @param msg
     * @param args
     */
    public static void i(String tag, String msg, Object... args) {
        Logger
                .t(tag)
                .i(msg, args);
    }

    /**
     * @param msg
     */
    public static void e(String msg) {
        Logger.e(msg);
    }

    /**
     * @param tag
     * @param msg
     */
    public static void e(String tag, String msg) {
        Logger
                .t(tag)
                .e(msg);
    }

    /**
     * @param tag
     * @param msg
     * @param args
     */
    public static void e(String tag, String msg, Object... args) {
        Logger
                .t(tag)
                .e(msg, args);
    }

    /**
     * @param tag
     * @param json
     */
    public static void json(String tag, String json) {
        Logger
                .t(tag)
                .json(json);
    }

    /**
     * @param throwable
     */
    public static void log(Throwable throwable) {
        Logger.log(Logger.ERROR, "Throwable", "message", throwable);
    }
}

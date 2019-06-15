package com.XHWJ.xiaoAiTongXue.utils;

import android.content.res.Resources;

import com.XHWJ.xiaoAiTongXue.MyApplication;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;


/**
 * @version 1.0
 * @file ResourceUtil.java
 * @brief 封装了获取资源的一些方法，方便的获得字符串，颜色等资源
 * @date 2017/6/4
 * Copyright (c) 2017
 * All rights reserved.
 */
public class ResourceUtil {

    /**
     * @return Resources
     * @brief 获得当前APP的Resource的方法
     */
    private static Resources getResource() {
        return MyApplication.getContext().getResources();
    }

    /**
     * 获取字符串
     * @param stringId
     * @return
     */
    public static String getString(int stringId) {
        return getResource().getString(stringId);
    }

    /**
     * 获取字符串，带格式化方法
     * @param stringId
     * @param formatArgs
     * @return
     */
    public static String getString(int stringId, Object... formatArgs) {
        return getResource().getString(stringId, formatArgs);
    }

    /**
     * 获取Dimen值对应的像素值
     * @param dimenId
     * @return
     */
    public static int getDimenPixel(int dimenId) {
        return getResource().getDimensionPixelOffset(dimenId);
    }

    /**
     * 获取颜色
     * @param colorId
     * @return
     */
    public static int getColor(int colorId) {
        return getResource().getColor(colorId);
    }

    /**
     * 获取资产中的字符串
     * @param fileName
     * @return
     */
    public static String getStringFromAssert(String fileName) {
        InputStream in = null;
        try {
            in = getResource().getAssets().open(fileName);
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(in));
            String line = null;
            StringBuilder sb = new StringBuilder();
            do {
                line = bufferedReader.readLine();
                if (line != null) {
                    sb.append(line);
                }
            } while (line != null);
            bufferedReader.close();
            in.close();
            return sb.toString();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (in != null) {
                try {
                    in.close();
                } catch (IOException e) {
                }
            }
        }
        return null;
    }
}

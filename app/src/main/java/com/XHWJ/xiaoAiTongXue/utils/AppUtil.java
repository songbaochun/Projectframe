package com.XHWJ.xiaoAiTongXue.utils;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Build;
import android.provider.Settings;
import android.telephony.TelephonyManager;
import android.text.TextUtils;

import java.util.UUID;

/**
 * @author 张志强
 * @version 1.0
 * @file AppUtil.java
 * @brief
 * @date 2017/12/22
 * Copyright (c) 2017
 * All rights reserved.
 */
public class AppUtil {

    static String TAG = "APP-UTIL";
    private static String uniqueMarker;
    private static String versionName;
    private static int versionCode;

    public static String getVersionName(Context context) {
        try {
            PackageInfo e = context.getPackageManager().getPackageInfo(context.getPackageName(), 0);
            versionName = e.versionName;
            return versionName;
        } catch (PackageManager.NameNotFoundException var2) {
            var2.printStackTrace();
            return null;
        }
    }

    public static int getVersionCode(Context context) {
        try {
            PackageInfo e = context.getPackageManager().getPackageInfo(context.getPackageName(), 0);
            versionCode = e.versionCode;
            return versionCode;
        } catch (PackageManager.NameNotFoundException var2) {
            var2.printStackTrace();
            return -1;
        }
    }

    public static String getPackageName(Context context) {
        try {
            PackageInfo e = context.getPackageManager().getPackageInfo(context.getPackageName(), 0);
            return e.packageName;
        } catch (PackageManager.NameNotFoundException var2) {
            LogUtils.e(TAG, "getPackageName %s" + new Object[]{var2.toString()});
            return "";
        }
    }

    public static String getAppName(Context context) {
        try {
            PackageInfo e = context.getPackageManager().getPackageInfo(context.getPackageName(), 0);
            return e.applicationInfo.name;
        } catch (PackageManager.NameNotFoundException var2) {
            LogUtils.e(TAG, "getAppName %s" + new Object[]{var2.toString()});
            return "";
        }
    }

    /**
     * @return String 设备产商
     * @brief 获得当前设备产商名
     */
    public static String getMaker() {
        return Build.MANUFACTURER.toLowerCase();
    }

    /**
     * 关闭APP
     */
    public static void shutDownApp() {
        android.os.Process.killProcess(android.os.Process.myPid());
    }

    /**
     * 获得元数据方法
     */
    public static String getMetaDataValue(Context ctx, String name) {
        Object value = null;
        PackageManager packageManager = ctx.getApplicationContext()
                .getPackageManager();
        ApplicationInfo applicationInfo;
        try {
            applicationInfo = packageManager.getApplicationInfo(ctx.getApplicationContext().getPackageName(), PackageManager.GET_META_DATA);
            if (applicationInfo != null && applicationInfo.metaData != null) {
                value = applicationInfo.metaData.get(name);
            }
        } catch (PackageManager.NameNotFoundException e) {
            throw new RuntimeException(
                    "Could not read the name in the manifest file.", e);
        }
        if (value == null) {
            throw new RuntimeException("The name '" + name
                    + "' is not defined in the manifest file's meta data.");
        }
        return value.toString();
    }

    /**
     * @return String 当前设备ID
     * @brief 获得设备ID
     */
    public static String getDeviceId(Context ctx) {
        String androidID = Settings.Secure.getString(ctx.getContentResolver(), Settings.Secure.ANDROID_ID);
        String id = androidID + Build.SERIAL;


        return id;

       // return uniqueMarker;
    }

    @SuppressLint("MissingPermission")
    private static String getUniqueMarker(Context ctx) {
        TelephonyManager tm = (TelephonyManager) ctx.getSystemService(Context.TELEPHONY_SERVICE);
        String tmDevice, tmSerial, androidId;
        tmDevice = tm.getDeviceId();
        tmSerial = tm.getSimSerialNumber();
        androidId = Settings.Secure.getString(ctx.getContentResolver(), Settings.Secure.ANDROID_ID);

        UUID deviceUuid = null;
        String uniqueId = null;
        if (!TextUtils.isEmpty(androidId)) {
            deviceUuid = new UUID(androidId.hashCode(),
                    ((long) (tmDevice == null ? 0 : tmDevice.hashCode() << 32) |
                            (tmSerial == null ? 100 : tmSerial.hashCode())));
            uniqueId = deviceUuid.toString();
        } else {
            deviceUuid = UUID.randomUUID();
            uniqueId = deviceUuid.toString();
        }
//        LogUtil.i("debug", "uuid=" + uniqueId);
        return uniqueId;
    }

    /**
     * @return String 系统版本号
     * @brief 获得当前系统版本号
     */
    public static String getOSVersion() {
        return Build.VERSION.RELEASE;
    }
}

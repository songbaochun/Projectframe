package com.XHWJ.xiaoAiTongXue.cache;

import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Serializable;

/**
 * @author Administrator
 * @version 1.0
 * @file ICache.java
 * @brief
 * @date 2018/6/7
 * Copyright (c) 2018,
 * All rights reserved.
 */
public interface ICache {

    void put(String key, String value);

    void put(String key, String value, int saveTime);

    String getAsString(String key);

    void put(String key, JSONObject value);

    void put(String key, JSONObject value, int saveTime);

    JSONObject getAsJSONObject(String key);

    void put(String key, JSONArray value);

    void put(String key, JSONArray value, int saveTime);

    JSONArray getAsJSONArray(String key);

    void put(String key, byte[] value);

    OutputStream put(String key) throws FileNotFoundException;

    InputStream get(String key) throws FileNotFoundException;

    void put(String key, byte[] value, int saveTime);

    byte[] getAsBinary(String key);

    void put(String key, Serializable value);

    void put(String key, Serializable value, int saveTime);

    Object getAsObject(String key);

    void put(String key, Bitmap value);

    void put(String key, Bitmap value, int saveTime);

    Bitmap getAsBitmap(String key);

    void put(String key, Drawable value);

    void put(String key, Drawable value, int saveTime);

    Drawable getAsDrawable(String key);

    File file(String key);

    boolean remove(String key);

    void clear();
}

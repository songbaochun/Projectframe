package com.example.network.handler;

import android.text.TextUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.Interceptor;

/**
 * @author 张志强
 * @version 1.0
 * @file GlobalHttpConfig.java
 * @brief
 * @date 2017/11/24
 * Copyright (c) 2017,
 * All rights reserved.
 */
public class GlobalHttpConfig {

    private List<Interceptor> interceptors;
    private GlobalErrorListener erroListener;
    private GlobalHttpHandler handler;
    private Map<String, String> headers;
    private Map<String, String> params;

    private GlobalHttpConfig(Buidler buidler) {
        this.handler = buidler.handler;
        this.interceptors = buidler.interceptors;
        this.erroListener = buidler.responseErroListener;
        this.headers = buidler.headers;
        this.params = buidler.params;
    }

    public static Buidler buidler() {
        return new Buidler();
    }

    public static final class Buidler {
        private GlobalHttpHandler handler;
        private GlobalErrorListener responseErroListener;
        private List<Interceptor> interceptors = new ArrayList<>();
        //公共头
        private Map<String, String> headers = new HashMap<>();
        //公共参数
        private Map<String, String> params = new HashMap<>();


        private Buidler() {
        }

        public Buidler globeHttpHandler(GlobalHttpHandler handler) {//用来处理http响应结果
            this.handler = handler;
            return this;
        }

        public Buidler addInterceptor(Interceptor interceptor) {//动态添加任意个interceptor
            this.interceptors.add(interceptor);
            return this;
        }


        public Buidler responseErroListener(GlobalErrorListener listener) {//处理所有Rxjava的onError逻辑
            this.responseErroListener = listener;
            return this;
        }

        public Map<String, String> getHeaders() {
            return headers;
        }

        public Buidler addCommonHeader(String key, String value) {
            if (!TextUtils.isEmpty(key) && !TextUtils.isEmpty(value)) {
                headers.put(key, value);
            }
            return this;
        }

        public Map<String, String> getParams() {
            return params;
        }

        public Buidler addCommonParam(String key, String value) {
            if (!TextUtils.isEmpty(key) && !TextUtils.isEmpty(value)) {
                params.put(key, value);
            }
            return this;
        }

        public GlobalHttpConfig build() {
            return new GlobalHttpConfig(this);
        }
    }


    public List<Interceptor> getInterceptors() {
        return interceptors;
    }

    public GlobalErrorListener getErroListener() {
        return erroListener;
    }

    public GlobalHttpHandler getHandler() {
        return handler;
    }

    public Map<String, String> getHeaders() {
        return headers;
    }

    public Map<String, String> getParams() {
        return params;
    }
}

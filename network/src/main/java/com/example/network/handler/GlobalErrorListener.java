package com.example.network.handler;

/**
 * @author 张志强
 * @version 1.0
 * @file GlobalErrorListener.java
 * @brief 全局错误监听
 * @date 2017/11/23
 * Copyright (c) 2017, 神州元元
 * All rights reserved.
 */
public interface GlobalErrorListener {

    void handleResponseError(Throwable e);
}
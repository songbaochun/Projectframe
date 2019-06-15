package com.example.network.model;

import java.io.Serializable;

/**
 * @author jhb
 * @version 1.0
 * @file BaseResponse.java
 * @brief
 * @date 2017/11/23
 * Copyright (c) 2017, 神州元元
 * All rights reserved.
 */
public class BaseResponse<T> implements Serializable {

    public static final String NONE_STATUS = "-1";
    public static final String RESULT_OK = "00000";
    public static final String TOKEN_INVALID = "1001";//单点登录
    public static final String TOKEN_EXPIRED = "1000";//TOKEN过期
    public static final String FORCE_UPGRADE = "103";//强制升级
    public static final String INVALIDATE_AUTH = "20005";//授权失败
    public static final String ERROR_MESSAGE = "msg";

    public String status;
    public String message;
    public T data;

    private static final long serialVersionUID = 5213230387175987833L;

    @Override
    public String toString() {
        return "LzyResponse{\n" +//
                "\tstatus=" + status + "\n" +//
                "\tmessage='" + message + "\'\n" +//
                "\tdata=" + data + "\n" +//
                '}';
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}

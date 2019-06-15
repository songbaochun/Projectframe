package com.example.network.exception;

/**
 * @author jianghongbo
 * @version 1.0
 * @file ResultException.java
 * @brief 服务器返回结果异常
 * @date 2017/11/21
 * Copyright (c) 2017, 神州元元
 * All rights reserved.
 */
public class ResultException extends RuntimeException {

    private String status;
    private String errorMessage;

    public ResultException(String message) {
        super(message);
    }


    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}

package com.example.network.callback;

import android.text.TextUtils;

import com.example.network.RequestHelper;
import com.example.network.exception.ResultException;
import com.example.network.model.BaseResponse;
import com.example.network.utils.Convert;
import com.google.gson.stream.JsonReader;
import com.lzy.okgo.callback.AbsCallback;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

import okhttp3.Response;

/**
 * @author jhb
 * @version 1.0
 * @file RequestCallback.java
 * @brief 请求回调
 * @date 2017/11/23
 * Copyright (c) 2017, 神州元元
 * All rights reserved.
 */
public abstract class RequestCallback<T> extends AbsCallback<T> {

    @Override
    public void onError(com.lzy.okgo.model.Response<T> response) {
        super.onError(response);
        if (RequestHelper.getInstance().getErrorListener() != null)
            RequestHelper.getInstance().getErrorListener().handleResponseError(response.getException());
    }

    @Override
    public T convertResponse(Response response) throws Throwable {
        //以下代码是通过泛型解析实际参数,泛型必须传
        Type genType = getClass().getGenericSuperclass();
        Type[] params = ((ParameterizedType) genType).getActualTypeArguments();
        Type type = params[0];
        if (!(type instanceof ParameterizedType)) throw new IllegalStateException("没有填写泛型参数");

        JsonReader jsonReader = new JsonReader(response.body().charStream());
        Type rawType = ((ParameterizedType) type).getRawType();
        if (rawType == BaseResponse.class) {
            BaseResponse baseResponse = Convert.fromJson(jsonReader, type);
            if (baseResponse != null) {
                if (TextUtils.equals(baseResponse.status, BaseResponse.RESULT_OK)) {
                    response.close();
                    //noinspection unchecked
                    return (T) baseResponse;
                } else if (!TextUtils.isEmpty(baseResponse.getStatus()) && !TextUtils.isEmpty(baseResponse.getMessage())) {
                    response.close();
                    ResultException exception = new ResultException("服务端接口错误");
                    exception.setStatus(baseResponse.getStatus());
                    exception.setErrorMessage(baseResponse.getMessage());
                    throw exception;
                } else {
                    ResultException exception = new ResultException("服务端数据返回无效");
                    exception.setStatus(BaseResponse.NONE_STATUS);
                    exception.setErrorMessage("服务端数据返回无效");
                    throw exception;
                }
            } else {
                ResultException exception = new ResultException("数据解析错误");
                exception.setStatus(BaseResponse.NONE_STATUS);
                exception.setErrorMessage("数据解析错误");
                throw exception;
            }
        } else {
            response.close();
            throw new IllegalStateException("基类错误无法解析!");
        }
    }
}

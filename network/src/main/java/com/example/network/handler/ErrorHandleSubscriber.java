package com.example.network.handler;


import com.example.network.RequestHelper;

import rx.Subscriber;

/**
 * Created by jess on 9/2/16 14:41
 * Contact with jess.yan.effort@gmail.com
 */

public abstract class ErrorHandleSubscriber<T> extends Subscriber<T> {


    public ErrorHandleSubscriber() {
    }

    @Override
    public void onCompleted() {
    }

    @Override
    public void onError(Throwable e) {
        e.printStackTrace();
        if (RequestHelper.getInstance().getErrorListener() != null)
            RequestHelper.getInstance().getErrorListener().handleResponseError(e);
    }
}


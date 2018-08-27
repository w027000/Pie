package com.pie.core.net.subscriber;

import com.pie.core.net.exception.PHttpException;
import com.pie.core.net.model.HttpCode;

import io.reactivex.observers.DisposableObserver;

/**
 * @author:zjh
 * @date:2018/8/27
 * @Description：API统一订阅者
 */
public abstract class ApiSubscriber extends DisposableObserver {

    @Override
    public void onError(Throwable e) {
        if (e instanceof PHttpException){
            onError((PHttpException)e);
        }else {
            onError(new PHttpException(e, HttpCode.Request.UNKNOWN));
        }
    }


    protected abstract void onError(PHttpException e);

}

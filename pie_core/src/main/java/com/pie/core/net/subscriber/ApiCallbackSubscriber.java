package com.pie.core.net.subscriber;

import com.pie.core.net.callback.ICallback;
import com.pie.core.net.exception.PHttpException;

import io.reactivex.annotations.NonNull;

/**
 * @author:zjh
 * @date:2018/8/27
 * @Description：包含回调的订阅者，如果订阅这个，上层在不使用订阅者的情况下可获得回调
 */
public class ApiCallbackSubscriber<T> extends ApiSubscriber{

    private ICallback<T> mCallBack;
    private T mData;

    public ApiCallbackSubscriber(@NonNull ICallback<T> callback){
        mCallBack = callback;
    }

    @Override
    protected void onError(PHttpException e) {
        if (e == null){
            mCallBack.onFail(-1, "This ApiException is Null.");
            return;
        }
        mCallBack.onFail(e.getCode(), e.getMessage());
    }

    @Override
    public void onNext(Object o) {

    }

    @Override
    public void onComplete() {

    }
}

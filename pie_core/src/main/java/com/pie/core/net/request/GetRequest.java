package com.pie.core.net.request;

import com.pie.core.net.callback.ICallback;
import com.pie.core.net.core.PiHttpCreator;
import com.pie.core.net.core.RequestManage;
import com.pie.core.net.model.CacheResult;
import com.pie.core.net.subscriber.ApiCallbackSubscriber;

import java.lang.reflect.Type;

import io.reactivex.Observable;
import io.reactivex.observers.DisposableObserver;
import okhttp3.ResponseBody;

/**
 * @author:zjh
 * @date:2018/8/28
 * @Description：Get请求
 */
public final class GetRequest extends BaseHttpRequest<GetRequest>{


    public GetRequest(String url) {
        super(url);
    }

    @Override
    protected <T> Observable<T> execute(Type type) {
        Observable<ResponseBody> observable = mApiService.get(mUrl,mHttpParams.getParams());
        return observable.compose(this.<T>noTransformer(type));
    }

    @Override
    protected <T> Observable<CacheResult<T>> cacheExecute(Type type) {
        Observable<T> observable = execute(type);
        return observable.compose(PiHttpCreator.getCacheManage().<T>transformer(mCacheMode, type));
    }

    @Override
    protected <T> void execute(ICallback<T> callback) {
        DisposableObserver disposableObserver = new ApiCallbackSubscriber(callback);
        if (mTag != null){
            RequestManage.getInstance().add(mTag,disposableObserver);
        }

        Observable<CacheResult<T>> cacheResultObservable;
        Type type;
        if (mIsLocalCache){
            type = getSubType(callback);
            cacheResultObservable = cacheExecute(type);
            cacheResultObservable.subscribe(disposableObserver);
        } else {
            type = getType(callback);
            cacheResultObservable = execute(type);
            cacheResultObservable.subscribe(disposableObserver);
        }

    }

}

package com.pie.core.net.request;

import android.text.TextUtils;

import com.pie.core.net.api.ApiService;
import com.pie.core.net.callback.ICallback;
import com.pie.core.net.core.PiHttpCreator;
import com.pie.core.net.func.ApiFunc;
import com.pie.core.net.func.ApiRetryFunc;
import com.pie.core.net.model.CacheMode;
import com.pie.core.net.model.CacheResult;
import com.pie.core.net.model.HttpParams;

import java.lang.reflect.Type;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.ObservableTransformer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import okhttp3.ResponseBody;

/**
 * @author:zjh
 * @date:2018/8/28
 * @Description：通用的请求基类
 */
public abstract class BaseHttpRequest<T extends BaseHttpRequest> extends BaseRequest<T>{

    protected String mUrl;//url
    protected ApiService mApiService;//通用接口服务

    protected HttpParams mHttpParams;//通用请求参数

    protected int mRetryDelayMillis;//请求失败重试间隔时间
    protected int mRetryCount;//重试次数

    protected boolean mIsLocalCache;//是否使用本地缓存
    protected CacheMode mCacheMode;//本地缓存类型

    private BaseHttpRequest(){
        mHttpParams = new HttpParams();
    }

    public BaseHttpRequest(String url) {
        this();
        if (!TextUtils.isEmpty(url)) {
            this.mUrl = url;
        }
        mApiService = PiHttpCreator.getApiService();
    }

    protected abstract <T> Observable<T> execute(Type type);

    protected abstract <T> Observable<CacheResult<T>> cacheExecute(Type type);

    protected abstract <T> void execute(ICallback<T> callback);

    public <T> void request(ICallback<T> callback){
        execute(callback);
    }

    protected <T> ObservableTransformer<ResponseBody,T> noTransformer(final Type type){
        return new ObservableTransformer<ResponseBody, T>() {
            @Override
            public ObservableSource<T> apply(Observable<ResponseBody> apiResultObservable) {
                return apiResultObservable
                        .subscribeOn(Schedulers.io())
                        .unsubscribeOn(Schedulers.io())
                        .map(new ApiFunc<T>(type))
                        .observeOn(AndroidSchedulers.mainThread())
                        .retryWhen(new ApiRetryFunc(mRetryCount, mRetryDelayMillis));
            }
        };
    }


    /**
     * 设置是否进行本地缓存
     *
     * @param isLocalCache
     * @return
     */
    public T withLocalCache(boolean isLocalCache) {
        this.mIsLocalCache = isLocalCache;
        return (T) this;
    }

    /**
     * 设置本地缓存类型
     *
     * @param cacheMode
     * @return
     */
    public T withCacheMode(CacheMode cacheMode) {
        this.mCacheMode = cacheMode;
        return (T) this;
    }
}

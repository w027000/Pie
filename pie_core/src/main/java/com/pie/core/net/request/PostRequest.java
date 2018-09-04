package com.pie.core.net.request;

import com.pie.core.net.callback.ICallback;
import com.pie.core.net.core.PiHttpCreator;
import com.pie.core.net.core.RequestManage;
import com.pie.core.net.model.CacheResult;
import com.pie.core.net.subscriber.ApiCallbackSubscriber;

import java.lang.reflect.Type;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;

import io.reactivex.Observable;
import io.reactivex.observers.DisposableObserver;
import okhttp3.ResponseBody;

/**
 * @author:zjh
 * @date:2018/9/3
 * @Description：Post请求
 */
public class PostRequest extends BaseHttpRequest<PostRequest>{

    public PostRequest(String url) {
        super(url);
    }

    @Override
    protected <T> Observable<T> execute(Type type) {
        Map<String,Object> forms = getForms();
        Observable<ResponseBody> observable = mApiService.post(mUrl,forms);
        return observable.compose(this.<T>noTransformer(type));
    }


    private Map<String,Object> getForms(){
        if (mHttpParams != null ){
            Map<String,Object> forms = new LinkedHashMap<>();
            Map<String,String> map = mHttpParams.getParams();
            Iterator<Map.Entry<String, String>> entryIterator = map.entrySet().iterator();
            while (entryIterator.hasNext()){
                Map.Entry<String,String> entry = entryIterator.next();
                forms.put(entry.getKey(),entry.getValue());
            }
            return forms;
        }
        return new LinkedHashMap<>();
    }



    @Override
    protected <T> Observable<CacheResult<T>> cacheExecute(Type type) {
        Observable<T> observable = execute(type);
        return observable.compose(PiHttpCreator.getCacheManage().<T>transformer(mCacheMode, type));
    }

    @Override
    protected <T> void execute(ICallback<T> callback) {
        DisposableObserver disposableObserver = new ApiCallbackSubscriber(callback);
        if (mTag != null) {
            RequestManage.getInstance().add(mTag,disposableObserver);
        }
        if (!mIsLocalCache){
            Type type = getType(callback);
            Observable<T> observable = execute(type);
            observable.subscribe(disposableObserver);
        }else{
            Type type = getSubType(callback);
            Observable<CacheResult<T>> observable = cacheExecute(type);
            observable.subscribe(disposableObserver);
        }
    }

    public final PostRequest withParams(String key,String value){
        mHttpParams.addParam(key,value);
        return this;
    }

}

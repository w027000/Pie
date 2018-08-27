package com.pie.core.net.request;

import com.pie.core.net.config.HttpConfigurator;
import com.pie.core.net.model.HttpHeaders;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import okhttp3.Interceptor;
import retrofit2.Retrofit;
import retrofit2.http.Url;

/**
 * @author:zjh
 * @date:2018/8/27
 * @Description：请求基类
 */
public abstract class BaseRequest <T extends BaseRequest>{

    protected HttpConfigurator mHttpConfigurator;//全局配置
    protected Retrofit mRetrofit;//Retrofit对象
    protected HttpHeaders mHeaders = new HttpHeaders();//请求头
    protected String mBaseUrl;//基础域名
    protected Object mTag;//请求标签
    protected long mReadTimeOut;//读取超时时间
    protected long mWriteTimeOut;//写入超时时间
    protected long mConnectTimeOut;//连接超时时间
    protected boolean mIsHttpCache;//是否使用Http缓存

    protected List<Interceptor> mRequestInterceptors = new ArrayList<>();//局部请求的拦截器


    public T withBaseUrl(String url){
        mBaseUrl = url;
        return (T) this;
    }

    public T withAddHeader(Map<String,String> map){
        mHeaders.headersMap.putAll(map);
        return (T) this;
    }


    public T withAddHeader(String key,String value){
        mHeaders.headersMap.put(key,value);
        return (T) this;
    }

    public T withAddHeader(HttpHeaders header){
        mHeaders = header;
        return (T) this;
    }

    public T withTag(Object tag) {
        this.mTag = tag;
        return (T) this;
    }

    public T withWriteTimeOut(int writeTimeOut) {
        this.mWriteTimeOut = writeTimeOut;
        return (T) this;
    }

    public T withConnectTimeOut(int connectTimeOut) {
        this.mConnectTimeOut = connectTimeOut;
        return (T) this;
    }

    public T withReadTimeOut(int readTimeOut) {
        this.mReadTimeOut = readTimeOut;
        return (T) this;
    }

    public T withIsHttpCache(boolean isHttpCache) {
        this.mIsHttpCache = isHttpCache;
        return (T) this;
    }






}

package com.pie.core.net.request;

import com.pie.core.net.model.HttpHeaders;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Map;

/**
 * @author:zjh
 * @date:2018/8/27
 * @Description：请求基类
 */
public abstract class BaseRequest <T extends BaseRequest>{

    protected HttpHeaders mHeaders = new HttpHeaders();//请求头
    protected String mBaseUrl;//基础域名
    protected Object mTag;//请求标签

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


    /**
     * 获取第一级type
     * @param t
     * @param <T>
     * @return
     */
    protected <T> Type getType(T t) {
        Type genType = t.getClass().getGenericSuperclass();
        Type[] params = ((ParameterizedType) genType).getActualTypeArguments();
        Type type = params[0];
        Type finalNeedType;
        if (params.length > 1) {
            if (!(type instanceof ParameterizedType)) throw new IllegalStateException("没有填写泛型参数");
            finalNeedType = ((ParameterizedType) type).getActualTypeArguments()[0];
        } else {
            finalNeedType = type;
        }
        return finalNeedType;
    }

    /**
     * 获取次一级type(如果有)
     * @param t
     * @param <T>
     * @return
     */
    protected <T> Type getSubType(T t) {
        Type genType = t.getClass().getGenericSuperclass();
        Type[] params = ((ParameterizedType) genType).getActualTypeArguments();
        Type type = params[0];
        Type finalNeedType;
        if (params.length > 1) {
            if (!(type instanceof ParameterizedType)) throw new IllegalStateException("没有填写泛型参数");
            finalNeedType = ((ParameterizedType) type).getActualTypeArguments()[0];
        } else {
            if (type instanceof ParameterizedType) {
                finalNeedType = ((ParameterizedType) type).getActualTypeArguments()[0];
            } else {
                finalNeedType = type;
            }
        }
        return finalNeedType;
    }




}

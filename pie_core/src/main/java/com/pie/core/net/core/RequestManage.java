package com.pie.core.net.core;

import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import io.reactivex.disposables.Disposable;

/**
 * @author:zjh
 * @date:2018/8/28
 * @Description： 请求管理，方便中途取消请求
 */
public class RequestManage {

    private ConcurrentHashMap<Object, Disposable> mArrayMaps;

    private RequestManage(){
        mArrayMaps = new ConcurrentHashMap<>();
    }


    private final static class RequestMessageHolder{
        final static RequestManage INSTANCE = new RequestManage();
    }

    public static RequestManage getInstance(){
        return RequestMessageHolder.INSTANCE;
    }


    public void add(Object tag, Disposable disposable){
        mArrayMaps.put(tag,disposable);
    }

    public void remove(Object tag){
        if (!mArrayMaps.isEmpty()){
            mArrayMaps.remove(tag);
        }
    }

    public void removeAll(){
        if (!mArrayMaps.isEmpty()){
            mArrayMaps.clear();
        }
    }

    public void cancel(Object tag){
        if (mArrayMaps.isEmpty()){
            return;
        }
        if (mArrayMaps.get(tag) == null){
            return;
        }
        if (!mArrayMaps.get(tag).isDisposed()){
            mArrayMaps.get(tag).dispose();
            remove(tag);
        }
    }

    public void cancelAll(){
        if (mArrayMaps.isEmpty()){
            return;
        }
        Set<Object> keys = mArrayMaps.keySet();
        for (Object obj : keys){
            cancel(obj);
        }
    }


}

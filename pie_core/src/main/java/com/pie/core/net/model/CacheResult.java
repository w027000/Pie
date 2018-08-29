package com.pie.core.net.model;

import java.io.Serializable;

/**
 * @author:zjh
 * @date:2018/8/28
 * @Description： 设置缓存后的数据返回结果，主要增加是否是缓存数据的区分
 */
public class CacheResult<T> implements Serializable {

    private boolean mIsCache;
    private T mCacheData;

    public CacheResult(boolean isCache, T cacheData) {
        this.mIsCache = isCache;
        this.mCacheData = cacheData;
    }

    public boolean isCache(){
        return this.mIsCache;
    }

    public T getCacheData(){
        return mCacheData;
    }

    public CacheResult setIsCache(boolean iscache){
        mIsCache = iscache;
        return this;
    }


    public CacheResult setCacheData(T cacheData){
        mCacheData = cacheData;
        return this;
    }

    @Override
    public String toString() {
        return "CacheResult{" +
                "mIsCache=" + mIsCache +
                ", mCacheData=" + mCacheData +
                '}';
    }
}

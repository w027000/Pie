package com.pie.core.net.strategy;

import com.pie.core.net.core.CacheManage;
import com.pie.core.net.model.CacheResult;

import java.lang.reflect.Type;

import io.reactivex.Observable;
import io.reactivex.functions.Predicate;

/**
 * @author:zjh
 * @date:2018/9/3
 * @Description：缓存策略--先使用缓存，不管是否存在，仍然请求网络，会回调两次
 */
public class CacheAndRemoteStrategy<T> extends CacheStrategy<T> {
    @Override
    public <T> Observable<CacheResult<T>> execute(CacheManage cacheManage, String cacheKey, Observable<T> source, final Type type) {
        Observable<CacheResult<T>> cache = loadCache(cacheManage, cacheKey, type);
        final Observable<CacheResult<T>> remote = loadRemote(cacheManage, cacheKey, source);
        return Observable.concat(cache, remote).filter(new Predicate<CacheResult<T>>() {
            @Override
            public boolean test(CacheResult<T> tCacheResult) throws Exception {
                return tCacheResult != null && tCacheResult.getCacheData() != null;
            }
        });
    }
}

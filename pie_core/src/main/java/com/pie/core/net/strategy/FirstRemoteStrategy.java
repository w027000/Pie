package com.pie.core.net.strategy;

import com.pie.core.net.core.CacheManage;
import com.pie.core.net.model.CacheResult;

import java.lang.reflect.Type;
import java.util.Arrays;

import io.reactivex.Observable;
import io.reactivex.functions.Function;
import io.reactivex.functions.Predicate;

/**
 * @author:zjh
 * @date:2018/8/30
 * @Description：先请求网络，请求网络失败后再加载缓存
 */
public class FirstRemoteStrategy<T> extends CacheStrategy<T>{
    @Override
    public <T> Observable<CacheResult<T>> execute(CacheManage cacheManage, String cacheKey, Observable<T> source, Type type) {
        Observable<CacheResult<T>> remote = loadRemote(cacheManage, cacheKey, source);
        remote.onErrorReturn(new Function<Throwable, CacheResult<T>>() {
            @Override
            public CacheResult<T> apply(Throwable throwable) throws Exception {
                return null;
            }
        });
        Observable<CacheResult<T>> cache = loadCache(cacheManage, cacheKey, type);
        return Observable.concatDelayError(Arrays.asList(remote,cache)).filter(new Predicate<CacheResult<T>>() {
            @Override
            public boolean test(CacheResult<T> tCacheResult) throws Exception {
                return tCacheResult != null && tCacheResult.getCacheData() != null;
            }
        }).firstElement().toObservable();
    }
}

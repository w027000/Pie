package com.pie.core.net.strategy;

import com.google.gson.Gson;
import com.pie.core.net.core.CacheManage;
import com.pie.core.net.model.CacheResult;
import com.pie.core.util.log.PLog;

import java.lang.reflect.Type;

import io.reactivex.Observable;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.functions.Predicate;
import io.reactivex.schedulers.Schedulers;

/**
 * @author:zjh
 * @date:2018/8/29
 * @Description：缓存策略
 */
public abstract class CacheStrategy<T> implements ICacheStrategy<T>  {

    <T> Observable<CacheResult<T>> loadCache(final CacheManage cacheManage, final String key, final Type type) {
        return cacheManage.<T>get(key).filter(new Predicate<String>() {
            @Override
            public boolean test(String s) throws Exception {
                return s != null;
            }
        }).map(new Function<String, CacheResult<T>>() {
            @Override
            public CacheResult<T> apply(String s) throws Exception {
                T t = new Gson().fromJson(s, type);
                PLog.i("loadCache result=" + t);
                return new CacheResult<>(true, t);
            }
        });
    }

    <T> Observable<CacheResult<T>> loadRemote(final CacheManage cacheManage, final String key, Observable<T> source) {
        return source.map(new Function<T, CacheResult<T>>() {
            @Override
            public CacheResult<T> apply(T t) throws Exception {
                PLog.i("loadRemote result=" + t);
                cacheManage.put(key, t).subscribeOn(Schedulers.io()).subscribe(new Consumer<Boolean>() {
                    @Override
                    public void accept(Boolean status) throws Exception {
                        PLog.i("save status => " + status);
                    }
                });
                return new CacheResult<>(false, t);
            }
        });
    }

}

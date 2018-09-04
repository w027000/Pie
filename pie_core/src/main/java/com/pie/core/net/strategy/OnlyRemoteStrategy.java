package com.pie.core.net.strategy;

import com.pie.core.net.core.CacheManage;
import com.pie.core.net.model.CacheResult;

import java.lang.reflect.Type;

import io.reactivex.Observable;

/**
 * @author:zjh
 * @date:2018/9/3
 * @Description：缓存策略--只取网络
 */
public class OnlyRemoteStrategy<T> extends CacheStrategy<T>{

    @Override
    public <T> Observable<CacheResult<T>> execute(CacheManage cacheManage, String cacheKey, Observable<T> source, Type type) {
        return loadRemote(cacheManage, cacheKey, source);
    }

}

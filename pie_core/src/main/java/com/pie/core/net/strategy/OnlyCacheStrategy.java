package com.pie.core.net.strategy;


import com.pie.core.net.core.CacheManage;
import com.pie.core.net.model.CacheResult;

import java.lang.reflect.Type;

import io.reactivex.Observable;

/**
 * @Description: 缓存策略--只取缓存
 * @author: <a href="http://www.xiaoyaoyou1212.com">DAWI</a>
 * @date: 16/12/31 14:29.
 */
public class OnlyCacheStrategy<T> extends CacheStrategy<T> {
    @Override
    public <T> Observable<CacheResult<T>> execute(CacheManage cacheManage, String cacheKey, Observable<T> source, Type type) {
        return loadCache(cacheManage, cacheKey, type);
    }
}

package com.pie.core.net.strategy;

import com.pie.core.net.core.CacheManage;
import com.pie.core.net.model.CacheResult;

import java.lang.reflect.Type;

import io.reactivex.Observable;

/**
 * @author:zjh
 * @date:2018/8/29
 * @Description：缓存策略接口
 */
public interface ICacheStrategy<T> {

    <T> Observable<CacheResult<T>> execute(CacheManage cacheManage, String cacheKey, Observable<T> source, Type type);

}

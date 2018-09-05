package com.pie.core.net.cache;

/**
 * @author:zjh
 * @date:2018/8/29
 * @Description：缓存接口
 */
public interface ICache {

    void put(String key,Object value);

    Object get(String key);

    boolean isContains(String key);

    void remove(String key);

    void clear();

}

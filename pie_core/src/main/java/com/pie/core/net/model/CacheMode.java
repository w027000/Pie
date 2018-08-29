package com.pie.core.net.model;

/**
 * @author:zjh
 * @date:2018/8/29
 * @Description：缓存模式
 */
public enum  CacheMode {

    /**
     *先请求网络，请求网络失败后再加载缓存
     */
    CACHE_MODE_FIRST_REMOTE("FirstRemoteStrategy"),

    /**
     *先加载缓存，缓存没有再去请求网络
     */
    CACHE_MODE_FIRST_CACHE("FirstCacheStrategy"),

    /**
     * 仅加载网络，但数据依然会被缓存
     */
    CACHE_MODE_ONLY_REMOTE("OnlyRemoteStrategy"),

    /***
     * 只读取缓存
     */
    CACHE_MODE_ONLY_CACHE("OnlyCacheStrategy"),

    /**
     * 先使用缓存，不管是否存在，仍然请求网络，会回调两次
     */
    CACHE_MODE_CACHE_AND_REMOTE("CacheAndRemoteStrategy");



    private String className;


    CacheMode(String className) {
        this.className = className;
    }

    public String getClassName() {
        return className;
    }
}

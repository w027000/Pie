package com.pie.core.app;

public enum  ConfigKeys {

    //全局context
    KEY_APPLACATION_CONTEXT,
    //全局配置是否已经OK
    KEY_CONFIG_READY_OK,
    //logger的tag
    KEY_LOGGER_TAG,
    //logger是否显示
    KEY_LOGGER_VISIBLE,
    //OkHttp的拦截器
    KEY_HTTP_INTERCEPTOR,
    //api host
    KEY_HTTP_API_HOST,
    //超时时间
    KEY_HTTP_DEFAULT_SECONDS,
    //HTTP缓存key
    KEY_HTTP_CACHE_KEY,
    //全局超时重试次数
    KEY_HTTP_RETRY_COUNT,
    //全局缓存时间
    KEY_CACHE_NEVER_EXPIRE,

}

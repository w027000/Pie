package com.pie.core.net.config;

import java.util.concurrent.TimeUnit;

/**
 * @author:zjh
 * @date:2018/8/29
 * @Description：http默认配置参数
 */
public final class HttpDefaultConfigs {

    //超时时间(单位：秒)
    public static final int HTTP_DEFAULT_CONNECT_TIME = 60;
    //read time(单位：秒)
    public static final int HTTP_DEFAULT_READ_TIME = 60;
    //write time(单位：秒)
    public static final int HTTP_DEFAULT_WRITE_TIME = 60;
    //http 重连次数
    public static final int HTTP_DEFAULT_RETRY_COUNT = 3;
    //缓存时间
    public static final long HTTP_DEFAULT_CACHE_NEVER_EXPIRE = -1;
    //cache key
    public static final String HTTP_DEFAULT_CACHE_KEY = "~!@#$%^&*";


}

package com.pie.core.app;

import android.content.Context;
import android.support.annotation.Nullable;

import com.orhanobut.logger.AndroidLogAdapter;
import com.orhanobut.logger.FormatStrategy;
import com.orhanobut.logger.Logger;
import com.orhanobut.logger.PrettyFormatStrategy;

import java.util.ArrayList;
import java.util.HashMap;

import okhttp3.Interceptor;

/***
 * 全局配置初始化类
 */
public final class Configurator {


    private static final HashMap<Object,Object> PIE_CONFIGS = new HashMap<>();
    private static final ArrayList<Interceptor> INTERCEPTORS = new ArrayList<>();

    private Configurator(){
        PIE_CONFIGS.put(ConfigKeys.KEY_CONFIG_READY_OK,false);
    }

    /***
     * pie库全局初始化完成
     */
    public final void configure() {
        PIE_CONFIGS.put(ConfigKeys.KEY_CONFIG_READY_OK,true);
        initLogger();
    }

    private static class ConfigHolder{
        private static final Configurator INSTANCE = new Configurator();
    }


    public static Configurator getInstance(){
        return ConfigHolder.INSTANCE;
    }


    /**配置全局context*/
    public Configurator withApplication(Context context){
        PIE_CONFIGS.put(ConfigKeys.KEY_APPLACATION_CONTEXT,context);
        return this;
    }


    /**配置全局log信息*/
    public Configurator withLogger(String tag,boolean isShow){
        PIE_CONFIGS.put(ConfigKeys.KEY_LOGGER_TAG,tag);
        PIE_CONFIGS.put(ConfigKeys.KEY_LOGGER_VISIBLE,isShow);
        return this;
    }

    /**配置全局host*/
    public Configurator withHttpApiHost(String apiHost){
        PIE_CONFIGS.put(ConfigKeys.KEY_HTTP_API_HOST,apiHost);
        return this;
    }

    /**添加OkHttp一个拦截器*/
    public final Configurator withHttpInterceptor(Interceptor interceptor) {
        INTERCEPTORS.add(interceptor);
        PIE_CONFIGS.put(ConfigKeys.KEY_HTTP_INTERCEPTOR, INTERCEPTORS);
        return this;
    }

    /**添加OkHttp一组拦截器*/
    public final Configurator withHttpInterceptors(ArrayList<Interceptor> interceptors) {
        INTERCEPTORS.addAll(interceptors);
        PIE_CONFIGS.put(ConfigKeys.KEY_HTTP_INTERCEPTOR, INTERCEPTORS);
        return this;
    }

    /**超时时间*/
    public final Configurator withHttpDefaultMilliseconds(long defaultMilliseconds){
        PIE_CONFIGS.put(ConfigKeys.KEY_HTTP_DEFAULT_SECONDS,defaultMilliseconds);
        return this;
    }

    /**全局超时重试次数*/
    public final Configurator withHttpRetryCount(int retryCount){
        PIE_CONFIGS.put(ConfigKeys.KEY_HTTP_RETRY_COUNT,retryCount);
        return this;
    }

    /**缓存时间*/
    public final Configurator withHttpCacheNeverExpire(long cacheNeverExpire){
        PIE_CONFIGS.put(ConfigKeys.KEY_CACHE_NEVER_EXPIRE,cacheNeverExpire);
        return this;
    }

    /**缓存KEY*/
    public final Configurator withHttpCacheKey(String cacheKey){
        PIE_CONFIGS.put(ConfigKeys.KEY_HTTP_CACHE_KEY,cacheKey);
        return this;
    }


    public final <T> T getConfigurationValue(Object key){
        checkConfiguration();
        final Object value = PIE_CONFIGS.get(key);
        if (value == null){
            throw new NullPointerException(key.toString() + " is null");
        }
        return (T) value;
    }

    /**检查app是否进行的初始化*/
    private void checkConfiguration(){
        final boolean isReady = (boolean) PIE_CONFIGS.get(ConfigKeys.KEY_CONFIG_READY_OK);
        if (!isReady){
            throw new RuntimeException("Configuration is not ready,call configure");
        }
    }

    /**初始化logger*/
    private void initLogger(){
        FormatStrategy formatStrategy = PrettyFormatStrategy.newBuilder()
                  //(Optional) Whether to show thread info or not. Default true
                .showThreadInfo(false)
                  //(Optional) How many method line to show. Default 2
                .methodCount(0)
                  //(Optional) Hides internal method calls up to offset. Default 5
                .methodOffset(7)
                  //(Optional) Global tag for every log. Default PRETTY_LOGGER
                .tag(String.valueOf(getConfigurationValue(ConfigKeys.KEY_LOGGER_TAG)))
                .build();
        Logger.addLogAdapter(new AndroidLogAdapter(formatStrategy){
            @Override
            public boolean isLoggable(int priority, @Nullable String tag) {
                return getConfigurationValue(ConfigKeys.KEY_LOGGER_VISIBLE);
            }
        });
    }

}

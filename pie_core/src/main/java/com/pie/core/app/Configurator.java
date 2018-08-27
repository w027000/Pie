package com.pie.core.app;

import android.content.Context;
import android.support.annotation.Nullable;

import com.orhanobut.logger.AndroidLogAdapter;
import com.orhanobut.logger.FormatStrategy;
import com.orhanobut.logger.Logger;
import com.orhanobut.logger.PrettyFormatStrategy;

import java.util.HashMap;

/***
 * 全局配置初始化类
 */
public final class Configurator {


    private static final HashMap<Object,Object> PIE_CONFIGS = new HashMap<>();

    private Configurator(){

        PIE_CONFIGS.put(ConfigKeys.KEY_CONFIG_READY_OK,false);

    }

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

    /**配置全局host*/
    public Configurator withApiHost(String apiHost){
        PIE_CONFIGS.put(ConfigKeys.KEY_API_HOST,apiHost);
        return this;
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
                .showThreadInfo(false)                                                             // (Optional) Whether to show thread info or not. Default true
                .methodCount(0)                                                                    // (Optional) How many method line to show. Default 2
                .methodOffset(7)                                                                   // (Optional) Hides internal method calls up to offset. Default 5
                .tag(String.valueOf(getConfigurationValue(ConfigKeys.KEY_LOGGER_TAG)))             // (Optional) Global tag for every log. Default PRETTY_LOGGER
                .build();
        Logger.addLogAdapter(new AndroidLogAdapter(formatStrategy){
            @Override
            public boolean isLoggable(int priority, @Nullable String tag) {
                return getConfigurationValue(ConfigKeys.KEY_LOGGER_VISIBLE);
            }
        });
    }

}

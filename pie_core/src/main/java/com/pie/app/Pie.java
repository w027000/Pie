package com.pie.app;

public final class Pie {

    /**提供给外部做配置*/
    public static Configurator doConfigurator(){
        return  getConfiguratorInstance();
    }

    /**获取Configurator的实例*/
    private static Configurator getConfiguratorInstance(){
        return Configurator.getInstance();
    }

    /**获取key对应的值*/
    public static <T> T getConfigurationValue(Object key){
        return getConfiguratorInstance().getConfigurationValue(key);
    }

}

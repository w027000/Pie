package com.pie.core.app;

import android.support.multidex.MultiDexApplication;

public abstract class PieApplication extends MultiDexApplication {

    @Override
    public void onCreate() {
        super.onCreate();
        doConfigurator();
    }

    /***
     * 初始化一些全局参数
     */
    protected abstract void doConfigurator();


}

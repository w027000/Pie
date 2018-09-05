package com.pie.core.app;

import android.support.multidex.MultiDexApplication;

import com.facebook.stetho.Stetho;

public abstract class PieApplication extends MultiDexApplication {

    @Override
    public void onCreate() {
        super.onCreate();
        doConfigurator();
        initStetho();
    }

    /***
     * 初始化一些全局参数
     */
    protected abstract void doConfigurator();


    private void initStetho(){
        Stetho.initializeWithDefaults(this);
    }


}

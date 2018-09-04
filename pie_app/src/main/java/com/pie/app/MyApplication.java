package com.pie.app;

import com.pie.core.app.Pie;
import com.pie.core.app.PieApplication;
import com.pie.core.net.config.HttpDefaultConfigs;
import com.pie.core.net.interceptors.DebugInterceptor;
import com.pie.core.net.interceptors.HttpLogInterceptor;

public class MyApplication extends PieApplication {

    @Override
    protected void doConfigurator() {
        Pie.doConfigurator()
                .withApplication(this)
                .withLogger("pie", true)
                .withHttpApiHost("http://v.juhe.cn")
                .configure();
    }
}

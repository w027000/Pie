package com.pie.app;

import com.pie.core.app.Pie;
import com.pie.core.app.PieApplication;
import com.pie.core.net.config.HttpDefaultConfigs;
import com.pie.core.net.interceptors.DebugInterceptor;
import com.pie.core.net.interceptors.HttpLogInterceptor;

public class MyApplication extends PieApplication{

    @Override
    protected void doConfigurator() {
        Pie.doConfigurator()
                .withApplication(this)
                .withHttpApiHost("http://v.juhe.cn")
                .withHttpDefaultMilliseconds(HttpDefaultConfigs.HTTP_DEFAULT_CONNECT_TIME)
                .withHttpRetryCount(HttpDefaultConfigs.HTTP_DEFAULT_RETRY_COUNT)
                .withHttpInterceptor(new HttpLogInterceptor())
                .withHttpCacheNeverExpire(HttpDefaultConfigs.HTTP_DEFAULT_CACHE_NEVER_EXPIRE)
                .withHttpCacheKey(HttpDefaultConfigs.HTTP_DEFAULT_CACHE_KEY)
                .withLogger("pie",true)
                .configure();
    }
}

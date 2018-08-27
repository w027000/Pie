package com.pie;

import com.pie.core.app.Pie;
import com.pie.core.app.PieApplication;

public class MyApplication extends PieApplication{

    @Override
    protected void doConfigurator() {
        Pie.doConfigurator()
                .withApplication(this)
                .withApiHost("http://www.baidu.com")
                .withLogger("pie",true)
                .configure();
    }
}

package com.pie;

import com.pie.app.Pie;
import com.pie.app.PieApplication;

public class MyApplication extends PieApplication{

    @Override
    protected void doConfigurator() {
        Pie.doConfigurator()
                .withApplication(this)
                .withApiHost("http://www.baidu.com")
                .configure();
    }
}

package com.pie;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.pie.app.ConfigKeys;
import com.pie.app.Pie;
import com.pie.util.log.PLog;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        String host = Pie.getConfigurationValue(ConfigKeys.KEY_API_HOST);

        PLog.i("TAG","11111");
        PLog.d("TAG","11111");
        PLog.e("TAG","11111");
        PLog.w("TAG","11111");
    }
}

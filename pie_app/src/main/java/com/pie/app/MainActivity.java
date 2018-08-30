package com.pie.app;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.pie.R;
import com.pie.core.net.PieHttpClient;
import com.pie.core.net.callback.ICallback;
import com.pie.core.net.model.CacheMode;
import com.pie.core.net.model.CacheResult;
import com.pie.core.util.log.PLog;
import com.pie.core.util.system.AppUtil;
import com.pie.model.RootBean;

public class MainActivity extends AppCompatActivity {

    private TextView mTv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mTv = findViewById(R.id.tv);
        PLog.i("version code-->" + AppUtil.getVersionCode(this));
        PLog.i("ExternalCacheDir-->" + getExternalCacheDir().getPath() );
        PLog.i("CacheDir-->" + getCacheDir().getPath() );
    }

    //不带缓存
    public void doGet_0(View view) {
        PieHttpClient
                .get("/toutiao/index?type=top&key=279fb51b85d7a24ed7e58c8b27073fa6")
                .withTag("get0")
                .request(new ICallback<RootBean>() {
                    @Override
                    public void onSuccess(RootBean data) {
                        PLog.i("onSuccess data--->" + data);
                        mTv.setText(data.toString());
                    }

                    @Override
                    public void onFail(int code, String msg) {
                        PLog.i("onFail code--->" + code + "  , msg=" + msg);
                    }
                });
    }

    //缓存优先
    public void doGet_1(View view) {
        PieHttpClient
                .get("/toutiao/index?type=top&key=279fb51b85d7a24ed7e58c8b27073fa6")
                .withTag("get1")
                .withLocalCache(true)
                .withCacheMode(CacheMode.CACHE_MODE_FIRST_CACHE)
                .request(new ICallback<CacheResult<RootBean>>() {
                    @Override
                    public void onSuccess(CacheResult<RootBean> data) {
                        PLog.i("onSuccess data--->" + data);
                        mTv.setText(data.getCacheData().toString());
                    }

                    @Override
                    public void onFail(int code, String msg) {
                    }
                });
    }


    //网络优先
    public void doGet_2(View view) {
        PieHttpClient
                .get("/toutiao/index?type=top&key=279fb51b85d7a24ed7e58c8b27073fa6")
                .withTag("get2")
                .withLocalCache(true)
                .withCacheMode(CacheMode.CACHE_MODE_FIRST_REMOTE)
                .request(new ICallback<CacheResult<RootBean>>() {
                    @Override
                    public void onSuccess(CacheResult<RootBean> data) {
                        PLog.i("onSuccess data--->" + data);
                        mTv.setText(data.getCacheData().toString());
                    }

                    @Override
                    public void onFail(int code, String msg) {
                    }
                });
    }

    //只加载缓存
    public void doGet_3(View view) {
        PieHttpClient
                .get("/toutiao/index?type=top&key=279fb51b85d7a24ed7e58c8b27073fa6")
                .withTag("get3")
                .withLocalCache(true)
                .withCacheMode(CacheMode.CACHE_MODE_ONLY_CACHE)
                .request(new ICallback<CacheResult<RootBean>>() {
                    @Override
                    public void onSuccess(CacheResult<RootBean> data) {
                        PLog.i("onSuccess data--->" + data);
                        mTv.setText(data.getCacheData().toString());
                    }

                    @Override
                    public void onFail(int code, String msg) {
                    }
                });
    }
}

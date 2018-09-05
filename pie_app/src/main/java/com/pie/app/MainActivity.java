package com.pie.app;

import android.content.Intent;
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
import com.pie.core.util.system.SDCardUtils;
import com.pie.model.RootBean;

public class MainActivity extends AppCompatActivity {

    private TextView mTv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mTv = findViewById(R.id.tv);
//        PLog.i("version code-->" + AppUtil.getVersionCode(this));
//        PLog.i("ExternalCacheDir-->" + getExternalCacheDir().getPath() );
//        PLog.i("CacheDir-->" + getCacheDir().getPath() );

        PLog.i("-->" + SDCardUtils.getDiskCacheDir(this,"zjh").getAbsolutePath() );

    }


    public void doGet_test(View view){
        Intent intent = new Intent();
        intent.setAction("com.android.settings.action.SETTINGS");
        intent.addCategory("com.android.settings.category");
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.setPackage("com.android.settings");
        intent.setClassName("com.android.settings","com.android.settings.Settings$PowerUsageSummaryActivity");
        startActivity(intent);
    }

    //不带缓存
    public void doGet_0(View view) {
        PieHttpClient
                .get("/toutiao/index?type=top&key=279fb51b85d7a24ed7e58c8b27073fa6")
                .withTag("get0")
                .request(new ICallback<RootBean>() {
                    @Override
                    public void onSuccess(RootBean data) {
                        //PLog.i("onSuccess data--->" + data);
                        mTv.setText(data.toString());
                    }

                    @Override
                    public void onFail(int code, String msg) {
                        //PLog.i("onFail code--->" + code + "  , msg=" + msg);
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
                        //PLog.i("onSuccess data--->" + data);
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
                        //PLog.i("onSuccess data--->" + data);
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
                        //PLog.i("onSuccess data--->" + data);
                        mTv.setText(data.getCacheData().toString());
                    }

                    @Override
                    public void onFail(int code, String msg) {
                    }
                });
    }

    //先使用缓存，不管是否存在，仍然请求网络，会回调两次
    public void doGet_4(View view) {
        PieHttpClient
                .get("/toutiao/index?type=top&key=279fb51b85d7a24ed7e58c8b27073fa6")
                .withTag("get4")
                .withLocalCache(true)
                .withCacheMode(CacheMode.CACHE_MODE_CACHE_AND_REMOTE)
                .request(new ICallback<CacheResult<RootBean>>() {
                    @Override
                    public void onSuccess(CacheResult<RootBean> data) {
                        //PLog.i("onSuccess data--->" + data);
                        mTv.setText(data.getCacheData().toString());
                    }

                    @Override
                    public void onFail(int code, String msg) {
                    }
                });
    }

    //post 正常提交
    public void doPost_1(View view) {
        PieHttpClient
                .post("/toutiao/index")
                .withTag("doPost_1")
                .withParams("type","top")
                .withParams("key","279fb51b85d7a24ed7e58c8b27073fa6")
                .request(new ICallback<RootBean>(){

                    @Override
                    public void onSuccess(RootBean data) {
                       // PLog.i("onSuccess data--->" + data);
                        mTv.setText(data.toString());
                    }

                    @Override
                    public void onFail(int code, String msg) {

                    }
                });
    }


}

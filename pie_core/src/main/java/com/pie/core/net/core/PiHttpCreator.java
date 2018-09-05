package com.pie.core.net.core;

import android.content.Context;
import android.text.TextUtils;

import com.pie.core.app.ConfigKeys;
import com.pie.core.app.Pie;
import com.pie.core.net.api.ApiService;
import com.pie.core.net.https.HttpsUtils;
import com.pie.core.net.https.SafeHostnameVerifier;
import com.pie.core.net.https.SafeTrustManager;

import java.io.IOException;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

import javax.net.ssl.X509TrustManager;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * @author:zjh
 * @date:2018/8/27
 * @Description：Http全局配置
 */
public final class PiHttpCreator {

    /***
     * 构建OkHttp
     */
    private static final class OKHttpHolder{

        private static final OkHttpClient.Builder BUILDER = new OkHttpClient.Builder();

        private static final ArrayList<Interceptor> INTERCEPTORS = Pie.getConfigurationValue(ConfigKeys.KEY_HTTP_INTERCEPTOR);

        private static OkHttpClient.Builder addInterceptor() {
            if (INTERCEPTORS != null && !INTERCEPTORS.isEmpty()) {
                for (Interceptor interceptor : INTERCEPTORS) {
                    BUILDER.addInterceptor(interceptor);
                }
            }
            return BUILDER;
        }

        public static OkHttpClient buildOkHttp(){
            OkHttpClient.Builder builder = addInterceptor();
            long timeOut = Pie.getConfigurationValue(ConfigKeys.KEY_HTTP_DEFAULT_SECONDS);
            if (timeOut > 0){
                builder.connectTimeout(timeOut,TimeUnit.SECONDS);
                builder.readTimeout(timeOut,TimeUnit.SECONDS);
                builder.writeTimeout(timeOut,TimeUnit.SECONDS);
            }
            //https
            //https相关设置，以下几种方案根据需要自己设置
            //方法一：信任所有证书,不安全有风险
            HttpsUtils.SSLParams sslParams1 = HttpsUtils.getSslSocketFactory();
            //方法二：自定义信任规则，校验服务端证书
            //HttpsUtils.SSLParams sslParams2 = HttpsUtils.getSslSocketFactory(new SafeTrustManager());
            //方法三：使用预埋证书，校验服务端证书（自签名证书）
            try {
                //HttpsUtils.SSLParams sslParams3 = HttpsUtils.getSslSocketFactory((X509TrustManager) Pie.getApplicationContext().getResources().getAssets().open("srca.cer"));
            } catch (Exception e) {
                e.printStackTrace();
            }
            //方法四：使用bks证书和密码管理客户端证书（双向认证），使用预埋证书，校验服务端证书（自签名证书）
            try {
                //HttpsUtils.SSLParams sslParams4 = HttpsUtils.getSslSocketFactory(Pie.getApplicationContext().getResources().getAssets().open("xxx.bks"), "123456", Pie.getApplicationContext().getResources().getAssets().open("yyy.cer"));
            } catch (Exception e) {
                e.printStackTrace();
            }
            builder.sslSocketFactory(sslParams1.sSLSocketFactory, sslParams1.trustManager);
            //配置https的域名匹配规则，详细看demo的初始化介绍，不需要就不要加入，使用不当会导致https握手失败
            builder.hostnameVerifier(new SafeHostnameVerifier());
            return builder.build();
        }
    }


    /**
     * 构建全局Retrofit客户端
     */
    private static final class RetrofitHolder {

        public static Retrofit buildRetrofit(){
            final Retrofit.Builder builder = new Retrofit.Builder();
            String baseUrl = Pie.getConfigurationValue(ConfigKeys.KEY_HTTP_API_HOST);
            if (TextUtils.isEmpty(baseUrl)){
                throw new NullPointerException("baseUrl is null");
            }
            builder.baseUrl(baseUrl);
            builder.client(OKHttpHolder.buildOkHttp());
            builder.addConverterFactory(GsonConverterFactory.create());
            builder.addCallAdapterFactory(RxJava2CallAdapterFactory.create());
            return builder.build();
        }
    }


    /**
     * 构建Service接口
     */
    private static final class ApiServiceHolder {
        private static final ApiService API_SERVICE =
                RetrofitHolder.buildRetrofit().create(ApiService.class);
    }

    public static ApiService getApiService() {
        return ApiServiceHolder.API_SERVICE;
    }


    private static final class CacheManagerHolder{

        private static CacheManage getCacheManage(Context context){
            final CacheManage.Builder builder = new CacheManage.Builder(context);
            String cache = Pie.getConfigurationValue(ConfigKeys.KEY_HTTP_CACHE_KEY);
            builder.cacheKey(cache);
            long cacheTime = Pie.getConfigurationValue(ConfigKeys.KEY_CACHE_NEVER_EXPIRE);
            builder.cacheTime(cacheTime);
            return builder.build();
        }

    }

    /**
     * 构建CacheManager
     * @return
     */
    public static CacheManage getCacheManage(){
        return CacheManagerHolder.getCacheManage(Pie.getApplicationContext());
    }


}

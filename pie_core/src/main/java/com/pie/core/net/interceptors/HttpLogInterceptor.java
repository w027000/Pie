package com.pie.core.net.interceptors;


import com.pie.core.net.model.HttpMethod;
import com.pie.core.util.log.PLog;

import java.io.IOException;

import okhttp3.FormBody;
import okhttp3.Request;
import okhttp3.Response;

/**
 * @author:zjh
 * @date:2018/8/29
 * @Description：Http日志打印拦截器
 */
public class HttpLogInterceptor extends BaseInterceptor{

    @Override
    public Response intercept(Chain chain) throws IOException {
        //这个chain里面包含了request和response，所以你要什么都可以从这里拿
        Request request = chain.request();
        if (HttpMethod.POST.name().equals(request.method())){
            FormBody formBody = (FormBody) request.body();
            StringBuffer sb = new StringBuffer(request.url() + "?");
            for (int i=0,size=formBody.size();i<size;i++){
                String name = formBody.encodedName(i);
                String value = formBody.encodedValue(i);
                sb.append(name);
                sb.append("=");
                sb.append(value);
                if (i != size-1){
                    sb.append("&");
                }
            }
            PLog.i("post url = " + sb);
        }else if (HttpMethod.GET.name().equals(request.method())){
            PLog.i("get url = " + request.url());
        }
        Response response = chain.proceed(request);
        PLog.json(response.body().string());
        return response;
    }
}

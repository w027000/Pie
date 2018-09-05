package com.pie.core.net;

import com.pie.core.net.request.GetRequest;
import com.pie.core.net.request.PostRequest;
import com.pie.core.net.request.UpdateRequest;

/**
 * @author:zjh
 * @date:2018/8/27
 * @Description：该类相当于该模块的门面类，也是网络请求的唯一入口类，所有的请求都是由该类构建
 */
public final class PieHttpClient {

    private PieHttpClient(){
    }

    /**
     * Get请求
     * @param url
     * @return
     */
    public static GetRequest get(String url){
        return new GetRequest(url);
    }


    /**
     * Post请求
     * @param url
     * @return
     */
    public static PostRequest post(String url){
        return new PostRequest(url);
    }

    /**
     * 下载更新包
     * @return
     */
    public static UpdateRequest update(String url){
        return new UpdateRequest(url);
    }




}

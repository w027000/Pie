package com.pie.core.net.callback;

/**
 * @author:zjh
 * @date:2018/8/27
 * @Description：请求接口回调
 */
public interface ICallback<T> {

    /**
     * 请求成功回调
     * @param data
     */
    void onSuccess(T data);


    /***
     * 请求失败回调
     * @param code
     * @param msg
     */
    void onFail(int code, String msg);

}

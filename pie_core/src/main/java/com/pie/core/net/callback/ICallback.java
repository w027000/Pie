package com.pie.core.net.callback;

/**
 * @author:zjh
 * @date:2018/8/27
 * @Description：请求接口回调
 */
public abstract class ICallback<T> {

    /**
     * 请求成功回调
     * @param data
     */
    public abstract void onSuccess(T data);


    /***
     * 请求失败回调
     * @param code
     * @param msg
     */
    public abstract void onFail(int code, String msg);

}

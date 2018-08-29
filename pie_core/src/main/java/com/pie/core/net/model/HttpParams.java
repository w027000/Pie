package com.pie.core.net.model;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @author:zjh
 * @date:2018/8/28
 * @Description：请求参数的包装类
 */
public class HttpParams {

    private Map<String, String> mParams;

    public HttpParams(){
        mParams = new LinkedHashMap<>();
    }

    /**
     * 添加请求参数
     *
     * @param paramKey
     * @param paramValue
     * @return
     */
    public void addParam(String paramKey, String paramValue) {
        if (paramKey != null && paramValue != null) {
            this.mParams.put(paramKey, paramValue);
        }
    }

    /**
     * 添加请求参数
     *
     * @param params
     * @return
     */
    public void addParams(Map<String, String> params) {
        if (params != null) {
            this.mParams.putAll(params);
        }
    }

    /**
     * 移除请求参数
     *
     * @param paramKey
     * @return
     */
    public void removeParam(String paramKey) {
        if (paramKey != null) {
            this.mParams.remove(paramKey);
        }
    }

    /**
     * 设置请求参数
     *
     * @param params
     * @return
     */
    public void params(Map<String, String> params) {
        if (params != null) {
            this.mParams = params;
        }
    }


    public Map<String, String> getParams() {
        return mParams;
    }

}

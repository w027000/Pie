package com.pie.core.util.strings;

import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.JSONObject;

/**
 * @author:zjh
 * @date:2018/9/4
 * @Description：
 */
public class JsonUtil {

    public final static boolean isJSONValid2(String jsonInString ) {
        try {
            JSONObject.parseObject(jsonInString);
        } catch (JSONException ex) {
            try {
                JSONObject.parseArray(jsonInString);
            } catch (JSONException ex1) {
                return false;
            }
        }
        return true;
    }

}

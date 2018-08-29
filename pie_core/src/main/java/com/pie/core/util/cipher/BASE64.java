package com.pie.core.util.cipher;

import android.util.Base64;

/**
 * @author:zjh
 * @date:2018/8/29
 * @Description：Base64加密解密
 */
public class BASE64 {

    public static byte[] encode(byte[] plain) {
        return Base64.encode(plain, Base64.DEFAULT);
    }

    public static String encodeToString(byte[] plain) {
        return Base64.encodeToString(plain, Base64.DEFAULT);
    }

    public static byte[] decode(String text) {
        return Base64.decode(text, Base64.DEFAULT);
    }

    public static byte[] decode(byte[] text) {
        return Base64.decode(text, Base64.DEFAULT);
    }

}

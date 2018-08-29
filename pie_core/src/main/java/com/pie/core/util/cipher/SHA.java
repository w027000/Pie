package com.pie.core.util.cipher;

import java.security.MessageDigest;

/**
 * @author:zjh
 * @date:2018/8/29
 * @Description：SHA加密
 */
public class SHA {

    public static byte[] encrypt(byte[] data) throws Exception {
        MessageDigest sha = MessageDigest.getInstance(CipherType.SHA.getType());
        sha.update(data);
        return sha.digest();
    }
}

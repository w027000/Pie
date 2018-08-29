package com.pie.core.util.cipher;

import javax.crypto.KeyGenerator;
import javax.crypto.Mac;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

/**
 * @author:zjh
 * @date:2018/8/29
 * @Description：HMAC
 */
public class HMAC {

    /**
     * 初始化HMAC密钥
     *
     * @param type 算法，可为空。默认为：CipherType.Hmac_MD5
     * @return
     * @throws Exception
     */
    public static String initMacKey(CipherType type) throws Exception {
        if (type == null) type = CipherType.Hmac_MD5;
        KeyGenerator keyGenerator = KeyGenerator.getInstance(type.getType());
        SecretKey secretKey = keyGenerator.generateKey();
        return BASE64.encodeToString(secretKey.getEncoded());
    }

    /**
     * HMAC加密
     *
     * @param plain     明文
     * @param key       key
     * @param type 算法，可为空。默认为：CipherType.Hmac_MD5
     * @return
     * @throws Exception
     */
    public static byte[] encrypt(byte[] plain, String key, CipherType type) throws Exception {
        if (type == null) type = CipherType.Hmac_MD5;
        SecretKey secretKey = new SecretKeySpec(BASE64.decode(key), type.getType());
        Mac mac = Mac.getInstance(secretKey.getAlgorithm());
        mac.init(secretKey);
        return mac.doFinal(plain);
    }

}

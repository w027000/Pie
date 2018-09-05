package com.pie.core.net.https;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLSession;

/**
 * @author:zjh
 * @date:2018/9/5
 * @Description：这里只是我谁便写的认证规则，具体每个业务是否需要验证，以及验证规则是什么，请与服务端或者leader确定
 */
public class SafeHostnameVerifier implements HostnameVerifier {
    @Override
    public boolean verify(String hostname, SSLSession session) {
        //验证主机名是否匹配
        //return hostname.equals("server.jeasonlzy.com");
        return true;
    }
}

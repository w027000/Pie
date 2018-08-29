package com.pie.core.util.system;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;

import com.pie.core.util.log.PLog;

/**
 * @author:zjh
 * @date:2018/8/29
 * @Description：
 */
public class AppUtil {

    /**
     * 方法: getVersionCode
     * 描述: 获取客户端版本号
     *
     * @return int    版本号
     */
    public static int getVersionCode(Context context) {
        int versionCode;
        try {
            PackageManager pm = context.getPackageManager();
            PackageInfo packageInfo = pm.getPackageInfo(context.getPackageName(), 0);
            versionCode = packageInfo.versionCode;
        } catch (Exception e) {
            e.printStackTrace();
            PLog.e(e.getMessage());
            versionCode = 999;
        }
        return versionCode;
    }

}

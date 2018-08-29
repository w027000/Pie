package com.pie.core.util.system;

import android.content.res.Resources;
import android.util.DisplayMetrics;

import com.pie.core.app.Pie;

/**
 * @author:zjh
 * @date:2018/8/28
 * @Description：
 */
public class DimenUtil {

    /**
     * 获取屏幕的宽
     * @return
     */
    public static int getScreenWidth() {
        final Resources resources = Pie.getApplicationContext().getResources();
        final DisplayMetrics dm = resources.getDisplayMetrics();
        return dm.widthPixels;
    }

    /**
     * 获取屏幕的高
     * @return
     */
    public static int getScreenHeight() {
        final Resources resources = Pie.getApplicationContext().getResources();
        final DisplayMetrics dm = resources.getDisplayMetrics();
        return dm.heightPixels;
    }

}

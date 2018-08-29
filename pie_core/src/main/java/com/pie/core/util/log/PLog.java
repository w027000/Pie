package com.pie.core.util.log;


import com.orhanobut.logger.Logger;


public class PLog {

    public static void v(String tag, String message) {
        Logger.t(tag).v(message);
    }

    public static void v(String message) {
        Logger.v(message);
    }

    public static void d(String tag, Object message) {
        Logger.t(tag).d(message);
    }

    public static void d(Object message) {
        Logger.d(message);
    }

    public static void i(String tag, String message) {
        Logger.t(tag).i(message);

    }

    public static void i(String message) {
        Logger.i(message);
    }

    public static void w(String tag, String message) {
        Logger.t(tag).w(message);
    }

    public static void w(String message) {
        Logger.w(message);
    }

    public static void json(String tag, String message) {
        Logger.t(tag).json(message);
    }

    public static void json(String message) {
        Logger.json(message);
    }

    public static void e(String tag, String message) {
        Logger.t(tag).e(message);
    }

    public static void e(String message) {
        Logger.e(message);
    }

}

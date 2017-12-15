package com.zzx.gank.utils;

import android.util.Log;
/**
 * Created by zuozhixiang on 17/11/9.
 */

public class MyLog {

    private static final String TAG = "MyLog";
    // 是否显示Log消息
    private static boolean isShow = true;

    public static boolean isShow() {
        return isShow;
    }

    public static void setShow(boolean show) {
        isShow = show;
    }

    public static void i(String tag, String msg) {
        if (isShow) {
            Log.i(tag, msg);
        }
    }

    public static void w(String tag, String msg) {
        if (isShow)
            Log.w(tag, msg);
    }

    public static void e(String tag, String msg) {
        if (isShow)
            Log.e(tag, msg);
    }

    public static void i(String msg) {
        if (isShow)
            Log.i(TAG, msg);
    }

    public static void w(String msg) {
        if (isShow)
            Log.w(TAG, msg);
    }

    public static void e(String msg) {
        if (isShow)
            Log.e(TAG, msg);
    }

    public static void v(String msg) {
        if (isShow)
            Log.v(TAG, msg);
    }

    public static void d(String msg) {
        if (isShow)
            Log.d(TAG, msg);
    }

    public static void d(String tag, String msg) {
        if (isShow)
            Log.d(tag, msg);
    }

    public static void w(String tag, String msg, Exception e) {
        if (isShow)
            Log.w(tag, msg, e);
    }

    public static void v(String tag, String msg) {
        if (isShow)
            Log.v(tag, msg);
    }
}

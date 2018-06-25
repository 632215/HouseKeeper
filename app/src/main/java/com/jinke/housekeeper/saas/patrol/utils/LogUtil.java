package com.jinke.housekeeper.saas.patrol.utils;

import android.util.Log;

/**
 * function: 日志统一管理类
 * author: hank
 * date: 2017/7/26
 */

public class LogUtil {

    private final static String TAG = "JK";
    private final static boolean isShow = true;

    public static void logi(String s){
        if (isShow){
            Log.i(TAG, s);
        }
    }

    public static void logw(String s){
        if (isShow){
            Log.w(TAG, s);
        }
    }

    public static void logd(String s){
        if (isShow){
            Log.d(TAG, s);
        }
    }

    public static void logv(String s){
        if (isShow){
            Log.v(TAG, s);
        }
    }

    public static void loge(String s){
        if (isShow){
            Log.e(TAG, s);
        }
    }

}

package com.jinke.housekeeper.saas.report.util;

import android.content.Context;
import android.util.Log;

import com.tencent.stat.StatService;

import java.util.Properties;

/**
 * Created by Administrator on 2017/9/6.
 */

public class TencentCountUtil {
    public static void count(Context context, String reportRegistration) {
        // 统计按钮被点击次数，统计对象：OK按钮
        Properties prop = new Properties();
        prop.setProperty("name", reportRegistration);
        StatService.trackCustomKVEvent(context, context.getClass().getName()+reportRegistration+"_click", prop);
        Log.e("32s", context.getClass().getName());
    }
}

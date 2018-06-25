package com.jinke.housekeeper.saas.patrol.config;

import android.content.Context;

import com.jinke.housekeeper.saas.patrol.bean.AlarmClockListBean;
import com.jinke.housekeeper.saas.patrol.utils.SharedPreferenceUtil;

/**
 * function:
 * author: hank
 * date: 2017/7/27
 */

public class AlarmClockConfig {
    private final  static String fileKey = "AlarmClockConfig";
    private final  static String key = "AlarmClockBean";

    public static AlarmClockListBean getAlarmClockBean(Context context) {
        return (AlarmClockListBean) SharedPreferenceUtil.get(context, fileKey , key);
    }

    public static void setAlarmClockBean(Context context , AlarmClockListBean alarmClockBean) {
        SharedPreferenceUtil.save(context , fileKey , key ,alarmClockBean);
    }
}

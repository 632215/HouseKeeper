package com.jinke.housekeeper.saas.patrol.ui.widget.alarm;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.jinke.housekeeper.saas.patrol.ui.activity.PatrolActivity;

/**
 * function:
 * author: hank
 * date: 2017/8/2
 */

public class AlarmReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        System.out.println("闹钟执行了！");   // 作调试用

        // 闹钟管理
        AlarmManager am = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        // 取消闹钟
        am.cancel(PendingIntent.getBroadcast(context, getResultCode(),
                new Intent(context, AlarmReceiver.class), 0));

        // 实例化一个Intent，启动该Activity
        Intent i = new Intent(context, PatrolActivity.class);
        i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(i);
    }
}

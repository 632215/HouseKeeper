package com.jinke.housekeeper.service;

import android.app.ActivityManager;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;

import com.jinke.housekeeper.R;
import com.jinke.housekeeper.applicaption.MyApplicaption;
import com.jinke.housekeeper.ui.activity.RestartStartActivity;
import com.jinke.housekeeper.ui.activity.StartupPageActivity;
import com.jinke.housekeeper.utils.CommonlyUtils;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Iterator;
import java.util.List;

import cn.jpush.android.api.JPushInterface;

import static android.content.Context.ACTIVITY_SERVICE;

/**
 * 自定义接收器
 * <p>
 * 如果不定义这个 Receiver，则：
 * 1) 默认用户会打开主界面
 * 2) 接收不到自定义消息
 */
public class JPushReceiver extends BroadcastReceiver {
    private static final String TAG = "JPush";

    @Override
    public void onReceive(Context context, Intent intent) {
        try {
            Bundle bundle = intent.getExtras();
            MyApplicaption applicaption = MyApplicaption.getInstance();
            if (false) {
                Log.d(TAG, "[JPushReceiver] onReceive - " + intent.getAction() + ", extras: " + printBundle(bundle));
            }
            if (JPushInterface.ACTION_REGISTRATION_ID.equals(intent.getAction())) {
                //SDK 向 JPush Server 注册所得到的注册ID 一般来说，可不处理此广播信息。
                String regId = bundle.getString(JPushInterface.EXTRA_REGISTRATION_ID);//注册所得到的注册ID
            } else if (JPushInterface.ACTION_MESSAGE_RECEIVED.equals(intent.getAction())) {
                //收到了自定义消息 Push  SDK 对自定义消息，只是传递，不会有任何界面上的展示。
                bundle.getString(JPushInterface.EXTRA_MESSAGE);//接收到推送下来的自定义消息
                processCustomMessage(context, bundle);
                String type = new JSONObject(bundle.getString(JPushInterface.EXTRA_EXTRA)).getString("type");
                String value = bundle.getString(JPushInterface.EXTRA_MESSAGE);
                if (type.equals("1")) {
                    applicaption.speak("你有新的成员需要审核");
                } else if (type.equals("2")) {
                    String[] values = value.split("-");
                    applicaption.speak(values[0] + "有新的工单需要处理");
                } else if (type.equals("4")) {
                    applicaption.speak("你有超时工单，请立即处理");
                } else if (type.equals("5")) {
                    applicaption.speak("你有新工单需要处理");
                } else if (type.equals("6")) {
                    applicaption.speak("你的工单被退回，请重新处理");
                } else if (type.equals("7")) {
                    applicaption.speak("你有新的工单需要处理");
                } else if (type.equals("8")) {
                    applicaption.speak("你有工单需要立即处理");
                } else if (type.equals("10")) {
                    applicaption.speak("你有新的工单需要处理");
                } else if (type.equals("13")) {
                    applicaption.speak("工单处理完成，请审核");
                } else if (type.equals("14")) {
                    applicaption.speak("工单处理完成，请审核");
                }
                setNotification(context, value);
            } else if (JPushInterface.ACTION_NOTIFICATION_RECEIVED.equals(intent.getAction())) {
                //收到了通知 Push 如果通知的内容为空，则在通知栏上不会展示通知。
                bundle.getInt(JPushInterface.EXTRA_NOTIFICATION_ID);//通知的内容
                String type = new JSONObject(bundle.getString(JPushInterface.EXTRA_EXTRA)).getString("type");
                String value = bundle.getString(JPushInterface.EXTRA_ALERT);
                if (type.equals("1")) {
                    applicaption.speak("你有新的成员需要审核");
                } else if (type.equals("2")) {
                    String[] values = value.split("-");
                    applicaption.speak(values[0] + "有新的工单需要处理");
                } else if (type.equals("4")) {
                    applicaption.speak("你有超时工单，请立即处理");
                } else if (type.equals("5")) {
                    applicaption.speak("你有新工单需要处理");
                } else if (type.equals("6")) {
                    applicaption.speak("你的工单被退回，请重新处理");
                } else if (type.equals("7")) {
                    applicaption.speak("你有新的工单需要处理");
                } else if (type.equals("8")) {
                    applicaption.speak("你有工单需要立即处理");
                } else if (type.equals("10")) {
                    applicaption.speak("你有新的工单需要处理");
                } else if (type.equals("13")) {
                    applicaption.speak("工单处理完成，请审核");
                } else if (type.equals("14")) {
                    applicaption.speak("工单处理完成，请审核");
                }
            } else if (JPushInterface.ACTION_NOTIFICATION_OPENED.equals(intent.getAction())) {
                //用户点击了通知。 一般情况下，用户不需要配置此 receiver action。
                Intent i = null;
                JSONObject json = new JSONObject(bundle.getString(JPushInterface.EXTRA_EXTRA));
                Log.i(TAG, json.toString());
                if (!isAppAlive(applicaption)) {
                    Intent mainIntent = new Intent(context, StartupPageActivity.class);
                    mainIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    Intent[] intents = {mainIntent};
                    context.startActivities(intents);
                } else {
                    if (isBackground(applicaption)) {
                        Intent mainIntent = new Intent(context, RestartStartActivity.class);
                        mainIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        Intent[] intents = {mainIntent};
                        context.startActivities(intents);
                    }
                }

            } else if (JPushInterface.ACTION_RICHPUSH_CALLBACK.equals(intent.getAction())) {
                Log.d(TAG, "[JPushReceiver] 用户收到到RICH PUSH CALLBACK: " + bundle.getString(JPushInterface.EXTRA_EXTRA));
                //在这里根据 JPushInterface.EXTRA_EXTRA 的内容处理代码，比如打开新的Activity，打开一个网页等..
            } else if (JPushInterface.ACTION_CONNECTION_CHANGE.equals(intent.getAction())) {
                //JPush 服务的连接状态发生变化。
                boolean connected = intent.getBooleanExtra(JPushInterface.EXTRA_CONNECTION_CHANGE, false);
                Log.w(TAG, "[JPushReceiver]" + intent.getAction() + " connected state change to " + connected);
            } else {
                Log.d(TAG, "[JPushReceiver] Unhandled intent - " + intent.getAction());
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }

    }


    /**
     * 打印所有的 intent extra 数据 发布时候注释掉 提示时候打开
     *
     * @param bundle
     * @return
     */
    private static String printBundle(Bundle bundle) {
        StringBuilder sb = new StringBuilder();
        for (String key : bundle.keySet()) {
            if (key.equals(JPushInterface.EXTRA_NOTIFICATION_ID)) {
                sb.append("\nkey:" + key + ", value:" + bundle.getInt(key));
            } else if (key.equals(JPushInterface.EXTRA_CONNECTION_CHANGE)) {
                sb.append("\nkey:" + key + ", value:" + bundle.getBoolean(key));
            } else if (key.equals(JPushInterface.EXTRA_EXTRA)) {
                if (TextUtils.isEmpty(bundle.getString(JPushInterface.EXTRA_EXTRA))) {
                    Log.i(TAG, "This message has no Extra data");
                    continue;
                }

                try {
                    JSONObject json = new JSONObject(bundle.getString(JPushInterface.EXTRA_EXTRA));
                    Log.i("wan", "json---" + json.toString());
                    Iterator<String> it = json.keys();

                    while (it.hasNext()) {
                        String myKey = it.next().toString();
                        sb.append("\nkey:" + key + ", value: [" +
                                myKey + " - " + json.optString(myKey) + "]");
                    }
                } catch (JSONException e) {
                    Log.e(TAG, "Get message extra JSON error!");
                }

            } else {
                sb.append("\nkey:" + key + ", value:" + bundle.getString(key));
            }
        }
        return sb.toString();
    }

    private void processCustomMessage(Context context, Bundle bundle) {
    }

    public static boolean isBackground(Context context) {
        ActivityManager activityManager = (ActivityManager) context
                .getSystemService(ACTIVITY_SERVICE);
        List<ActivityManager.RunningAppProcessInfo> appProcesses = activityManager
                .getRunningAppProcesses();
        for (ActivityManager.RunningAppProcessInfo appProcess : appProcesses) {
            if (appProcess.processName.equals(context.getPackageName())) {

                if (appProcess.importance != ActivityManager.RunningAppProcessInfo.IMPORTANCE_FOREGROUND) {
                    return true;//后台
                } else {
                    return false;//前台
                }
            }
        }
        return false;
    }

    public static boolean isAppAlive(Context context) {
        ActivityManager activityManager =
                (ActivityManager) context.getSystemService(ACTIVITY_SERVICE);
        List<ActivityManager.RunningAppProcessInfo> processInfos
                = activityManager.getRunningAppProcesses();
        for (int i = 0; i < processInfos.size(); i++) {
            if (processInfos.get(i).processName.equals(context.getPackageName())) {
                return true;//启动
            }
        }
        return false;//未启动
    }

    protected void setNotification(Context context, String message) {
        Intent broadcastIntent = new Intent(context, RestartStartActivity.class);
        broadcastIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, broadcastIntent, PendingIntent.FLAG_CANCEL_CURRENT);
        Notification.Builder builder = new Notification.Builder(context);
        builder.setSmallIcon(R.mipmap.housekeeper)
                .setWhen(System.currentTimeMillis())
                .setContentTitle("通知")
                .setContentText(message)
                .setContentInfo("通知")
                .setContentIntent(pendingIntent)
                .setDefaults(Notification.DEFAULT_SOUND | Notification.DEFAULT_VIBRATE)
                .setSound(RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION));
        Notification notification = builder.build();
        notification.flags |= Notification.FLAG_AUTO_CANCEL;
        NotificationManager manager = (NotificationManager) context.getSystemService(context.getApplicationContext().NOTIFICATION_SERVICE);
        int id = (int) (System.currentTimeMillis() / 1000);
        manager.notify(id, notification);
    }

}

package com.jinke.housekeeper.saas.patrol.ui.widget.alarm;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;

/**
 * function:
 * author: hank
 * date: 2017/8/2
 */

public class AlarmService extends Service{

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}

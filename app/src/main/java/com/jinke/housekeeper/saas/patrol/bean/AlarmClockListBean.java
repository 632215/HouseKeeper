package com.jinke.housekeeper.saas.patrol.bean;

import java.io.Serializable;
import java.util.List;

/**
 * function: 闹钟实体
 * author: hank
 * date: 2017/7/27
 */

public class AlarmClockListBean implements Serializable {
    private List<AlarmClockBean> alarmClockList;

    public List<AlarmClockBean> getAlarmClockList() {
        return alarmClockList;
    }

    public void setAlarmClockList(List<AlarmClockBean> alarmClockList) {
        this.alarmClockList = alarmClockList;
    }


}

package com.jinke.housekeeper.saas.patrol.bean;

import java.io.Serializable;

public class AlarmClockBean implements Serializable {
    private int alarmId;
    private String startTime;
    private String endTime;
    private boolean stateAlarmClock;//true 打开 false关闭

    public AlarmClockBean() {
    }

    public AlarmClockBean(int alarmId, String startTime, String endTime, boolean stateAlarmClock) {
        this.alarmId = alarmId;
        this.startTime = startTime;
        this.endTime = endTime;
        this.stateAlarmClock = stateAlarmClock;
    }

    public int getAlarmId() {
        return alarmId;
    }

    public void setAlarmId(int alarmId) {
        this.alarmId = alarmId;
    }
    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public boolean isStateAlarmClock() {
        return stateAlarmClock;
    }

    public void setStateAlarmClock(boolean stateAlarmClock) {
        this.stateAlarmClock = stateAlarmClock;
    }
}
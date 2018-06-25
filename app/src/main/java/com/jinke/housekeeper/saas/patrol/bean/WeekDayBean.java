package com.jinke.housekeeper.saas.patrol.bean;

import java.io.Serializable;

/**
 * function:
 * author: hank
 * date: 2017/7/28
 */

public class WeekDayBean implements Serializable {

    private boolean monday;//周一 true开启 false关闭
    private boolean tuesday;//周二
    private boolean wednesday;//周三
    private boolean thursday;//周四
    private boolean friday;//周五
    private boolean saturday;//周六
    private boolean sunday;//周日

    public WeekDayBean(boolean monday, boolean tuesday, boolean wednesday, boolean thursday, boolean friday, boolean saturday, boolean sunday) {
        this.monday = monday;
        this.tuesday = tuesday;
        this.wednesday = wednesday;
        this.thursday = thursday;
        this.friday = friday;
        this.saturday = saturday;
        this.sunday = sunday;
    }

    public boolean isMonday() {
        return monday;
    }

    public void setMonday(boolean monday) {
        this.monday = monday;
    }

    public boolean isTuesday() {
        return tuesday;
    }

    public void setTuesday(boolean tuesday) {
        this.tuesday = tuesday;
    }

    public boolean isWednesday() {
        return wednesday;
    }

    public void setWednesday(boolean wednesday) {
        this.wednesday = wednesday;
    }

    public boolean isThursday() {
        return thursday;
    }

    public void setThursday(boolean thursday) {
        this.thursday = thursday;
    }

    public boolean isFriday() {
        return friday;
    }

    public void setFriday(boolean friday) {
        this.friday = friday;
    }

    public boolean isSaturday() {
        return saturday;
    }

    public void setSaturday(boolean saturday) {
        this.saturday = saturday;
    }

    public boolean isSunday() {
        return sunday;
    }

    public void setSunday(boolean sunday) {
        this.sunday = sunday;
    }

    public boolean isAllDay() {
        return (sunday && monday && tuesday && wednesday && thursday && friday && saturday);
    }

    public boolean isEdit() {
        return (sunday || monday || tuesday || wednesday || thursday || friday || saturday);
    }

    public int getChooceNumber(){
        int chooceNumber = 0;
        if (sunday) {
            chooceNumber++;
        }
        if (monday) {
            chooceNumber++;
        }
        if (tuesday) {
            chooceNumber++;
        }
        if (wednesday) {
            chooceNumber++;
        }
        if (thursday) {
            chooceNumber++;
        }
        if (friday) {
            chooceNumber++;
        }
        if (saturday) {
            chooceNumber++;
        }
        return chooceNumber;
    }
}

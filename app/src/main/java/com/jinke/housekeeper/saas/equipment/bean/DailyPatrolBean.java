package com.jinke.housekeeper.saas.equipment.bean;

import java.io.Serializable;

/**
 * function:获取巡检统计数据
 * author: hank
 * date: 2017/10/11
 */

public class DailyPatrolBean  implements Serializable {

    /**
     * overtimeNum : 333
     * weekRate : 222
     * completionRate : 222
     * unCompletion : 333
     */

    private String overtimeNum;
    private String weekRate;
    private String completionRate;
    private String unCompletion;

    public String getOvertimeNum() {
        return overtimeNum;
    }

    public void setOvertimeNum(String overtimeNum) {
        this.overtimeNum = overtimeNum;
    }

    public String getWeekRate() {
        return weekRate;
    }

    public void setWeekRate(String weekRate) {
        this.weekRate = weekRate;
    }

    public String getCompletionRate() {
        return completionRate;
    }

    public void setCompletionRate(String completionRate) {
        this.completionRate = completionRate;
    }

    public String getUnCompletion() {
        return unCompletion;
    }

    public void setUnCompletion(String unCompletion) {
        this.unCompletion = unCompletion;
    }
}

package com.jinke.housekeeper.saas.patrol.bean;

import java.io.Serializable;

/**
 * function: 点位信息列表
 * author: hank
 * date: 2017/7/25
 */

public class PointListBean  implements Serializable {

    /**
     * pointId : 123456
     * pointName : 一栋巡逻点
     * todayNum : 5
     * monthNum : 50
     */

    private String pointId;
    private String pointName;
    private int todayNum;
    private int monthNum;

    public String getPointId() {
        return pointId;
    }

    public void setPointId(String pointId) {
        this.pointId = pointId;
    }

    public String getPointName() {
        return pointName;
    }

    public void setPointName(String pointName) {
        this.pointName = pointName;
    }

    public int getTodayNum() {
        return todayNum;
    }

    public void setTodayNum(int todayNum) {
        this.todayNum = todayNum;
    }

    public int getMonthNum() {
        return monthNum;
    }

    public void setMonthNum(int monthNum) {
        this.monthNum = monthNum;
    }
}

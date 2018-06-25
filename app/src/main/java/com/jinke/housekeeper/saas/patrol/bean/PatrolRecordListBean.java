package com.jinke.housekeeper.saas.patrol.bean;

/**
 * function: 打卡记录
 * author: hank
 * date: 2017/7/25
 */

public class PatrolRecordListBean {
    public PatrolRecordListBean() {
    }

    public PatrolRecordListBean(String pointName, String punchTime) {
        this.pointName = pointName;
        this.punchTime = punchTime;
    }

    /**
     * pointName : 一栋巡更点
     * punchTime : 2017-07-24 09:41:52
     */

    private String pointName;
    private String punchTime;

    public String getPointName() {
        return pointName;
    }

    public void setPointName(String pointName) {
        this.pointName = pointName;
    }

    public String getPunchTime() {
        return punchTime;
    }

    public void setPunchTime(String punchTime) {
        this.punchTime = punchTime;
    }
}

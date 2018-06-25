package com.jinke.housekeeper.saas.patrol.bean;

/**
 * author : huominghao
 * date : 2018/3/1 0001
 * function : 对比报表数据
 */

public class TimeDataBean {

    /**
     * dayTime : 01
     * ifLou : false
     * ifReport : false
     * ifNormal : false
     * toDayLou : null
     * toDayPlan : null
     * reportNum : null
     */

    private String dayTime;
    private boolean ifLou;
    private boolean ifReport;
    private boolean ifNormal;
    private Object toDayLou;
    private Object toDayPlan;
    private Object reportNum;

    public String getDayTime() {
        return dayTime;
    }

    public void setDayTime(String dayTime) {
        this.dayTime = dayTime;
    }

    public boolean isIfLou() {
        return ifLou;
    }

    public void setIfLou(boolean ifLou) {
        this.ifLou = ifLou;
    }

    public boolean isIfReport() {
        return ifReport;
    }

    public void setIfReport(boolean ifReport) {
        this.ifReport = ifReport;
    }

    public boolean isIfNormal() {
        return ifNormal;
    }

    public void setIfNormal(boolean ifNormal) {
        this.ifNormal = ifNormal;
    }

    public Object getToDayLou() {
        return toDayLou;
    }

    public void setToDayLou(Object toDayLou) {
        this.toDayLou = toDayLou;
    }

    public Object getToDayPlan() {
        return toDayPlan;
    }

    public void setToDayPlan(Object toDayPlan) {
        this.toDayPlan = toDayPlan;
    }

    public Object getReportNum() {
        return reportNum;
    }

    public void setReportNum(Object reportNum) {
        this.reportNum = reportNum;
    }
}

package com.jinke.housekeeper.saas.patrol.bean;

/**
 * author : huominghao
 * date : 2018/3/1 0001
 * function :对比报表数据
 */

public class PointDataBean {

    /**
     * toDayLou : 0
     * toDayPlan : 0
     * toDayComplent : 0
     * pointPlanNum : 25
     * pointTotal : 9
     * reportNum : 0
     */

    private String toDayLou;
    private String toDayPlan;
    private String toDayComplent;
    private String pointPlanNum;
    private String pointTotal;
    private String reportNum;

    public String getToDayLou() {
        return toDayLou;
    }

    public void setToDayLou(String toDayLou) {
        this.toDayLou = toDayLou;
    }

    public String getToDayPlan() {
        return toDayPlan;
    }

    public void setToDayPlan(String toDayPlan) {
        this.toDayPlan = toDayPlan;
    }

    public String getToDayComplent() {
        return toDayComplent;
    }

    public void setToDayComplent(String toDayComplent) {
        this.toDayComplent = toDayComplent;
    }

    public String getPointPlanNum() {
        return pointPlanNum;
    }

    public void setPointPlanNum(String pointPlanNum) {
        this.pointPlanNum = pointPlanNum;
    }

    public String getPointTotal() {
        return pointTotal;
    }

    public void setPointTotal(String pointTotal) {
        this.pointTotal = pointTotal;
    }

    public String getReportNum() {
        return reportNum;
    }

    public void setReportNum(String reportNum) {
        this.reportNum = reportNum;
    }
}

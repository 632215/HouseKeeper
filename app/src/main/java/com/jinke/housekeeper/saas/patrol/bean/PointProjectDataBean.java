package com.jinke.housekeeper.saas.patrol.bean;

/**
 * author : huominghao
 * date : 2018/3/1 0001
 * function :巡更报表数据
 */

public class PointProjectDataBean {

    /**
     * toDayLou : 0
     * toDayPlan : 16
     * toDayComplent : 0
     * type : 3
     * projectName : 重庆分公司
     * pointName : null
     * planName : null
     */

    private String toDayLou;
    private String toDayPlan;
    private String toDayComplent;
    private int type;
    private String projectName;
    private String pointName;
    private String planName;

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

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public String getPointName() {
        return pointName;
    }

    public void setPointName(String pointName) {
        this.pointName = pointName;
    }

    public String getPlanName() {
        return planName;
    }

    public void setPlanName(String planName) {
        this.planName = planName;
    }
}

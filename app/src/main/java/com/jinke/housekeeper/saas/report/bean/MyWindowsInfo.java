package com.jinke.housekeeper.saas.report.bean;

/**
 * Created by root on 17-3-23.
 */

public class MyWindowsInfo {
    private String startTime;
    private String endTime;
    private String sponsor;
    private String rectificationMan;
    private String categoryName;
    private String categoryId;
    private String state;
    private String keyPointId;
    private String keyPointName;

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

    public String getKeyPointId() {
        return keyPointId;
    }

    public void setKeyPointId(String keyPointId) {
        this.keyPointId = keyPointId;
    }

    public String getKeyPointName() {
        return keyPointName;
    }

    public void setKeyPointName(String keyPointName) {
        this.keyPointName = keyPointName;
    }

    public String getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(String categoryId) {
        this.categoryId = categoryId;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public String getSponsor() {
        return sponsor;
    }

    public void setSponsor(String sponsor) {
        this.sponsor = sponsor;
    }

    public String getRectificationMan() {
        return rectificationMan;
    }

    public void setRectificationMan(String rectificationMan) {
        this.rectificationMan = rectificationMan;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }
}

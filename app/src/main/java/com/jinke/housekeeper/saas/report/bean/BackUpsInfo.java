package com.jinke.housekeeper.saas.report.bean;

import java.util.ArrayList;

/**
 * Created by root on 17-7-14.
 */
public class BackUpsInfo {
    private String json;
    private String date;
    private ArrayList<String> pictureList;
    private ArrayList<RecorderBean> recorderList;
    private String text;
    private String category;
    private String pointKey;
    private String recordTime;

    public String getJson() {
        return json;
    }

    public void setJson(String json) {
        this.json = json;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public ArrayList<String> getPictureList() {
        return pictureList;
    }

    public void setPictureList(ArrayList<String> pictureList) {
        this.pictureList = pictureList;
    }

    public ArrayList<RecorderBean> getRecorderList() {
        return recorderList;
    }

    public void setRecorderList(ArrayList<RecorderBean> recorderList) {
        this.recorderList = recorderList;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getPointKey() {
        return pointKey;
    }

    public void setPointKey(String pointKey) {
        this.pointKey = pointKey;
    }

    public String getRecordTime() {
        return recordTime;
    }

    public void setRecordTime(String recordTime) {
        this.recordTime = recordTime;
    }
}
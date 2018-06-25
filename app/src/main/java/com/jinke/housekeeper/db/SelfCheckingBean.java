package com.jinke.housekeeper.db;


import com.jinke.housekeeper.saas.report.bean.RecorderBean;

import org.litepal.crud.DataSupport;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by pc on 2017/3/9.
 */

public class SelfCheckingBean extends DataSupport {
    private List<String> pictureList;
    private List<String> recordNameList;
    private List<String> recordTimeList;
    private String sessionId;    //是	string	唯一标识
    private String userId;    //是	string	用户ID
    //    private String filePath;    //是	文件类型	文件
    private String decribe;//描述
    private String category;//类别
    private String keyPoint;//关键点位
    private String staffTime;//发起时间
    private String info;//整个信息

    public SelfCheckingBean() {
    }

    public List<String> getPictureList() {
        return pictureList;
    }

    public void setPictureList(List<String> pictureList) {
        this.pictureList = pictureList;
    }

    public List<String> getRecordNameList() {
        return recordNameList;
    }

    public void setRecordNameList(List<String> recordNameList) {
        this.recordNameList = recordNameList;
    }

    public List<String> getRecordTimeList() {
        return recordTimeList;
    }

    public void setRecordTimeList(List<String> recordTimeList) {
        this.recordTimeList = recordTimeList;
    }

    public String getSessionId() {
        return sessionId;
    }

    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getDecribe() {
        return decribe;
    }

    public void setDecribe(String decribe) {
        this.decribe = decribe;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getKeyPoint() {
        return keyPoint;
    }

    public void setKeyPoint(String keyPoint) {
        this.keyPoint = keyPoint;
    }

    public String getStaffTime() {
        return staffTime;
    }

    public void setStaffTime(String staffTime) {
        this.staffTime = staffTime;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }
}

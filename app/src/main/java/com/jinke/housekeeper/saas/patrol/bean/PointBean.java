package com.jinke.housekeeper.saas.patrol.bean;

import java.util.List;

/**
 * Created by root on 17-7-24.
 */

public class PointBean {


    /**
     * projectId : 1ADF1582SDFSDF149612
     * projectName : 金科十年城
     * listData : [{"pointId":"123456","pointName":"一栋巡逻点","todayNum":5,"monthNum":50},{"pointId":"789456","pointName":"二栋巡逻点","todayNum":3,"monthNum":30}]
     */

    private String projectId;
    private String projectName;
    private List<PointListBean> listData;

    public String getProjectId() {
        return projectId;
    }

    public void setProjectId(String projectId) {
        this.projectId = projectId;
    }

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public List<PointListBean> getListData() {
        return listData;
    }

    public void setListData(List<PointListBean> listData) {
        this.listData = listData;
    }

}

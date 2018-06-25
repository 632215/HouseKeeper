package com.jinke.housekeeper.bean;

/**
 * Created by 32 on 2016/12/22.
 */
public class RegisterProjectDetailBean {
    private String projectDetail;

    public RegisterProjectDetailBean(String projectDetail) {
        this.projectDetail = projectDetail;
    }

    public String getProjectDetail() {
        return projectDetail;
    }

    public void setProjectDetail(String projectDetail) {
        this.projectDetail = projectDetail;
    }
}

package com.jinke.housekeeper.saas.handoverroom.bean;

/**
 * function:
 * author: hank
 * date: 2017/11/26
 */

public class TokenBean {


    /**
     * token : bc3c45797f234c298b3342d45ba3b117
     * staffName : 包强测试
     * userId : 1512095813622
     * ifManage : 1
     * position :
     */

    private String token;
    private String staffName;
    private String userId;
    private String ifManage;
    private String position;

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getStaffName() {
        return staffName;
    }

    public void setStaffName(String staffName) {
        this.staffName = staffName;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getIfManage() {
        return ifManage;
    }

    public void setIfManage(String ifManage) {
        this.ifManage = ifManage;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }
}

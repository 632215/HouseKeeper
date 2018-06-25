package com.jinke.housekeeper.saas.patrol.bean;

/**
 * Created by root on 17-7-24.
 */

public class TokenBean {

    /**
     * token : 1ADF1582SDFSDF149612
     * staffName : 李四
     * position : 经理
     * ifManage : 1
     */

    private String token;
    private String staffName;
    private String position;
    private String ifManage;

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

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public String getIfManage() {
        return ifManage;
    }

    public void setIfManage(String ifManage) {
        this.ifManage = ifManage;
    }
}

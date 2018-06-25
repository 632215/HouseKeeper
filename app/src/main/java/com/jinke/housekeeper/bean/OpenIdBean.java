package com.jinke.housekeeper.bean;

import java.io.Serializable;

/**
 * function: 平台验证获取openId
 * author: hank
 * date: 2017/8/1
 */

public class OpenIdBean implements Serializable {

    /**
     * openId : 1497405254263
     * phone : 13110278921
     */

    private String openId;
    private String phone;

    public String getOpenId() {
        return openId;
    }

    public void setOpenId(String openId) {
        this.openId = openId;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}

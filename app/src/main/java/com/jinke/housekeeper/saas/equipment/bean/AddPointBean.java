package com.jinke.housekeeper.saas.equipment.bean;

import java.io.Serializable;

/**
 * function:添加点位实体
 *
 * author: hank
 * date: 2017/10/9
 */

public class AddPointBean implements Serializable {

    /**
     * cardId : 046281a61
     * deviceId : 1504862238161
     * deviceName : 名称
     */

    private String cardId;
    private String deviceId;
    private String deviceName;
    private String projectId;

    public String getProjectId() {
        return projectId;
    }

    public void setProjectId(String projectId) {
        this.projectId = projectId;
    }

    public String getDeviceName() {
        return deviceName;
    }

    public void setDeviceName(String deviceName) {
        this.deviceName = deviceName;
    }

    public String getCardId() {
        return cardId;
    }

    public void setCardId(String cardId) {
        this.cardId = cardId;
    }

    public String getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }
}

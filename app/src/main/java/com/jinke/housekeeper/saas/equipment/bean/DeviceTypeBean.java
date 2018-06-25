package com.jinke.housekeeper.saas.equipment.bean;

import java.io.Serializable;
import java.util.List;

/**
 * function: 设备基本类型
 * author: hank
 * date: 2017/9/22
 */

public class DeviceTypeBean implements Serializable{

    private List<ListDataBean> listData;

    public List<ListDataBean> getListData() {
        return listData;
    }

    public void setListData(List<ListDataBean> listData) {
        this.listData = listData;
    }

    public static class ListDataBean implements Serializable {
        /**
         * name : 电梯轿箱
         * id : 1504746839822
         * type : 3
         * deviceId : 1504862238161
         * installationOcation : -1楼
         */

        private String name;
        private String id;
        private String type;
        private String deviceId;
        private String cardId;
        private String installationOcation;

        public String getCardId() {
            return cardId;
        }

        public void setCardId(String cardId) {
            this.cardId = cardId;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getDeviceId() {
            return deviceId;
        }

        public void setDeviceId(String deviceId) {
            this.deviceId = deviceId;
        }

        public String getInstallationOcation() {
            return installationOcation;
        }

        public void setInstallationOcation(String installationOcation) {
            this.installationOcation = installationOcation;
        }
    }
}

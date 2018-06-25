package com.jinke.housekeeper.saas.handoverroom.bean;

import java.io.Serializable;
import java.util.List;

/**
 * function:
 * author: hank
 * date: 2017/11/26
 */

public class HandoverRoomBean implements Serializable{


    private List<ListDataBean> listData;

    public List<ListDataBean> getListData() {
        return listData;
    }

    public void setListData(List<ListDataBean> listData) {
        this.listData = listData;
    }

    public static class ListDataBean implements Serializable {
        /**
         * projectId : 209
         * userPhone : 18983478189
         * building : 7栋
         * projectName : 金科智慧城
         * room : 7-3-1003
         * unit : 2单元
         * serialNumber : null
         * userId : 412724198009290969
         * userName : 胡庆华
         */

        private String projectId;
        private String userPhone;
        private String building;
        private String projectName;
        private String room;
        private String unit;
        private Object serialNumber;
        private String userId;
        private String userName;
        private String lineNo;

        public String getLineNo() {
            return lineNo;
        }

        public void setLineNo(String lineNo) {
            this.lineNo = lineNo;
        }

        public String getProjectId() {
            return projectId;
        }

        public void setProjectId(String projectId) {
            this.projectId = projectId;
        }

        public String getUserPhone() {
            return userPhone;
        }

        public void setUserPhone(String userPhone) {
            this.userPhone = userPhone;
        }

        public String getBuilding() {
            return building;
        }

        public void setBuilding(String building) {
            this.building = building;
        }

        public String getProjectName() {
            return projectName;
        }

        public void setProjectName(String projectName) {
            this.projectName = projectName;
        }

        public String getRoom() {
            return room;
        }

        public void setRoom(String room) {
            this.room = room;
        }

        public String getUnit() {
            return unit;
        }

        public void setUnit(String unit) {
            this.unit = unit;
        }

        public Object getSerialNumber() {
            return serialNumber;
        }

        public void setSerialNumber(Object serialNumber) {
            this.serialNumber = serialNumber;
        }

        public String getUserId() {
            return userId;
        }

        public void setUserId(String userId) {
            this.userId = userId;
        }

        public String getUserName() {
            return userName;
        }

        public void setUserName(String userName) {
            this.userName = userName;
        }
    }
}

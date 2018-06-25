package com.jinke.housekeeper.saas.handoverroom.bean;

import java.io.Serializable;
import java.util.List;

/**
 * function:
 * author: hank
 * date: 2017/12/1
 */

public class FindListDataBean implements Serializable{


    private List<DeviceListDataBean> deviceListData;
    private List<ProjectListDataBean> projectListData;

    public List<DeviceListDataBean> getDeviceListData() {
        return deviceListData;
    }

    public void setDeviceListData(List<DeviceListDataBean> deviceListData) {
        this.deviceListData = deviceListData;
    }

    public List<ProjectListDataBean> getProjectListData() {
        return projectListData;
    }

    public void setProjectListData(List<ProjectListDataBean> projectListData) {
        this.projectListData = projectListData;
    }

    public static class DeviceListDataBean implements Serializable{
        /**
         * deviceName :
         * deviceSerial : 123123
         */

        private String deviceName;
        private String deviceSerial;

        public String getDeviceName() {
            return deviceName;
        }

        public void setDeviceName(String deviceName) {
            this.deviceName = deviceName;
        }

        public String getDeviceSerial() {
            return deviceSerial;
        }

        public void setDeviceSerial(String deviceSerial) {
            this.deviceSerial = deviceSerial;
        }
    }

    public static class ProjectListDataBean implements Serializable{
        /**
         * projectName : 和园小区
         * projectId : 34
         */

        private String projectName;
        private String projectId;

        public String getProjectName() {
            return projectName;
        }

        public void setProjectName(String projectName) {
            this.projectName = projectName;
        }

        public String getProjectId() {
            return projectId;
        }

        public void setProjectId(String projectId) {
            this.projectId = projectId;
        }
    }
}

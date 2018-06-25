package com.jinke.housekeeper.saas.equipment.bean;

import java.io.Serializable;
import java.util.List;

/**
 * function: 设备详情
 * author: hank
 * date: 2017/9/22
 */

public class DeviceInfoBean implements Serializable {


    private List<ListDataBean> listData;

    public List<ListDataBean> getListData() {
        return listData;
    }

    public void setListData(List<ListDataBean> listData) {
        this.listData = listData;
    }

    public static class ListDataBean implements Serializable {
        /**
         * liabilityDept : 999
         * fileNum : 25582
         * deviceName : 电梯轿箱
         * typeId : 1504746839822
         * maintenanceStime : 2012-12
         * installUnitId : 重庆天地合
         * standard : 888
         * enableTime : 2017-12
         * deviceType : 电梯类型
         * factoryNum : 1232312
         * projectName : 10年城东区
         * liabilityPerson : 999
         * manufacturer : 生产厂商1
         * home : 123124
         * supplier : 供应商1
         * id : 1504862238161
         * installTime : 2012-12
         * installationOcation : -1楼
         * deviceSys : 电梯系统
         * contractNum :
         * contractStime : 2012-12
         * scrapTime : 2017-12-12
         * maintenanceUnitId : 维保单位1
         * serviceUnit : 维修单位1
         * factoryTime : 2012-12
         * maintenanceEtime : 2012-12
         * deviceModel : 8888
         * annualTime : 2017-09-12
         * shelfLife : 2017-09-12
         * deviceNum : tks-001
         * contractEtime : 2012-12
         */

        private String liabilityDept;
        private String fileNum;
        private String deviceName;
        private String typeId;
        private String maintenanceStime;
        private String installUnitId;
        private String standard;
        private String enableTime;
        private String deviceType;
        private String factoryNum;
        private String projectName;
        private String liabilityPerson;
        private String manufacturer;
        private String home;
        private String supplier;
        private String id;
        private String installTime;
        private String installationOcation;
        private String deviceSys;
        private String contractNum;
        private String contractStime;
        private String scrapTime;
        private String maintenanceUnitId;
        private String serviceUnit;
        private String factoryTime;
        private String maintenanceEtime;
        private String deviceModel;
        private String annualTime;
        private String shelfLife;
        private String deviceNum;
        private String contractEtime;

        public String getLiabilityDept() {
            return liabilityDept;
        }

        public void setLiabilityDept(String liabilityDept) {
            this.liabilityDept = liabilityDept;
        }

        public String getFileNum() {
            return fileNum;
        }

        public void setFileNum(String fileNum) {
            this.fileNum = fileNum;
        }

        public String getDeviceName() {
            return deviceName;
        }

        public void setDeviceName(String deviceName) {
            this.deviceName = deviceName;
        }

        public String getTypeId() {
            return typeId;
        }

        public void setTypeId(String typeId) {
            this.typeId = typeId;
        }

        public String getMaintenanceStime() {
            return maintenanceStime;
        }

        public void setMaintenanceStime(String maintenanceStime) {
            this.maintenanceStime = maintenanceStime;
        }

        public String getInstallUnitId() {
            return installUnitId;
        }

        public void setInstallUnitId(String installUnitId) {
            this.installUnitId = installUnitId;
        }

        public String getStandard() {
            return standard;
        }

        public void setStandard(String standard) {
            this.standard = standard;
        }

        public String getEnableTime() {
            return enableTime;
        }

        public void setEnableTime(String enableTime) {
            this.enableTime = enableTime;
        }

        public String getDeviceType() {
            return deviceType;
        }

        public void setDeviceType(String deviceType) {
            this.deviceType = deviceType;
        }

        public String getFactoryNum() {
            return factoryNum;
        }

        public void setFactoryNum(String factoryNum) {
            this.factoryNum = factoryNum;
        }

        public String getProjectName() {
            return projectName;
        }

        public void setProjectName(String projectName) {
            this.projectName = projectName;
        }

        public String getLiabilityPerson() {
            return liabilityPerson;
        }

        public void setLiabilityPerson(String liabilityPerson) {
            this.liabilityPerson = liabilityPerson;
        }

        public String getManufacturer() {
            return manufacturer;
        }

        public void setManufacturer(String manufacturer) {
            this.manufacturer = manufacturer;
        }

        public String getHome() {
            return home;
        }

        public void setHome(String home) {
            this.home = home;
        }

        public String getSupplier() {
            return supplier;
        }

        public void setSupplier(String supplier) {
            this.supplier = supplier;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getInstallTime() {
            return installTime;
        }

        public void setInstallTime(String installTime) {
            this.installTime = installTime;
        }

        public String getInstallationOcation() {
            return installationOcation;
        }

        public void setInstallationOcation(String installationOcation) {
            this.installationOcation = installationOcation;
        }

        public String getDeviceSys() {
            return deviceSys;
        }

        public void setDeviceSys(String deviceSys) {
            this.deviceSys = deviceSys;
        }

        public String getContractNum() {
            return contractNum;
        }

        public void setContractNum(String contractNum) {
            this.contractNum = contractNum;
        }

        public String getContractStime() {
            return contractStime;
        }

        public void setContractStime(String contractStime) {
            this.contractStime = contractStime;
        }

        public String getScrapTime() {
            return scrapTime;
        }

        public void setScrapTime(String scrapTime) {
            this.scrapTime = scrapTime;
        }

        public String getMaintenanceUnitId() {
            return maintenanceUnitId;
        }

        public void setMaintenanceUnitId(String maintenanceUnitId) {
            this.maintenanceUnitId = maintenanceUnitId;
        }

        public String getServiceUnit() {
            return serviceUnit;
        }

        public void setServiceUnit(String serviceUnit) {
            this.serviceUnit = serviceUnit;
        }

        public String getFactoryTime() {
            return factoryTime;
        }

        public void setFactoryTime(String factoryTime) {
            this.factoryTime = factoryTime;
        }

        public String getMaintenanceEtime() {
            return maintenanceEtime;
        }

        public void setMaintenanceEtime(String maintenanceEtime) {
            this.maintenanceEtime = maintenanceEtime;
        }

        public String getDeviceModel() {
            return deviceModel;
        }

        public void setDeviceModel(String deviceModel) {
            this.deviceModel = deviceModel;
        }

        public String getAnnualTime() {
            return annualTime;
        }

        public void setAnnualTime(String annualTime) {
            this.annualTime = annualTime;
        }

        public String getShelfLife() {
            return shelfLife;
        }

        public void setShelfLife(String shelfLife) {
            this.shelfLife = shelfLife;
        }

        public String getDeviceNum() {
            return deviceNum;
        }

        public void setDeviceNum(String deviceNum) {
            this.deviceNum = deviceNum;
        }

        public String getContractEtime() {
            return contractEtime;
        }

        public void setContractEtime(String contractEtime) {
            this.contractEtime = contractEtime;
        }
    }
}

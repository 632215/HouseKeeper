package com.jinke.housekeeper.saas.equipment.bean;

import java.io.Serializable;
import java.util.List;

/**
 * function:当前部门下的任务实体
 * author: hank
 * date: 2017/10/11
 */

public class TaskBean implements Serializable {

    /**
     * id : 12
     * startTime : 2017-09-19
     * pointList : [{"pointName":"未来城点位1","id":"1505371951585","status":"","installationOcation":"未","cardNum":"88888","deviceName":"电梯轿箱","deviceId":"1505371951584","deviceNum":"tks-0012123","typeId":"123123"}]
     * endTime : 2017-09-20
     * taskStatus : 222
     * taskOrder:jdjfjksf_1,
     */

    public static final String TASK_NO_TSTARTED = "1";
    public static final String TASK_TO_PERFORM = "2";
    public static final String TASK_COMPLETED = "3";
    public static final String TASK_TIMEOUT = "4";
    public static final String TASK_DELETED = "5";

    private String id;
    private String startTime;
    private String endTime;
    private String completeTime;
    private String taskStatus;//1未开始，2待执行，3已完成，4已超时，5已删除
    private String taskOrder;

    private List<PointListBean> pointList;

    public String getTaskOrder() {
        return taskOrder;
    }

    public void setTaskOrder(String taskOrder) {
        this.taskOrder = taskOrder;
    }

    public String getCompleteTime() {
        return completeTime;
    }

    public void setCompleteTime(String completeTime) {
        this.completeTime = completeTime;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public String getTaskStatus() {
        return taskStatus;
    }

    public void setTaskStatus(String taskStatus) {
        this.taskStatus = taskStatus;
    }

    public List<PointListBean> getPointList() {
        return pointList;
    }

    public void setPointList(List<PointListBean> pointList) {
        this.pointList = pointList;
    }

    public static class PointListBean implements Serializable {
        /**
         * pointName : 未来城点位1
         * id : 1505371951585
         * status : null 未完成 1已完成 2 完成未上传
         * installationOcation : 未
         * cardNum : 88888
         * deviceName : 电梯轿箱
         * deviceId : 1505371951584
         * deviceNum : tks-0012123
         * typeId : 123123
         */
        public static final String TASK_COMPLETED = "1";
        public static final String TASK_NO_UPDATE = "2";

        private String pointName;
        private String id;
        private String status;
        private String installationOcation;
        private String cardNum;
        private String deviceName;
        private String deviceId;
        private String deviceNum;
        private String typeId;
        private String inspecTime;//点位巡检时间
        private String result;//结果1，正常,2异常
        private String remark;//备注

        public String getResult() {
            return result;
        }

        public void setResult(String result) {
            this.result = result;
        }

        public String getRemark() {
            return remark;
        }

        public void setRemark(String remark) {
            this.remark = remark;
        }

        public String getInspecTime() {
            return inspecTime;
        }

        public void setInspecTime(String inspecTime) {
            this.inspecTime = inspecTime;
        }

        public String getPointName() {
            return pointName;
        }

        public void setPointName(String pointName) {
            this.pointName = pointName;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public String getInstallationOcation() {
            return installationOcation;
        }

        public void setInstallationOcation(String installationOcation) {
            this.installationOcation = installationOcation;
        }

        public String getCardNum() {
            return cardNum;
        }

        public void setCardNum(String cardNum) {
            this.cardNum = cardNum;
        }

        public String getDeviceName() {
            return deviceName;
        }

        public void setDeviceName(String deviceName) {
            this.deviceName = deviceName;
        }

        public String getDeviceId() {
            return deviceId;
        }

        public void setDeviceId(String deviceId) {
            this.deviceId = deviceId;
        }

        public String getDeviceNum() {
            return deviceNum;
        }

        public void setDeviceNum(String deviceNum) {
            this.deviceNum = deviceNum;
        }

        public String getTypeId() {
            return typeId;
        }

        public void setTypeId(String typeId) {
            this.typeId = typeId;
        }
    }
}

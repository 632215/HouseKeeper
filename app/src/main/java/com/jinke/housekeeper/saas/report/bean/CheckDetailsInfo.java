package com.jinke.housekeeper.saas.report.bean;

import java.util.List;

/**
 * Created by root on 8/01/17.
 */

public class CheckDetailsInfo {


    /**
     * info : {"id":"1484019079357","orgName":"无锡观庭","orgId":"6957","sceneName":"安全管理","areaName":"关键点","superviseName":"唐君左","superviseDate":"2017-01-10","superviseTime":"18:59:40","superviseDescribe":"加班加班加班","supervisePotoadd":"/process/2017-01-10/1484019079357.jpg","supervisePotoadds":"/process/2017-01-10/1484019079357_.jpg","staffName":"唐君左","staffDate":"2017-01-10","staffTime":"19:01:10","staffDescribe":"解决了","staffPotoadd":"/process/2017-01-10/227960.jpg","staffPotoadds":"/process/2017-01-10/227960_.jpg","openStatus":"Y","openTime":"2017-01-10 19:00:50","isHangup":"","hangTime":"","hangMsg":"","delegateName":"","delegateTime":"","delegateMsg":"","seriousName":"","seriousMsg":"","seriousTime":"","nodeInfos":[{"processName":"发现问题","userName":"唐君左","dateTime":"2017-01-10 18:59:40","result":"","opinion":""},{"processName":"处理问题","userName":"唐君左","dateTime":"2017-01-10 19:01:10","result":"","opinion":""},{"processName":"监管审核","userName":"","dateTime":"","result":"","opinion":""}]}
     */

    private InfoBean info;

    public InfoBean getInfo() {
        return info;
    }

    public void setInfo(InfoBean info) {
        this.info = info;
    }

    public static class InfoBean {

        private String taskId;
        private String id;
        private String orgName;
        private String orgId;
        private String sceneName;
        private String areaName;
        private String superviseName;
        private String superviseDate;
        private String superviseTime;
        private String superviseDescribe;
        private String supervisePotoadd;
        private String supervisePotoadds;
        private String staffName;
        private String staffDate;
        private String staffTime;
        private String staffDescribe;
        private String staffPotoadd;
        private String staffPotoadds;
        private String openStatus;
        private String openTime;
        private String isHangup;
        private String hangTime;
        private String hangMsg;
        private String delegateName;
        private String delegateTime;
        private String delegateMsg;
        private String seriousName;
        private String seriousMsg;
        private String seriousTime;
        private List<NodeInfosBean> nodeInfos;

        public String getTaskId() {
            return taskId;
        }

        public void setTaskId(String taskId) {
            this.taskId = taskId;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getOrgName() {
            return orgName;
        }

        public void setOrgName(String orgName) {
            this.orgName = orgName;
        }

        public String getOrgId() {
            return orgId;
        }

        public void setOrgId(String orgId) {
            this.orgId = orgId;
        }

        public String getSceneName() {
            return sceneName;
        }

        public void setSceneName(String sceneName) {
            this.sceneName = sceneName;
        }

        public String getAreaName() {
            return areaName;
        }

        public void setAreaName(String areaName) {
            this.areaName = areaName;
        }

        public String getSuperviseName() {
            return superviseName;
        }

        public void setSuperviseName(String superviseName) {
            this.superviseName = superviseName;
        }

        public String getSuperviseDate() {
            return superviseDate;
        }

        public void setSuperviseDate(String superviseDate) {
            this.superviseDate = superviseDate;
        }

        public String getSuperviseTime() {
            return superviseTime;
        }

        public void setSuperviseTime(String superviseTime) {
            this.superviseTime = superviseTime;
        }

        public String getSuperviseDescribe() {
            return superviseDescribe;
        }

        public void setSuperviseDescribe(String superviseDescribe) {
            this.superviseDescribe = superviseDescribe;
        }

        public String getSupervisePotoadd() {
            return supervisePotoadd;
        }

        public void setSupervisePotoadd(String supervisePotoadd) {
            this.supervisePotoadd = supervisePotoadd;
        }

        public String getSupervisePotoadds() {
            return supervisePotoadds;
        }

        public void setSupervisePotoadds(String supervisePotoadds) {
            this.supervisePotoadds = supervisePotoadds;
        }

        public String getStaffName() {
            return staffName;
        }

        public void setStaffName(String staffName) {
            this.staffName = staffName;
        }

        public String getStaffDate() {
            return staffDate;
        }

        public void setStaffDate(String staffDate) {
            this.staffDate = staffDate;
        }

        public String getStaffTime() {
            return staffTime;
        }

        public void setStaffTime(String staffTime) {
            this.staffTime = staffTime;
        }

        public String getStaffDescribe() {
            return staffDescribe;
        }

        public void setStaffDescribe(String staffDescribe) {
            this.staffDescribe = staffDescribe;
        }

        public String getStaffPotoadd() {
            return staffPotoadd;
        }

        public void setStaffPotoadd(String staffPotoadd) {
            this.staffPotoadd = staffPotoadd;
        }

        public String getStaffPotoadds() {
            return staffPotoadds;
        }

        public void setStaffPotoadds(String staffPotoadds) {
            this.staffPotoadds = staffPotoadds;
        }

        public String getOpenStatus() {
            return openStatus;
        }

        public void setOpenStatus(String openStatus) {
            this.openStatus = openStatus;
        }

        public String getOpenTime() {
            return openTime;
        }

        public void setOpenTime(String openTime) {
            this.openTime = openTime;
        }

        public String getIsHangup() {
            return isHangup;
        }

        public void setIsHangup(String isHangup) {
            this.isHangup = isHangup;
        }

        public String getHangTime() {
            return hangTime;
        }

        public void setHangTime(String hangTime) {
            this.hangTime = hangTime;
        }

        public String getHangMsg() {
            return hangMsg;
        }

        public void setHangMsg(String hangMsg) {
            this.hangMsg = hangMsg;
        }

        public String getDelegateName() {
            return delegateName;
        }

        public void setDelegateName(String delegateName) {
            this.delegateName = delegateName;
        }

        public String getDelegateTime() {
            return delegateTime;
        }

        public void setDelegateTime(String delegateTime) {
            this.delegateTime = delegateTime;
        }

        public String getDelegateMsg() {
            return delegateMsg;
        }

        public void setDelegateMsg(String delegateMsg) {
            this.delegateMsg = delegateMsg;
        }

        public String getSeriousName() {
            return seriousName;
        }

        public void setSeriousName(String seriousName) {
            this.seriousName = seriousName;
        }

        public String getSeriousMsg() {
            return seriousMsg;
        }

        public void setSeriousMsg(String seriousMsg) {
            this.seriousMsg = seriousMsg;
        }

        public String getSeriousTime() {
            return seriousTime;
        }

        public void setSeriousTime(String seriousTime) {
            this.seriousTime = seriousTime;
        }

        public List<NodeInfosBean> getNodeInfos() {
            return nodeInfos;
        }

        public void setNodeInfos(List<NodeInfosBean> nodeInfos) {
            this.nodeInfos = nodeInfos;
        }

        public static class NodeInfosBean {
            /**
             * processName : 发现问题
             * userName : 唐君左
             * dateTime : 2017-01-10 18:59:40
             * result :
             * opinion :
             */

            private String processName;
            private String userName;
            private String dateTime;
            private String result;
            private String opinion;

            public String getProcessName() {
                return processName;
            }

            public void setProcessName(String processName) {
                this.processName = processName;
            }

            public String getUserName() {
                return userName;
            }

            public void setUserName(String userName) {
                this.userName = userName;
            }

            public String getDateTime() {
                return dateTime;
            }

            public void setDateTime(String dateTime) {
                this.dateTime = dateTime;
            }

            public String getResult() {
                return result;
            }

            public void setResult(String result) {
                this.result = result;
            }

            public String getOpinion() {
                return opinion;
            }

            public void setOpinion(String opinion) {
                this.opinion = opinion;
            }
        }
    }
}
